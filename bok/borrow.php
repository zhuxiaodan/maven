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

<html>
<body>
<?
// 提交前
if ($show=="" and $lend==""){
?>
<form name="form1" method="post" action="<?php echo $PHP_SELF ?>">
  <table width="60%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2">借 书 登 记</th>
    </tr>
    <tr> 
      <td width="30%" height="32" align="right">图书编号：</td>
      <td width="70%" height="32"> 
        <input type="text" name="book_id" size="10">
        <input type="submit" name="show" value="显示该书信息">
      </td>
    </tr>    
  </table>
</form>
<?
}
// 提交后处理
else {
	// 只是显示图书详细信息
	if ($show){
		// 如果图书编号没填写，提示用户
		if ($book_id==""){
			echo "<div align=center><font color=red>图书编号没有填写！</font></div>";
			exit();
		}
		else {
			$booksql="select * from book where id='$book_id'";
			$bookresult=mysql_query($booksql,$conn);
			$bookinfo=mysql_fetch_array($bookresult);
			// 编号有误，没有这本书
			if (empty($bookinfo)){
				echo "<div align=center><font color=red>不存在该图书编号</font></div>";
				exit();
			}
			else {
				// 如果该书已经全部借出，提示用户
				if ($bookinfo[leave_number]=="0"){
					echo "<div align=center><font color=red>该图书已全部借出！</font></div>";
				}
				// 显示该书详细信息
				?>
<form name="form1" method="post" action="<?php echo $PHP_SELF ?>">
  <table width="60%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2">借 书 登 记</th>
    </tr>
    <tr> 
      <td width="30%" height="32" align="right">图书编号：</td>
      <td width="70%" height="32"><?echo $book_id?>
	  <? // 传递图书ID信息					   	?>
	  <input type="hidden" name="book_id" value="<? echo $bookinfo[id];?>">
	  <input type="hidden" name="title" value="<? echo $bookinfo[title];?>">
	  <input type="hidden" name="leave" value="<? echo $bookinfo[leave_number];?>">
      </td>
    </tr>
    <tr> 
      <td width="30%" align="right">书名：</td>
      <td width="70%"><? echo $bookinfo[title];?></td>
    </tr>
    <tr> 
      <td width="30%" align="right">作者：</td>
      <td width="70%"><? echo $bookinfo[author];?></td>
    </tr>
    <tr> 
      <td width="30%" align="right">出版社：</td>
      <td width="70%"><? echo $bookinfo[publisher];?></td>
    </tr>
    <tr> 
      <td width="30%" align="right">出版年份：</td>
      <td width="70%"><? echo $bookinfo[publish_year];?></td>
    </tr>
    <tr> 
      <td height="23" align="right">总共：<? echo $bookinfo[total];?>本；</td>
      <td height="23">库存剩余：<? echo $bookinfo[leave_number];?>本</td>
    </tr>
    <tr> 
      <td width="30%" align="right">借阅用户ID:</td>
      <td width="70%"> 
        <input type="text" name="user_id" size="10">
      </td>
    </tr>
    <tr> 
      <td width="30%" align="right"> 
        <input type="submit" name="lend" value="借出">
      </td>
      <td width="70%"> 
        <input type="reset" name="Submit2" value="重置">
      </td>
    </tr>
  </table>
</form>

<?
			}
		}
	}
	// 借书
	if ($lend){
		// 查看用户ID是否已填
		if ($user_id==""){
			echo "<div align=center><font color=red>用户ID没有填写！</font></div>";
			exit();
		}
		// 可以正常借书，记录之
		// 获得当前日期
		$now = date("Y-m-d");
		$lendsql="insert into lend(book_id, book_title, lend_time, user_id) values('$book_id','$title','$now','$user_id')";
		mysql_query($lendsql,$conn) or die ("操作失败：".mysql_error());
		// 还需要在log中记录
		$logsql="insert into lend_log(book_id,user_id,lend_time) values('$book_id','$user_id','$now')";
		mysql_query($logsql,$conn) or die ("记录失败：".mysql_error());
		// 借出后需要在该书记录中库存剩余数减一
		$leave_num=$leave-1;
		mysql_query("update book set leave_number='$leave_num' where id='$book_id'",$conn);
		?>
		<p align="center">&nbsp;</p>
		<p align="center">&nbsp;</p>
		<p align="center"><font color="red">借阅登记完成！</p>
		<p align="center"><a href="<?php echo $PHP_SELF ?>">继续添加</a></p>
<?
	}
}

?>
</body>
</html>