<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%
String zdbdbName=IncString.formatNull(request.getParameter("zdbdbName"));
String cName=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getDwmc());
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
<BODY>
<br><br><br>
<form method='post'  action="" id="f" name="f" >
<%if(cName.equals("dac")){%>
<table border="1" align="center" cellspacing="1" width="500" bordercolordark="#FB8F02" bgcolor="#FFECD0" height="76">
<tr>
<td colspan="3"  bgcolor="#FB8F02"  bordercolor="#FB8F02" height="13">&nbsp;编目打印
</td>
</tr>
<tr>
<td colspan="3" align=center>
<img src="../images/anda-ajml.gif" alt="案卷目录" onclick="goprint('printAJ.jsp');">
<img src="../images/anda-yjb.gif" alt="档案移交表" onclick="goprint('printYJ.jsp');">
<img src="../images/anda-ls.gif" alt="流水登记表" onclick="goprint('printLS.jsp');">
</td>
</tr>
</table>
<%}else{%>
<table border="1" align="center" cellspacing="1" width="500" bordercolordark="buttonshadow" bgcolor="#ffffe8" height="76">
<tr>
<td colspan="3"  bgcolor="buttonshadow"  bordercolor="buttonshadow" height="13">&nbsp;<font color="#ffffff">编目打印</font>
</td>
</tr>
<tr>
<td colspan="3" align=center>
<img src="../images/t_ajbj.gif" alt="案卷背脊" onclick="goprint('printBJ.jsp');">
<img src="../images/t_ajml.gif" alt="案卷目录" onclick="goprint('printAJ.jsp');">
<img src="../images/t_yjb.gif" alt="档案移交表" onclick="goprint('printYJ.jsp');">
<img src="../images/t_lsdjb.gif" alt="流水登记表" onclick="goprint('printLS.jsp');">
</td>
</tr>
</table>
<%}%>
</form>
</BODY>
</HTML>
<script language="javascript">
	function goprint(url){
		window.location.replace(url+'?zdbdbName=<%=zdbdbName%>');
	}
</script>