<%@page import="com.hr.contract.global.util.CommonFuc"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../global.jsp"%>
<%@ include file="../se_check.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>�ļ����غ���</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="/publicJsp/libInclude.jsp" />
<script type="text/javascript">
	$(function(){
		var msg=$('#msg').text();
		if(msg&&msg!=''){
			parent.$.messager.show({
				title:'��ʾ',
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
