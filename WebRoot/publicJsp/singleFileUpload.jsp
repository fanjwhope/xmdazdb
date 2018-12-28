<%@page import="com.hr.contract.global.util.CommonFuc"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../global.jsp"%>
<%@ include file="../se_check.jsp"%>
<%String cxt=request.getContextPath(); %>
<!DOCTYPE HTML>
<html>
<head>
<title>���ļ��ϴ�</title>
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
							title : '��ʾ',
							msg : '���ϴ�'+allowedSuffix+'��ʽ���ļ�'
						});
						return false;
					}
				}
			
				//var allowedSuffix='<%=CommonFuc.sVar(request, "allowedSuffix") %>';
				//if(allowedSuffix!=''){
				//	var index=$('#fileName').val().lastIndexOf('.'+allowedSuffix);
				//	if(index==-1){
				//		$.messager.show({
				//			title : '��ʾ',
				//			msg : '���ϴ�'+allowedSuffix+'��ʽ���ļ�'
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
						title : '��ʾ',
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
		<H2 style="text-align:center;font-family:����_GB2312,����;font-size:22px;font-weight:bold;">���ļ��ϴ�</H2>
		<div style="width:400px; padding: 20px; border: 1px solid; margin: 20px auto">
			<form class="tableForm" action="<%=cxt %>/singleFileUpload?saveDir=<%=CommonFuc.sVar(request, "saveDir") %>" method="post" enctype="multipart/form-data">
				��ѡ�����ϴ����ļ���<br><br><input type="file" id="fileName" name="fileName">
				<br>
				<br>
				<a id="uploadBtn" href="javascript:void(0);" class="easyui-linkbutton" onclick="save();"
					data-options="iconCls:'icon-ok', plain:true">�ϴ�</a>
				<div id="progressShow"></div>
			</form>
		</div>
	</div>
	<div id="msgDiv">
		<p id="msg">�ļ��Ѿ��ɹ��ϴ���</p>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="window.close();"
					data-options="iconCls:'icon-cross', plain:true">�رմ���</a>
	</div>
</body>
</html>
