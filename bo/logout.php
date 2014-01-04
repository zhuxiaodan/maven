<?
session_start();

//注销session
session_unset();
session_destroy();
include('head.php');
?>
<html>
<head>
<title>注销登录</title>
</head>
<body>
<p align="center">你已经成功注销登录！</p>
<p align="center">欢迎您的再次登录管理！</p>
<p align="center"><a href="login.php">重新登录</a></p>
</body>
</html>
