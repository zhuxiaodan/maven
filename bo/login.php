<?
//��ʼ��session
session_start();

// $_SESSION['UserName'] ������$UserName��������
if(isset($_SESSION['user'])) {
	//�ض��򵽲鿴���
	header("Location:browse.php");
	// ��¼���Ļ�����������
   exit;
}
include ('head.php');
?>
<script language="javascript"> 
    function checklogin()
    { 
      if ((login.username.value!="") && (login.password.value!=""))
        // ����ǳƺ��������Ϊ��,�򷵻�true
         return true
      else {
        // ����ǳƻ�����Ϊ��,����ʾ������Ϣ
         alert("�ǳƻ����벻��Ϊ��!")
         return false
      } 	
    } 
</script>

<h1></h1>
<form action="checkLogin.php" method="post" name="login" onsubmit="return checklogin()">
<p align="center">�û���¼</p>
<table align="center" border="0">
 <tr>
  <th>
�û�ID:
  </th>
  <th>
<input type="text" name="username">
  </th>
 </tr>
 <tr>
  <th>
�� ��:
  </th>
  <th>
<input type="password" name="password">
  </th>
 </tr>
 <tr><td colspan=2><a href="reg.php">ע�����û�</a></td></tr>
 <tr>
  <th colspan="2" align="right">
<input type="submit" value="��¼">
</form>
  </th>
 </tr>
</table>
