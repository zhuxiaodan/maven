<?
include ('head.php');
require ('dbconnect.php');
// ��ѯ�鲻��Ҫ��¼
?>
<?
// ��ò���
$id=$_POST['id'];
$title=$_POST['title'];
$author=$_POST['author'];
$publisher=$_POST['publisher'];
$year=$_POST['year'];
// �����ѯ����ֻ��һЩ�ո�ȥ������ʾ�û�û�в�ѯ����
if ($id=="" && $title=="" && $author=="" && $publisher=="" && $year==""){
	echo "<div align=center>�������ѯ����<br>";
	echo "<a href='javascript:history.back(-1)'>����</a></div>";
	exit();
}
// ���庯����ѯid
function Get_search_id(){
        $args=func_get_args();
        $queryfield=$args[0];
        $queryvalue=$args[1];
        $conn=$args[2];
        $id_search=array();  //store the searched id
        $sqlsearch="select id from book where ".$queryfield." like '%".$queryvalue."%'";
        //print $sqlsearch;
        $re_search=mysql_query($sqlsearch,$conn);
        while ($row_search=mysql_fetch_row($re_search)){
                array_push($id_search,$row_search[0]);
        }
        return $id_search;
}
// ���������������id
$resultid=array();
$arr=array();
// ������������Ƿ����в�ѯ���
$flag=0;
if ($id!=""){
	$id_id=array();
	$result=mysql_query("select id from book where id='$id'",$conn);
	while($row=mysql_fetch_row($result)){
		array_push($id_id,$row[0]);
	}
	$flag=1;
	$resultid=$id_id;
}
if ($title!=""){
	$title_id=array();
	$title_id=Get_search_id("title",$title,$conn);
	// ǰ��û�в�ѯ���
	if ($flag==0){
		$resultid=$title_id;
	}
	// ���в�ѯ���
	else {
		$flag=1;
		// ȡ����
		$arr=array_intersect($resultid,$title_id);
		$resultid=$arr;
	}
}
if ($author!=""){
	$author_id=array();
	$author_id=Get_search_id("author",$author,$conn);
	// ǰ��û�в�ѯ���
	if ($flag==0){
		$resultid=$author_id;
	}
	// ���в�ѯ���
	else {
		$flag=1;
		// ȡ����
		$arr=array_intersect($resultid,$author_id);
		$resultid=$arr;
	}
}
if ($publisher!=""){
	$publisher_id=array();
	$publisher_id=Get_search_id("publisher",$publisher,$conn);
	// ǰ��û�в�ѯ���
	if ($flag==0){
		$resultid=$publisher_id;
	}
	// ���в�ѯ���
	else {
		$flag=1;
		// ȡ����
		$arr=array_intersect($resultid,$publisher_id);
		$resultid=$arr;
	}
}
if ($year!=""){
	$year_id=array();
	$result=mysql_query("select id from book where publish_year='$year'",$conn);
	while($row=mysql_fetch_row($result)){
		array_push($year_id,$row[0]);
	}
	// ǰ��û�в�ѯ���
	if ($flag==0){
		$resultid=$year_id;
	}
	// ���в�ѯ���
	else {
		$flag=1;
		// ȡ����
		$arr=array_intersect($resultid,$year_id);
		$resultid=$arr;
	}
}
// ��ʾ��ѯ���
$num=count($resultid);
echo "<h2 align=center>ͼ���ѯ���</h2>";
if ($num==0){
	echo "<div align=center><font color=red>û���ҵ����ϲ�ѯ������ͼ��</a></div>";
	exit();
}
echo "<div align=center>��ѯ��<font color=red>$num</font>��ͼ�飡��Ŀ���£�</div>";
echo "<br>";
echo "<table border=1 width='80%' align=center>";
echo "<th >���</th>";
echo "<th >����</th>";
echo "<th >����</th>";
echo "<th >������</th>";
echo "<th >���</th>";
echo "<th >ʣ�����/�ܲ���</th>";
// ��ò�ѯ���������ϸ��Ϣ
for ($i=0;$i<$num;$i++){
	$bresult=mysql_query("select * from book where id='$resultid[$i]'",$conn);
	$binfo=mysql_fetch_array($bresult);
	echo "<tr align=center><td>$binfo[id]</td>";
	echo "<td>$binfo[title]</td>";
	echo "<td>$binfo[author]</td>";
	echo "<td>$binfo[publisher]</td>";
	echo "<td>$binfo[publish_year]</td>";
	echo "<td>$binfo[leave_number]/$binfo[total]</td>";
	echo "</tr>";
}
echo "</table>";
?>