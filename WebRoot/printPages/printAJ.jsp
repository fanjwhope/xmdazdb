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
int countNum=0;
int pageNum=0;
	String sql="select count(*) from "+"zdb_xmwj_"+deptNum + " where (yjflag != '2' or yjflag is  null)";
	countNum=IncString.formatInt(op.querySingleData(sql));
	if(countNum<0){
		countNum=0;
	}
	if(countNum%18 != 0){
		pageNum=countNum/18+1;
	}else{
		pageNum=countNum/18;
	} 	

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="../publicJsp/libInclude.jsp" />
</head>
<BODY>
<br><br><br>
<form method='post'  action="" id="f" name="f" >
<table border="1" align="center" cellspacing="1" width="500px" bordercolordark="buttonshadow" bgcolor="#ffffe8">
<tr>
	<td>
		<table  border="0" cellpadding="0" cellspacing="0" style="height:65px;">
			<tr>
				<td colspan="3"  bgcolor="buttonshadow"  bordercolor="buttonshadow" height="20px">&nbsp;<font color="#ffffff">打印案卷目录（共<%=countNum %>条记录共<%=pageNum %>页）:</font></td>
			</tr>
			<tr>
				<td colspan="2" align="middle" style="width:350px;height:40px;">从
				<input type="text" name="pagef" id="pagef" size='4' value="1" readonly>-
				<input type="text" name="pagee" id="pagee" size='4' value="<%=pageNum %>">页</td>
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
		var pages=$('#pagee').val();
		
		if(<%=countNum%> < 1){
			alert("没有可供打印的案卷记录！");
			return;
		}
		if(pages < 1){
			alert("请输入正确页数！");
			return;
		}
		window.open("printContent.jsp?zdbdbName=<%=zdbdbName%>&pages="+pages);
	}
</script>