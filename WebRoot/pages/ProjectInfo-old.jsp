<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String zdbdbName=request.getParameter("zdbdbName");		
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<jsp:include page="/publicJsp/libInclude.jsp" />

</head>

<script type="text/javascript">

	var center;
	var xmlx_;
	
	$(function() {

		//����tool������ʾ
		var sumWidth = $(document).width();
		var divWidth = $("#tool_").width();
		center = sumWidth / 2 - divWidth / 2;
		$("#tool").css({
			'padding-left' : center
		});
			
		//��Ŀ˵��--�����--������
		$("#save").click(function(){								
			$.ajax({
				type:"post",
				url:"<%=path%>/do/XMSMDo/add?zdbdbName=<%=zdbdbName%>",
				dataType:"json",
				data:"xmlx="+$("#xmlx").val()+"&ydabh="+$("#ydabh").val()+"&dkgb="+$("#dkgb").val()+"&dkxyh="+$("#dkxyh").val()+"&xmmc="+$("#xmmc").val()+"&xmzg="+$("#xmzg").val()+"&jkrmc="+$("#jkrmc").val()+"&zdxyh="+$("#zdxyh").val()+"&dabh="+$("#dabh").val()+"&tbsj="+$("#tbsj").val()+"&dkxyqdsj="+$("#dkxyqdsj").val()+"&dkxy="+$("#dkxy").val()+"&xmtk="+$("#xmtk").val()+"&hbfx="+$("#hbfx").val()+"&qtzl="+$("#qtzl").val()+"&tbr="+$("#tbr").val()+"&jcr="+$("#jcr").val()+"",
				success:function(data){
					$("#back").hide();
					$("#write").show();
					$.messager.show({
    	    						title : '��ʾ',
    	    						msg : data.msg
    	    					});
				}
			
			});
			
		
		});
		
		
		//��Ŀ˵��--�����--����¼
		$("#write").click(function(){
			$("#dabh").val("");
			$("#add").show();
			$("#dk").hide();
			
		});
		
		
		$("#back").click(function() {

			$("#add").css({
				display : 'block'
			});
			$("#dk").css({
				display : 'none'
			});

		});

	})

	//���
	function add() {
		
		$("#add").css({
			'padding-left' : center
		});
	
		$("#tool").css({
			display : 'none'
		});
		$("#add").css({
			display : 'block'
		});
		
		$.post("<%=path%>/do/XMSMDo/cateateTable?zdbdbName=<%=zdbdbName%>");
		
		
	}
	
	
	
	
	//���--->��ѯ
	function add_search(){
		//���ù�����������ʾ
		var sumWidth = $(document).width();
		var myWidth = $("#dk").width();
		var padding = sumWidth / 2 - myWidth / 2;
		
		//��Ŀ����
		var xmlx_ = $("#xmlx_").combobox("getValue");
		//ԭ��Ŀ���
		var re = $("#dabh").val();
		
		if(re != ''){
		$.ajax({
    		url:"<%=path%>/do/XMSMDo/getDAData?zdbdbName=<%=zdbdbName%>&ydabh=" + re,
				type : "post",
				async : false,
				success : function(data) {
					var result = $.parseJSON(data);
					$("#xmlx").val(result.smtype);
					$("#ydabh").val(result.olddabh);
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

			//���ԭ��Ŀ��Ų�Ϊ�գ������ѡ����Ŀ���ͣ���ʾ��Ӧ��
			if (xmlx_ == 'zk') {
				$("#zk_title").css({
					display : "block"
				});
				$("#dk_title").css({
					display : "none"
				});
			} else {
				$("#zk_title").css({
					display : "none"
				});
				$("#dk_title").css({
					display : "block"
				});
			}
			$("#xm").attr("value", xmlx_);
			$("#ydabh").val(re);
			$("#dk").css({
				'padding-left' : padding
			});
			$("#add").css({
				display : 'none'
			});
			$("#dk").css({
				display : 'block'
			});

		} else {
			alert("������Ų���Ϊ�գ�");
		}
	}

	//�������������Ŀ���˵���� ����--->���ذ�ť
	function back() {
		$("#back").click(function() {
			$("#add").css({
				display : 'block'
			});
			$("#dk").css({
				display : 'none'
			});
		});
	}

	//�������������Ŀ���˵���� ����--->���̰�ť
	function save() {
		alert("1111");
	}
	function search(){
		$("#tool").css({
			display : 'none'
		});
		$("#search").css({
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
			url : '<%=path%>/do/XMSMDo/queryAll?zdbdbName=<%=zdbdbName%>&smtype='
							+ $("#smtype_query").val() + '&newxmbh='
							+ $("#newxmbh_query").val() + '&dkxyh='
							+ $("#dkxyh_query").val() + '&ywhc='
							+ $("#ywhc_query").val() + '&qymc='
							+ $("#qymc_query").val() + '&xmmc='
							+ $("#xmmc_query").val(),
					singleSelect:true,
					columns : [ [ {
						title : '',
						field : 'lsh',
						checkbox : true
					}, {
						field : 'qymc',
						title : '���������',
						width : 220
					}, {
						field : 'xmmc',
						title : '��Ŀ����',
						width : 340
					}, {
						field : 'dkxyh',
						title : '����Э���',
						width : 120
					}, {
						field : 'newxmbh',
						title : '�������',
						width : 120
					}, {
						field : 'xmfzr',
						title : '��Ŀ����',
						width : 120
					} ] ],
					onSelect : function(rowIndex, rowData) {
						window.open("pages/ProjectInfo.jsp?zdbdbName=<%=zdbdbName%>&lsh="+rowData.lsh);
					}
				});
	}
</script>

<body>
	<div id="main"
		style="padding:0px;margin:0px;width: 100%;height: 100%;overflow: auto;">


		<div id="tool" style="padding-top:20px;">
			<div id="tool_" class="easyui-panel" title="����/������Ŀ���˵��"
				style="width:500px;height:150px;padding-left:10px;padding-top:20px;padding-right:10px;padding-bottom:10px;background:#fafafa;"
				data-options="
        iconCls:null,
        closable:true,   
        collapsible:true,
        minimizable:true,
        maximizable:true,
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closable:false">
				<p>ѡ����ӡ���������µ���Ŀ���˵����ѡ�񡰲�ѯ�����Բ�ѯ���޸�����ӵ����˵��</p>
				<p style="float:right;">
					<a id="btn_add" href="javascript:add()" class="easyui-linkbutton"
						data-options="iconCls:'icon-add'">���</a> <a id="btn_e"
						href="javascript:search()" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'">��ѯ</a>
				</p>
			</div>
		</div>
		<!-- ��� -->
		<div id="add" style="display: none;padding-top:20px;">

			<div id="add_tool" class="easyui-panel" title="����/������Ŀ���˵��"
				style="width:500px;height:150px;padding-left:10px;padding-top:20px;padding-right:10px;padding-bottom:10px;background:#fafafa;"
				data-options="
        iconCls:null,
        closable:true,   
        collapsible:true,
        minimizable:true,
        maximizable:true,
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closable:false">
				<p>
					��Ŀ���ͣ�<select id="xmlx_" class="easyui-combobox" name="xmlx_"
						style="width:100px;" data-options="panelHeight:100">
						<option value="dk">����</option>
						<option value="zk">����</option>
					</select> &nbsp;&nbsp; ԭ������ţ�<input id="dabh" style="width: 120px;" />&nbsp;&nbsp;
					<a id="btn" href="javascript:add_search()"
						class="easyui-linkbutton" data-options="iconCls:'icon-search'">��ѯ</a>
				</p>
			</div>

		</div>

		<!-- ��ѯ -->
		<div id="search" style="display: none;">
			<table width=450 cellspacing=1 cellpadding="5" align="center"
				border=1>
				<tr>
					<td width="382" height="16" bgcolor="buttonshadow" colspan="4"><font
						style='font-size:12px;color:#ffffff'>����/������Ŀ���˵��</font></td>
				</tr>
				<tr>
					<td width="72" height="13" valign=center><input type=hidden
						name=listtype id=listtype> ��Ŀ����&nbsp;</td>
					<td width="101" height="13" valign=center><select name=smtype
						id="smtype_query">
							<option value=''></option>
							<option value='dk'>����˵��</option>
							<option value='zk'>����˵��</option>
					</select></td>
					<td width="68" height="13" valign=center>�������&nbsp;</td>
					<td width="101" height="13" valign=center><input type=text
						id="newxmbh_query" size=15></td>
				</tr>
				<tr>
					<td width="72" height="-4" valign=center>����Э���&nbsp;</td>
					<td width="101" height="-4" valign=center><input type=text
						id="dkxyh_query" size=15></td>
					<td width="68" height="-4" valign=center>ת��Э���&nbsp;</td>
					<td width="101" height="-4" valign=center><input type=text
						id="ywhc_query" size=15></td>
				</tr>
				<tr>
					<td width="72" height="-4" valign=center>��/������&nbsp;</td>
					<td width="101" height="-4" valign=center><input type=text
						id="qymc_query" size=15></td>
					<td width="68" height="-4" valign=center>��Ŀ����</td>
					<td width="101" height="-4" valign=center><input type=text
						id="xmmc_query" size=15></td>
				</tr>
				<tr>
					<td width="382" height="-4" valign=center colspan="4"><input
						type=image onclick="queryAll()" name="I1"></td>
				</tr>
			</table>
		</div>

		<!-- ��ѯ��� -->
		<div id="search_result" style="display: none;">
			<div data-options="region:'center',border:false"
				style="overflow:hidden;width:80%; float:left;padding-left: 100px;">
				<table class="easyui-datagrid" title="��Ŀ˵����ѯ���"
					id="search_result_table">
				</table>
			</div>
		</div>

		<!-- �����������˵���� -->
		<div id="dk"
			style="display: none;padding-top:20px;padding-bottom:20px;">

			<h2 id="dk_title" align="center" style="width: 700px;display: none;">�������������Ŀ���˵����
			</h2>
			<h2 id="zk_title" align="center" style="width: 700px;display: none;">�������������Ŀ���˵����
			</h2>
			<form id="xmsm">
				<table class="table" width="700" border="1">

					<tr>
						<td colspan="2" height="40">��Ŀ���ͣ�<select id="xmlx"
							name="xmlx"><option value="dk">����</option>
								<option value="zk">����</option></select>&nbsp;&nbsp;ԭ������ţ�<input
							id="ydabh" name="ydabh" class="input" style="width:120px;" />&nbsp;&nbsp;<input
							type="button" class="button" value="����" id="back" /><input
							type="button" value="��¼" class="button" style="display: none;"
							id="write" />&nbsp;&nbsp;<input type="button" value="����"
							class="button" id="save" />&nbsp;&nbsp;<input type="button"
							value="��ӡ" class="button" id="print" /></td>
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
