<?
include("head.php");
?>
<script language="javascript"> 
    function checkreg()
    { 			
		if (form1.name.value=="")
		{
			// �����ʵ����Ϊ�գ�����ʾ������Ϣ
	        alert("��ʵ��������Ϊ�գ�");
			form1.name.focus();
			return false;
	    }
		if (form1.password.value=="" )
		{
			// �������Ϊ�գ�����ʾ������Ϣ
	        alert("���벻��Ϊ�գ�");
			form1.password.focus();
			return false;
	    }
		if (form1.pwd.value=="" )
		{
			// �������Ϊ�գ�����ʾ������Ϣ
	        alert("ȷ�����벻��Ϊ�գ�");
			form1.pwd.focus();
			return false;
	    }
		// ��������Ӧһ��
		if (form1.password.value!=form1.pwd.value && form1.password.value!="")
		{
			alert("�������벻һ������ȷ�ϣ�");
			form1.password.focus();
			return false;
		}
		if (form1.email.value=="")
		{
			// ���EmailΪ�գ�����ʾ������Ϣ
	        alert("Email����Ϊ�գ�");
			form1.email.focus();
			return false;
	    }
			// ���email��ʽ�Ƿ���ȷ
		else if (form1.email.value.charAt(0)=="." ||
			form1.email.value.charAt(0)=="@"||
			form1.email.value.indexOf('@', 0) == -1 ||
			form1.email.value.indexOf('.', 0) == -1 ||
			form1.email.value.lastIndexOf("@")==form1.email.value.length-1 ||
			form1.email.value.lastIndexOf(".")==form1.email.value.length-1)
		{
			alert("Email�ĸ�ʽ����ȷ��");
			form1.email.select();
			return false;
		}
		return true;

    }	
</script>

<html>
<body>

<form name="form1" method="post" action="regok.php" enctype='multipart/form-data' onsubmit="return checkreg()" >
  <table border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2"><font size="5">�� �� ע �� �� ��</font></th>
    </tr>    
    <tr> 
      <td>��   ����</td>
      <td> 
        <input type="text" name="name">
    </tr>
    <tr> 
      <td>��   ��:</td>
      <td> 
        <input type="password" name="password">        
    </tr>
	<tr> 
      <td>ȷ�����룺</td>
      <td> 
        <input type="password" name="pwd">        
    </tr>
	<tr> 
      <td>Email��</td>
      <td> 
        <input type="text" name="email">        
    </tr>
	 <tr> 
      <td>��   ��:</td>
      <td> 
        <input type="text" name="tel">
    </tr>
	<tr> 
      <td>��   ַ:</td>
      <td> 
        <input type="text" name="address">
    </tr>    
    <tr> 
      <td  align=right > 
        <input type="submit" name="Submit" value="ע ��">
      </td>
      <td align=center> 
        <input type="reset" name="Submit2" value="�� д">
      </td>
    </tr>
  </table>
</form>

</body>
</html>