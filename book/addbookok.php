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

<?
// �����һҳ���ݲ���
$title=$_POST['title'];
$author=$_POST['author'];
$publisher=$_POST['publisher'];
$publish_year=$_POST['publish_year'];
$total=$_POST['total'];
$other=$_POST['other'];

// ����Ƿ��Ѿ����ڸ���
// ��Ҫͬʱ����������������ߣ������磬���
$checksql="select * from book where title='$title' and publisher='$publisher' and author='$author' and publish_year='$publish_year'";
$checkresult=mysql_query($checksql,$conn);
$checkrow=mysql_fetch_array($checkresult);
if(!empty($checkrow)){
	// �����Ѿ���⣬�˳�����
	echo "<table width='100%' align=center><tr><td align=center>";
	echo "�����Ѿ���⣬��������<br>";
    echo "<font color=red>����ʧ�ܣ�</font><br><a href='addbook.php'>������</a>";
    echo "</td></tr></table>";
	exit();
}
// ����˳�����
$sql="insert into book(title, author, publisher, publish_year, total, leave_number,other) values('$title','$author','$publisher','$publish_year','$total','$total','$other')";
mysql_query($sql,$conn) or die("����ʧ�ܣ�".mysql_error());

// ���ע���û����Զ�id���Ժ�ʹ�ô�id�ſɵ�¼
$result=mysql_query("select last_insert_id()",$conn);
$re_arr=mysql_fetch_array($result);
$id=$re_arr[0];
echo "<table align=center><tr><td align=center>�������ɹ���</td></tr>";
echo "<tr><td align=center><font color=red>�����ID�ǣ�".$id."</font>";
echo "<br><a href='addbook.php'>�����������</a></td></tr></table>";

?>