<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <jsp:include page="publicJsp/libInclude.jsp" />
  </head>
  <body class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false" style="overflow:hidden">
    <center>
       <table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border-width: 0" bordercolor="#111111" width="100%" id="AutoNumber1" >
		  <tr>
		    <td width="32.4%" height="71" >　</td>
		    <td width="41%" height="71" >　</td>
		    <td width="26.6%" height="71" >　</td>
		  </tr>
		  <tr>
		    <td width="32.4%" height="1" >　</td>
		    <td width="41%" height="1" ><img src="images/xmda.gif">　</td>
		    <td width="26.6%" height="1" >　</td>
		  </tr>
       </table>
    </center>
</div>
  </body>
</html>
