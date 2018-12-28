<%@page import="com.hr.dao.BaseDao"%>
<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%
String zdbdbName=request.getParameter("zdbdbName");
BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
String cName=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getDwmc());
String deptNum=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
String xmbh=IncString.formatNull(request.getParameter("xmbh"));
String subType=IncString.formatNull(request.getParameter("subType"));
String sql="";
int countNum=0;
int pageNum=0;
if(subType.equals("print")){
	if(xmbh.equals("")){
		sql="select count(*) from "+"zdb_xmwj_"+deptNum + " where (yjflag != '2' or yjflag is  null)";
	}else{
		sql="select count(*) from "+"zdb_xmwj_"+deptNum+" where (yjflag != '2' or yjflag is  null) and xmbh='"+xmbh+"'";	
	}
	countNum=IncString.formatInt(op.querySingleData(sql));
	if(countNum<0){
		countNum=0;
	}
	 if(countNum%2==1){
		pageNum=countNum/2+1;
	}else{
		pageNum=countNum/2;
	} 	
}
%>
<style>
<!--
 td{font-family: 宋体;font-size:14px;}
-->
</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="../publicJsp/libInclude.jsp" />
</head>
<BODY>
<br><br><br>
<form method='post'  action="" id="f" name="f" >
<table border="1" align="center" cellspacing="1" width="500px" bordercolordark="buttonshadow" bgcolor="#ffffe8">
<%if(subType.equals("print")){%>
<tr>
	<td style="width:500px;">
			<table  border="0" cellpadding="0" cellspacing="0" style="width:500px;height:20px;">
				<tr>
					<td bgcolor="buttonshadow"  bordercolor="buttonshadow" height="20px">&nbsp;<font color="#ffffff">打印案卷目录(共<%=countNum%>条记录共<%=countNum%>页！)</font></td>
				</tr>
			</table>
	</td>
</tr>
<tr>
	<td>
			<table  border="0" cellpadding="0" cellspacing="0" style="height:60px;">
				<tr>
					<td colspan="3"  bgcolor="buttonshadow"  bordercolor="buttonshadow" height="20px">&nbsp;<font color="#ffffff">请选择您要打印的卷的大小:</font></td>
				</tr>
				<tr>
					<td colspan="2" align="right" style="width:300px;height:40px;"><input type="radio" name="bjType" value="thick"><font color="#0000FF">厚盒</font><input type="radio" name="bjType" value="middle"><font color="#0000FF">中盒</font><input type="radio" name="bjType" value="mean" checked ><font color="#0000FF">薄盒</font></td>
					<td style="width:200px;height:40px;" align="left"><IMG STYLE="cursor:hand" border="0" src="../images/anda-dy.gif" width="46" height="24" id="id_qd" onclick="goPrint();"></td>
				</tr>
				<tr>
					<td colspan="3" style="width:500px;height:50px;">
						      <fieldset style="padding: 2;width:480px;">
									<legend>打印样式设置</legend>
									脚标:
									<input type='text' size='3' name='sign' id='sign' value="Ⅰ">
									编号输出格式:
									<input type="text" name="prefix" id="prefix" size=3 value="第(" readonly>
									<select name="bhtype" id="bhtype" onchange="formatSerisNo()">
										<option value="hth">项目编号</option>
										<option value="all">整个编号</option>
									</select>
									<input type="text" name="endfix" id="endfix" size='3' value=")号" readonly>
							</fieldset>
					</td>
				</tr>
			</table>
	</td>
</tr>
<%}%>
<tr>
	<td>
		<table  border="0" cellpadding="0" cellspacing="0" style="height:65px;">
			<tr>
				<td colspan="3"  bgcolor="buttonshadow"  bordercolor="buttonshadow" height="20px">&nbsp;<font color="#ffffff">请选择您要打印的借款合同号:</font></td>
			</tr>
			<tr>
				<td colspan="2" align="right" style="width:350px;height:40px;"><font color="#0000FF">借款合同号</font><input type="text" name="xmbh" id="xmbh" value="<%=xmbh%>" style="border: 1 solid #000000;width:140;height:20px"></td>
				<td style="width:150px;height:40px;" align="left"><IMG STYLE="cursor:hand" border="0" src="../images/anda-qd.gif" width="46" height="24" id="id_qd" onclick="subContract();"></td>
			</tr>
		</table>
	</td>
</tr>
</table>
</form>
</BODY>
</HTML>
<script language="javascript">
	function subContract(){
		var xmbh=$('#xmbh').val();
		f.action="printBJ.jsp?zdbdbName=<%=zdbdbName%>&xmbh="+xmbh+"&subType=print";
		f.submit();
	}
	function formatSerisNo(){
	 	if($('#bhtype').val()=="all"){
	 		$('#prefix').attr("value","");
	 		$('#endfix').attr("value","");
	 	}else{
	 		$('#prefix').attr("value","第(");
	 		$('#endfix').attr("value",")号");   	
	 	}
	}
	function goPrint(){
		var bjType=$("input[name='bjType']:checked").val();
		var sign=$('#sign').val();
		var xmbh=$('#xmbh').val();
		var bhtype=$('#bhtype').val();
		var prefix = $('#prefix').val();
		var endfix = $('#endfix').val();
		if(<%=countNum%> < 1)
			alert("没有可供打印的案卷记录！");
		else
			window.open("aJBJ.jsp?zdbdbName=<%=zdbdbName%>&bjType="+bjType+"&sign="+sign+"&xmbh="+xmbh+"&bhtype="+bhtype+"&prefix="+prefix+"&endfix="+endfix);
	}
</script>