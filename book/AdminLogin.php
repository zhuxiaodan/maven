<?
//��ʼ��session
session_start();

// $_SESSION['UserName'] ������$UserName��������
if(isset($_SESSION['Adm'])) {
	//�ض��򵽽���ҳ��
	header("Location:borrow.php");
	// ��¼���Ļ�����������
   exit;
}
// ����ͷ�ļ�
include('head.php');
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
<form action="ChkAdmLogin.php" method="post" name="login" onsubmit="return checklogin()">
<p align="center">����Ա��¼</p>
<table align="center" border="0">
 <tr>
  <th>
����Ա:
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
 <tr>
  <th colspan="2" align="right">
<input type="submit" value="��¼">
</form>
  </th>
 </tr>
</table>
