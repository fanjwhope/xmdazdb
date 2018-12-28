<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String zdbdbName=request.getParameter("zdbdbName");
%>

<html>
  <head>
    <title>日志管理</title>
    <%@ include file="/publicJsp/libInclude.jsp" %>
    <script>
    $(function(){
    	load("");
    	
    	$('#state').combobox({
    		valueField:'id',
  			textField: 'value',
  			panelHeight:'auto',
			data:[{
				value: '所有登录',
				id: 'all',
				selected: 'true'
			},{
				value: '登录成功',
				id: 'suc'
			},{
				value: '登录失败',
				id: 'fail'
  			}],
  			onSelect:function(record){
  				var tp = $('#user').combobox('getText');
  				var tep = "?zdbdbName=<%=zdbdbName%>&state=" + record.id + "&user=" + tp;
  				load(tep);
  			}
    	});
    	$('#user').combobox({
    		url:"<%=path%>/do/LogInfoDo/findAllUser?zdbdbName=<%=zdbdbName%>",
    		valueField:'id',
  			textField: 'account',
  			onSelect:function(record){
  				var tp = $('#state').combobox('getValue');
  				var tep = "?user=" + record.account + "&state=" + tp;
  				load(tep);
  			}
    	});
    });
    
    function load(check){
    	 $('#loginfo').datagrid({
    		url:"<%=path%>/do/LogInfoDo/findAll?zdbdbName=<%=zdbdbName%>&" + check,
    		fit:true,
    		fitColumns:true,
    		autoRowHeight:false,
    		striped:true,
    		pagination:true,
    		rownumbers:true,
    		singleSelect:true,
    		width:800,
    		height:610,
    		pageSize:20,
    		pageList:[20, 50, 100],
    		columns:[[{
    			title:'IP地址',
    			field:'ip',
    			width:79,
    			align:'center',
    			sortable:true    			
    			}, {
    			title:'登录账号',
    			field:'account',
    			width:78,
    			align:'center',
    			sortable:true    			
    			}, {
    			title:'登录密码',
    			field:'password',
    			width:88,
    			align:'center',
    			sortable:true    			
    			}, {
    			title:'登录日期',
    			field:'logDate',
    			width:107,
    			align:'center',
    			formatter:function(value,row){ //value：字段值，row:行数据
    				//return row.logYear + "-" + row.logMonth + "-" + row.logDay;
    				if(row.logMonth < 10){
    					if( row.logDay < 10)
    						return row.logYear + "-0" + row.logMonth + "-0" + row.logDay;
    					else
    						return row.logYear + "-0" + row.logMonth + "-" + row.logDay;
    				} else {
    					if( row.logSecond < 10)
    						return row.logYear + "-" + row.logMonth + "-0" + row.logDay;
    					else
    						return row.logYear + "-" + row.logMonth + "-" + row.logDay;
    				}
    			}    
    			}, {
    			title:'登录时间',
    			field:'logTime',
    			width:106,
    			align:'center',
    			formatter:function(value,row){
    				if( row.logMinute < 10){
    					if(row.logSecond < 10)
    						return row.logHour + ":0" + row.logMinute + ":0" + row.logSecond;
    					else
    						return row.logHour + ":0" + row.logMinute + ":" + row.logSecond;
    				}else {
    					if( row.logSecond < 10)
    						return row.logHour + ":" + row.logMinute + ":0" + row.logSecond;
    					else
    						return row.logHour + ":" + row.logMinute + ":" + row.logSecond;
    				}
    			}    			
    			}, {
    			title:'登录结果',
    			field:'finish',
    			width:69,
    			align:'center',
    			sortable:true,
    			formatter:function(value,row){  
    				if(value == 0){
    					value = "登录成功";
    					return value;
    				} else {
    					value = "登录失败";
    					return value;
    				}
    			},
    			styler:function(value,row){
    				if(value == 1)
    					return 'color:red;';
    			}    			
    			}, {
    			title:'最后在线时间',
    			field:'lastTime',
    			width:110,
    			align:'center',
    			sortable:true    			
    			}
    		]]
    	});
    };
    
  </script>

  </head>
  <body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" style="overflow:hidden">
		<div id="lp2" class="easyui-layout"	data-options="fit:true,border:false">
			<div data-options="region:'north',border:false,title:'系统登录日志'" style="height:50px;overflow: hidden;" align="left">
				<div class="datagrid-toolbar" style="width: 100%;height:100%;border:opx;">
					用户：<input id="user" type="text" /> 
					登录结果：<input id="state" type="text" />
				</div>
			</div>
			<div data-options="region:'center',border:false" style="overflow:hidden;">
				<table id="loginfo"></table>
			</div>
		</div>
	</div>
   </body>
</html>
