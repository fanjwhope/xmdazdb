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
String searchDwh=IncString.formatNull(request.getParameter("searchDwh"));
PrivilegeInfo pi=new PrivilegeInfo();
String[][] privilegeList=pi.getPrivilegeList(dbName,searchDwh,zdbdbName);
%>
<style>
<!--
 td{font-family: 宋体;font-size:14px;}
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
<script language="javascript">
	function setVal(){
		$('#dqdw').combobox('setValue', '<%=searchDwh%>');	
	}
</script>
</head>
<BODY style="overflow-x:hidden;overflow-y:auto;" onload="setVal();">
<form method='post'  action="" id="f" name="f" >
<div style="overflow-x: auto; overflow-y: auto; height: 500px; width:100%;padding-bottom:150px;">
<table border="1" align="center" cellspacing="1" width="500" bordercolordark="buttonshadow" bgcolor="#ffffe8">
<tr>
<td style="width:200" bgcolor="buttonshadow"  bordercolor="buttonshadow" height="13">&nbsp;<font color="#ffffff">访问者</font>
</td>
<td style="width:200" bgcolor="buttonshadow"  bordercolor="buttonshadow" height="13">&nbsp;<font color="#ffffff">来源</font>
</td> 
<td style="width:100" bgcolor="buttonshadow"  bordercolor="buttonshadow" height="13">&nbsp;
</td>
</tr>
<%
	if(privilegeList!=null&&privilegeList.length>0){
		for(int i=0;i<privilegeList.length;i++){
%>
	<tr>
		<td style="width:200"><%=pi.getDwmc(dbName,IncString.formatNull(privilegeList[i][0]),zdbdbName)%></td>
		<td style="width:200"><%=pi.getDwmc(dbName,IncString.formatNull(privilegeList[i][1]),zdbdbName)%></td>
		<td style="width:100" align="center"><input type="radio" name="privilegeId" value="<%=IncString.formatNull(privilegeList[i][0])%>:<%=IncString.formatNull(privilegeList[i][1])%>"></td>
	</tr>	
<%			
		}
	}else{}
%>
</table>

<table border="0" align="center" cellspacing="0" width="500">
	<tr>
		<td style="height:50px;">&nbsp;&nbsp;<font color="#0000FF">当前的单位</font>
		<input style="width:100px;" id="dqdw" class="easyui-combobox" 
		data-options="   
        valueField: 'id',   
        textField: 'text',   
        url:'<%=cxt%>/do/PrivilegeInfo/getDeptInfo?zdbdbName=<%=zdbdbName%>&dbName=<%=dbName%>',
        onSelect:function(rec){
        window.location.replace('privilegeMain.jsp?zdbdbName=<%=zdbdbName%>&searchDwh='+rec.id);
        }" />&nbsp;&nbsp;&nbsp;&nbsp;<IMG STYLE="cursor:hand" border="0" src="../images/t_add.gif" width="46" height="24" id="id_qd" onclick="add();"><IMG STYLE="cursor:hand" border="0" src="../images/t_del.gif" width="46" height="24" id="id_del" onclick="del();"><IMG STYLE="cursor:hand" border="0" src="../images/t_delAll.gif" width="61" height="24" id="id_delall" onclick="delAll();"></td>
	</tr>
</table>
</div>
</form>
</BODY>
</HTML>
<script language="javascript">
	function add(){
		window.location.replace("addPrivilege.jsp?zdbdbName=<%=zdbdbName%>");
	}
	function delAll(){
		$.ajax({
		    type: "POST",
		    url: "<%=cxt%>/do/PrivilegeInfo/del?zdbdbName=<%=zdbdbName%>",
		    data: "delId=all",
		    success: function(msg){
			  if(msg=="OK"){
			   $.messager.alert('Warning','删除成功！');
			   window.location.replace("privilegeMain.jsp?zdbdbName=<%=zdbdbName%>");
			  }else {
			   $.messager.alert('Warning','删除失败！');
		   	  }
		    }
		});
	}
	function del(){
		var privilegeId=$("input[name='privilegeId']:checked").val();
		if(privilegeId==''||privilegeId==undefined){
			alert("请选择要删除的记录！");
			return;
		}
		 $.ajax({
		    type: "POST",
		    url: "<%=cxt%>/do/PrivilegeInfo/del?zdbdbName=<%=zdbdbName%>",
		    data: "delId="+privilegeId,
		    success: function(msg){
			  if(msg=="OK"){
			   $.messager.alert('Warning','删除成功！');
			   window.location.replace("privilegeMain.jsp?zdbdbName=<%=zdbdbName%>");
			  }else {
			   $.messager.alert('Warning','删除失败！');
		   	  }
		    }
		});
	}
</script>