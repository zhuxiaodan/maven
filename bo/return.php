<?
//��ʼ��session
session_start();
include ('head.php');
require ('dbconnect.php');
// ���û�е�¼���˳�
if(!isset($_SESSION['Adm'])) {
   echo "<p align=center>";
   echo "<font color=#FF0000 size=5><strong><big>";
   echo "����Աû�е�¼,��<a href='AdminLogin.php'>��¼</a>!";
   echo "</big></strong></font></p>";
   exit();
}

?>
<script language="javascript"> 
	function checkall(form){
		//alert(form.selectall.value);
		for (var i=0;i<form.elements.length;i++){
			//Ϊ��ʹ�÷��㽫form.elements[i]��ֵ�L����e
			var e=form.elements[i];
			//�ж϶��������Ƿ�Ϊ��ѡ��
			if (e.type=="checkbox"){
				e.checked=true;		
			}
			// ȫѡ��ʹ"ѡ�й黹�����������radio"��ѡ��
			if (e.name=="selecttype"){
				e.checked=false;
			}
		}
	}
</script>
<html>
<body>
<?
// �ύǰ
if ($show==""){
?>
<form name="form1" method="post" action="<?php echo $PHP_SELF ?>">
  <table width="60%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2">�� �� �� �� �� �� ��</th>
    </tr>
    <tr> 
      <td width="30%" height="32" align="right">�û���ţ�</td>
      <td width="70%" height="32"> 
        <input type="text" name="user_id" size="10">
        <input type="submit" name="show" value="��ʾ���û�������Ϣ">
      </td>
    </tr>    
  </table>
</form>
<?
}
// �ύ����
else {
	// ��ʾ�û�������Ϣ
	if ($show){
		// ����û����û��д����ʾ�û�
		if ($user_id==""){
			echo "<div align=center><font color=red>�û����û����д��</font></div>";
			exit();
		}
		else {
			$sql="select * from lend where user_id='$user_id'";
			$result=mysql_query($sql,$conn);
			$num=mysql_num_rows($result);
	
			// ����û�û�н��飬��ʾ�û�
			if ($num==0){
				echo "<p align=center>���Ľ�������Ϊ<font color=red>0</font>��</p>";
				exit();
			}

			echo "<p align=center>���Ľ�������Ϊ<font color=red>$num</font>���ѽ���Ŀ���£�</p>";
			echo "<p align='center'>&nbsp;</p>";
			// ����form
			echo "<form name='form2' method='post' action='returnok.php'>";
			echo "<INPUT TYPE=radio NAME=selecttype VALUE='selected' CHECKED>";
			echo "ѡ��Ҫ�黹�����������";
			// �����ȫѡ����ִ��javascript �ű�ѡ�����е�checkbox
			echo "<INPUT TYPE=radio NAME=selectall VALUE='1' onclick='checkall(this.form)'>";
			echo "ȫ��ѡ��<p>";
			// �����������ݲ���
			echo "<input type=hidden name=book_id value='$book_id'>";
			echo "<input type=hidden name=user_id value='$user_id'>";
			echo "<table border=1 width='100%' align=center>";
			echo "<th >&nbsp;</th>";
			echo "<th >���</th>";
			echo "<th >����</th>";
			echo "<th >����</th>";
			echo "<th >������</th>";
			echo "<th >���</th>";
			echo "<th >����ʱ��</th>";
			while($row=mysql_fetch_array($result)){
				// ��ø�����ϸ��Ϣ
				$bsql="select * from book where id='$row[book_id]'";
				$bresult=mysql_query($bsql,$conn);
				$binfo=mysql_fetch_array($bresult);
				// һ��checkbox�����ǵ�ֵ�ֱ�Ϊ�����id
				echo "<tr><td><input type='checkbox' name='bookbox' value='$row[book_id]'></td>";
				echo "<td>$row[book_id]</td>";
				echo "<td>$binfo[title]</td>";
				echo "<td>$binfo[author]</td>";
				echo "<td>$binfo[publisher]</td>";
				echo "<td>$binfo[publish_year]</td>";
				echo "<td>$row[lend_time]</td>";
				echo "</tr>";
			}
			?>
			</table>
			<p align="center">&nbsp;</p>
			<table width='50%' align=center><tr><td align=center>
				<input type="submit" name="return" value="����">
				</td><td align=center>
		        <input type="submit" name="renew" value="����">
				</td></tr>
			</table>
			</form>
			<?
		}
	}
}

?>
</body>
</html>