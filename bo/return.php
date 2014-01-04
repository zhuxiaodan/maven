<?
//初始化session
session_start();
include ('head.php');
require ('dbconnect.php');
// 如果没有登录，退出
if(!isset($_SESSION['Adm'])) {
   echo "<p align=center>";
   echo "<font color=#FF0000 size=5><strong><big>";
   echo "管理员没有登录,请<a href='AdminLogin.php'>登录</a>!";
   echo "</big></strong></font></p>";
   exit();
}

?>
<script language="javascript"> 
	function checkall(form){
		//alert(form.selectall.value);
		for (var i=0;i<form.elements.length;i++){
			//为了使用方便将form.elements[i]赋值繪变量e
			var e=form.elements[i];
			//判断对象类型是否为复选框
			if (e.type=="checkbox"){
				e.checked=true;		
			}
			// 全选则使"选中归还或续借的文献radio"不选上
			if (e.name=="selecttype"){
				e.checked=false;
			}
		}
	}
</script>
<html>
<body>
<?
// 提交前
if ($show==""){
?>
<form name="form1" method="post" action="<?php echo $PHP_SELF ?>">
  <table width="60%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2">还 书 和 续 借 登 记</th>
    </tr>
    <tr> 
      <td width="30%" height="32" align="right">用户编号：</td>
      <td width="70%" height="32"> 
        <input type="text" name="user_id" size="10">
        <input type="submit" name="show" value="显示该用户借书信息">
      </td>
    </tr>    
  </table>
</form>
<?
}
// 提交后处理
else {
	// 显示用户借书信息
	if ($show){
		// 如果用户编号没填写，提示用户
		if ($user_id==""){
			echo "<div align=center><font color=red>用户编号没有填写！</font></div>";
			exit();
		}
		else {
			$sql="select * from lend where user_id='$user_id'";
			$result=mysql_query($sql,$conn);
			$num=mysql_num_rows($result);
	
			// 如果用户没有借书，提示用户
			if ($num==0){
				echo "<p align=center>您的借书数量为<font color=red>0</font>！</p>";
				exit();
			}

			echo "<p align=center>您的借书数量为<font color=red>$num</font>！已借书目如下：</p>";
			echo "<p align='center'>&nbsp;</p>";
			// 设置form
			echo "<form name='form2' method='post' action='returnok.php'>";
			echo "<INPUT TYPE=radio NAME=selecttype VALUE='selected' CHECKED>";
			echo "选中要归还或续借的文献";
			// 如果是全选，则执行javascript 脚本选择所有的checkbox
			echo "<INPUT TYPE=radio NAME=selectall VALUE='1' onclick='checkall(this.form)'>";
			echo "全部选中<p>";
			// 隐含变量传递参数
			echo "<input type=hidden name=book_id value='$book_id'>";
			echo "<input type=hidden name=user_id value='$user_id'>";
			echo "<table border=1 width='100%' align=center>";
			echo "<th >&nbsp;</th>";
			echo "<th >书号</th>";
			echo "<th >书名</th>";
			echo "<th >作者</th>";
			echo "<th >出版社</th>";
			echo "<th >年份</th>";
			echo "<th >借阅时间</th>";
			while($row=mysql_fetch_array($result)){
				// 获得该书详细信息
				$bsql="select * from book where id='$row[book_id]'";
				$bresult=mysql_query($bsql,$conn);
				$binfo=mysql_fetch_array($bresult);
				// 一组checkbox，它们的值分别为各书的id
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
				<input type="submit" name="return" value="还书">
				</td><td align=center>
		        <input type="submit" name="renew" value="续借">
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