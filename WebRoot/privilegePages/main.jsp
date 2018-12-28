<%@page contentType="text/html;charset=gb2312"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil,com.hr.dao.PrivilegeDate,com.hr.dao.PrivilegeInfo"%>
<%
String cxt=request.getContextPath();
   String zdbdbName=request.getParameter("zdbdbName");
%>
<style>
<!--
 td{font-family: 宋体;font-size:14px;}
 .loginlong {
	font-family: "宋体", "楷体_GB2312", "黑体";
	font-size: 10pt;
	background-image: url(images/login.JPG);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 55px;
	cursor:hand;
}
.login {
	font-family: "宋体", "楷体_GB2312", "黑体";
	font-size: 9pt;
	background-image: url(images/login_long.jpg);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 113px;
	cursor:hand;
}
  .font10blue {font-family: 宋体;font-size:12px;color:#0000A0}
  .font10 {font-family: 宋体;font-size:12px;}
  .zlfontlabel{font-family: 宋体;font-size:14px;color:#000099}
-->
</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="../publicJsp/libInclude.jsp" />
</head>
<BODY class="easyui-layout" data-options="fit:true">
<div data-options="region:'west',title:'权限类别',split:true" style="width:200px;height:100%;" style="overflow:hidden;">
	<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:200px;height:100%;">
		<tr>
			<td style="height:40px;width:180px;"><a href="javascript:void(0);" class="easyui-linkbutton" onclick="goUrl('privilegeMain.jsp');" data-options="iconCls:'icon-group', plain:true">单位人员项目权限设置</a></td>
		</tr>
		<tr>
			<td style="height:40px;width:180px;"><a href="javascript:void(0);" class="easyui-linkbutton" onclick="goUrl('priProjectList.jsp');" data-options="iconCls:'icon-pictures', plain:true">项目权限设置</a></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>
<div data-options="region:'center',title:'权限信息',split:true" data-options="fit:true" style="overflow:hidden;">
	<iframe id="frameMain" name="frameMain" src="privilegeMain.jsp?zdbdbName=<%=zdbdbName%>" style="width:100%;height:100%;border:0;overflow:hidden;" scrolling="no" FRAMEBORDER="0"></iframe>
</div>   
</BODY>
</HTML>
<script language="javascript">
	function goUrl(url){
		$('#frameMain').attr("src",url+'?zdbdbName=<%=zdbdbName%>');
	}
</script>