<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
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
        var $xmwjList;
        $(function(){
        	  $xmwjList=$('#xmwjList').datagrid({
	    		    url:'<%=cxt%>/do/XmwjYJDo/getRefuseList?zdbdbName=<%=zdbdbName%>',
	    			rownumbers:true,
	    			rowStyler: function(index,row){
	    				if (index%2==1){
	    					return 'background-color:#f9f9f8;';
	    				}
	    			},
	    			idField:'yjdw',
	    			fit:true,
	    			fitColumns:true,
	    			checkOnSelect:true,
	    			selectOnCheck:true,
	    			checkbox:true,
	    			singleSelect:true,
	    			pagination:false,//不分页
	    			pageSize:20,
	    			pageList:[20,30,40,50],
	    			pagePosition:'bottom',
	    			columns:[[{
	    				title:'jsdw',
	    				field:'jsdw',
	    				checkbox:true
	    				
	    			},{
	    				title:'退回人',
	    				field:'jsr',
	    				width:100,
	    				sortable:true,
	    				formatter: function(value,row,index){
	    					return glb.formatString('<span title="{0}">{1}</span>', value, value);
	    				}
	    			},{
	    				title:'退回时间',
	    				field:'yjsj',
	    				width:150,
	    				formatter: function(value,row,index){
	    					var str=row.jsy+"年"+row.jsm+"月"+row.jsd+"日";
	    					return glb.formatString('<span title="{0}">{1}</span>', str, str);
	    				}
	    			},{
	    				title:'操作',
	    				field:'type',
	    				width:100,
	    				sortable:true,
	    				formatter: function(value,row,index){
	    					var str='<a href="javascript:void(0)"  class=\"edit1\" onclick="receive(\''+row.jsdw+'\')">确定</a>';
	    					return str;
	    				}
	    			}]],
	    			onLoadSuccess:function(){
	    			    $('.edit1').linkbutton({
	    			    iconCls:'icon-edit',
	    			    plain:true
	    			    });
	    			}
	    		});
        });
        
        function  doIt(sr){
        	$.ajax({
    			type:'post',
    			url:sr,
    			dataType:'json',
    			success:function(data){
    				 $xmwjList.datagrid('load');//刷新列表
    				 $.messager.show({
						 title:'提示',
						 msg:data.msg
					  }); 
    			}
    		});
        }
        
        function  receive(jsdw){
        	var url='<%=cxt%>/do/XmwjYJDo/confirmRefuse?zdbdbName=<%=zdbdbName%>&jsdw='+jsdw;
        	doIt(url);
        }
        
     </script>
  </head>
  
  <body class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center',border:false" style="overflow:hidden">
        <div id="listPage" style="width:100%;height:100%;padding:0px;margin:0px;">
              <div id="lp2" class="easyui-layout" data-options="fit:true,border:false">
				 <div data-options="region:'north',border:false,title:'移交退回列表'" style="height:25px;overflow: hidden;" align="left">
					<!-- <form id="searchForm">
						<div class="datagrid-toolbar" style="width: 100%;height:100%;border:opx;">
							<table class="tableForm" style="width:1100px;">
								<tr>
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$xmwjList.datagrid('load');" data-options="iconCls:'icon-reload', plain:true">刷新</a>
	        	                    <a href="javascript:void(0);" class="easyui-linkbutton" onclick="receives();" data-options="iconCls:'icon-group', plain:true">批接收</a>
	        	                     <a href="javascript:void(0);" class="easyui-linkbutton" onclick="refuses();" data-options="iconCls:'icon-remove', plain:true">批拒绝</a>
								</tr>
							</table>
						</div>
					</form> -->
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
