<?
//��ʼ��session
session_start();
// ����Ѿ���¼����ֱ���˳�
if(isset($_SESSION['user'])) {
	//�ض��򵽹�������
	header("Location:browse.php");
	// ��¼���Ļ�����������
   exit;
}
require ('dbconnect.php');
// ��ò���
$nickname=$_POST['username'];
$password=$_POST['password'];
$password=md5($password);

// ����ʺź������Ƿ���ȷ,
$sql="select * from user where id='$nickname' and password='$password'";
$re=mysql_query($sql,$conn);
$result=mysql_fetch_array($re);
// ����û���¼��ȷ
if( !empty($result)) {
	//ע��session���������浱ǰ�Ự�û����ǳ�
	session_register("user");
	$user=$nickname;

	// ��¼�ɹ��ض��򵽹���ҳ��
	header("Location:browse.php");
}
else { 
	// ����ͷ�ļ�
	include('head.php');
    // ����Ա��¼ʧ��
	echo "<table width='100%' align=center><tr><td align=center>";
	echo "�û�ID���������<br>";
    echo "<font color=red>��¼ʧ�ܣ�</font><br><a href='login.php'>������</a>";
    echo "</td></tr></table>";
}
?>

