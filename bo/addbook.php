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

<script language="javascript"> 
    function checkadd()
    { 	
		if (form1.title.value=="")
		{
			// ���������Ϊ�գ�����ʾ������Ϣ
	        alert("��������Ϊ�գ�");
			form1.title.focus();
			return false;
	    }
		if (form1.author.value=="" )
		{
			// �������Ϊ�գ�����ʾ������Ϣ
	        alert("���߲���Ϊ�գ�");
			form1.author.focus();
			return false;
	    }
		if (form1.publisher.value=="" )
		{
			// ���������Ϊ�գ�����ʾ������Ϣ
	        alert("�����粻��Ϊ�գ�");
			form1.publisher.focus();
			return false;
	    }
		if (form1.publish_year.value=="")
		{
			// ����������Ϊ�գ�����ʾ������Ϣ
	        alert("������ݲ���Ϊ�գ�");
			form1.publish_year.focus();
			return false;
	    }
		if (form1.publish_year.value <1000 || form1.publish_year.value >3000)
		{
			// ���������ݲ���ȷ������ʾ������Ϣ
	        alert("������ݲ���ȷ��");
			form1.publish_year.focus();
			return false;
	    }
		if (form1.total.value=="")
		{
			// ����������Ϊ�գ�����ʾ������Ϣ
	        alert("�����������Ϊ�գ�");
			form1.total.focus();
			return false;
	    }
								
		return true;

    }	
</script>

<html>
<body>

<form name="form1" method="post" action="addbookok.php" onsubmit="return checkadd()">
  <table width="50%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2">�������</th>
    </tr>
    <tr> 
      <td width="26%" align="right">������</td>
      <td width="74%"> 
        <input type="text" name="title" size="50" maxlength="100">
      </td>
    </tr>
    <tr> 
      <td width="26%" align="right">���ߣ�</td>
      <td width="74%"> 
        <input type="text" name="author" size="50" maxlength="100">
      </td>
    </tr>
    <tr> 
      <td width="26%" align="right">�����磺</td>
      <td width="74%"> 
        <input type="text" name="publisher" size="50" maxlength="50">
      </td>
    </tr>
    <tr> 
      <td width="26%" align="right">������ݣ�</td>
      <td width="74%"> 
        <input type="text" name="publish_year" size="10" maxlength="10">
        �� </td>
    </tr>
    <tr> 
      <td width="26%" align="right">���������</td>
      <td width="74%"> 
        <input type="text" name="total" size="10" maxlength="10">
        ��</td>
    </tr>
    <tr> 
      <td width="26%" align="right">��ע��</td>
      <td width="74%"> 
        <textarea name="other" cols="50"></textarea>
      </td>
    </tr>
    <tr> 
      <td width="26%" align="right"> 
        <input type="submit" name="Submit" value="�ύ">
      </td>
      <td width="74%"> 
        <input type="reset" name="Reset" value="����">
      </td>
    </tr>
  </table>
</form>


</body>
</html>