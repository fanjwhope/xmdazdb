<%@ page language="java"
	import="java.util.*,com.hr.util.*,com.hr.global.util.ArchiveUtil"
	pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String deptNum = IncString.formatNull(ArchiveUtil
			.getDepartmentCode(session));
	String userTabName = ("zdb_xmwj_" + deptNum).toLowerCase();
	String zdbdbName = request.getParameter("zdbdbName");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="/publicJsp/libInclude.jsp" />
<title>数据验证</title>
<script type="text/javascript">
	function queryAll(){
		$("#search_result").css({
			display : 'block'
		});
		$('#search_result_table').datagrid({
			title:'下列档案编号重复',
			url : '<%=path%>/do/SjyzDo/yzsj?zdbdbName=<%=zdbdbName%>',
			singleSelect : true,
			columns : [ [ {
				field : 'xmbh',
				title : '档案编号',
				width : 220
			}] ]
		});
	}
	
	function queryOne(){
		if($("#xmbh").val()==''){
			alert('请输入档案编号');
			return;
		}
		$("#search_result").css({
			display : 'block'
		});
		$('#search_result_table').datagrid({
			url : '<%=path%>/do/SjyzDo/xxxx?zdbdbName=<%=zdbdbName%>&xmbh='+$("#xmbh").val(),
			singleSelect : true,
			columns : [ [ {
				field : 'dabh',
				title : '项目编号',
				width : 220
			}, {
				field : 'dhsl',
				title : '贷后数量',
				width : 340
			}, {
				field : 'dhsmj',
				title : '贷后扫描件',
				width : 120
			}, {
				field : 'spsl',
				title : '审批数量',
				width : 120
			}, {
				field : 'spsmj',
				title : '审批扫描件',
				width : 120
			} ] ]
		});
	}
</script>
</head>

<body>
	<div
				style="overflow-x: auto; overflow-y: auto; height: 500px; width:100%;">
	<div id="search" style="display: block;">
		<center>
			<table border="1" style="border-collapse:collapse" cellspacing="0"
				cellpadding="0" width="300">
				<tr>
					<td class="TDbgcolor1">&nbsp; 验证数据</td>
				</tr>

				<tr height=30>
					<td>&nbsp;&nbsp;档案编号 <input type="text" name="xmbh" id="xmbh" size=12
						value="">

					</td>
				</tr>
				<tr height=30>
					<td>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
						class="smallBtn" id="verifyData" name="verifyData" value="验证数据"
						onclick="queryAll()"> <input type="button"
						class="smallBtn" value="查看数据详细信息" onclick="queryOne()">

					</td>
				</tr>
				<tr height=30>
					<td><input type=hidden name="subtype" id="subtype"> <input
						type=hidden name="subsubtype" id="subsubtype">
						&nbsp;&nbsp;验证数据时系统将查询档案：<br> 1、是否有重复<br> 2、是否有审批内容<br>
						3、是否有贷后管理<br> 4、是否有扫描件<br> <font color=red>如查看数据详细信息，请输入详细条件，否则数据太多影响显示速度！！</font>
					</td>
				</tr>
			</table>
		</center>
	</div>
	<!-- 查询结果 -->
	<div id="search_result" style="display: none;">
		<div data-options="region:'center',border:false"
			style="overflow:hidden;width:80%; float:left;padding-left: 100px;">
			<table class="easyui-datagrid" title="查询结果"
				id="search_result_table">
			</table>
		</div>
	</div>
</div>
</body>
</html>
