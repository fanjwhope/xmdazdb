<%@page import="com.hr.dao.BaseDao"%>
<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@ include file="printset.jsp"%>
<%
String zdbdbName=request.getParameter("zdbdbName");
BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
String cName=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getDwmc());
String deptNum=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
String xmbh=IncString.formatNull(request.getParameter("xmbh"));
String subType=IncString.formatNull(request.getParameter("subType"));
String display="display:none;";
String[][] val=null;
if(subType.equals("print")){
	String sql="select qymc,xmmc,xmbh,dkqx,xmfzr from "+"zdb_xmwj_"+deptNum+" where xmbh='"+xmbh+"'";
	val=op.queryRowAndCol(sql);
	display="";
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
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
.text1 {
	width: 120px;
	overflow: hidden;
	text-overflow:ellipsis;
}
.text2 {
	width: 80px;
	overflow: hidden;
	text-overflow:ellipsis;
}
</style>
<jsp:include page="../publicJsp/libInclude.jsp" />
<script type="text/javascript" src="<%=cxt%>/js/print.js" charset="gbk"></script>
<script language="javascript">
var pp = new CLASS_PRINT();
window.onload = function()
{
    pp.header = document.getElementById("tabHeader");
    pp.content= document.getElementById("tabDetail");
    pp.footer = document.getElementById("tabFooter");
	pp.pageStyle="第p页";
    pp.pageSize = 10;
    pp.beforePrint(); 
}
</script>
</head>
<BODY>
<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 width=0>
</OBJECT>
		<table  border=0 width="680" align=center id="tabHeader">
			<tr>
				<td align="center" width="680" height="30px"><%=xmbh%>移交列表</td>
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
		    <%
		    	if(val!=null&&val.length>0){
		    		for(int i=0;i<val.length;i++){
		    %>
		    <tr>
		      <td height="25" width="18">　</td>
		      <td height="25" width="150">
		        <p align="center"><font style='font-size:12px'><%=val[i][0]%></font></p>
		      </td>
		      <td height="25" width="150">
		        <p align="center"><font style='font-size:12px'><%=val[i][1]%></font></p>
		      </td>
		      <td height="25" width="100">
		        <p align="center"><font style='font-size:12px'><%=val[i][2]%></font></p>
		      </td>
		      <td height="25" width="70">
		        <p align="center"><font style='font-size:12px'><%=val[i][3]%></font></p>
		      </td>
		        <td height="25" width="100">
		        <p align="center"><font style='font-size:12px'><%=val[i][4]%></font></p>
		      </td>
		    </tr>
		    <%
		    		}
		    	}
		    %>
		</table>
		<table width="680" border=0 id="tabFooter" align=center cellpadding=4>
		  <tr>
		   <td>&nbsp;</td>
		  </tr>
		</table>
<div id="divPrint"></div>
<!--table width="95%" align=center class="notprint">
  <tr>
    <td align=right>
      <input type=button value='打印' onClick="window.print()" style="border:1px solid #000000">
    </td>
  </tr>
</table-->
</BODY>
</HTML>