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
	$return=$_POST['return'];
	$renew=$_POST['renew'];
	$user_id=$_POST[user_id];
	$book_id=$_POST[book_id];	
	// ����
	if ($return){
		// �鿴��Щ�鱾��Ҫ�黹
		for($i=0;$i<count($bookbox);$i++){
			// �����bookû��ѡ�ϣ�ִ����һѭ��
			if ($bookbox[$i]==""){
				next;
			}
			else {
				$book_id=$bookbox[$i];
				// ����sql��仹��
				// ��lend����ɾ���ý����¼
				$returnsql="delete from lend where book_id='$book_id' and user_id='$user_id'";
				mysql_query($returnsql,$conn) or die ("ɾ�������¼ʧ�ܣ�".mysql_error());
				// ��lend_log���м�¼����ʱ��
				// ��õ�ǰ����
				$now = date("Y-m-d");
				$logsql="update lend_log set return_time='$now' where book_id='$book_id' and user_id='$user_id'";
				mysql_query($logsql,$conn) or die ("��¼����ʱ��ʧ�ܣ�".mysql_error());
				// ��book��������һ���ִ�������
				$booksql="update book set leave_number=leave_number+1 where id='$book_id'";
				mysql_query($booksql,$conn) or die ("����ʣ��������ʧ�ܣ�".mysql_error());
				?>
				<p align="center">&nbsp;</p>
				<p align="center">&nbsp;</p>
				<p align="center"><font color="red">����Ǽ���ɣ�</p>
				<?
			}
		}
	}
	// ����
	if ($renew){
		// �鿴��Щ�鱾��Ҫ�黹
		for($i=0;$i<count($bookbox);$i++){
			// �����bookû��ѡ�ϣ�ִ����һѭ��
			if ($bookbox[$i]==""){
				next;
			}
			else {
				$book_id=$bookbox[$i];
				// ��õ�ǰ����
				$now = date("Y-m-d");
				// ����sql�������
				// ����ֻ��Ҫ��lend������������ʱ�����
				$renewsql="update lend set renew_time='$now' where book_id='$book_id' and user_id='$user_id'";
				mysql_query($renewsql,$conn) or die ("����ʧ�ܣ�".mysql_error());
				?>
				<p align="center">&nbsp;</p>
				<p align="center">&nbsp;</p>
				<p align="center"><font color="red">������ɣ�</p>
				<?
			}
		}
	}
?>