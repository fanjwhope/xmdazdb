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
<title>������֤</title>
<script type="text/javascript">
	function queryAll(){
		$("#search_result").css({
			display : 'block'
		});
		$('#search_result_table').datagrid({
			title:'���е�������ظ�',
			url : '<%=path%>/do/SjyzDo/yzsj?zdbdbName=<%=zdbdbName%>',
			singleSelect : true,
			columns : [ [ {
				field : 'xmbh',
				title : '�������',
				width : 220
			}] ]
		});
	}
	
	function queryOne(){
		if($("#xmbh").val()==''){
			alert('�����뵵�����');
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
				title : '��Ŀ���',
				width : 220
			}, {
				field : 'dhsl',
				title : '��������',
				width : 340
			}, {
				field : 'dhsmj',
				title : '����ɨ���',
				width : 120
			}, {
				field : 'spsl',
				title : '��������',
				width : 120
			}, {
				field : 'spsmj',
				title : '����ɨ���',
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
					<td class="TDbgcolor1">&nbsp; ��֤����</td>
				</tr>

				<tr height=30>
					<td>&nbsp;&nbsp;������� <input type="text" name="xmbh" id="xmbh" size=12
						value="">

					</td>
				</tr>
				<tr height=30>
					<td>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
						class="smallBtn" id="verifyData" name="verifyData" value="��֤����"
						onclick="queryAll()"> <input type="button"
						class="smallBtn" value="�鿴������ϸ��Ϣ" onclick="queryOne()">

					</td>
				</tr>
				<tr height=30>
					<td><input type=hidden name="subtype" id="subtype"> <input
						type=hidden name="subsubtype" id="subsubtype">
						&nbsp;&nbsp;��֤����ʱϵͳ����ѯ������<br> 1���Ƿ����ظ�<br> 2���Ƿ�����������<br>
						3���Ƿ��д������<br> 4���Ƿ���ɨ���<br> <font color=red>��鿴������ϸ��Ϣ����������ϸ��������������̫��Ӱ����ʾ�ٶȣ���</font>
					</td>
				</tr>
			</table>
		</center>
	</div>
	<!-- ��ѯ��� -->
	<div id="search_result" style="display: none;">
		<div data-options="region:'center',border:false"
			style="overflow:hidden;width:80%; float:left;padding-left: 100px;">
			<table class="easyui-datagrid" title="��ѯ���"
				id="search_result_table">
			</table>
		</div>
	</div>
</div>
</body>
</html>
