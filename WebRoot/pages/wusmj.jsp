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
	String zdbdbName=request.getParameter("zdbdbName");
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="/publicJsp/libInclude.jsp" />
<title>无扫描件</title>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function tj(){
	$("#tj").css({
			display : 'block'
		});
}

function queryAll() {
		$("#search").css({
			display : 'none'
		});
		$("#search_result").css({
			display : 'block'
		});
		$('#search_result_table').datagrid({
			url : '<%=path%>/do/wusmj/wqmysmj?zdbdbName=<%=zdbdbName%>',
					singleSelect:true,
					columns : [ [ {
						title : '',
						field : 'lsh',
						checkbox : true
					}, {
						field : 'qymc',
						title : '借款人名称',
						width : 220
					}, {
						field : 'xmmc',
						title : '项目名称',
						width : 340
					}, {
						field : 'xmbh',
						title : '档案编号',
						width : 120
					}, {
						field : 'xmfzr',
						title : '协议号',
						width : 120
					} , {
						field : 'xmfzr',
						title : '项目主管',
						width : 120
					}] ],
					onSelect: function(rowIndex, rowData){
     					showForm(rowData.lsh,rowData.xmbh,rowData.yjflag,rowData.tbname);
     				}
				});
	}
	
	  
function querybf() {
		$("#search").css({
			display : 'none'
		});
		$("#search_result").css({
			display : 'block'
		});
		$('#search_result_table').datagrid({
			url : '<%=path%>/do/wusmj/bfmysmj?zdbdbName=<%=zdbdbName%>',
					singleSelect:true,
					columns : [ [ {
						title : '',
						field : 'lsh',
						checkbox : true
					}, {
						field : 'qymc',
						title : '借款人名称',
						width : 220
					}, {
						field : 'xmmc',
						title : '项目名称',
						width : 340
					}, {
						field : 'xmbh',
						title : '档案编号',
						width : 120
					}, {
						field : 'xmfzr',
						title : '协议号',
						width : 120
					} , {
						field : 'xmfzr',
						title : '项目主管',
						width : 120
					}] ],
					onSelect: function(rowIndex, rowData){
     					showForm(rowData.lsh,rowData.xmbh,rowData.yjflag,rowData.tbname);
     				}
				});
	}
        function showForm(lsh,xmbh,yjflag,tbname){
        	window.open("pages/queryHuibianArchiveShow.jsp?zdbdbName=<%=zdbdbName%>&lsh="+lsh+"&xmbh="+xmbh+"&yjflag="+yjflag+"&type=1&tbname="+'<%=userTabName%>' + "");
	}
</script>
</head>

<body>
	<div id="search" style="display: block;">
		<table width=500 cellspacing=1 cellpadding="5" align="center" border=1>
			<tr>
				<td width="100%" height="16" bgcolor=buttonshadow><font
					style='font-size:12px;color:#ffffff'>缺少扫描件的项目档案</font></td>
			</tr>

			<tr>
				<td width="100%" height="16" valign=center><input type=hidden
					name=listtype id=listtype> <input type=button
					value="完全没有扫描件" onclick="queryAll()"> <input type=button
					value="部分无扫描件" onclick="querybf()"> <br>
					注：如果数据有变化（如条目信息修改、审批卷或贷后卷条目增删等），先点击【统计档案扫描情况】按钮进行刷新。 <input
					type=button value="统计档案扫描情况" id="tj_btn" name="tj_btn"
					onclick="tj()"><br>
					<p style="color: blue;display: none;" id="tj">数据重新统计完毕！！</p></td>
			</tr>
		</table>
	</div>
	<!-- 查询结果 -->
	<div id="search_result" style="display: none;">
		<div data-options="region:'center',border:false"
			style="overflow:hidden;width:80%; float:left;padding-left: 100px;">
			<table class="easyui-datagrid" title="项目说明查询结果"
				id="search_result_table">
			</table>
		</div>
	</div>

</body>
</html>
