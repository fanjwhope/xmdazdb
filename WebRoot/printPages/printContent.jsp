<%@page import="com.hr.dao.BaseDao"%>
<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@ include file="printset.jsp"%>
<%
String zdbdbName=request.getParameter("zdbdbName");
BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
String deptNum=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
String xmbh=IncString.formatNull(request.getParameter("xmbh"));
int pages = Integer.parseInt(request.getParameter("pages"));

String sql = "select xmbh, xmmc, xmnd, bz from " + "zdb_xmwj_"+deptNum+ " where (yjflag != '2' or yjflag is  null)";
String[][] str = op.queryRowAndCol(sql);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>打印案卷目录</title>
    <script src="../js/jquery-1.8.0.min.js"></script>
    <script src="../js/print.js"></script>
    <meta http-equiv="content-type" content="text/html;charset=GBK">
<style>
/**//**
*    打印相关
*/ 
@media print {
.notprint {
	display:none;
}
.PageNext {
	page-break-after:always;
}
}
 @media screen {
.notprint {
	display:inline;
	cursor:hand;
}
}
</style>
  <script>
  	var pp = new CLASS_PRINT();

	window.onload = function()
	{
    	pp.header = document.getElementById("tabHeader");
   	 	pp.content= document.getElementById("tabDetail");
    	pp.footer = document.getElementById("tabFooter");
		pp.pageStyle="第p页";
    	pp.pageSize = 18;
    	pp.beforePrint();    
	}
  </script>
  </head>
  
  <body>
  	<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 width=0></OBJECT>
	<!--table width="95%" align=center class=" notprint ">
  		<tr>
    		<td align=left margin-left="50px">
      			<input type=button value='打印' onClick="window.print()" style="border:1px solid #000000">
      			<input type=button value='打印设置' onClick="window.PageSetup()" style="border:1px solid #000000">
    		</td>
  		</tr>
  	</table-->
	<table border=0 width="950" align=center id="tabHeader">
  	<tr>
    	<TD align="center"  colspan="2"><FONT color="#000000" style='font-size:18pt' face="黑体">项 目 档 案 案 卷 目 录</FONT></TD>
  	</tr>
  	<tr>
  		<td align="right"><!--ct--></td>
  	</tr>
	</table>
  	<table border="2" bordercolor="#000000" cellspacing="0" cellpadding="0" align="center" style="width:950;border-collapse:collapse" id="tabDetail">
   <thead>
      <TR height="20">
        <TD  align="center" width="150" style='height:21.0pt'><FONT color="#000000" style='font-size:12pt;' face="黑体">档 案 编 号</FONT></TD>
        <TD  align="center" width=453  style='height:21.0pt'><FONT color="#000000" style='font-size:12pt;' face="黑体">项 目 名 称</FONT></TD>
        <TD  align="center" width=74   style='height:21.0pt'><FONT color="#000000" style='font-size:12pt;' face="黑体">年度</FONT></TD>
        <TD  align="center" width=76   style='height:21.0pt'><FONT color="#000000" style='font-size:12pt;' face="黑体">页数</FONT></TD>
        <TD  align="center" width=172  style='height:21.0pt'><FONT color="#000000" style='font-size:12pt;' face="黑体">备     注</FONT></TD>
      </TR></thead>
      <%for(int i = 0; i < 18 * pages; i++){ 
      		if(i < str.length){%>
    	<tbody> <tr>
      	<TD align='center' style='height:21.0pt'<font style='font-size:10pt'><%=str[i][0] %> &nbsp;</font></TD>
      	<TD align='center' style='height:21.0pt'><font style='font-size:10pt'><%=str[i][1] %> &nbsp;</font></TD>
      	<TD align='center' style='height:21.0pt'><font style='font-size:10pt'> <%=str[i][2] %> &nbsp;</font></TD>
      	<TD align='center' style='height:21.0pt'><font style='font-size:10pt'> &nbsp;</font></TD>
      	<TD align='center' style='height:21.0pt'><font style='font-size:10pt'><%=str[i][3] %>&nbsp;</font></TD>
      </tr>
      <%} else {%>
      <tr>
      	<TD align='center'  style='height:21.0pt'><font style='font-size:10pt'> &nbsp;</font></TD>
      	<TD align='center'  style='height:21.0pt'><font style='font-size:10pt'> &nbsp;</font></TD>
      	<TD align='center'  style='height:21.0pt'><font style='font-size:10pt'> &nbsp;</font></TD>
      	<TD align='center'  style='height:21.0pt'><font style='font-size:10pt'> &nbsp;</font></TD>
      	<TD align='center'  style='height:21.0pt'><font style='font-size:10pt'> &nbsp;</font></TD>
      </tr>
      <%}} %></tbody>
  	</table>
  	<table width="95%" border=0 id="tabFooter" align=center cellpadding=4>
  <tr>
    <td align=right>&nbsp;</td>
  </tr>
  </table>
  <div id="divPrint"></div>
  </body>
</html>
