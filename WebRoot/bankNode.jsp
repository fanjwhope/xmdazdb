<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String  cxt=request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <jsp:include page="/publicJsp/libInclude.jsp" />
   <style type="text/css">

	  table.bankNodeList th, table.bankNodeList td{
				text-align: center;
				padding: 10px 5px 10px 5px;
				border-style: solid;
				border-width: 1px;
				font-size: 14px;
			}
		table.tableForm th{
	        text-align: right;
	        padding: 8px 0px 8px 0px;
	        font-family:"微软雅黑", "Microsoft Yahei"; FONT-SIZE: 14px;
	        font-size: 14px;
	   } 
	   table.tableForm td{
	        text-align: left;
	        padding: 5px 0px 5px 0px;
			font-size: 14px;
			}	
			
			td{
			  font-family:"微软雅黑", "Microsoft Yahei"; FONT-SIZE: 14px;
	          font-size: 14px;
			}
	</style>
   <script type="text/javascript" >
		var $bankNodeList;
		$(function(){
	        $bankNodeList=$('#bankNodeList').datagrid({
			url:'<%=cxt%>/do/BankNodeDo/getList',
			rownumbers:true,
			rowStyler: function(index,row){
				if (index%2==1){
					return 'background-color:#f9f9f8;';
				}
			},
			idField:'id',
			fit:true,
			toolbar:'#tb',			
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
				title:'分行编号',
				field:'id',
				width:50,
				sortable:true,
				formatter: function(value,row,index){
					return glb.formatString('<span title="{0}">{1}</span>', value, value);
				}
			},{
				title:'分行名称',
				field:'bankName',
				width:200,
				sortable:true,
				formatter: function(value,row,index){
					return glb.formatString('<span title="{0}">{1}</span>', value, value);
				}
			},{
				title:'是否有效',
				field:'flag',
				width:100,
				sortable:true,
				formatter: function(value,row,index){
					var str;
					if(value==1){
						str="有效";
					}else{
						str="无效";
					}
					return glb.formatString('<span title="{0}">{1}</span>', str, str);
				}
			},{
				title:'操作',
				field:'type',
				width:300,
				sortable:true,
				formatter: function(value,row,index){
				  var str;
				  str='<a href="javascript:void(0)"  class=\"edit1\" onclick="toUpdateNode(\''+row.id+'\')">编辑</a>';
				  if(row.flag==1){
				    str+='&nbsp;&nbsp;<a href="javascript:void(0)" class=\"delete1\"  onclick="deleteRow(\''+row.id+"','0"+'\')">关闭</a>';
				  }else{
					  str+='&nbsp;&nbsp;<a href="javascript:void(0)" class=\"delete1\"  onclick="deleteRow(\''+row.id+"','1"+'\')">启用</a>';  
				  }
				  str+='&nbsp;&nbsp;<a href="javascript:void(0)" class=\"edit2\"  onclick="nodeAdmin(\''+row.id+'\')">分行管理员</a>'
			      return str;
				}
			}
			]],
			onLoadSuccess:function(){
			    $('.edit1').linkbutton({
			    iconCls:'icon-edit',
			    plain:true
			    });
			    $('.delete1').linkbutton({
			    iconCls:'icon-remove',
			    plain:true
			    });
			    $('.edit2').linkbutton({
				iconCls:'icon-user',
				plain:true
				});
			}
		});
	  });
		
	function nodeAdmin(id){
		$.ajax({
			type:'post',
    		url:'<%=cxt%>/do/BankNodeDo/getNodeAdmin?nodeId='+id,
    		dataType:'JSON',
    		success:function(data){
    			$('#nodeId').val(id);
    			$('#pwd').val(data.userpwd);
    		}
		});
		 $('#bankNodeAdmin').dialog({
 		    title:'管理员设置',
 		    modal: true
 		   });
	}
	
	function updateAdmin(){
		var id=$('#nodeId').val();
		var pwd=$('#pwd').val();
		$.ajax({
			type:'post',
    		url:'<%=cxt%>/do/BankNodeDo/updateNodeAdmin?nodeId='+id+'&pwd='+pwd,
    		dataType:'JSON',
    		success:function(data){
		     $('#bankNodeAdmin').dialog('close');
    			$.messager.show({
    				title : '提示',
    				msg : data.msg
    			});
    		}
		});
	}
		
	function  deleteRow(id,flag){
		$.ajax({
    		type:'post',
    		url:'<%=cxt%>/do/BankNodeDo/isAvailable?id='+id+'&flag='+flag,
    		dataType:'JSON',
    		success:function(date){
    		   $bankNodeList.datagrid('load');
    			}
    		});
	}	
	function  add(){
		$('#main_form').form("clear");
		$('#id').show();
		$('#up').hide();
		$('#add').show();
		$('#u_id').html('');
		 $('#check1').css('display','none');
		$('#bankNodeDialog').dialog({
 		    title:'添加分行信息',
 		    modal: true
 		 });
	}
	
    function saveNode(){
     var name=$('#bankName').val();
     if(name==''||name==null)return false;
     var  url='<%=cxt%>/do/BankNodeDo/add';
	   $('#main_form').form('submit', {
	        url : url,
		    success : function(data) {			
			var json = $.parseJSON(data);
			$('#bankNodeDialog').dialog('close');
	        $bankNodeList.datagrid('load');
			$.messager.show({
				title : '提示',
				msg : json.msg
			});
		}
	});
	}
    
    function toUpdateNode(id){
    	$.ajax({
    		type:'post',
    		url:'<%=cxt%>/do/BankNodeDo/getOne?id='+id,
    		dataType:'JSON',
    		success:function(data){
    			$('#id').hide();
    			$('#add').hide();
    			$('#up').show();
    			$('#name').val(data.bankName);
    			$('#u_id').html(data.id);
		    	 $('#bankNodeDialog').dialog({
		    		    title:'修改分行名称',
		    		    modal: true
		    		   });
    		}
    	});
    }
    
	function updateNode(){
		 var name=$('#bankName').val();
	     if(name==''||name==null)return false;
		 var  url='<%=cxt%>/do/BankNodeDo/update';
		   $('#main_form').form('submit', {
		        url : url,
			    success : function(data) {			
				var json = $.parseJSON(data);
				$('#bankNodeDialog').dialog('close');
		        $bankNodeList.datagrid('load');
				$.messager.show({
					title : '提示',
					msg : json.msg
				});
			}
		});
	}
	
	function  check(){
	  var  id=$('#id').val();
	   $.ajax({
              type: "post", 
              url:'<%=cxt%>/do/BankNodeDo/check?id='+id,
	           dataType:'JSON',
	           success: function (data) {
					if(!data.success){
					 $('#check1').attr('src','js/easyui/themes/icons/no.png');
					 $('#check1').css('display','');
					 $('#add').attr("disabled",true);
					}else{
					 $('#check1').css('display','none');
					 $('#add').attr("disabled",false);
					}
				}
	   });
	}
	

	
	function goColse(flag){
		if(flag){
			$('#bankNodeAdmin').dialog('close');
			$('#admin_form').form("clear");
		}else{
		    $('#bankNodeDialog').dialog('close');
		    $('#main_form').form("clear");
		}
	}
	
     
	</script>
  </head>
  
 <body class="easyui-layout" data-options="fit:true">
	<div id="bankNodeDialog" name="bankNodeDialog" style="width: 260px; height:160px">
	  <form id="main_form" name="main_form" method="post" class="main_form">
         <table style="margin-top:12px;margin-bottom:5px;margin-left:5px;font-size:14px">
           <tr>
             <td>分行编号:</td>
             <td> <input id="id" name="id" type="text" style="margin-left:0;width:150px;"  onBlur="check();"/><span id="u_id"></span></td>
             <td><img src="#" id="check1" style="display:none"/></td>
           </tr>
           <tr>
             <td>分行名称:</td>
             <td> <input type="text" id="bankName" name="bankName" style="margin-left:0;width:150px;font-family:'微软雅黑', 'Microsoft Yahei'; FONT-SIZE: 14px" /></td>
           </tr>
           <tr><td align="center" colspan="2">
                                        <a href="javascript:void(0);" id="add"  name="add"
										class="easyui-linkbutton" onclick="saveNode();"
										data-options="iconCls:'icon-save', plain:true">保存</a>
										<a href="javascript:void(0);"  id="up" name="up"
										class="easyui-linkbutton"  onclick="updateNode();"
										data-options="iconCls:'icon-save', plain:true">更新</a>
                                        <a href="javascript:void(0);" id="up"  name="up"
										class="easyui-linkbutton" onclick="goColse();"
										data-options="iconCls:'icon-cancel', plain:true">关闭</a></td>
           </tr>
         </table>
      </form>
	</div>
	
	<div id="bankNodeAdmin" name="bankNodeAdmin" style="width: 260px; height:160px">
	  <form id="admin_form" name="admin_form" method="post" class="admin_form">
	  <input type="hidden" id="nodeId"/>
         <table style="margin-top:12px;margin-bottom:5px;margin-left:5px;font-size:14px">
           <tr>
             <td>用户名:</td>
             <td style="width: 150px">system</td>
           </tr>
            <tr>
             <td>密&#12288;码:</td>
             <td> <input type="password" id="pwd" name="pwd" style="margin-left:0;width:150px;font-family:'微软雅黑', 'Microsoft Yahei'; FONT-SIZE: 14px" /></td>
           </tr>
           <tr><td align="center" colspan="2">
										<a href="javascript:void(0);"  id="upAdmin" name="upAdmin"
										class="easyui-linkbutton"  onclick="updateAdmin();"
										data-options="iconCls:'icon-save', plain:true">保存</a>
                                        <a href="javascript:void(0);" 
										class="easyui-linkbutton" onclick="goColse(1);"
										data-options="iconCls:'icon-cancel', plain:true">关闭</a></td>
           </tr>
         </table>
      </form>
	</div>


	     <div data-options="region:'center',border:false" style="overflow:hidden">
		<div id="listPage" style="width:100%;height:100%;padding:0px;margin:0px;">
		<div id="lp2" class="easyui-layout" data-options="fit:true">
	  <div id="tb" style="padding:5px;height:auto">  
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$bankNodeList.datagrid('load');" data-options="iconCls:'icon-reload', plain:true">刷新</a>
	        	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="add();" data-options="iconCls:'icon-add', plain:true">新增分行</a>
	        	<!-- <a href="javascript:void(0);" class="easyui-linkbutton" onclick="toSort();" data-options="iconCls:'icon-menu', plain:true">分行排序</a> -->
    </div> 
	<div data-options="region:'center',border:false" style="overflow:hidden;top:30px">
		<table id="bankNodeList"></table>
	</div>
	</div>
    </div>
	  	<div id="modifyPage" style="width:100%;height:100%;padding:0px;margin:0px;display:none;">
	  	      <iframe id="load" style="width:100%;height:100%;" src=""></iframe>
	  	</div>
	  </div>
   
  </body>
</html>
