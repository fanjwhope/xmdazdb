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
 td{font-family: ����;font-size:12px;}
 .loginlong {
	font-family: "����", "����_GB2312", "����";
	font-size: 10pt;
	background-image: url(images/login.JPG);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 55px;
	cursor:hand;
}
.login {
	font-family: "����", "����_GB2312", "����";
	font-size: 9pt;
	background-image: url(images/login_long.jpg);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 113px;
	cursor:hand;
}
  .font10blue {font-family: ����;font-size:12px;color:#0000A0}
  .font10 {font-family: ����;font-size:12px;}
  .zlfontlabel{font-family: ����;font-size:14px;color:#000099}
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
    				  {field:'qymc',title:'�����',width:50,align:'center'},
    				  {field:'xmmc',title:'��Ŀ����',width:50,align:'center'},
    				  {field:'by2',title:'�������',width:50,align:'center'},
    				  {field:'xmbh',title:'����ͬ��',width:50,align:'center'},
    				  {field:'xmfzr',title:'�Ŵ�Ա',width:50,align:'center'}	                   
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
����ͬ�ţ�<input id="projectId" class="easyui-validatebox" style="width:100px;" />
&nbsp;����ˣ�<input id="projectUser" class="easyui-validatebox" style="width:100px;" />&nbsp;�Ŵ�Ա��<input id="xmfzr" class="easyui-validatebox" style="width:100px;" />&nbsp;<a href="javascript:void(0);" onclick="searchForm()" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true">��ѯ</a>&nbsp;�����ߣ�<select id="visiter" class="easyui-combotree" style="width:140px;" data-options="url:'<%=cxt%>/do/PrivilegeInfo/getVisiterInfo?dbName=<%=dbName%>&zdbdbName=<%=zdbdbName%>',checkbox:true,multiple:true,cascadeCheck:false"></select>&nbsp;Ȩ����Ч���ޣ�<input style="width:60px;" class="easyui-combobox" id="privilegeDate" data-options="
									valueField: 'value',
									textField: 'text',
									data: [{
										text: 'һ��',
										value: 'һ��'
									},{
										text: '����',
										value: '����'
									},{
										text: 'һ��',
										value: 'һ��'
									},{
										text: '����',
										value: '����'
									},{
										text: 'һ����',
										value: 'һ����'
									},{
										text: '������',
										value: '������'
									},{
										text: '����',
										value: '����'
									},{
										text: 'һ��',
										value: 'һ��'
									},{
										text: '����',
										value: '����'
									},{
										text: '����',
										value: '����'
									}]" /><a href="javascript:void(0);" onclick="add()" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true">��Ȩ</a><a href="javascript:void(0);" onclick="goback()" class="easyui-linkbutton" data-options="iconCls:'icon-back', plain:true">������ҳ</a>
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
    	alert("��ѡ������ߣ�");
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
    	dgdwh+=dg[j].tabname; //������
    }
    if(dgid==''){
    	alert("��ѡ����Ŀ�ļ���");
    	return;
    }
    var pDate=$('#privilegeDate').combobox('getValue');
		if(pDate==''){
			alert("��ѡ��Ȩ����Ч�ڣ�");
			return;
		}
	console.log(dgdwh);
	$.ajax({
	    type: "POST",
	    url: "<%=cxt%>/do/PrivilegeInfo/addByProject?zdbdbName=<%=zdbdbName%>",
	    data: "privilegeDate="+pDate+"&visiterIds="+vtid+"&projectIds="+dgid+"&projectNames="+dgname+"&projectBhs="+dghth+"&visiterIdsNames="+vtname+"&dwhs="+dgdwh,
	    success: function(msg){
		  if(msg=="OK"){
		   $.messager.alert('Warning','��Ȩ�ɹ���');
		  }else {
		   $.messager.alert('Warning','��Ȩʧ�ܣ�');
	   	  }
	    }
	});
	}
	
	
	function goback(){
		window.location.replace("<%=cxt%>/privilegePages/priProjectList.jsp?zdbdbName=<%=zdbdbName%>");
	}
</script>