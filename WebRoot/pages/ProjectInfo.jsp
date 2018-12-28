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

<title>项目说明</title>
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
		if(window.confirm('你确定要删除吗？')){
                 var lsh = <%=lsh%>;
		if(lsh != ''){
		$.ajax({
    		url:"<%=path%>/do/XMSMDo/deleteXMSM?zdbdbName=<%=zdbdbName%>&lsh=" + lsh,
					type : "post",
					async : false,
					success : function(data) {
						if(window.confirm('删除成功')){
							window.close();
						}
					}
				});
			} else {
				alert("项目说明不存在");
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
	<!-- 外国政府贷款说明表 -->
	<div align="center">
		<h2 id="dk_title" align="center" style="width: 700px;display: none;">外国政府贷款项目情况说明表
		</h2>
		<h2 id="zk_title" align="center" style="width: 700px;display: none;">外国政府赠款项目情况说明表
		</h2>
		<form id="xmsm">
			<table class="table" width="700" border="1">

				<tr>
					<td colspan="2" height="40">项目类型：<select id="xmlx" name="xmlx"><option
								value="dk">贷款</option>
							<option value="zk">赠款</option></select>&nbsp;&nbsp;原档案编号：<input id="ydabh"
						class="input" style="width:120px;" name="ydabh" />&nbsp;&nbsp;<input
						type="button" class="button" value="删除" id="delete"
						onclick="deleteXMSM()" />&nbsp;&nbsp;<input type="button"
						value="存盘" class="button" id="save" onclick="saveXMSM()" />&nbsp;&nbsp;<input
						type="button" value="打印" class="button" id="print" /></td>
				</tr>
				<tr>
					<td height="40">贷款国别：<input class="input" id="dkgb"
						name="dkgb" /></td>
					<td>贷款协议号：<input class="input" id="dkxyh" /></td>
				</tr>
				<tr>
					<td height="40">项目名称：<input class="input" id="xmmc"
						name="xmmc" /></td>
					<td>项目主管：<input class="input" id="xmzg" /></td>
				</tr>
				<tr>
					<td height="40">借款人名称：<input class="input" id="jkrmc"
						name="jkrmc" /></td>
					<td>转贷协议号：<input class="input" id="zdxyh" name="zdxyh" /></td>
				</tr>
				<tr>
					<td height="40">档案编号：<input class="input" id="dabh"
						name="dabh" /></td>
					<td>填表时间：<input class="input" id="tbsj" name="tbsj" /></td>
				</tr>
				<tr>
					<td colspan="2" height="40">贷款协议签订时间：<input class="input"
						id="dkxyqdsj" name="dkxyqdsj" /></td>
				</tr>
				<tr>
					<td colspan="2" height="40">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="40">贷款项目需要说明的情况：</td>
				</tr>
				<tr>
					<td colspan="2" height="40">一、贷款协议情况</td>
				</tr>
				<tr>
					<td colspan="2" height="80"><textarea class="area" id="dkxy"
							name="dkxy"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" height="40">二、项目提款情况</td>
				</tr>
				<tr>
					<td colspan="2" height="80"><textarea class="area" id="xmtk"
							name="xmtk"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" height="40">三、还本付息情况</td>
				</tr>
				<tr>
					<td colspan="2" height="80"><textarea class="area" id="hbfx"
							name="hbfx"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" height="40">四、其他资料情况</td>
				</tr>
				<tr>
					<td colspan="2" height="80"><textarea class="area" id="qtzl"
							name="qtzl"></textarea></td>
				</tr>
				<tr>
					<td height="40">填表人：<input class="input" id="tbr" name="tbr" /></td>
					<td>检查人：<input class="input" id="jcr" name="jcr" /></td>
				</tr>
			</table>
		</form>
	</div>
	</div>
</body>
</html>
