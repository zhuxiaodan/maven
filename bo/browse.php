<?
//��ʼ��session
session_start();
include ('head.php');
require ('dbconnect.php');
// ���û�е�¼������ʾ�û���¼
if(!isset($_SESSION['user'])) {
   echo "<p align=center>";
   echo "<font color=#FF0000 size=5><strong><big>";
   echo "����û�е�¼,��<a href='login.php'>��¼</a>!";
   echo "</big></strong></font></p>";
   exit();
}

?>
<?
$user_id=$_SESSION['user'];
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
echo "<table border=1 width='80%' align=center>";
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