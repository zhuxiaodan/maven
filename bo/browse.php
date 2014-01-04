<?
//初始化session
session_start();
include ('head.php');
require ('dbconnect.php');
// 如果没有登录过，提示用户登录
if(!isset($_SESSION['user'])) {
   echo "<p align=center>";
   echo "<font color=#FF0000 size=5><strong><big>";
   echo "您还没有登录,请<a href='login.php'>登录</a>!";
   echo "</big></strong></font></p>";
   exit();
}

?>
<?
$user_id=$_SESSION['user'];
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
echo "<table border=1 width='80%' align=center>";
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
	echo "<tr><td>$row[book_id]</td>";
	echo "<td>$binfo[title]</td>";
	echo "<td>$binfo[author]</td>";
	echo "<td>$binfo[publisher]</td>";
	echo "<td>$binfo[publish_year]</td>";
	echo "<td>$row[lend_time]</td>";
	echo "</tr>";
}
echo "</table>";
?>