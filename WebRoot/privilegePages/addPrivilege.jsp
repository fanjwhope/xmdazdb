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
	font-family: ����;
	font-size: 12px;
}

.loginlong {
	font-family: "����", "����_GB2312", "����";
	font-size: 10pt;
	background-image: url(images/login.JPG);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 55px;
	cursor: hand;
}

.login {
	font-family: "����", "����_GB2312", "����";
	font-size: 9pt;
	background-image: url(images/login_long.jpg);
	background-repeat: no-repeat;
	border: 0px none;
	height: 21px;
	width: 113px;
	cursor: hand;
}

.font10blue {
	font-family: ����;
	font-size: 12px;
	color: #0000A0
}

.font10 {
	font-family: ����;
	font-size: 12px;
}

.zlfontlabel {
	font-family: ����;
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
					color="#ffffff">������</font>
				</td>
				<td style="width:200" bgcolor="buttonshadow"
					bordercolor="buttonshadow" height="13px">&nbsp;<font
					color="#ffffff">�鵵��</font>
				</td>
				<td style="width:200" bgcolor="buttonshadow"
					bordercolor="buttonshadow" height="13px">&nbsp;<font
					color="#ffffff">Ȩ��</font>
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
											<td style="height:30px;">Ȩ����Ч����</td>
										</tr>
										<tr>
											<td style="height:30px;"><input style="width:120;"
												class="easyui-combobox" id="privilegeDate"
												data-options="
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
			alert("��ѡ������ߣ�");
			return;
		}
		var pigeonholessChecked = $("input[name='pigeonholess']:checked");  
		var pigeonholessids = [];  
		pigeonholessChecked.each(function(i){          
		    pigeonholessids.push($(this).val());  
		});
		if(pigeonholessChecked.length==0){
			alert("��ѡ��鵵�ߣ�");
			return;
		}
		var privilegeDate=$('#privilegeDate').combobox('getValue');
		if(privilegeDate==''){
			alert("��ѡ����Ȩʱ�䣡");
			return;
		}
		$.ajax({
		    type: "POST",
		    url: "<%=cxt%>/do/PrivilegeInfo/add?zdbdbName=<%=zdbdbName%>",
			data : "privilegeDate=" + privilegeDate + "&visiterids="
					+ visiterids + "&pigeonholessids=" + pigeonholessids,
			success : function(msg) {
				if (msg == "OK") {
					$.messager.alert('Warning', '��Ȩ�ɹ���');
					window.location.replace("privilegeMain.jsp?zdbdbName=<%=zdbdbName%>");
				} else {
					$.messager.alert('Warning', '��Ȩʧ�ܣ�');
				}
			}
		});
	}
</script>