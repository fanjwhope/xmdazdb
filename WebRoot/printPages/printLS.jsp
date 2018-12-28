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
String sql="";
int countNum=0;
int pageNum=0;
int rowsNum=22;
	sql="select count(*) from "+"zdb_xmwj_"+deptNum;
	countNum=IncString.formatInt(op.querySingleData(sql));
	if(countNum<0){
		countNum=0;
	}
	if(countNum%22>0){
		pageNum=countNum/rowsNum+1;
	}else{
		pageNum=countNum/rowsNum;
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
<tr>
	<td>
		<table  border="0" cellpadding="0" cellspacing="0" style="height:65px;">
			<tr>
				<td colspan="3"  bgcolor="buttonshadow"  bordercolor="buttonshadow" height="20px">&nbsp;<font color="#ffffff">打印流水登记表（共<%=countNum%>条记录共<%=pageNum%>页）:</font></td>
			</tr>
			<tr>
				<td colspan="2" align="right" style="width:350px;height:40px;">从<input type="text" name="beginPage" id="beginPage" size=3 value="1">-<input type="text" name="endPage" id="endPage" size=3 value="<%=pageNum%>">页</td>
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
		var beginPage=$('#beginPage').val();
		if(beginPage<0){
			alert("请输入有效数字！");
			return;
		}
		var endPage=$('#endPage').val();
		if(endPage<0){
			alert("请输入有效数字！");
			return;
		}
		if(endPage><%=pageNum%>){
			alert("页数不能大于总页数！");
			return;
		}
		window.open("printListLS.jsp?zdbdbName=<%=zdbdbName%>&beginPage="+beginPage+"&endPage="+endPage+"&rowsNum="+<%=rowsNum%>,"_blank","directories=yes,location=yes,directories=yes,menubar=yes,resizable=yes,scrollbars=yes",false);
	}
</script>