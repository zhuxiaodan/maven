<?
include ('head.php');
require ('dbconnect.php');
// ��ѯ�鲻��Ҫ��¼
?>
<script language="javascript"> 
	function checksearch(){
		if (form1.id.value=="" && form1.title.value=="" && form1.author.value=="" && form1.publisher.value=="" && form1.year.value==""){
			// û�������ѯ��Ϣ
			alert("û�������ѯ��Ϣ!");
	         return false;	
		}
		return true;
	}
</script>
<html>
<body>

<form name="form1" method="post" action="search_result.php" onsubmit="return checksearch()">
  <table width="60%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr> 
      <th colspan="2">ͼ �� �� ѯ</th>
    </tr>
    <tr> 
      <td width="26%" align="right">ͼ���ţ�</td>
      <td width="74%"> 
        <input type="text" name="id" size="10">
      </td>
    </tr>
    <tr> 
      <td width="26%" align="right">������</td>
      <td height="25" width="74%"> 
        <input type="text" name="title" size="50">
      </td>
    </tr>
    <tr> 
      <td height="25" width="26%" align="right">���ߣ�</td>
      <td width="74%"> 
        <input type="text" name="author" size="50" maxlength="100">
      </td>
    </tr>
    <tr> 
      <td width="26%" align="right">�����磺</td>
      <td width="74%"> 
        <input type="text" name="publisher" size="50" maxlength="100">
      </td>
    </tr>
    <tr> 
      <td width="26%" align="right">������ݣ�</td>
      <td width="74%"> 
        <input type="text" name="year" size="10">
      </td>
    </tr>
    <tr> 
      <td width="26%" align="right"> 
        <input type="submit" name="Submit" value="�ύ">
      </td>
      <td width="74%"> 
        <input type="reset" name="Submit2" value="����">
      </td>
    </tr>
  </table>
</form>


</body>
</html>