<%@ page language="java" import="java.util.*,com.hr.util.*,com.hr.global.util.ArchiveUtil" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String archiveType=request.getParameter("archiveType");
String roles=request.getParameter("roles");
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
String deptNum=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String userTabName=dbName+"_xmwj_"+deptNum;
String dbName=request.getParameter("zdbdbName");

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
	        font-family:"宋体", "Arial Narrow"; FONT-SIZE: 12px;
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
        	$('#dbNameSearch').combobox({
	        	url:'<%=cxt%>/do/PrivilegeInfo/getAllBankNode',
	    		valueField:'id',   
	     	    textField:'bankName',
	     	    editable:false,
	     	   panelHeight:120,
	     	  onLoadSuccess: function () { //加载完成后,设置选中第一项
                  var val = $(this).combobox("getData");
                  for (var item in val[0]) {
                      if (item == "id") {
                          $(this).combobox("select", val[0][item]);
                      }
                  }
        	    },
        	  onSelect: function(rec){ 
        	    	var url='<%=cxt%>/do/ProjectTypeDo/getAllProjectType?dbNameSearch='+rec.id;
        	    	$('#projectType').combobox('reload', url);   
        	    }
        	 });
      
     	
        	
        	$('#square').combobox({
        		valueField:'id',   
         	    textField:'name',
         	    editable:false,
         	    panelHeight:70,
         	    data:[{"id":"2","name":"全部","selected":true},{"id":"1","name":"是"},{"id":"0","name":"否"}]
        	});
        	
        	$('#searchDiv').show();
        	$('#listPage').hide();
        });
        
        function _search(){
        	var dbNameSearch=$('#dbNameSearch').combobox('getValue');
        	var  dbNameText=$('#dbNameSearch').combobox('getText');
        	if(!dbNameSearch){
        		alert("请选择查询分行的名称！");
        		return false;
        	}
        	$('#searchDiv').hide();
        	$('#listPage').show();
        	$('#xmwjList').datagrid({
      			url:"<%=cxt%>/do/PrivilegeInfo/getWebSearch?dkqx="+$('#dkqx').val()+"&xmbh="+$('#xmbh').val()+"&xmmc="+$('#xmmc').val()+"&qymc="+$('#qymc').val()+"&ywhc="+$('#ywhc').val()+"&by1="+$('#by1').val()+"&projectType="+$('#projectType').combobox('getValue')+"&xmfzr="+$('#xmfzr').val()+"&by2="+$('#by2').val()+"&dkje="+$('#dkje').val()+"&tbDate="+$('#tbDate').val()+"&tbDateE="+$('#tbDateE').val()+"&square="+$('#square').combobox('getValue')+"&roles=<%=roles%>&dbNameSearch="+dbNameSearch,
     			rownumbers:true,
     			rowStyler: function(index,row){
     				if("1"=='<%=roles%>'){
	     				if (row.tbname!='<%=userTabName%>'){
	     					return 'color:red;';
	     				}
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
     				title:'协议号',
     				field:'lsh',
     				width:50,
     				sortable:true,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'分行名称',
     				field:'pt',
     				width:75,
     				sortable:true,
     				formatter: function(value,row,index){
     					return dbNameText;
     				}
     			},{
     				title:'借款合同号',
     				field:'xmbh',
     				width:100,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'借款人名称',
     				field:'qymc',
     				width:100,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'项目名称',
     				field:'xmmc',
     				width:200,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'项目金额',
     				field:'dkje',
     				width:150,
     				align:'right',
     				sortable:true,
     				formatter: function(value,row,index){
     					/* if(value==''||value==null){
     						value="0.00万("+row.dw+")";
     					}else{
     					    value=row.dkje+"万("+row.dw+")";
     					} */
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'著录时间',
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
     				title:'审批时间',
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
     				title:'实施单位',
     				field:'ywhc',
     				width:200,
     				sortable:true,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			},{
     				title:'项目主管',
     				field:'xmfzr',
     				width:100,
     				formatter: function(value,row,index){
     					return glb.formatString('<span title="{0}">{1}</span>', value, value);
     				}
     			}]],
     			onSelect: function(rowIndex, rowData){
     				showForm(rowData.lsh,rowData.xmbh,rowData.yjflag,rowData.tbname);
     			}
     		});
        }
        
        function showForm(lsh,xmbh,yjflag,tbname){
        	$('#load').attr('src','pages/queryHuibianArchiveShow.jsp?lsh='+lsh+'&xmbh='+xmbh+'&yjflag='+yjflag+'&type=1'+'&tbname='+tbname);
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
     <div data-options="region:'center',border:false" style="overflow:hidden">
       <div id="listPage" style="width:100%;height:100%;padding:0px;margin:0px;">
              <div id="lp2" class="easyui-layout" data-options="fit:true,border:false">
				  <div data-options="region:'north',border:false,title:'项目档案查询结果'" style="height:50px;overflow: hidden;" align="left">
					<form id="searchForm">
						<div class="datagrid-toolbar" style="width: 100%;height:100%;border:0px;">
							<table class="tableForm" style="width:800px;">
								<tr>
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="goBack();" data-options="iconCls:'icon-back', plain:true">返回</a>
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
		          <td width="100%" bgcolor="#999999" height="16"><font style='font-size:10pt' color="#FFFFFF">[案卷查询]请输入查询条件：</font></td>
		        </tr>
				<tr>
					<td height="104" bordercolor="#FFFFE8">
						  <form action='' method="post">
							<table border="0" width="100%" height="131">
							   <br>
							  <tr >
							   <td  class="font14">分行名称  <input name="dbNameSearch" id="dbNameSearch" type="text" style="width: 136px" /></td>
							    <td  class="font14">借款人 <input type="text" name="qymc" id="qymc" style="width: 150px"/></td>
							  </tr>
							   <tr>
							     <td  class="font14">项目品种 <input name="projectType" id="projectType" type="text"  class="easyui-combobox" style="width: 135px"  data-options="valueField:'id',textField:'name',editable:false,panelHeight:120"/></td>
							  	<td  class="font14">合同号 <input name="xmbh" id="xmbh" type="text" style="width: 150px" /></td>
							  </tr>
							   <tr>
							      <td  class="font14">项目名称 <input name="xmmc" id="xmmc" type="text" style="width: 135px" /></td>
							     <td  class="font14">保证人 <input name="by1" id="by1" type="text" style="width: 150px"/></td>
							  </tr>
							 <tr>
							     <td  class="font14">档案编号 <input name="by2" id="by2" type="text" style="width: 136px" /></td>
							    <td  class="font14">信贷员 <input type="text" name="xmfzr" id="xmfzr" style="width: 150px"/></td>
							  </tr>
							   <tr>
							  
							    <td  class="font14">贷款期限 <input name="dkqx" id="dkqx" type="text" style="width: 136px" /></td>
							    <td  class="font14">金&#12288;额 <input name="dkje" id="dkje" type="text" style="width: 150px" /></td>
							  </tr>
							   <tr>
							      <td  class="font14">实施单位 <input name="ywhc" id="ywhc" type="text" style="width: 135px" /></td>
							    <td  class="font14" >结&#12288;清 <input name="square" id="square" type="text" style="width:62px;" /></td>
							   </tr> 
							   <tr>
							     <td class="font14" colspan="2">审批时间 <input name="tbDate" class="easyui-datebox" id="tbDate" type="text" style="width:90px;" /> 至 <input name="tbDateE" id="tbDateE" class="easyui-datebox" type="text" style="width:90px;" />
							   &#12288;&#12288;&nbsp;</td>
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
		</center>
     </div> 
	  </div>
	  
  </body>
</html>
