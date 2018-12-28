<%@page import="com.hr.dao.BaseDao"%>
<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page
	import="com.hr.global.util.ArchiveUtil,com.hr.dao.PrivilegeDate,com.hr.dao.PrivilegeInfo"%>
<%
	String cxt = request.getContextPath();
	String zdbdbName=request.getParameter("zdbdbName");
	BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
	String cName = IncString.formatNull(ArchiveUtil.getCurrentUser(
			session).getDwmc());
	String deptNum = IncString.formatNull(ArchiveUtil
			.getDepartmentCode(session));
	String dbName = IncString.formatNull(ArchiveUtil
			.getDataBaseName(session));
	PrivilegeInfo pi = new PrivilegeInfo();
	String[][] visiters = pi.getUsers(dbName, deptNum,zdbdbName);
%>
<style>

td {
	font-family: 宋体;
	font-size: 12px;
}

.loginlong {
	font-family: "宋体", "楷体_GB2312", "黑体";
	font-size: 10pt;
	background-image: url(images/login.JPG);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 55px;
	cursor: hand;
}

.login {
	font-family: "宋体", "楷体_GB2312", "黑体";
	font-size: 9pt;
	background-image: url(images/login_long.jpg);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 113px;
	cursor: hand;
}

.font10blue {
	font-family: 宋体;
	font-size: 12px;
	color: #0000A0
}

.font10 {
	font-family: 宋体;
	font-size: 12px;
}

.zlfontlabel {
	font-family: 宋体;
	font-size: 14px;
	color: #000099
}
-->
</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="../publicJsp/libInclude.jsp" />
</head>
<BODY style="overflow:hidden;" height:100%;>
	<form method='post' action="" id="f" name="f">
	<div style="overflow-x: auto; overflow-y: auto;  width:100%;">
		<table border="1" align="center" cellpadding="0" cellspacing="1"
			width="600px" bordercolordark="buttonshadow"
			bgcolor="#ffffe8">
			<tr>
				<td style="width:200" bgcolor="buttonshadow"
					bordercolor="buttonshadow" height="13px">&nbsp;<font
					color="#ffffff">访问者</font>
				</td>
				<td style="width:200" bgcolor="buttonshadow"
					bordercolor="buttonshadow" height="13px">&nbsp;<font
					color="#ffffff">归档者</font>
				</td>
				<td style="width:200" bgcolor="buttonshadow"
					bordercolor="buttonshadow" height="13px">&nbsp;<font
					color="#ffffff">权限</font>
				</td>
			</tr>
			<tr>
				<td bordercolor="buttonshadow" valign="top">
					<div style="overflow-x: hidden; overflow-y: auto;height:450px; width:100%;">
						<table border="0"; height:500px; align="left">
							<tr>
								<td><IMG STYLE="cursor:hand" border="0"
									src="../images/t_allselect.gif" width="46" height="24"
									id="id_qd" onclick="selectAll('visiter');"> <IMG
									STYLE="cursor:hand" border="0" src="../images/t_allerase.gif"
									width="46" height="24" id="id_del"
									onclick="unSelectAll('visiter');"></td>
							</tr>
							<tr>
								<td>
									<table border="0" align="center" width="200">
										<%
											if (visiters != null && visiters.length > 0) {
												for (int i = 0; i < visiters.length; i++) {
										%>
										<tr>
											<td><input type="checkbox"
												id="<%=IncString.formatNull(visiters[i][1])%>"
												name="visiter"
												value="<%=IncString.formatNull(visiters[i][2])%>"><%=IncString.formatNull(visiters[i][0])%></input></td>
										</tr>
										<%
											}
											}
										%>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td bordercolor="buttonshadow">
					<div style="overflow-x: hidden; overflow-y: auto;height:450px; width:100%;">
						<table border="0";height:500px; align="left">
							<tr>
								<td><IMG STYLE="cursor:hand" border="0"
									src="../images/t_allselect.gif" width="46" height="24"
									id="id_qd" onclick="selectAll('pigeonholess');"><IMG
									STYLE="cursor:hand" border="0" src="../images/t_allerase.gif"
									width="46" height="24" id="id_del"
									onclick="unSelectAll('pigeonholess');"></td>
							</tr>
							<tr>
								<td>
									<table border="0" align="center" width="200">
										<%
											if (visiters != null && visiters.length > 0) {
												for (int i = 0; i < visiters.length; i++) {
										%>
										<tr>
											<td><input type="checkbox"
												id="<%=IncString.formatNull(visiters[i][1])%>"
												name="pigeonholess"
												value="<%=IncString.formatNull(visiters[i][2])%>"><%=IncString.formatNull(visiters[i][0])%></input></td>
										</tr>
										<%
											}
											}
										%>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td valign="top" bordercolor="buttonshadow">
					<table border="0" align="left" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top" style="height:30px;"><IMG
								STYLE="cursor:hand" border="0" src="../images/t_sendprivite.gif"
								width="46" height="24" id="id_qd" onclick="add();"></td>
						</tr>
						<tr>
							<td>					
									<table border="0" align="center" cellpadding="0"
										cellspacing="0" width="200" valign="top">
										<tr>
											<td style="height:30px;">权限有效期限</td>
										</tr>
										<tr>
											<td style="height:30px;"><input style="width:120;"
												class="easyui-combobox" id="privilegeDate"
												data-options="
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
											}]" />
											</td>
										</tr>						
									</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</div>
	</form>
</BODY>
</HTML>
<script language="javascript">
	function selectAll(selname){
		var ck = $("input[name="+selname+"]");       
	    ck.attr("checked",true);
	}
	function unSelectAll(selname){
		var ck = $("input[name="+selname+"]");
	    ck.attr("checked",false);
	}
	function add(){
		var visiterChecked = $("input[name='visiter']:checked");  
		var visiterids = [];  
		visiterChecked.each(function(i){          
		    visiterids.push($(this).val());  
		});
		if(visiterChecked.length==0){
			alert("请选择访问者！");
			return;
		}
		var pigeonholessChecked = $("input[name='pigeonholess']:checked");  
		var pigeonholessids = [];  
		pigeonholessChecked.each(function(i){          
		    pigeonholessids.push($(this).val());  
		});
		if(pigeonholessChecked.length==0){
			alert("请选择归档者！");
			return;
		}
		var privilegeDate=$('#privilegeDate').combobox('getValue');
		if(privilegeDate==''){
			alert("请选择授权时间！");
			return;
		}
		$.ajax({
		    type: "POST",
		    url: "<%=cxt%>/do/PrivilegeInfo/add?zdbdbName=<%=zdbdbName%>",
			data : "privilegeDate=" + privilegeDate + "&visiterids="
					+ visiterids + "&pigeonholessids=" + pigeonholessids,
			success : function(msg) {
				if (msg == "OK") {
					$.messager.alert('Warning', '赋权成功！');
					window.location.replace("privilegeMain.jsp?zdbdbName=<%=zdbdbName%>");
				} else {
					$.messager.alert('Warning', '赋权失败！');
				}
			}
		});
	}
</script>