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
	function queryOne(){
		$("#search_result").css({
			display : 'block'
		});
		$('#search_result_table').datagrid({
			url : '<%=path%>/do/SjyzDo/xxxx?xmfzrid=y200040043&zdbdbName=<%=zdbdbName%>&xmbh='
									+ $("#xmbh").val()
									+ '&tbsj='
									+ $('#tbsj').datebox('getValue'),
							pageNumber : 1,
							pagination : true,//��ҳ�ؼ� 
							rownumbers : true,//��ʾ�к�
							pageSize : 10,
							pageList : [ 20, 50, 100 ],
							loader : myLoader,
							columns : [ [ {
								field : 'lsh',
								title : '��ˮ��',
								width : 120
							}, {
								field : 'dabh',
								title : '��Ŀ���',
								width : 220
							}, {
								field : 'xmmc',
								title : '��Ŀ����',
								width : 420
							}, {
								field : 'dhsl',
								title : '��������',
								width : 140
							}, {
								field : 'spsl',
								title : '��������',
								width : 120
							}, {
								field : 'time',
								title : '���ʱ��',
								width : 120
							} ] ]
						});
	}

	function dhsl() {
		var rows = $('#search_result_table').datagrid('getRows');//��ȡ��ǰ��������
		var total = 0;
		for (var i = 0; i < rows.length; i++) {
			if (rows[i]['dhsl'] != '')
				total += parseInt(rows[i]['dhsl']);
		}
		return total;
	}

	function spsl() {
		var rows = $('#search_result_table').datagrid('getRows');//��ȡ��ǰ��������
		var total = 0;
		for (var i = 0; i < rows.length; i++) {
			if (rows[i]['spsl'] != '')
				total += parseInt(rows[i]['spsl']);
		}
		return total;
	}

	function myLoader(param, success, error) {
		var that = $(this);
		var opts = that.datagrid("options");
		if (!opts.url) {
			return false;
		}
		var cache = that.data().datagrid.cache;
		if (!cache) {
			$.ajax({
				type : opts.method,
				url : opts.url,
				data : param,
				dataType : "json",
				success : function(data) {
					that.data().datagrid['cache'] = data;
					success(bulidData(data));
					var rows = data.rows;
					var total = 0;
					for (var i = 0; i < rows.length; i++) {
					if (rows[i]['dhsl'] != '')
							total += parseInt(rows[i]['dhsl']);
					}
					var total2 = 0;
					for (var i = 0; i < rows.length; i++) {
					if (rows[i]['spsl'] != '')
						total2 += parseInt(rows[i]['spsl']);
					}
					var str = '����(' + total + ')ҳ  ������(' + total2
										+ ')ҳ  �ϼ�:(' + (total + total2) + ')ҳ';
								$('#total')[0].innerHTML = str;
				},
				error : function() {
					error.apply(this, arguments);
				}
			});
		} else {
			success(bulidData(cache));
		}
		function bulidData(data) {
			debugger;
			var temp = $.extend({}, data);
			var tempRows = [];
			var start = (param.page - 1) * parseInt(param.rows);
			var end = start + parseInt(param.rows);
			var rows = data.rows;
			for (var i = start; i < end; i++) {
				if (rows[i]) {
					tempRows.push(rows[i]);
				} else {
					break;
				}
			}
			temp.rows = tempRows;
			return temp;
		}
	}
</script>
</head>

<body>
	<div
		style="overflow-x: auto; overflow-y: auto; height: 500px; width:100%;">
		<div id="search" style="display: block; margin-top: 20px">
			<center>
				<table border="1" style="border-collapse:collapse" cellspacing="0"
					cellpadding="0" width="300">
					<tr>
						<td class="TDbgcolor1">&nbsp; ��֤����</td>
					</tr>

					<tr height=30>
						<td>&nbsp;&nbsp;������� <input type="text" name="xmbh" id="xmbh"
							size=16 value="">
						</td>
					</tr>
					<tr height=30>
						<td>&nbsp;&nbsp;ʱ����� <input type="text" name="tbsj" id="tbsj"
							type="text" class="easyui-datebox" editable="false">
						</td>
					</tr>
					<tr height=30>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
							class="smallBtn" value="�鿴������ϸ��Ϣ" onclick="queryOne()">
						</td>
					</tr>
				</table>
			</center>
		</div>

		<!-- ��ѯ��� -->
		<div id="search_result" style="display: none;margin-top: 20px">
			<div data-options="region:'center',border:false"
				style="overflow:hidden;width:98%;padding-left: 10px">
				<table class="easyui-datagrid" title="��ѯ���" id="search_result_table">
				</table>
			</div>
		</div>
	</div>
	<div>
		<center>
			<p id="total" style="color: blue;margin-top: 10px"></p>
		</center>
	</div>
</body>
</html>
