<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil,com.hr.dao.PrivilegeDate,com.hr.dao.PrivilegeInfo"%>
<%
String cxt=request.getContextPath();
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
	String zdbdbName=request.getParameter("zdbdbName");

%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>赋权信息列表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<jsp:include page="../publicJsp/libInclude.jsp" />
	<script type="text/javascript">
	var activeObj = new Array();
    var	columns=[[
    				  {field:'id',title:'id',checkbox:true,width:0},
    				  {field:'dwmc',title:'访问者',width:50,align:'center'},
    				  {field:'xmmc',title:'项目名称',width:50,align:'center'},
    				  {field:'hth',title:'借款合同号',width:50,align:'center'}
			      ]]
		var url ="<%=cxt%>/do/PrivilegeInfo/getListByPro?zdbdbName=<%=zdbdbName%>&dbName=<%=dbName%>";
		var otherConf=[];
		otherConf.width=$("body").width()-20;
		otherConf.height=$("body").height()-20;
		otherConf.fit=true;
		otherConf.id='id';
		otherConf.idField='id';
		otherConf.toolbar='#tb';
		otherConf.collapsible=true;
		otherConf.fitColumns=true;
		otherConf.checkbox=true;
		otherConf.iconCls='icon-application_lightning';
		otherConf.singleSelect=false;
		otherConf.autoRowHeight=true;
		otherConf.onLoadSuccess=function(data){$(".buttons").linkbutton({plain:true, iconCls:'icon-edit' });$(".oks").linkbutton({plain:true, iconCls:'icon-ok' });$(".dels").linkbutton({plain:true, iconCls:'icon-cancel' });}	
		$(function(){
				var  grid=new Grid();  
				grid.paintGrid("PrivilegeList",url,columns,otherConf);
	  });
	</script>
  </head>
  
  <body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" style="overflow:hidden">
		<table id="PrivilegeList"></table>
	</div>
	<div id="tb" style="padding:5px;height:auto">
     	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="add();" data-options="iconCls:'icon-add', plain:true">新增赋权</a> 
     	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="del();" data-options="iconCls:'icon-cancel', plain:true">删除权限</a>  
    </div>  
  </body>
</html>
<script language="javascript">
	function add(){
		window.location.replace("priProjectMain.jsp?zdbdbName=<%=zdbdbName%>");
	}
	function del(){
		var pids=$("#PrivilegeList").datagrid('getSelections');
    	var did='';
    	for(var j=0;j<pids.length;j++){
    	if(did!=''){
    		did+=',';
    	}
    	did+=pids[j].id;
    	}
    	if(did==''){
    		alert("请选择要删除的赋权！");
    		return;
    	}
	    $.ajax({
		    type: "POST",
		    url: "<%=cxt%>/do/PrivilegeInfo/delPriForPro?zdbdbName=<%=zdbdbName%>",
		    data: "id="+did,
		    success: function(msg){
			  if(msg=="OK"){
			   $.messager.alert('Warning','删除成功！');
			   window.location.replace("priProjectList.jsp?zdbdbName=<%=zdbdbName%>");
			  }else {
			   $.messager.alert('Warning','删除失败！');
		   	  }
		    }
		});
	}
</script>
