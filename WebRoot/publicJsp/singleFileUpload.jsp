<%@page import="com.hr.contract.global.util.CommonFuc"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../global.jsp"%>
<%@ include file="../se_check.jsp"%>
<%String cxt=request.getContextPath(); %>
<!DOCTYPE HTML>
<html>
<head>
<title>单文件上传</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style type="text/css">
	#uploadDiv, #msgDiv{
		width: 100%;
		height: 100%;
	}
	#msgDiv{
		display: none;
		width:400px;
		margin: 0px auto;
		padding-left: 60px;
	}
	#progressShow{
		width: 94px;
		height: 17px;
		padding: 0px;
		margin-right:16px;
		margin-top:8px;
		background-image: url("<%=cxt%>/images/progressBar.gif");
		float: right;
		display: none;
	}
	#msg{
		margin-top: 100px;
	}
</style>
<jsp:include page="/publicJsp/libInclude.jsp" />
<script type="text/javascript">
	function save() {
		$('form.tableForm').form('submit', {
			onSubmit:function(){
				var allowedSuffix='<%=CommonFuc.sVar(request, "allowedSuffix") %>';
				if(allowedSuffix!=''){
					var allowedReg=eval('/^.+('+allowedSuffix+')/i');
					var res=$('#fileName').val().match(allowedReg);
					if(res==null){
						$.messager.show({
							title : '提示',
							msg : '请上传'+allowedSuffix+'格式的文件'
						});
						return false;
					}
				}
			
				//var allowedSuffix='<%=CommonFuc.sVar(request, "allowedSuffix") %>';
				//if(allowedSuffix!=''){
				//	var index=$('#fileName').val().lastIndexOf('.'+allowedSuffix);
				//	if(index==-1){
				//		$.messager.show({
				//			title : '提示',
				//			msg : '请上传'+allowedSuffix+'格式的文件'
				//		});
				//		return false;
				//	}
				//}
				
				$('#progressShow').show('normal');
				$('#uploadBtn').linkbutton('disable');
			},
			success:function(data) {
				var json = $.parseJSON(data);
				if(json.success) {
					opener.handleAfterUpload(json.param);
					$('#uploadDiv').hide('fast');
					$('#msgDiv').show('fast');
				}else{
					$('#progressShow').hide('fast');
					$('#uploadBtn').linkbutton('enable');
					$.messager.show({
						title : '提示',
						msg : json.msg
					});
				}
			}
		});
	}
</script>
</head>

<body>
	<div id="uploadDiv">
		<H2 style="text-align:center;font-family:楷体_GB2312,宋体;font-size:22px;font-weight:bold;">单文件上传</H2>
		<div style="width:400px; padding: 20px; border: 1px solid; margin: 20px auto">
			<form class="tableForm" action="<%=cxt %>/singleFileUpload?saveDir=<%=CommonFuc.sVar(request, "saveDir") %>" method="post" enctype="multipart/form-data">
				请选择欲上传的文件：<br><br><input type="file" id="fileName" name="fileName">
				<br>
				<br>
				<a id="uploadBtn" href="javascript:void(0);" class="easyui-linkbutton" onclick="save();"
					data-options="iconCls:'icon-ok', plain:true">上传</a>
				<div id="progressShow"></div>
			</form>
		</div>
	</div>
	<div id="msgDiv">
		<p id="msg">文件已经成功上传！</p>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="window.close();"
					data-options="iconCls:'icon-cross', plain:true">关闭窗口</a>
	</div>
</body>
</html>
