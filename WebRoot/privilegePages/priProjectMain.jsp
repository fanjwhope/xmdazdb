<%@page import="com.hr.dao.BaseDao"%>
<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil,com.hr.dao.PrivilegeDate,com.hr.dao.PrivilegeInfo"%>
<%
String cxt=request.getContextPath();
	String zdbdbName=request.getParameter("zdbdbName");

BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
String cName=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getDwmc());
String deptNum=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
PrivilegeInfo pi=new PrivilegeInfo();
String[][] visiters=pi.getUsers(dbName, deptNum,zdbdbName) ;
%>
<style>
<!--
 td{font-family: 宋体;font-size:12px;}
 .loginlong {
	font-family: "宋体", "楷体_GB2312", "黑体";
	font-size: 10pt;
	background-image: url(images/login.JPG);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 55px;
	cursor:hand;
}
.login {
	font-family: "宋体", "楷体_GB2312", "黑体";
	font-size: 9pt;
	background-image: url(images/login_long.jpg);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 113px;
	cursor:hand;
}
  .font10blue {font-family: 宋体;font-size:12px;color:#0000A0}
  .font10 {font-family: 宋体;font-size:12px;}
  .zlfontlabel{font-family: 宋体;font-size:14px;color:#000099}
-->
</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="../publicJsp/libInclude.jsp" />
</head>
	<script type="text/javascript">
	var activeObj = new Array();
    var	columns=[[
    				  {field:'lsh',title:'ID',checkbox:true,width:0},
    				  {field:'qymc',title:'借款人',width:50,align:'center'},
    				  {field:'xmmc',title:'项目名称',width:50,align:'center'},
    				  {field:'by2',title:'档案编号',width:50,align:'center'},
    				  {field:'xmbh',title:'借款合同号',width:50,align:'center'},
    				  {field:'xmfzr',title:'信贷员',width:50,align:'center'}	                   
			      ]]
		var url ="<%=cxt%>/do/PrivilegeInfo/getSearchInfo?zdbdbName=<%=zdbdbName%>&dbName=<%=dbName%>";
		var otherConf=[];
		otherConf.width=$("body").width()-20;
		otherConf.height=$("body").height()-20;
		otherConf.fit=true;
		otherConf.id='lsh';
		otherConf.idField='lsh';
		otherConf.toolbar='#btnbar';
		otherConf.collapsible=true;
		otherConf.fitColumns=true;
		otherConf.checkbox=true;
		otherConf.iconCls='icon-application_lightning';
		otherConf.singleSelect=false;
		otherConf.autoRowHeight=true;
		otherConf.onLoadSuccess=function(data){$(".buttons").linkbutton({plain:true, iconCls:'icon-edit' });$(".oks").linkbutton({plain:true, iconCls:'icon-ok' });$(".dels").linkbutton({plain:true, iconCls:'icon-cancel' });}	
		$(function(){
				var  grid=new Grid();  
				grid.paintGrid("formsList",url,columns,otherConf);
	  });
	  function searchForm(){
			$("#formsList").datagrid('load', {projectId:$('#projectId').val(),projectUser:$('#projectUser').val(),xmfzr:$('#xmfzr').val()
				,dbName:'<%=dbName%>',zdbdbName:'<%=zdbdbName%>'});
	}
</script>
<BODY class="easyui-layout" data-options="fit:true">
<br><br><br>
	 <div id="btnbar" style="height:35px;">
借款合同号：<input id="projectId" class="easyui-validatebox" style="width:100px;" />
&nbsp;借款人：<input id="projectUser" class="easyui-validatebox" style="width:100px;" />&nbsp;信贷员：<input id="xmfzr" class="easyui-validatebox" style="width:100px;" />&nbsp;<a href="javascript:void(0);" onclick="searchForm()" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true">查询</a>&nbsp;访问者：<select id="visiter" class="easyui-combotree" style="width:140px;" data-options="url:'<%=cxt%>/do/PrivilegeInfo/getVisiterInfo?dbName=<%=dbName%>&zdbdbName=<%=zdbdbName%>',checkbox:true,multiple:true,cascadeCheck:false"></select>&nbsp;权限有效期限：<input style="width:60px;" class="easyui-combobox" id="privilegeDate" data-options="
									valueField: 'value',
									textField: 'text',
									data: [{
										text: '一天',
										value: '一天'
									},{
										text: '三天',
										value: '三天'
									},{
										text: '一周',
										value: '一周'
									},{
										text: '两周',
										value: '两周'
									},{
										text: '一个月',
										value: '一个月'
									},{
										text: '三个月',
										value: '三个月'
									},{
										text: '半年',
										value: '半年'
									},{
										text: '一年',
										value: '一年'
									},{
										text: '三年',
										value: '三年'
									},{
										text: '永久',
										value: '永久'
									}]" /><a href="javascript:void(0);" onclick="add()" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true">赋权</a><a href="javascript:void(0);" onclick="goback()" class="easyui-linkbutton" data-options="iconCls:'icon-back', plain:true">返回上页</a>
	</div>
	<div>
 		<form id="form1" name="form1"  method="post" height="0px" border="0"></form>
 	</div>
	<div data-options="region:'center',border:false" style="overflow:hidden;top:30px">
		<table id="formsList"></table>
	</div>
</BODY>
</HTML>
<script language="javascript">
	function add(){
	var vt = $('#visiter').combotree('tree');
	var n = vt.tree('getChecked');
	var vtid = '';
	var vtname='';
    for (var i = 0; i <n.length; i++) {
        if (vtid != '') 
            vtid += ',';
        if (vtname != '') 
        	vtname += ',';
        vtid += n[i].id;
 		vtname+=n[i].text;
    }
    if(vtid==''){
    	alert("请选择访问者！");
    	return;
    }
    var dg=$("#formsList").datagrid('getSelections');
    var dgdwh='';  //2018-03-28
    var dgid='';
    var dgname='';
    var dghth='';
    for(var j=0;j<dg.length;j++){
    	if(dgid!=''){
    		dgid+=',';
    	}
    	if(dgname!=''){
    		dgname+=',';
    		dgdwh+=',';
    	}
    	if(dghth!=''){
    		dghth+=',';
    	}
    	dgid+=dg[j].lsh;
    	dgname+=dg[j].xmmc;
    	dghth+=dg[j].xmbh;
    	dgdwh+=dg[j].tabname; //表名称
    }
    if(dgid==''){
    	alert("请选择项目文件！");
    	return;
    }
    var pDate=$('#privilegeDate').combobox('getValue');
		if(pDate==''){
			alert("请选择权限有效期！");
			return;
		}
	console.log(dgdwh);
	$.ajax({
	    type: "POST",
	    url: "<%=cxt%>/do/PrivilegeInfo/addByProject?zdbdbName=<%=zdbdbName%>",
	    data: "privilegeDate="+pDate+"&visiterIds="+vtid+"&projectIds="+dgid+"&projectNames="+dgname+"&projectBhs="+dghth+"&visiterIdsNames="+vtname+"&dwhs="+dgdwh,
	    success: function(msg){
		  if(msg=="OK"){
		   $.messager.alert('Warning','赋权成功！');
		  }else {
		   $.messager.alert('Warning','赋权失败！');
	   	  }
	    }
	});
	}
	
	
	function goback(){
		window.location.replace("<%=cxt%>/privilegePages/priProjectList.jsp?zdbdbName=<%=zdbdbName%>");
	}
</script>