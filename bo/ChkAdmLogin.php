<?
//��ʼ��session
session_start();
// $_SESSION['UserName'] ������$UserName��������
if(isset($_SESSION['Adm'])) {
	//�ض��򵽹�������
	header("Location:borrow.php");
	// ��¼���Ļ�����������
   exit;
}

// ��ò���
$nickname=$_POST['username'];
$password=$_POST['password'];
// ������Ա�ʺź������Ƿ���ȷ,
// �������ֱ�Ӽ�⣬����Ҫ�������ݿ�
if( $nickname=="admin" and $password=="123456") {
	//ע��session���������浱ǰ�Ự�û����ǳ�
	session_register("Adm");
	$Adm=$nickname;
	// ��¼�ɹ��ض��򵽹���ҳ��
	header("Location:borrow.php");
}
else { 
	// ����ͷ�ļ�
	include('head.php');
    // ����Ա��¼ʧ��
	echo "<table width='100%' align=center><tr><td align=center>";
	echo "�ʺŻ�������󣬻��߲��ǹ���Ա�ʺ�<br>";
    echo "<font color=red>����Ա��¼ʧ�ܣ�</font><br><a href='login.php'>������</a>";
    echo "</td></tr></table>";
}

?>

