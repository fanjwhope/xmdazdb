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
<title>正式入库</title>
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
			alert('请输入档案编号');			
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
								title : '项目编号',
								width : 220
							}, {
								field : 'ywhc',
								title : '协议号',
								width : 220
							}, {
								field : 'dhsl',
								title : '贷后数量',
								width : 340
							}, {
								field : 'spsl',
								title : '审批数量',
								width : 340
							} ] ]
						});
		}
	}
	
	function yz(){
		if($("#ywhc").val()==''){
			alert('请输入协议号');			
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
								title : '项目名称',
								width : 340
							}, {
								field : 'xmbh',
								title : '档案编号',
								width : 140
							}, {
								field : 'ywhc',
								title : '贷款协议号',
								width : 120
							} , {
								field : 'qymc',
								title : '借款人名称',
								width : 220
							}] ]
						});
		}
	}
	
	function dqrk(){
		if($("#ywhc").val()==''){
			alert('请输入协议号');	
			return;		
		}
		$.ajax({
    		  type:'post',
    	      url:'<%=path%>/do/ZsrkDo/dqrk?zdbdbName=<%=zdbdbName%>&xmfzrid='+$("#xmzg").combobox('getValue')+'&ywhc='
									+ $("#ywhc").val(),
    	      dataType:'json',
    	      success:function(data){
    	    	  $.messager.show({
								title : '提示',
								msg : data.msg
							});
    	      }
    	  });
	}
	
	function bqspnr(){
		if($("#ywhc").val()==''){
			alert('请输入协议号');
			return;			
		}
		if($("#xmbh").val()==''){
			alert('请输入档案编号');	
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
								title : '提示',
								msg : data.msg
							});
    	      }
    	  });
	}
	
	function bqdhnr(){
		if($("#ywhc").val()==''){
			alert('请输入协议号');
			return;			
		}
		if($("#xmbh").val()==''){
			alert('请输入档案编号');	
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
								title : '提示',
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
				<font color=red style='font-size:16px'><b>协议号不存在!!</b></font>
			</center>
		</div>
		<div id="search" style="display: block;margin-top: 20px">
			<center>
				<table border="1" style="border-collapse:collapse" cellspacing="0"
					cellpadding="0" width="360">
					<tr>
						<td class="TDbgcolor1">&nbsp; 正式入库</td>
					</tr>
					<tr height="28">
						<td>&nbsp;&nbsp;&nbsp;&nbsp; 项 目主 管 <input name="xmzg"
							id="xmzg" style='width:153px;font-size:14px'>
						</td>
					</tr>
					<tr height="28">
						<td>&nbsp;&nbsp;&nbsp;&nbsp; 贷款协议号 <input type=text
							name="ywhc" id="ywhc" value=""> <input type=hidden
							name="subtype" id="subtype"> <input type=hidden
							name="subsubtype" id="subsubtype"> <input type=hidden
							name="addsubtype" id="addsubtype">
						</td>
					</tr>
					<tr height=30>
						<td>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
							class="smallBtn" value=" 印  证 " onclick="yz()"> <input
							type="button" class="smallBtn" value="贷前入库" onclick="dqrk()">
							<input type="button" class="smallBtn" value="贷后入库"
							onclick="dqrk()"> <!--入库的动作就是将用户的数据从个人数据库导入到data管理的库中。-->


						</td>
					</tr>
					<tr height=30>
						<td>&nbsp;&nbsp;档案编号 <input type="text" name="xmbh" id="xmbh"
							size=12 value=""> <input type="button" class="smallBtn"
							value="统计项目" onclick="queryOne()">
						</td>
					</tr>
					<tr height=30>
						<td>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
							class="smallBtn" value="补齐审批内容" onclick="bqspnr()"> <input
							type="button" class="smallBtn" value="补齐贷后内容" onclick="bqdhnr()">
							<!--补齐的动作就是将用户的数据从个人数据库重新导入到data管理的库中。-->
						</td>
					</tr>
				</table>

			</center>
		</div>
		<!-- 查询结果 -->
		<div id="search_result" style="display: none;margin-top: 20px">
			<div data-options="region:'center',border:false"
				style="overflow:hidden;width:80%; float:left;padding-left: 100px;">
				<table class="easyui-datagrid" title="查询结果" id="search_result_table">
				</table>
			</div>
		</div>
		<div
			style="overflow-x: auto; overflow-y: auto; height: 500px; width:100%;">
</body>
</html>
