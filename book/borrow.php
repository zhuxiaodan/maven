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

<html>
<body>
<?
// �ύǰ
if ($show=="" and $lend==""){
?>
<form name="form1" method="post" action="<?php echo $PHP_SELF ?>">
  <table width="60%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2">�� �� �� ��</th>
    </tr>
    <tr> 
      <td width="30%" height="32" align="right">ͼ���ţ�</td>
      <td width="70%" height="32"> 
        <input type="text" name="book_id" size="10">
        <input type="submit" name="show" value="��ʾ������Ϣ">
      </td>
    </tr>    
  </table>
</form>
<?
}
// �ύ����
else {
	// ֻ����ʾͼ����ϸ��Ϣ
	if ($show){
		// ���ͼ����û��д����ʾ�û�
		if ($book_id==""){
			echo "<div align=center><font color=red>ͼ����û����д��</font></div>";
			exit();
		}
		else {
			$booksql="select * from book where id='$book_id'";
			$bookresult=mysql_query($booksql,$conn);
			$bookinfo=mysql_fetch_array($bookresult);
			// �������û���Ȿ��
			if (empty($bookinfo)){
				echo "<div align=center><font color=red>�����ڸ�ͼ����</font></div>";
				exit();
			}
			else {
				// ��������Ѿ�ȫ���������ʾ�û�
				if ($bookinfo[leave_number]=="0"){
					echo "<div align=center><font color=red>��ͼ����ȫ�������</font></div>";
				}
				// ��ʾ������ϸ��Ϣ
				?>
<form name="form1" method="post" action="<?php echo $PHP_SELF ?>">
  <table width="60%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2">�� �� �� ��</th>
    </tr>
    <tr> 
      <td width="30%" height="32" align="right">ͼ���ţ�</td>
      <td width="70%" height="32"><?echo $book_id?>
	  <? // ����ͼ��ID��Ϣ					   	?>
	  <input type="hidden" name="book_id" value="<? echo $bookinfo[id];?>">
	  <input type="hidden" name="title" value="<? echo $bookinfo[title];?>">
	  <input type="hidden" name="leave" value="<? echo $bookinfo[leave_number];?>">
      </td>
    </tr>
    <tr> 
      <td width="30%" align="right">������</td>
      <td width="70%"><? echo $bookinfo[title];?></td>
    </tr>
    <tr> 
      <td width="30%" align="right">���ߣ�</td>
      <td width="70%"><? echo $bookinfo[author];?></td>
    </tr>
    <tr> 
      <td width="30%" align="right">�����磺</td>
      <td width="70%"><? echo $bookinfo[publisher];?></td>
    </tr>
    <tr> 
      <td width="30%" align="right">������ݣ�</td>
      <td width="70%"><? echo $bookinfo[publish_year];?></td>
    </tr>
    <tr> 
      <td height="23" align="right">�ܹ���<? echo $bookinfo[total];?>����</td>
      <td height="23">���ʣ�ࣺ<? echo $bookinfo[leave_number];?>��</td>
    </tr>
    <tr> 
      <td width="30%" align="right">�����û�ID:</td>
      <td width="70%"> 
        <input type="text" name="user_id" size="10">
      </td>
    </tr>
    <tr> 
      <td width="30%" align="right"> 
        <input type="submit" name="lend" value="���">
      </td>
      <td width="70%"> 
        <input type="reset" name="Submit2" value="����">
      </td>
    </tr>
  </table>
</form>

<?
			}
		}
	}
	// ����
	if ($lend){
		// �鿴�û�ID�Ƿ�����
		if ($user_id==""){
			echo "<div align=center><font color=red>�û�IDû����д��</font></div>";
			exit();
		}
		// �����������飬��¼֮
		// ��õ�ǰ����
		$now = date("Y-m-d");
		$lendsql="insert into lend(book_id, book_title, lend_time, user_id) values('$book_id','$title','$now','$user_id')";
		mysql_query($lendsql,$conn) or die ("����ʧ�ܣ�".mysql_error());
		// ����Ҫ��log�м�¼
		$logsql="insert into lend_log(book_id,user_id,lend_time) values('$book_id','$user_id','$now')";
		mysql_query($logsql,$conn) or die ("��¼ʧ�ܣ�".mysql_error());
		// �������Ҫ�ڸ����¼�п��ʣ������һ
		$leave_num=$leave-1;
		mysql_query("update book set leave_number='$leave_num' where id='$book_id'",$conn);
		?>
		<p align="center">&nbsp;</p>
		<p align="center">&nbsp;</p>
		<p align="center"><font color="red">���ĵǼ���ɣ�</p>
		<p align="center"><a href="<?php echo $PHP_SELF ?>">�������</a></p>
<?
	}
}

?>
</body>
</html>