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

<?
// 获得上一页传递参数
$title=$_POST['title'];
$author=$_POST['author'];
$publisher=$_POST['publisher'];
$publish_year=$_POST['publish_year'];
$total=$_POST['total'];
$other=$_POST['other'];

// 检测是否已经存在该书
// 需要同时检测该书的书名，作者，出版社，年份
$checksql="select * from book where title='$title' and publisher='$publisher' and author='$author' and publish_year='$publish_year'";
$checkresult=mysql_query($checksql,$conn);
$checkrow=mysql_fetch_array($checkresult);
if(!empty($checkrow)){
	// 该书已经入库，退出操作
	echo "<table width='100%' align=center><tr><td align=center>";
	echo "该书已经入库，无需再入<br>";
    echo "<font color=red>操作失败！</font><br><a href='addbook.php'>请重试</a>";
    echo "</td></tr></table>";
	exit();
}
// 可以顺利入库
$sql="insert into book(title, author, publisher, publish_year, total, leave_number,other) values('$title','$author','$publisher','$publish_year','$total','$total','$other')";
mysql_query($sql,$conn) or die("操作失败：".mysql_error());

// 获得注册用户的自动id，以后使用此id才可登录
$result=mysql_query("select last_insert_id()",$conn);
$re_arr=mysql_fetch_array($result);
$id=$re_arr[0];
echo "<table align=center><tr><td align=center>新书入库成功！</td></tr>";
echo "<tr><td align=center><font color=red>该书的ID是：".$id."</font>";
echo "<br><a href='addbook.php'>继续添加新书</a></td></tr></table>";

?>