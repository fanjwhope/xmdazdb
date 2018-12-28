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

		//设置tool居中显示
		var sumWidth = $(document).width();
		var divWidth = $("#tool_").width();
		center = sumWidth / 2 - divWidth / 2;
		$("#tool").css({
			'padding-left' : center
		});
			
		//项目说明--》添加--》存盘
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
    	    						title : '提示',
    	    						msg : data.msg
    	    					});
				}
			
			});
			
		
		});
		
		
		//项目说明--》添加--》著录
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

	//添加
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
	
	
	
	
	//添加--->查询
	function add_search(){
		//设置工具栏居中显示
		var sumWidth = $(document).width();
		var myWidth = $("#dk").width();
		var padding = sumWidth / 2 - myWidth / 2;
		
		//项目类型
		var xmlx_ = $("#xmlx_").combobox("getValue");
		//原项目编号
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

			//如果原项目编号不为空，则根据选定项目类型，显示相应表单
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
			alert("档案编号不能为空！");
		}
	}

	//外国政府赠款项目情况说明表 界面--->返回按钮
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

	//外国政府赠款项目情况说明表 界面--->存盘按钮
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
						title : '借款人名称',
						width : 220
					}, {
						field : 'xmmc',
						title : '项目名称',
						width : 340
					}, {
						field : 'dkxyh',
						title : '贷款协议号',
						width : 120
					}, {
						field : 'newxmbh',
						title : '档案编号',
						width : 120
					}, {
						field : 'xmfzr',
						title : '项目主管',
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
			<div id="tool_" class="easyui-panel" title="贷款/赠款项目情况说明"
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
				<p>选择“添加”可以添加新的项目情况说明，选择“查询”可以查询和修改已添加的情况说明</p>
				<p style="float:right;">
					<a id="btn_add" href="javascript:add()" class="easyui-linkbutton"
						data-options="iconCls:'icon-add'">添加</a> <a id="btn_e"
						href="javascript:search()" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'">查询</a>
				</p>
			</div>
		</div>
		<!-- 添加 -->
		<div id="add" style="display: none;padding-top:20px;">

			<div id="add_tool" class="easyui-panel" title="贷款/赠款项目情况说明"
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
					项目类型：<select id="xmlx_" class="easyui-combobox" name="xmlx_"
						style="width:100px;" data-options="panelHeight:100">
						<option value="dk">贷款</option>
						<option value="zk">赠款</option>
					</select> &nbsp;&nbsp; 原档案编号：<input id="dabh" style="width: 120px;" />&nbsp;&nbsp;
					<a id="btn" href="javascript:add_search()"
						class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
				</p>
			</div>

		</div>

		<!-- 查询 -->
		<div id="search" style="display: none;">
			<table width=450 cellspacing=1 cellpadding="5" align="center"
				border=1>
				<tr>
					<td width="382" height="16" bgcolor="buttonshadow" colspan="4"><font
						style='font-size:12px;color:#ffffff'>贷款/赠款项目情况说明</font></td>
				</tr>
				<tr>
					<td width="72" height="13" valign=center><input type=hidden
						name=listtype id=listtype> 项目类型&nbsp;</td>
					<td width="101" height="13" valign=center><select name=smtype
						id="smtype_query">
							<option value=''></option>
							<option value='dk'>贷款说明</option>
							<option value='zk'>赠款说明</option>
					</select></td>
					<td width="68" height="13" valign=center>档案编号&nbsp;</td>
					<td width="101" height="13" valign=center><input type=text
						id="newxmbh_query" size=15></td>
				</tr>
				<tr>
					<td width="72" height="-4" valign=center>贷款协议号&nbsp;</td>
					<td width="101" height="-4" valign=center><input type=text
						id="dkxyh_query" size=15></td>
					<td width="68" height="-4" valign=center>转贷协议号&nbsp;</td>
					<td width="101" height="-4" valign=center><input type=text
						id="ywhc_query" size=15></td>
				</tr>
				<tr>
					<td width="72" height="-4" valign=center>借/赠款人&nbsp;</td>
					<td width="101" height="-4" valign=center><input type=text
						id="qymc_query" size=15></td>
					<td width="68" height="-4" valign=center>项目名称</td>
					<td width="101" height="-4" valign=center><input type=text
						id="xmmc_query" size=15></td>
				</tr>
				<tr>
					<td width="382" height="-4" valign=center colspan="4"><input
						type=image onclick="queryAll()" name="I1"></td>
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

		<!-- 外国政府贷款说明表 -->
		<div id="dk"
			style="display: none;padding-top:20px;padding-bottom:20px;">

			<h2 id="dk_title" align="center" style="width: 700px;display: none;">外国政府贷款项目情况说明表
			</h2>
			<h2 id="zk_title" align="center" style="width: 700px;display: none;">外国政府赠款项目情况说明表
			</h2>
			<form id="xmsm">
				<table class="table" width="700" border="1">

					<tr>
						<td colspan="2" height="40">项目类型：<select id="xmlx"
							name="xmlx"><option value="dk">贷款</option>
								<option value="zk">赠款</option></select>&nbsp;&nbsp;原档案编号：<input
							id="ydabh" name="ydabh" class="input" style="width:120px;" />&nbsp;&nbsp;<input
							type="button" class="button" value="返回" id="back" /><input
							type="button" value="著录" class="button" style="display: none;"
							id="write" />&nbsp;&nbsp;<input type="button" value="存盘"
							class="button" id="save" />&nbsp;&nbsp;<input type="button"
							value="打印" class="button" id="print" /></td>
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
