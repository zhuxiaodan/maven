<?
//初始化session
session_start();
// 如果已经登录过，直接退出
if(isset($_SESSION['user'])) {
	//重定向到管理留言
	header("Location:browse.php");
	// 登录过的话，立即结束
   exit;
}
require ('dbconnect.php');
// 获得参数
$nickname=$_POST['username'];
$password=$_POST['password'];
$password=md5($password);

// 检查帐号和密码是否正确,
$sql="select * from user where id='$nickname' and password='$password'";
$re=mysql_query($sql,$conn);
$result=mysql_fetch_array($re);
// 如果用户登录正确
if( !empty($result)) {
	//注册session变量，保存当前会话用户的昵称
	session_register("user");
	$user=$nickname;

	// 登录成功重定向到管理页面
	header("Location:browse.php");
}
else { 
	// 包含头文件
	include('head.php');
    // 管理员登录失败
	echo "<table width='100%' align=center><tr><td align=center>";
	echo "用户ID或密码错误<br>";
    echo "<font color=red>登录失败！</font><br><a href='login.php'>请重试</a>";
    echo "</td></tr></table>";
}
?>

