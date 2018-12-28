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
	  var action=0; //0�ǳ�ʼ״̬��1��ʾ��ӣ�2��ʾ�޸�.
	datagrid=$('#dg').datagrid({   
    url:'<%=cxt%>/do/ZdbStandardDo/getList?zdbdbName=<%=zdbdbName%>',
											columns : [ [ {
												field : 'id',
												checkbox : true
											}, {
												field : 'spnr',
												title : '�������ݹ���(˫��һ�в鿴����ά������)',
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
														text : '���',
														iconCls : 'icon-add',
														handler : function() {//����б�Ĳ�����ť��ӣ��޸ģ�ɾ����
															//���ʱ���ж��Ƿ��п����༭���У��������ѿ����༭�����н����༭
															if (editRow != undefined) {
																datagrid
																		.datagrid(
																				"endEdit",
																				editRow);
															}
															//���ʱ���û�����ڱ༭���У�����datagrid�ĵ�һ�в���һ��
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
																//���²������һ�п����༭״̬
																datagrid
																		.datagrid(
																				"beginEdit",
																				rows);
																//����ǰ�༭���и�ֵ
																editRow = rows;
															}

														}
													},
													'-',
														{
														text : '����',
														iconCls : 'icon-undo',
														handler : function() {//����б�Ĳ�����ť��ӣ��޸ģ�ɾ����												
														var rows = datagrid
																	.datagrid("getSelections");
															selectRow=datagrid.datagrid('getRowIndex',rows[0]);
															//���ֻѡ����һ������Խ����޸ģ����򲻲���
															if (rows.length == 1&&selectRow>0) {														
																	selectRow=selectRow-1;
																		$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDo/moveUp?zdbdbName=<%=zdbdbName%>",
																		   	      type:"post",
																		   	      data:{'id':rows[0].id},
																		   	      success:function(result){
																		   	      var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'��ʾ',
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
														text : '����',
														iconCls : 'icon-redo',
														handler : function() {//����б�Ĳ�����ť��ӣ��޸ģ�ɾ����												
																	var rows = datagrid
																	.datagrid("getSelections");
																	var sumRows=datagrid
																	.datagrid("getRows").length;
																	selectRow=datagrid.datagrid('getRowIndex',rows[0]);
															//���ֻѡ����һ������Խ����޸ģ����򲻲���
															if (rows.length == 1&&selectRow<sumRows-1) {																
																	selectRow=selectRow+1;
																		$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDo/moveDown?zdbdbName=<%=zdbdbName%>",
																		   	      type:"post",
																		   	      data:{'id':rows[0].id},
																		   	      success:function(result){
																		   	       var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'��ʾ',
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
														text : 'ɾ��',
														iconCls : 'icon-remove',
														handler : function() {
															//ɾ��ʱ�Ȼ�ȡѡ����
															var rows = datagrid
																	.datagrid("getSelections");
															//ѡ��Ҫɾ������
															if (rows.length > 0) {
																$.messager
																		.confirm(
																				"��ʾ",
																				"��ȷ��Ҫɾ����?",
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
																									title:'��ʾ',
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
																				"��ʾ",
																				"��ѡ��Ҫɾ������",
																				"error");
															}
														}
													},
													'-',
													{
														text : '�޸�',
														iconCls : 'icon-edit',
														handler : function() {
															//�޸�ʱҪ��ȡѡ�񵽵���
															var rows = datagrid
																	.datagrid("getSelections");
															//���ֻѡ����һ������Խ����޸ģ����򲻲���
															if (rows.length == 1) {
																action = 2;
																//�޸�֮ǰ�ȹر��Ѿ������ı༭�У�������endEdit�÷���ʱ�ᴥ��onAfterEdit�¼�
																if (editRow != undefined) {
																	datagrid
																			.datagrid(
																					"endEdit",
																					editRow);
																}
																//���ޱ༭��ʱ
																if (editRow == undefined) {
																	//��ȡ����ǰѡ���е��±�
																	var index = datagrid
																			.datagrid(
																					"getRowIndex",
																					rows[0]);
																	//�����༭
																	datagrid
																			.datagrid(
																					"beginEdit",
																					index);
																	//�ѵ�ǰ�����༭���и�ֵ��ȫ�ֱ���editRow
																	editRow = index;
																	//�������˵�ǰѡ���еı༭״̬֮��
																	//Ӧ��ȡ����ǰ�б������ѡ���У�Ҫ��Ȼ˫��֮���޷���ѡ�������н��б༭
																	datagrid
																			.datagrid("unselectAll");
																}
															}
														}
													},
													'-',
													{
														text : '����',
														iconCls : 'icon-save',
														handler : function() {
															//����ʱ������ǰ�༭���У��Զ�����onAfterEdit�¼����Ҫ���̨�����ɽ�����ͨ��Ajax�ύ��̨
															datagrid.datagrid(
																	"endEdit",
																	editRow);

														}
													},
													'-',
													{
														text : 'ȡ���༭',
														iconCls : 'icon-redo',
														handler : function() {
															action = 0;
															//ȡ����ǰ�༭�аѵ�ǰ�༭�а�undefined�ع��ı������,ȡ��ѡ�����
															editRow = undefined;
															datagrid
																	.datagrid("rejectChanges");
															datagrid
																	.datagrid("unselectAll");
														}
													}, '-' ],
											onAfterEdit : function(rowIndex,
													rowData, changes) {
												//endEdit�÷����������¼�
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
																									title:'��ʾ',
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
																									title:'��ʾ',
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
	  var action=0; //0�ǳ�ʼ״̬��1��ʾ��ӣ�2��ʾ�޸�.
	datagrid=$('#dg-detail').datagrid({   
    url:'<%=cxt%>/do/ZdbStandardDetailDo/getList?zdbdbName=<%=zdbdbName%>&id='+id,
											columns : [ [ {
												field : 'dalx',
												checkbox : true
											}, {
												field : 'damc',
												title : '('+spnr+')����ά������',
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
														text : '���',
														iconCls : 'icon-add',
														handler : function() {//����б�Ĳ�����ť��ӣ��޸ģ�ɾ����
															//���ʱ���ж��Ƿ��п����༭���У��������ѿ����༭�����н����༭
															if (editRow != undefined) {
																datagrid
																		.datagrid(
																				"endEdit",
																				editRow);
															}
															//���ʱ���û�����ڱ༭���У�����datagrid�ĵ�һ�в���һ��
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
																//���²������һ�п����༭״̬
																datagrid
																		.datagrid(
																				"beginEdit",
																				rows);
																//����ǰ�༭���и�ֵ
																editRow = rows;
															}

														}
													},
													'-',
														{
														text : '����',
														iconCls : 'icon-undo',
														handler : function() {//����б�Ĳ�����ť��ӣ��޸ģ�ɾ����												
														var rows = datagrid
																	.datagrid("getSelections");
															selectRow=datagrid.datagrid('getRowIndex',rows[0]);
															//���ֻѡ����һ������Խ����޸ģ����򲻲���
															if (rows.length == 1&&selectRow>0) {														
																	selectRow=selectRow-1;
																		$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDetailDo/moveUp?zdbdbName=<%=zdbdbName%>",
																		   	      type:"post",
																		   	      data:{'dalx':rows[0].dalx,'id':id},
																		   	      success:function(result){
																		   	      	 var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'��ʾ',
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
														text : '����',
														iconCls : 'icon-redo',
														handler : function() {//����б�Ĳ�����ť��ӣ��޸ģ�ɾ����												
																	var rows = datagrid
																	.datagrid("getSelections");
																	var sumRows=datagrid
																	.datagrid("getRows").length;
																	selectRow=datagrid.datagrid('getRowIndex',rows[0]);
															//���ֻѡ����һ������Խ����޸ģ����򲻲���
															if (rows.length == 1&&selectRow<sumRows-1) {																
																	selectRow=selectRow+1;
																		$.ajax({
																		   	      url:"<%=cxt%>/do/ZdbStandardDetailDo/moveDown?zdbdbName=<%=zdbdbName%>",
																		   	      type:"post",
																		   	      data:{'dalx':rows[0].dalx,'id':id},
																		   	      success:function(result){
																		   	       var json = JSON.parse(result);
																		   	      $.messager.show({
																									title:'��ʾ',
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
														text : 'ɾ��',
														iconCls : 'icon-remove',
														handler : function() {
															//ɾ��ʱ�Ȼ�ȡѡ����
															var rows = datagrid
																	.datagrid("getSelections");
															//ѡ��Ҫɾ������
															if (rows.length > 0) {
																$.messager
																		.confirm(
																				"��ʾ",
																				"��ȷ��Ҫɾ����?",
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
																									title:'��ʾ',
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
																				"��ʾ",
																				"��ѡ��Ҫɾ������",
																				"error");
															}
														}
													},
													'-',
													{
														text : '�޸�',
														iconCls : 'icon-edit',
														handler : function() {
															//�޸�ʱҪ��ȡѡ�񵽵���
															var rows = datagrid
																	.datagrid("getSelections");
															//���ֻѡ����һ������Խ����޸ģ����򲻲���
															if (rows.length == 1) {
																action = 2;
																//�޸�֮ǰ�ȹر��Ѿ������ı༭�У�������endEdit�÷���ʱ�ᴥ��onAfterEdit�¼�
																if (editRow != undefined) {
																	datagrid
																			.datagrid(
																					"endEdit",
																					editRow);
																}
																//���ޱ༭��ʱ
																if (editRow == undefined) {
																	//��ȡ����ǰѡ���е��±�
																	var index = datagrid
																			.datagrid(
																					"getRowIndex",
																					rows[0]);
																	//�����༭
																	datagrid
																			.datagrid(
																					"beginEdit",
																					index);
																	//�ѵ�ǰ�����༭���и�ֵ��ȫ�ֱ���editRow
																	editRow = index;
																	//�������˵�ǰѡ���еı༭״̬֮��
																	//Ӧ��ȡ����ǰ�б������ѡ���У�Ҫ��Ȼ˫��֮���޷���ѡ�������н��б༭
																	datagrid
																			.datagrid("unselectAll");
																}
															}
														}
													},
													'-',
													{
														text : '����',
														iconCls : 'icon-save',
														handler : function() {
															//����ʱ������ǰ�༭���У��Զ�����onAfterEdit�¼����Ҫ���̨�����ɽ�����ͨ��Ajax�ύ��̨
															datagrid.datagrid(
																	"endEdit",
																	editRow);

														}
													},
													'-',
													{
														text : 'ȡ���༭',
														iconCls : 'icon-redo',
														handler : function() {
															action = 0;
															//ȡ����ǰ�༭�аѵ�ǰ�༭�а�undefined�ع��ı������,ȡ��ѡ�����
															editRow = undefined;
															datagrid
																	.datagrid("rejectChanges");
															datagrid
																	.datagrid("unselectAll");
														}
													}, '-' ],
											onAfterEdit : function(rowIndex,
													rowData, changes) {
												//endEdit�÷����������¼�
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
																									title:'��ʾ',
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
														title : '��ʾ',
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
