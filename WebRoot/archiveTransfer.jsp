<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String dbName=ArchiveUtil.getDataBaseName(request.getSession());
String archiveType=request.getParameter("archiveType");
String dwh=ArchiveUtil.getCurrentUser(request.getSession()).getDwh();
String zdbdbName=request.getParameter("zdbdbName");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  <jsp:include page="/publicJsp/libInclude.jsp" />
     <style type="text/css">
        table.xmwjList th, table.xmwjList td{
				text-align: center;
				padding: 0px 0px 0px 0px;
				border-style: solid;
				border-width: 0px;
				font-size: 12px;
			}
		table.tableForm th{
	        text-align: right;
	        padding: 0px 0px 0px 0px;
	        font-family:"宋体", "Arial Narrow"; FONT-SIZE: 12px;
	        font-size: 12px;
	   } 
	   table.tableForm td{
	        text-align: left;
	        padding: 0px 0px 0px 0px;
			font-size: 12px;
			}	
     </style>
     <script type="text/javascript">
	       $(function(){
	       	$("#zdbdbName").val('<%=zdbdbName%>');
	    	   $('#jsr').combotree({
	    		   url:'<%=cxt%>/do/PrivilegeInfo/getVisiterInfo?zdbdbName=<%=zdbdbName%>&dbName=<%=dbName%>',
	    		   valueField:'id',   
	         	    textField:'text',
	         	   onBeforeSelect: function(rec){ 
	         		   if(rec.id=='<%=dwh%>'){
	         			   alert('不能移交给自己！');
	         		       return false;
	         		   }
	         	   }
	    	   });
	    	   
	    	  $('#xmwjList').datagrid({
	    		  url:'<%=cxt%>/do/ArchiveDo/getListToTransfer?zdbdbName=<%=zdbdbName%>',
	    			rownumbers:true,
	    			rowStyler: function(index,row){
	    				if (index%2==1){
	    					return 'background-color:#f9f9f8;';
	    				}
	    			},
	    			idField:'lsh',
	    			fit:true,
	    			fitColumns:true,
	    			checkOnSelect:true,
	    			selectOnCheck:true,
	    			checkbox:true,
	    			pagination:false,//不分页
	    			pageSize:20,
	    			pageList:[20,30,40,50],
	    			pagePosition:'bottom',
	    			columns:[[{
	    				title:'lsh',
	    				field:'lsh',
	    				checkbox:true
	    				
	    			},{
	    				title:'档案编号',
	    				field:'by2',
	    				width:100,
	    				sortable:true,
	    				formatter: function(value,row,index){
	    					return glb.formatString('<span title="{0}">{1}</span>', value, value);
	    				}
	    			},{
	    				title:'借款合同号',
	    				field:'xmbh',
	    				width:75,
	    				formatter: function(value,row,index){
	    					return glb.formatString('<span title="{0}">{1}</span>', value, value);
	    				}
	    			},{
	    				title:'借款人',
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
	    				title:'信贷员',
	    				field:'xmfzr',
	    				width:100,
	    				sortable:true,
	    				formatter: function(value,row,index){
	    					return glb.formatString('<span title="{0}">{1}</span>', value, value);
	    				}
	    			}]]
	    		});
	    	  });
	    	   
       function addXmwj(){
       		$("#zdbdbName").val('<%=zdbdbName%>');
    	   $('#xmwjList').datagrid('load',glb.serializeObject($('#searchForm')));
    	   $('#xmwjList').datagrid('clearSelections');
       }
       function transF(){
    	   var  check= $('#xmwjList').datagrid('getSelections');
    	   var  jsr=$('#jsr').combotree('getValue');
    	   if(check.length<1){
    		   alert('请选择移交项！');
    		   return false;
    	   }else if(!jsr){
    		   alert('请选择移交人！');
    		   return false;
    	   }else{
    		   var checks="";
    		   for (var i=0;i<check.length;i++) {
    			   checks+=check[i].lsh+",";
			      }
    		   $.ajax({
    			   type:'post',
    			   url:'<%=cxt%>/do/XmwjYJDo/add?zdbdbName=<%=zdbdbName%>&lsh='+checks+'&jsr='+jsr,
    			   dataType:'json',
    			   success:function(data){
    				   addXmwj();//刷新表单数据
    				  $.messager.show({
						 title:'提示',
						 msg:data.msg
					  }); 
    			   }
    		   });
    	   }
       }
       function resetD(){
    	   $('#searchForm').form('clear');
    	   $.ajax({
    			   type:'post',
    			   url:'<%=cxt%>/do/ArchiveDo/clear?zdbdbName=<%=zdbdbName%>',
    			   dataType:'json',
    			   success:function(data){
    			  	$('#xmwjList').datagrid('reload',{});
    				  $.messager.show({ 				 
						 title:'提示',
						 msg:data.msg
					  }); 
    			   }
    		   });
    		
       }
     </script>

  </head>
  
  <body class="easyui-layout" data-options="fit:true">
     <div data-options="region:'center',border:false" style="overflow:hidden">
        <div id="listPage" style="width:100%;height:100%;padding:0px;margin:0px;">
              <div id="lp2" class="easyui-layout" data-options="fit:true,border:false">
				 <div data-options="region:'north',border:false,title:'项目档案移交列表'" style="height:52px;overflow: hidden;" align="left">
					<form id="searchForm">
					<input type="hidden" name="zdbdbName" id="zdbdbName">
						<div class="datagrid-toolbar" style="width: 100%;height:100%;border:opx;">
							<table class="tableForm" style="width:920px;">
								<tr >
									<th style="width: 90px;text-align:right">借款合同号：</th>
									<th style="width: 140px;text-align:left"><input name="xmbh" id="xmbh" type="text"  /></th>
								    <th style="width: 70px; text-align:right">借款人：</th>
									<th style="width: 140px;text-align:left"><input name="qymc" id="qymc"  type="text" /></th>
									<th style="width: 70px; text-align:right">移交至：</th>
									<th style="width: 140px;text-align:left"><input name="jsr" id="jsr"  type="text" /></th>
									<th><a herf="#" class="easyui-linkbutton" id="add"  name="add" onclick="addXmwj()" data-options="iconCls:'icon-add',plaint:true">添加<a/></th>
									<th><a herf="#" class="easyui-linkbutton" id="res"  name="res" onclick="resetD()" data-options="iconCls:'icon-reload',plaint:true">重置</a>
									<th><a herf="#" class="easyui-linkbutton" id="trsF"  name="trsF" onclick="transF()" data-options="iconCls:'icon-OK',plaint:true">移交</a></th>
								</tr>
							</table>
						</div>
					</form>
				  </div>
             <div data-options="region:'center',border:false" style="overflow:hidden;">
		           <table id="xmwjList"></table>
	         </div>
	       </div>  
        </div>
        <div id="modifyPage" style="width:100%;height:100%;padding:0px;margin:0px;display:none;">
	  	      <iframe id="load" style="width:100%;height:100%;" src=""></iframe>
	    </div>
	 </div>
  </body>
</html>
