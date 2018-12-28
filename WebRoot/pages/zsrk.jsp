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
<title>��ʽ���</title>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	function query(){
	$("#tstxt").css({
			display : 'block'
		});
	}
	function queryOne(){
		if($("#xmbh").val()==''){
			alert('�����뵵�����');			
		}else{
		$("#search_result").css({
			display : 'block'
		});
		$('#search_result_table').datagrid({
			url : '<%=path%>/do/SjyzDo/xxxx?zdbdbName=<%=zdbdbName%>&xmfzrid='+$("#xmzg").combobox('getValue')+'&xmbh='
									+ $("#xmbh").val(),
							singleSelect : true,
							columns : [ [ {
								field : 'dabh',
								title : '��Ŀ���',
								width : 220
							}, {
								field : 'ywhc',
								title : 'Э���',
								width : 220
							}, {
								field : 'dhsl',
								title : '��������',
								width : 340
							}, {
								field : 'spsl',
								title : '��������',
								width : 340
							} ] ]
						});
		}
	}
	
	function yz(){
		if($("#ywhc").val()==''){
			alert('������Э���');			
		}else{
		$("#search_result").css({
			display : 'block'
		});
		$('#search_result_table').datagrid({
			url : '<%=path%>/do/ZsrkDo/yz?zdbdbName=<%=zdbdbName%>&xmfzrid='+$("#xmzg").combobox('getValue')+'&ywhc='
									+ $("#ywhc").val(),
							singleSelect : true,
							columns : [ [ {
								field : 'xmmc',
								title : '��Ŀ����',
								width : 340
							}, {
								field : 'xmbh',
								title : '�������',
								width : 140
							}, {
								field : 'ywhc',
								title : '����Э���',
								width : 120
							} , {
								field : 'qymc',
								title : '���������',
								width : 220
							}] ]
						});
		}
	}
	
	function dqrk(){
		if($("#ywhc").val()==''){
			alert('������Э���');	
			return;		
		}
		$.ajax({
    		  type:'post',
    	      url:'<%=path%>/do/ZsrkDo/dqrk?zdbdbName=<%=zdbdbName%>&xmfzrid='+$("#xmzg").combobox('getValue')+'&ywhc='
									+ $("#ywhc").val(),
    	      dataType:'json',
    	      success:function(data){
    	    	  $.messager.show({
								title : '��ʾ',
								msg : data.msg
							});
    	      }
    	  });
	}
	
	function bqspnr(){
		if($("#ywhc").val()==''){
			alert('������Э���');
			return;			
		}
		if($("#xmbh").val()==''){
			alert('�����뵵�����');	
			return;			
		}
		$.ajax({
    		  type:'post',
    	      url:'<%=path%>/do/ZsrkDo/bqspnr?zdbdbName=<%=zdbdbName%>&xmfzrid='+$("#xmzg").combobox('getValue')+'&ywhc='
									+ $("#ywhc").val()+'&xmbh='
									+ $("#xmbh").val(),
    	      dataType:'json',
    	      success:function(data){
    	    	  $.messager.show({
								title : '��ʾ',
								msg : data.msg
							});
    	      }
    	  });
	}
	
	function bqdhnr(){
		if($("#ywhc").val()==''){
			alert('������Э���');
			return;			
		}
		if($("#xmbh").val()==''){
			alert('�����뵵�����');	
			return;			
		}
		$.ajax({
    		  type:'post',
    	      url:'<%=path%>/do/ZsrkDo/bqdhnr?zdbdbName=<%=zdbdbName%>&xmfzrid='+$("#xmzg").combobox('getValue')+'&ywhc='
									+ $("#ywhc").val()+'&xmbh='
									+ $("#xmbh").val(),
    	      dataType:'json',
    	      success:function(data){
    	    	  $.messager.show({
								title : '��ʾ',
								msg : data.msg
							});
    	      }
    	  });
	}
	
	$(function(){
		$("#xmzg").combobox({   
    		url:'<%=path%>/do/SjyzDo/getUsers?zdbdbName=<%=zdbdbName%>',
			valueField : 'value',
			textField : 'text'
		});
	});
</script>
</head>

<body>
	<div
		style="overflow-x: auto; overflow-y: auto; height: 500px; width:100%;">
		<div id="tstxt" style="display: none;">
			<center>
				<font color=red style='font-size:16px'><b>Э��Ų�����!!</b></font>
			</center>
		</div>
		<div id="search" style="display: block;margin-top: 20px">
			<center>
				<table border="1" style="border-collapse:collapse" cellspacing="0"
					cellpadding="0" width="360">
					<tr>
						<td class="TDbgcolor1">&nbsp; ��ʽ���</td>
					</tr>
					<tr height="28">
						<td>&nbsp;&nbsp;&nbsp;&nbsp; �� Ŀ�� �� <input name="xmzg"
							id="xmzg" style='width:153px;font-size:14px'>
						</td>
					</tr>
					<tr height="28">
						<td>&nbsp;&nbsp;&nbsp;&nbsp; ����Э��� <input type=text
							name="ywhc" id="ywhc" value=""> <input type=hidden
							name="subtype" id="subtype"> <input type=hidden
							name="subsubtype" id="subsubtype"> <input type=hidden
							name="addsubtype" id="addsubtype">
						</td>
					</tr>
					<tr height=30>
						<td>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
							class="smallBtn" value=" ӡ  ֤ " onclick="yz()"> <input
							type="button" class="smallBtn" value="��ǰ���" onclick="dqrk()">
							<input type="button" class="smallBtn" value="�������"
							onclick="dqrk()"> <!--���Ķ������ǽ��û������ݴӸ������ݿ⵼�뵽data����Ŀ��С�-->


						</td>
					</tr>
					<tr height=30>
						<td>&nbsp;&nbsp;������� <input type="text" name="xmbh" id="xmbh"
							size=12 value=""> <input type="button" class="smallBtn"
							value="ͳ����Ŀ" onclick="queryOne()">
						</td>
					</tr>
					<tr height=30>
						<td>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
							class="smallBtn" value="������������" onclick="bqspnr()"> <input
							type="button" class="smallBtn" value="�����������" onclick="bqdhnr()">
							<!--����Ķ������ǽ��û������ݴӸ������ݿ����µ��뵽data����Ŀ��С�-->
						</td>
					</tr>
				</table>

			</center>
		</div>
		<!-- ��ѯ��� -->
		<div id="search_result" style="display: none;margin-top: 20px">
			<div data-options="region:'center',border:false"
				style="overflow:hidden;width:80%; float:left;padding-left: 100px;">
				<table class="easyui-datagrid" title="��ѯ���" id="search_result_table">
				</table>
			</div>
		</div>
		<div
			style="overflow-x: auto; overflow-y: auto; height: 500px; width:100%;">
</body>
</html>
