<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String cxt = request.getContextPath();
	String zdbdbName=request.getParameter("zdbdbName");
	
%>

<html>
<head>
<jsp:include page="/publicJsp/libInclude.jsp" />
<script type="text/javascript">
	$(document).ready(function() {	
	 var datagrid;
	  var editRow = undefined;
	  var selectRow = undefined;
	  var action=0; //0是初始状态，1表示添加，2表示修改.
	datagrid=$('#dg').datagrid({   
    url:'<%=cxt%>/do/ZdbStandardDo/getList?zdbdbName=<%=zdbdbName%>',
											columns : [ [ {
												field : 'id',
												checkbox : true
											}, {
												field : 'spnr',
												title : '审批内容管理(双击一行查看内容维护管理)',
												align : 'center',
												fitColumns:true,
												editor : {
													type : 'validatebox',
													options : {
														required : true
													}
												}
											} ] ],
											singleSelect : true,
											rownumbers : true,
											striped:true,
											toolbar : [
													{
														text : '添加',
														iconCls : 'icon-add',
														handler : function() {//添加列表的操作按钮添加，修改，删除等
															//添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
															if (editRow != undefined) {
																datagrid
																		.datagrid(
																				"endEdit",
																				editRow);
															}
															//添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
															if (editRow == undefined) {
																action=1;
																var rows = datagrid
																		.datagrid("getRows").length;
																datagrid
																		.datagrid(
																				"insertRow",
																				{
																					index : rows, // index start with 0
																					row : {

																					}
																				});
																//将新插入的那一行开户编辑状态
																datagrid
																		.datagrid(
																				"beginEdit",
																				rows);
																//给当前编辑的行赋值
																editRow = rows;
															}

														}
													},
													'-',
														{
														text : '上移',
														iconCls : 'icon-undo',
														handler : function() {//添加列表的操作按钮添加，修改，删除等												
														var rows = datagrid
																	.datagrid("getSelections");
															selectRow=datagrid.datagrid('getRowIndex',rows[0]);
															//如果只选择了一行则可以进行修改，否则不操作
															if (rows.length == 1&&selectRow>0) {														
																	selectRow=selectRow-1;
																		$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDo/moveUp?zdbdbName=<%=zdbdbName%>",
																		   	      type:"post",
																		   	      data:{'id':rows[0].id},
																		   	      success:function(result){
																		   	      var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																		   	        datagrid.datagrid('reload');
																					
																		   	      }
																		   	   });
																
															}
														
														}
													},
													'-',
													{
														text : '下移',
														iconCls : 'icon-redo',
														handler : function() {//添加列表的操作按钮添加，修改，删除等												
																	var rows = datagrid
																	.datagrid("getSelections");
																	var sumRows=datagrid
																	.datagrid("getRows").length;
																	selectRow=datagrid.datagrid('getRowIndex',rows[0]);
															//如果只选择了一行则可以进行修改，否则不操作
															if (rows.length == 1&&selectRow<sumRows-1) {																
																	selectRow=selectRow+1;
																		$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDo/moveDown?zdbdbName=<%=zdbdbName%>",
																		   	      type:"post",
																		   	      data:{'id':rows[0].id},
																		   	      success:function(result){
																		   	       var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																		   	        datagrid.datagrid('reload');	   	      											   	     
																		   	      }
																		   	   });
																
															}
														
														
														}
													},
													'-',
													{
														text : '删除',
														iconCls : 'icon-remove',
														handler : function() {
															//删除时先获取选择行
															var rows = datagrid
																	.datagrid("getSelections");
															//选择要删除的行
															if (rows.length > 0) {
																$.messager
																		.confirm(
																				"提示",
																				"你确定要删除吗?",
																				function(
																						r) {
																					if (r) {
																						$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDo/delete?zdbdbName=<%=zdbdbName%>",
																									type : "post",
																									data : {
																										'id' : rows[0].id
																									},
																									success : function(result) {
																		   	       var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																										datagrid
																												.datagrid('reload');
																									}
																								});
																					}
																				});
															} else {
																$.messager
																		.alert(
																				"提示",
																				"请选择要删除的行",
																				"error");
															}
														}
													},
													'-',
													{
														text : '修改',
														iconCls : 'icon-edit',
														handler : function() {
															//修改时要获取选择到的行
															var rows = datagrid
																	.datagrid("getSelections");
															//如果只选择了一行则可以进行修改，否则不操作
															if (rows.length == 1) {
																action = 2;
																//修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
																if (editRow != undefined) {
																	datagrid
																			.datagrid(
																					"endEdit",
																					editRow);
																}
																//当无编辑行时
																if (editRow == undefined) {
																	//获取到当前选择行的下标
																	var index = datagrid
																			.datagrid(
																					"getRowIndex",
																					rows[0]);
																	//开启编辑
																	datagrid
																			.datagrid(
																					"beginEdit",
																					index);
																	//把当前开启编辑的行赋值给全局变量editRow
																	editRow = index;
																	//当开启了当前选择行的编辑状态之后，
																	//应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
																	datagrid
																			.datagrid("unselectAll");
																}
															}
														}
													},
													'-',
													{
														text : '保存',
														iconCls : 'icon-save',
														handler : function() {
															//保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
															datagrid.datagrid(
																	"endEdit",
																	editRow);

														}
													},
													'-',
													{
														text : '取消编辑',
														iconCls : 'icon-redo',
														handler : function() {
															action = 0;
															//取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
															editRow = undefined;
															datagrid
																	.datagrid("rejectChanges");
															datagrid
																	.datagrid("unselectAll");
														}
													}, '-' ],
											onAfterEdit : function(rowIndex,
													rowData, changes) {
												//endEdit该方法触发此事件
												console.info(rowData);
												editRow = undefined;
												if (action == 1) {
																	$.ajax({
																		    url:"<%=cxt%>/do/ZdbStandardDo/add?zdbdbName=<%=zdbdbName%>",
																						type : "post",
																						data : {
																								'spnr' : rowData.spnr
																								},
																								success : function(result) {
																					 var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																								datagrid
																										.datagrid('reload');
																									}
																								});
												} else if (action == 2) {
																															$.ajax({
																		    url:"<%=cxt%>/do/ZdbStandardDo/update?zdbdbName=<%=zdbdbName%>",
																						type : "post",
																						data : {
																								'id': rowData.id,
																								'spnr' : rowData.spnr
																								},
																								success : function(result) {
																									 var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																								datagrid
																										.datagrid('reload');
																									}
																								});
												}
												action =0;
											},
											onDblClickRow : function(rowIndex,
													rowData) {
													DbClick(rowData.id,rowData.spnr);
											},
											onLoadSuccess : function(data) {
												datagrid.datagrid('selectRow',
														selectRow);
											}
										});
					});
					

	function DbClick(id,spnr){
	 var datagrid;
	  var editRow = undefined;
	  var selectRow = undefined;
	  var action=0; //0是初始状态，1表示添加，2表示修改.
	datagrid=$('#dg-detail').datagrid({   
    url:'<%=cxt%>/do/ZdbStandardDetailDo/getList?zdbdbName=<%=zdbdbName%>&id='+id,
											columns : [ [ {
												field : 'dalx',
												checkbox : true
											}, {
												field : 'damc',
												title : '('+spnr+')内容维护管理',
												align : 'center',
												fitColumns:true,
												editor : {
													type : 'validatebox',
													options : {
														required : true
													}
												}
											} ] ],
											singleSelect : true,
											rownumbers : true,
											striped:true,
											toolbar : [
													{
														text : '添加',
														iconCls : 'icon-add',
														handler : function() {//添加列表的操作按钮添加，修改，删除等
															//添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
															if (editRow != undefined) {
																datagrid
																		.datagrid(
																				"endEdit",
																				editRow);
															}
															//添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
															if (editRow == undefined) {
																action=1;
																var rows = datagrid
																		.datagrid("getRows").length;
																datagrid
																		.datagrid(
																				"insertRow",
																				{
																					index : rows, // index start with 0
																					row : {

																					}
																				});
																//将新插入的那一行开户编辑状态
																datagrid
																		.datagrid(
																				"beginEdit",
																				rows);
																//给当前编辑的行赋值
																editRow = rows;
															}

														}
													},
													'-',
														{
														text : '上移',
														iconCls : 'icon-undo',
														handler : function() {//添加列表的操作按钮添加，修改，删除等												
														var rows = datagrid
																	.datagrid("getSelections");
															selectRow=datagrid.datagrid('getRowIndex',rows[0]);
															//如果只选择了一行则可以进行修改，否则不操作
															if (rows.length == 1&&selectRow>0) {														
																	selectRow=selectRow-1;
																		$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDetailDo/moveUp?zdbdbName=<%=zdbdbName%>",
																		   	      type:"post",
																		   	      data:{'dalx':rows[0].dalx,'id':id},
																		   	      success:function(result){
																		   	      	 var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																		   	        datagrid.datagrid('reload');
																		   	      }
																		   	   });
																
															}
														
														}
													},
													'-',
													{
														text : '下移',
														iconCls : 'icon-redo',
														handler : function() {//添加列表的操作按钮添加，修改，删除等												
																	var rows = datagrid
																	.datagrid("getSelections");
																	var sumRows=datagrid
																	.datagrid("getRows").length;
																	selectRow=datagrid.datagrid('getRowIndex',rows[0]);
															//如果只选择了一行则可以进行修改，否则不操作
															if (rows.length == 1&&selectRow<sumRows-1) {																
																	selectRow=selectRow+1;
																		$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDetailDo/moveDown?zdbdbName=<%=zdbdbName%>",
																		   	      type:"post",
																		   	      data:{'dalx':rows[0].dalx,'id':id},
																		   	      success:function(result){
																		   	       var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																		   	        datagrid.datagrid('reload');	   	      											   	     
																		   	      }
																		   	   });
																
															}
														
														
														}
													},
													'-',
													{
														text : '删除',
														iconCls : 'icon-remove',
														handler : function() {
															//删除时先获取选择行
															var rows = datagrid
																	.datagrid("getSelections");
															//选择要删除的行
															if (rows.length > 0) {
																$.messager
																		.confirm(
																				"提示",
																				"你确定要删除吗?",
																				function(
																						r) {
																					if (r) {
																						$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDetailDo/delete?zdbdbName=<%=zdbdbName%>",
																									type : "post",
																									data : {
																										'id':id,
																										'dalx' : rows[0].dalx
																									},
																									success : function(result) {
																									 var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																										datagrid
																												.datagrid('reload');
																									}
																								});
																					}
																				});
															} else {
																$.messager
																		.alert(
																				"提示",
																				"请选择要删除的行",
																				"error");
															}
														}
													},
													'-',
													{
														text : '修改',
														iconCls : 'icon-edit',
														handler : function() {
															//修改时要获取选择到的行
															var rows = datagrid
																	.datagrid("getSelections");
															//如果只选择了一行则可以进行修改，否则不操作
															if (rows.length == 1) {
																action = 2;
																//修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
																if (editRow != undefined) {
																	datagrid
																			.datagrid(
																					"endEdit",
																					editRow);
																}
																//当无编辑行时
																if (editRow == undefined) {
																	//获取到当前选择行的下标
																	var index = datagrid
																			.datagrid(
																					"getRowIndex",
																					rows[0]);
																	//开启编辑
																	datagrid
																			.datagrid(
																					"beginEdit",
																					index);
																	//把当前开启编辑的行赋值给全局变量editRow
																	editRow = index;
																	//当开启了当前选择行的编辑状态之后，
																	//应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
																	datagrid
																			.datagrid("unselectAll");
																}
															}
														}
													},
													'-',
													{
														text : '保存',
														iconCls : 'icon-save',
														handler : function() {
															//保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
															datagrid.datagrid(
																	"endEdit",
																	editRow);

														}
													},
													'-',
													{
														text : '取消编辑',
														iconCls : 'icon-redo',
														handler : function() {
															action = 0;
															//取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
															editRow = undefined;
															datagrid
																	.datagrid("rejectChanges");
															datagrid
																	.datagrid("unselectAll");
														}
													}, '-' ],
											onAfterEdit : function(rowIndex,
													rowData, changes) {
												//endEdit该方法触发此事件
												console.info(rowData);
												editRow = undefined;
												if (action == 1) {
																	$.ajax({
																		    url:"<%=cxt%>/do/ZdbStandardDetailDo/add?zdbdbName=<%=zdbdbName%>",
																						type : "post",
																						data : {
																								'id' : id,
																								'damc' : rowData.damc
																								},
																								success : function(result) {
																								 var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'提示',
																									msg:json.msg
																								}); 
																								datagrid
																										.datagrid('reload');
																									}
																								});
												} else if (action == 2) {
											$.ajax({
												url:"<%=cxt%>/do/ZdbStandardDetailDo/update?zdbdbName=<%=zdbdbName%>",
												type : "post",
												data : {
													'id' : id,
													'dalx' : rowData.dalx,
													'damc' : rowData.damc
												},
												success : function(result) {
													var json = JSON
															.parse(result);
													$.messager.show({
														title : '提示',
														msg : json.msg
													});
													datagrid.datagrid('reload');
												}
											});
								}
								action = 0;
							},
							onDblClickRow : function(rowIndex, rowData) {

							},
							onLoadSuccess : function(data) {
								datagrid.datagrid('selectRow', selectRow);
							}
						});
	}
</script>

</head>

<body>
	<div data-options="region:'center',border:false"
		style="overflow:hidden;width:40%; float:left;padding-left: 100px;">
		<table id="dg"></table>
	</div>
	<div data-options="region:'center',border:false"
		style="overflow:hidden;width:40%; float:right;padding-right: 100px;">
		<table id="dg-detail"></table>
	</div>
</body>
</html>
