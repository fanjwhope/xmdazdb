<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");
%>

<html>
  <head>
    <base href="<%=basePath%>">
     <jsp:include page="/publicJsp/libInclude.jsp" />
     <style type="text/css">

	 table.xmwjList th, table.xmwjList td{
				text-align: center;
				padding: 10px 5px 10px 5px;
				border-style: solid;
				border-width: 0px;
				font-size: 12px;
			} 
		table.tableForm th{
	        text-align: right;
	        padding: 2px 0px 2px 0px;
	        font-family:"����", "Arial Narrow"; FONT-SIZE: 12px;
	        font-size: 12px;
	   } 
	   table.tableForm td{
	        text-align: left;
	        padding: 5px 0px 5px 0px;
			font-size: 12px;
			}	
		 .font14{font-size:14px;color:#000080}
	</style>
    <script type="text/javascript">

        $(function(){
        	$('#projectType').combobox({ 
        		url:'<%=cxt%>/do/ProjectTypeDo/getAllProjectType?zdbdbName=<%=zdbdbName%>',
        		valueField:'id',   
         	    textField:'name',
         	    editable:false
        	});
        	
        	$('#square').combobox({
        		valueField:'id',   
         	    textField:'name',
         	    editable:false,
         	    data:[{"id":"2","name":"ȫ��","selected":true},{"id":"1","name":"��"},{"id":"0","name":"��"}]
        	});
        	
        	$('#searchDiv').show();
        	$('#listPage').hide();
        });
        
        function _search(){
        	$('#searchDiv').hide();
        	$('#listPage').show();
        	$('#xmwjList').datagrid({
      			url:"<%=cxt%>/do/ArchiveDo/getList?zdbdbName=<%=zdbdbName%>&dkqx="+$('#dkqx').val()+"&xmbh="+$('#xmbh').val()+"&xmmc="+$('#xmmc').val()+"&qymc="+$('#qymc').val()+"&ywhc="+$('#ywhc').val()+"&by1="+$('#by1').val()+"&projectType="+$('#projectType').combobox('getValue')+"&xmfzr="+$('#xmfzr').val()+"&by2="+$('#by2').val()+"&dkje="+$('#dkje').val()+"&tbDate="+$('#tbDate').datebox('getValue')+"&tbDateE="+$('#tbDateE').datebox('getValue')+"&square="+$('#square').combobox('getValue'),
     			rownumbers:true,
     			rowStyler: function(index,row){
     				if (row.yjflag==2){
     					return 'color:red;';
     				}
     			},
     			idField:'lsh',
     			fit:true,
     			fitColumns:true,
     			checkOnSelect:true,
     			selectOnCheck:true,
     			singleSelect:true,
     			checkbox:true,
     			pagination:true,
     			pageSize:20,
     			pageList:[20,30,40,50],
     			pagePosition:'bottom',
     			columns:[[{
     				title:'id',
     				field:'id',
     				checkbox:true
     				
     			},{
     				title:'Э���',
     				field:'lsh',
     				width:50,
     				sortable:true,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'����ͬ��',
     				field:'xmbh',
     				width:90,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'���������',
     				field:'qymc',
     				width:100,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'��Ŀ����',
     				field:'xmmc',
     				width:200,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'��Ŀ���',
     				field:'dkje',
     				width:150,
     				align:'right',
     				sortable:true,
     				formatter: function(value,row,index){
     					/* if(value==''||value==null){
     						value="0.00��("+row.dw+")";
     					}else{
     					    value=row.dkje+"��("+row.dw+")";
     					} */
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'��¼ʱ��',
     				field:'ljDate',
     				width:100,
     				sortable:true,
     				formatter: function(value,row,index){
     					var value=row.dasryy+"."+row.dasrmm+"."+row.dasrdd;
     					if(row.dasryy == '0' || row.dasrmm == '0' || row.dasrdd == '0')
     						value = '';
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'���ʱ��',
     				field:'tbDate',
     				width:100,
     				sortable:true,
     				formatter: function(value,row,index){
     					var value=row.tbyy+"."+row.tbmm+"."+row.tbdd;
     					if(row.tbyy == '0' || row.tbmm == '0' || row.tbdd == '0')
     						value = '';
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'����Э���',
     				field:'ywhc',
     				width:200,
     				sortable:true,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'��Ŀ����',
     				field:'xmfzr',
     				width:100,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			}]],
     			onSelect: function(rowIndex, rowData){
     				showForm(rowData.lsh,rowData.xmbh,rowData.yjflag);
     			}
     		});
        }
        
        function showForm(lsh,xmbh,yjflag){
        	if('zdb'=='<%=zdbdbName%>'){
        		$('#load').attr('src','pages/queryHuibianArchiveUpdateZDB.jsp?zdbdbName=<%=zdbdbName%>&lsh='+lsh+'&xmbh='+xmbh+'&yjflag='+yjflag);
        	}else{
        	    $('#load').attr('src','pages/queryHuibianArchiveUpdate.jsp?zdbdbName=<%=zdbdbName%>&lsh='+lsh+'&xmbh='+xmbh+'&yjflag='+yjflag+'&type=1');
        	}
        	$('#listPage').hide();
    		$('#modifyPage').show(); 
        }
        
        function resetD(){
        	$('#searchForm').form('clear');
        	$('#square').combobox('setValue','2');
        }
        
        
        function goBack(){
        	$('#searchDiv').show();
        	$('#listPage').hide();
        }
    </script>

  </head>
  
  <body class="easyui-layout" data-options="fit:true">
     <div id="listDiv" data-options="region:'center',border:false" style="overflow:hidden;">
        <div id="listPage" style="width:100%;height:100%;padding:0px;margin:0px;">
        
              <div id="lp2" class="easyui-layout" data-options="fit:true,border:false">
				  <div data-options="region:'north',border:false,title:'��Ŀ������ѯ���'" style="height:50px;overflow: hidden;" align="left">
					<form id="searchForm">
						<div class="datagrid-toolbar" style="width: 100%;height:100%;border:0px;">
							<table class="tableForm" style="width:800px;">
								<tr>
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="goBack();" data-options="iconCls:'icon-back', plain:true">����</a>
								</tr>
							</table>
						</div>
					</form>
				  </div>  
				  
                  <div data-options="region:'center',border:false" style="overflow:hidden;top:30px">
		            <table id="xmwjList"></table>
	              </div>
	         
	         </div> 
	        
        </div>
        
        <div id="modifyPage" style="width:100%;height:100%;padding:0px;margin:0px;display:none;">
	  	  <iframe id="load" style="width:100%;height:100%;" src="" scrolling="no" FRAMEBORDER="0"></iframe>
	   </div>
	   
	<div id="searchDiv"  style="display: none;">
	  	<BR>
		 <center>
		  <table border="3" cellpadding="0" cellspacing="0" width="490" bordercolorlight="#FFFFE8" bordercolordark="#999999" bgcolor="#FFFFE8" height="1">
		<tr>
		  <td width="100%" height="1" valign="top">
		   
		      <table border="0" cellpadding="0" cellspacing="0" width="100%" height="120">
		        <tr>
		          <td width="100%" bgcolor="#999999" height="16"><font style='font-size:10pt' color="#FFFFFF">[�����ѯ]�������ѯ������</font></td>
		        </tr>
				<tr>
					<td height="104" bordercolor="#FFFFE8">
						  <form action='' method="post">
							<table border="0" width="100%" height="131">
							   <br>
							  <tr >
							    <td  class="font14">��Ŀ���� <input name="xmmc" id="xmmc" type="text" style="width: 135px" /></td>
							    <td  class="font14">����� <input type="text" name="qymc" id="qymc" style="width: 150px"/></td>							  	
							  </tr>						   
							   <tr>
							     <td  class="font14">����Э��� <input name="ywhc" id="ywhc" type="text" style="width: 135px" /></td>
							     <td  class="font14">��&#12288;�� <input name="by1" id="by1" type="text" style="width: 150px"/></td>
							  </tr>
							 <tr>
							    <td  class="font14">��ĿƷ�� <input name="projectType" id="projectType" type="text"  style="width: 135px" /></td>
							    <td  class="font14">�Ŵ�Ա <input type="text" name="xmfzr" id="xmfzr" style="width: 150px"/></td>
							  </tr>
							   <tr>
							    <td  class="font14">�������� <input name="dkqx" id="dkqx" type="text" style="width: 135px" /></td>
							    <td  class="font14">��&#12288;�� <input name="dkje" id="dkje" type="text" style="width: 150px" /></td>
							  </tr>
							   <tr>
							    <td  class="font14" colspan="2">���ʱ�� <input name="tbDate" class="easyui-datebox" id="tbDate" type="text" style="width:90px;" /> �� <input name="tbDateE" id="tbDateE" class="easyui-datebox" type="text" style="width:90px;" />
							   &#12288;&#12288;&nbsp;�Ƿ���� <input name="square" id="square" type="text" style="width:60px;" /></td>
							   </tr> 
							   <tr>
							     <input name="by2" id="by2" type="hidden" style="width: 135px" />
							     <td  class="font14">������� <input name="xmbh" id="xmbh" type="text" style="width: 150px" /></td>
							  </tr>
							   <tr>
							    <td width="100%" colspan="2" height="37" bordercolor="#FFFFE8">
							              &nbsp;&nbsp;<img src="images/t_query.gif" onclick="_search();">
							             &nbsp;&nbsp;<img src="images/t_reset.gif" onclick="resetD();">
							      </td>
							  </tr>
							</table>
						</form>  
					</td>
		          <td height="104" bordercolor="#FFFFE8"></td>
		        </tr>
		      </table>
		     </td>
		    </tr>
		  </table>
		</center>
     </div>  
     
	</div>
	
	
     
  </body>
</html>
