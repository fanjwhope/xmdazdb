<%@page import="com.hr.contract.global.util.CommonFuc"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../global.jsp"%>
<%@ include file="../se_check.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>文件下载后处理</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="/publicJsp/libInclude.jsp" />
<script type="text/javascript">
	$(function(){
		var msg=$('#msg').text();
		if(msg&&msg!=''){
			parent.$.messager.show({
				title:'提示',
				msg:msg
			});
		}
	});
</script>
</head>

<body>
	<div id="msg"><%=CommonFuc.getStrAttribute(request, "msg") %></div>
</body>
</html>
