<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@page import="java.util.*,com.hr.util.*,com.hr.user.*"%>
<%
   String path = request.getContextPath();
   String userId = IncString.formatNull(ArchiveUtil.getCurrentUser(session).getUserid());
   	String zdbdbName=request.getParameter("zdbdbName");
   
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>�����޸�</title>
 <jsp:include page="publicJsp/libInclude.jsp" />
<script src="js/jquery-1.8.0.min.js"></script> 
<script>
  var currentPwd = null;
  
  function save(){
     var userid = document.getElementById("userid").value;
     var userpwd = document.getElementById("userpwd").value;
     $.ajax({
         url:"<%=path%>/do/UserDo/updatePWD?zdbdbName=<%=zdbdbName%>",
         type:"post",
         data:{'userid':userid, 'userpwd':userpwd},
         success:function(){
             alert("�޸ĳɹ���");
             toClear();
         }
     });
  };
  
  function change(){
     if((document.getElementById("oldpwd").value == "") | 
        (document.getElementById("userpwd").value == "") |
        (document.getElementById("userpwd1").value == "")){
         alert("������������");
         toClear();
     }
     else {
         if($('#userpwd').val() == $('#userpwd1').val()){
             pwd();
    	 } else {
         	alert("�������������벻һ�£�");
         	toClear();
     	 }
     }
  }
  
  function pwd(){
     var userid = document.getElementById("userid").value;
     var userpwd = document.getElementById("oldpwd").value;
     $.ajax({
        url:"<%=path%>/do/UserDo/getPwd?zdbdbName=<%=zdbdbName%>",
        type:"post",
        data:{'userid':userid, 'userpwd':userpwd},
        success:function(data){
           var json = $.parseJSON(data);
           if(json.param == 1) {
               save();
           } else {
              alert(json.msg);
              toClear();
           }
        }
     });
  }
  
  function toClear(){
	  $('#oldpwd').val('');
	  $('#userpwd').val('');
	  $('#userpwd1').val('');
    // document.getElementById("oldpwd").value = "";
     //document.getElementById("userpwd").value = "";
     //document.getElementById("userpwd1").value = "";
  };
</script>
</head>
 <body class="easyui-layout" data-options="fit:true">
	<div align="center"  data-options="region:'center',border:false" style="overflow:hidden;">
	<center>
		<table border="3" cellpadding="0" cellspacing="0" width="340"
			bordercolorlight='#FFFFF8' bordercolordark='#000099'
			bgcolor="#FFFFF8" height="164">
			<br>
			<tr>
				<td width="100%" height="162">
					<table border="0" cellpadding="0" cellspacing="0" width="100%"
						height="166">
						<tr>
							<td width='100%' colspan='2' height='15' bgcolor='#0066CC'>
								<font color="#FFFFFF" style="font-size:10pt">&nbsp; �����޸�</font>
							</td>
						</tr>
						<tr>
							<td width="100%" align="center" colspan="2" height="18">&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td width="52%" align="center" height="25"><font
								color="#000099" style="font-size:12pt">�û��� </font>
							</td>
							<td width="48%" height="25"><input type="text"
								value="<%=userId %>" id="userid" name="userid" size=8 maxlength="8"
								style="width:100;height:23" readonly><br>
							</td>
						</tr>
						<tr>
							<td width="52%" align="center" height="25"><font
								color="#000099" style="font-size:12pt">������ </font>
							</td>
							<td width="48%" height="25"><input type="password" value=""
								id="oldpwd" name="oldpwd" maxlength="8" size="8" 
								style="width:100;height:23"></td>
						</tr>
						<tr>
							<td width="52%" align="center" height="25"><font
								color="#000099" style="font-size:12pt">������ </font>
							</td>
							<td width="48%" height="25"><input type="password" value=""
								id="userpwd" name="userpwd" maxlength="8" size="8" 
								style="width:100;height:23">
							</td>
						</tr>
						<tr>
							<td width="52%" align="center" height="25"><font
								color="#000099" style="font-size:12pt">ȷ������ </font>
							</td>
							<td width="48%" height="25"><input type="password" value=""
								id="userpwd1" name="userpwd1" maxlength="8" size="8" 
								style="width:100;height:23">
							</td>
						</tr>
						<tr>
							<td style="padding-left:2cm">
							    <input type="button" value="ȷ ��" onclick="change()" />
							</td>
							<td style="padding-left:1cm">
							    <input type="button" value="ȡ ��" onclick="toClear()" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</center>
	</div>
</html>
