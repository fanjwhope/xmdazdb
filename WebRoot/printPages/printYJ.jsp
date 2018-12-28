<%@page import="com.hr.dao.BaseDao"%>
<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%
String cxt=request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");
BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
String cName=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getDwmc());
String deptNum=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
String xmbh=IncString.formatNull(request.getParameter("xmbh"));
String subType=IncString.formatNull(request.getParameter("subType"));
String display="display:none;";
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
<form method='post' id="f" name="f" >
<table border="0" align="center" cellspacing="0" width="680">
<tr>
	<td>
		<table  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3"  bgcolor="008080">&nbsp;<font style='font-size:12px' color='FFFFFF'>打印项目档案移交系列表格</font></td>
			</tr>
			<tr>
				<td colspan="2" align="right" style="width:340px;height:13;"><font style='font-size:12px' color="#0000FF">借款合同号</font><input type="text" name="xmbh" id="xmbh" value="<%=xmbh%>" style="border: 1 solid #000000;width:140;height:20px"></td>
				<td style="width:340px;height:13;" align="left"><IMG STYLE="cursor:hand" border="0" src="../images/t_add.gif" width="46" height="24" id="id_qd" onclick="subContract();"></td>
			</tr>
		</table>
		<table  border=0 width="680" align=center id="tabDetail" cellpadding="0" cellspacing="0">
			 <tr bgColor="#008080">
		      <td height="16" width="18">　</td>
		      <td height="16" width="150">
		        <p align="center"><font color="#FFFFFF" style='font-size:12px'>借款人</font></p>
		      </td>
		      <td height="16" width="150">
		        <p align="center"><font color="#FFFFFF" style='font-size:12px'>项目名称</font></p>
		      </td>
		      <td height="16" width="100">
		        <p align="center"><font color="#FFFFFF" style='font-size:12px'>借款合同号</font></p>
		      </td>
		      <td height="16" width="70">
		        <p align="center"><font color="#FFFFFF" style='font-size:12px'>期 限</font></p>
		      </td>
		        <td height="16" width="100">
		        <p align="center"><font color="#FFFFFF" style='font-size:12px'>信贷员</font></p>
		      </td>
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
		if(xmbh==''){
			alert("请输入合同号！");
			return;
		}
		window.open("printListYJ.jsp?zdbdbName=<%=zdbdbName%>&xmbh="+xmbh+"&subType=print","_blank","directories=yes,location=yes,directories=yes,menubar=yes,resizable=yes,scrollbars=yes",false);
	}
</script>