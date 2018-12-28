<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String lsh = request.getParameter("lsh");
	if(lsh==null||"".equals(lsh.trim())){
		lsh="0";
	}
	String olddabh = request.getParameter("olddabh");
	String zdbdbName=request.getParameter("zdbdbName");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>��Ŀ˵��</title>
<jsp:include page="/publicJsp/libInclude.jsp" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
$(function(){
		var lsh = "<%=lsh%>";
		var olddabh = "<%=olddabh%>";
		$.ajax({
    		url:"<%=path%>/do/XMSMDo/getXMSMForm?zdbdbName=<%=zdbdbName%>&lsh=" + lsh+"&olddabh="+olddabh,
				type : "post",
				async : false,
				success : function(data) {
					var result = $.parseJSON(data);
					$("#ydabh").val(result.olddabh);
					$("#xmlx").val(result.smtype);
					$("#dkgb").val(result.by1);
					$("#dkxyh").val(result.dkxyh);
					$("#xmmc").val(result.xmmc);
					$("#xmzg").val(result.xmfzr);
					$("#jkrmc").val(result.qymc);
					$("#zdxyh").val(result.ywhc);
					$("#dabh").val(result.newxmbh);
					$("#tbsj").val(result.tbsj);
					$("#dkxyqdsj").val(result.qysj);
					$("#dkxy").val(result.xxqk);
					$("#xmtk").val(result.getqk);
					$("#hbfx").val(result.returnqk);
					$("#qtzl").val(result.infoqk);
					$("#tbr").val(result.tbr);
					$("#jcr").val(result.jcr);
				}
			});
	});
	
	function deleteXMSM(){
		if(window.confirm('��ȷ��Ҫɾ����')){
                 var lsh = <%=lsh%>;
		if(lsh != ''){
		$.ajax({
    		url:"<%=path%>/do/XMSMDo/deleteXMSM?zdbdbName=<%=zdbdbName%>&lsh=" + lsh,
					type : "post",
					async : false,
					success : function(data) {
						if(window.confirm('ɾ���ɹ�')){
							window.close();
						}
					}
				});
			} else {
				alert("��Ŀ˵��������");
			}
			return true;
		} else {
			return false;
		}
	}
	
	function saveXMSM(){
		var lsh = <%=lsh%>;
		$.ajax({
    		url:"<%=path%>/do/XMSMDo/saveXMSM?zdbdbName=<%=zdbdbName%>&lsh=" + lsh + "&xmlx="
					+ $("#xmlx").val() + "&ydabh=" + $("#ydabh").val()
					+ "&dkgb=" + $("#dkgb").val() + "&dkxyh="
					+ $("#dkxyh").val() + "&xmmc=" + $("#xmmc").val()
					+ "&xmzg=" + $("#xmzg").val() + "&jkrmc="
					+ $("#jkrmc").val() + "&zdxyh=" + $("#zdxyh").val()
					+ "&dabh=" + $("#dabh").val() + "&tbsj=" + $("#tbsj").val()
					+ "&dkxyqdsj=" + $("#dkxyqdsj").val() + "&dkxy="
					+ $("#dkxy").val() + "&xmtk=" + $("#xmtk").val() + "&hbfx="
					+ $("#hbfx").val() + "&qtzl=" + $("#qtzl").val() + "&tbr="
					+ $("#tbr").val() + "&jcr=" + $("#jcr").val() + "",
			type : "post",
			success : function(data) {
					var result = $.parseJSON(data);
					alert(result.msg);
			}
		});
	}
</script>
</head>

<body>
	<!-- �����������˵���� -->
	<div align="center">
		<h2 id="dk_title" align="center" style="width: 700px;display: none;">�������������Ŀ���˵����
		</h2>
		<h2 id="zk_title" align="center" style="width: 700px;display: none;">�������������Ŀ���˵����
		</h2>
		<form id="xmsm">
			<table class="table" width="700" border="1">

				<tr>
					<td colspan="2" height="40">��Ŀ���ͣ�<select id="xmlx" name="xmlx"><option
								value="dk">����</option>
							<option value="zk">����</option></select>&nbsp;&nbsp;ԭ������ţ�<input id="ydabh"
						class="input" style="width:120px;" name="ydabh" />&nbsp;&nbsp;<input
						type="button" class="button" value="ɾ��" id="delete"
						onclick="deleteXMSM()" />&nbsp;&nbsp;<input type="button"
						value="����" class="button" id="save" onclick="saveXMSM()" />&nbsp;&nbsp;<input
						type="button" value="��ӡ" class="button" id="print" /></td>
				</tr>
				<tr>
					<td height="40">�������<input class="input" id="dkgb"
						name="dkgb" /></td>
					<td>����Э��ţ�<input class="input" id="dkxyh" /></td>
				</tr>
				<tr>
					<td height="40">��Ŀ���ƣ�<input class="input" id="xmmc"
						name="xmmc" /></td>
					<td>��Ŀ���ܣ�<input class="input" id="xmzg" /></td>
				</tr>
				<tr>
					<td height="40">��������ƣ�<input class="input" id="jkrmc"
						name="jkrmc" /></td>
					<td>ת��Э��ţ�<input class="input" id="zdxyh" name="zdxyh" /></td>
				</tr>
				<tr>
					<td height="40">������ţ�<input class="input" id="dabh"
						name="dabh" /></td>
					<td>���ʱ�䣺<input class="input" id="tbsj" name="tbsj" /></td>
				</tr>
				<tr>
					<td colspan="2" height="40">����Э��ǩ��ʱ�䣺<input class="input"
						id="dkxyqdsj" name="dkxyqdsj" /></td>
				</tr>
				<tr>
					<td colspan="2" height="40">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="40">������Ŀ��Ҫ˵���������</td>
				</tr>
				<tr>
					<td colspan="2" height="40">һ������Э�����</td>
				</tr>
				<tr>
					<td colspan="2" height="80"><textarea class="area" id="dkxy"
							name="dkxy"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" height="40">������Ŀ������</td>
				</tr>
				<tr>
					<td colspan="2" height="80"><textarea class="area" id="xmtk"
							name="xmtk"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" height="40">����������Ϣ���</td>
				</tr>
				<tr>
					<td colspan="2" height="80"><textarea class="area" id="hbfx"
							name="hbfx"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" height="40">�ġ������������</td>
				</tr>
				<tr>
					<td colspan="2" height="80"><textarea class="area" id="qtzl"
							name="qtzl"></textarea></td>
				</tr>
				<tr>
					<td height="40">����ˣ�<input class="input" id="tbr" name="tbr" /></td>
					<td>����ˣ�<input class="input" id="jcr" name="jcr" /></td>
				</tr>
			</table>
		</form>
	</div>
	</div>
</body>
</html>
