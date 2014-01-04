<?
include ('head.php');
require ('dbconnect.php');
// 查询书不需要登录
?>
<?
// 获得参数
$id=$_POST['id'];
$title=$_POST['title'];
$author=$_POST['author'];
$publisher=$_POST['publisher'];
$year=$_POST['year'];
// 如果查询参数只是一些空格，去除，提示用户没有查询条件
if ($id=="" && $title=="" && $author=="" && $publisher=="" && $year==""){
	echo "<div align=center>请输入查询条件<br>";
	echo "<a href='javascript:history.back(-1)'>后退</a></div>";
	exit();
}
// 定义函数查询id
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
// 定义数组存放最后结果id
$resultid=array();
$arr=array();
// 定义变量控制是否已有查询结果
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
	// 前面没有查询结果
	if ($flag==0){
		$resultid=$title_id;
	}
	// 已有查询结果
	else {
		$flag=1;
		// 取交集
		$arr=array_intersect($resultid,$title_id);
		$resultid=$arr;
	}
}
if ($author!=""){
	$author_id=array();
	$author_id=Get_search_id("author",$author,$conn);
	// 前面没有查询结果
	if ($flag==0){
		$resultid=$author_id;
	}
	// 已有查询结果
	else {
		$flag=1;
		// 取交集
		$arr=array_intersect($resultid,$author_id);
		$resultid=$arr;
	}
}
if ($publisher!=""){
	$publisher_id=array();
	$publisher_id=Get_search_id("publisher",$publisher,$conn);
	// 前面没有查询结果
	if ($flag==0){
		$resultid=$publisher_id;
	}
	// 已有查询结果
	else {
		$flag=1;
		// 取交集
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
	// 前面没有查询结果
	if ($flag==0){
		$resultid=$year_id;
	}
	// 已有查询结果
	else {
		$flag=1;
		// 取交集
		$arr=array_intersect($resultid,$year_id);
		$resultid=$arr;
	}
}
// 显示查询结果
$num=count($resultid);
echo "<h2 align=center>图书查询结果</h2>";
if ($num==0){
	echo "<div align=center><font color=red>没有找到符合查询条件的图书</a></div>";
	exit();
}
echo "<div align=center>查询到<font color=red>$num</font>本图书！书目如下：</div>";
echo "<br>";
echo "<table border=1 width='80%' align=center>";
echo "<th >书号</th>";
echo "<th >书名</th>";
echo "<th >作者</th>";
echo "<th >出版社</th>";
echo "<th >年份</th>";
echo "<th >剩余册数/总册数</th>";
// 获得查询到的书的详细信息
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