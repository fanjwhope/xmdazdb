<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*,com.hr.util.*,com.hr.user.*"%>
<%  String path = request.getContextPath();
     session.invalidate(); %>
<html>
<head>
<title>欢迎使用项目档案管理系统</title>
<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/xmda/favicon.ico"> 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%@ include file="/publicJsp/libInclude.jsp" %>
</head>
<BODY topmargin="0" leftmargin="0" style="overflow:hidden;">
 <form   method='post'  id="findex" action="<%=cxt%>/do/UserDo/login?zdbdbname=zdb">
    <input type="hidden" value="" name="oklogin" >
    <input type="hidden" value="" name="cd" >
   <table border="0" cellpadding="0" cellspacing="0" width="900px" height="400px" align=center valign="center" bordercolorlight="#FFFFCC" bordercolordark="#CC6600">
      <tr>
     	<td width="100%" height="30px">&nbsp;</tds>
     </tr>
     <tr>
	   <td width="100%" height="443px" align="center">
	       <table border="0" cellpadding="0" cellspacing="0" width="900px" height="384px"  background="images/login1.png" align="center">
	         <tr >
	           <td width="500px" height="58px">&nbsp;</td>
	           <td width="350px" height="58px">&nbsp;</td>
	           <td width="50px" height="58px">&nbsp;</td>
	         </tr>
	         <tr width="100%"  height="84px">
	         	<td width="500px" height="84px">&nbsp;</td>
	         	<td width="350px" align="left">
	         		<table border="0" cellpadding="0" cellspacing="0" width="100%"  height="114px">			           
				         <tr>
				           <td width="170px" height="24px">
				           <input type="hidden" id="dataBaseName" name="dataBaseName"  value="zdb" />
				           <input type="hidden" id="zdbdbName" name="zdbdbName"  value="zdb" />
				             <p align="right"><font style="font-size:10pt" color="#2c88d8">&nbsp;<!-- 请输入用户名： --></font></td>
				           <td width="180px" height="24px"><input type="text" value="" name="userid" id="userid"  maxlength="8" size="19"  style="border: 1 solid #A4BED4;width:140px;height:20"></td>
				         </tr>
				         <tr>
				           <td width="170px" height="24px">
				             <p align="right"><font style="font-size:10pt" color="#2c88d8">&nbsp;<!-- 输入您的口令： --></font></td>
				             <td width="180px" height="24px"><input type="password" value="" name="userpwd" id="userpwd" maxlength="8" size="19" style="border: 1 solid #A4BED4;width:140px;height:20"></td>
				         </tr>
				         <tr height="35px">
	                        <td width="350px" colspan="2" align="left">
				         	  <table border="0" cellpadding="0" cellspacing="0" width="100%">
				         	     <tr>
							         <td width="85px" height="30px">&nbsp;</td>
							          <td width="265px"  height="30px" valign="bottom">
							           <IMG STYLE="cursor:hand" border="0" src="images/login_qd.jpg" width="50" height="19" id="id_qd" onclick="save();">&#12288;&#12288;
							           <IMG STYLE="cursor:hand" border="0" src="images/login_ex.jpg" width="50" height="19"  onclick="window.close();">&#12288;&#12288;
							          </td>
						         </tr>
				         	  </table>
	         	           </td>
	                  </tr>
	         		</table>
	         	</td>
	         	<td width="50px" height="84px">&nbsp;</td>
	         </tr>
	         <tr  >
	           <td width="500px" height="50px"></td>
	           <td width="350px" height="50px"></td>
	           <td width="50px" height="50px"></td>
	         </tr>
	       </table>
	   </td>
     </tr>
     <tr>
     	 <td width="100%" height="50px" align="center"></td>
     </tr>
     <tr>
     	<td width="100%" height="30px" align="center"><font style="font-size:10pt">中国进出口银行版权所有</font></td>
     </tr>
   </table>
</form>
<div style="display: hidden">
  <form action="<%=cxt%>/loginDo" method="post" id="login">
    <input type="hidden" id="url" name="url" />
  </form>
</div>
</BODY>


<script lanuage="javascript">

   $(function(){
	
	   
	   $('#userpwd').keypress(function(event){
		   if(event.which==13){
			   save(); 
		   }
	   });
   });


 function save() {
		/*
		var dataBaseName=$('#dataBaseName').combobox('getValue');
		if(dataBaseName == ''){
			$.messager.alert('提示','请选择分行！');
			return;
		}
		*/
		$('form#findex').form('submit', {
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.success) {//登录成功
					//$.cookie('dataBaseName',dataBaseName,{expires:30,path:'/'});
					//window.location.href='<%=cxt%>'+json.param;
					location.replace('<%=cxt%>'+json.param);
					//$('#url').val(json.param);
					//$('#login').submit();
				}else{
					$.messager.show({
						title : '提示',
						msg : json.msg
					});
				}
			}
		});
}

 
 function goAdmin(){
	 window.location.href="<%=cxt%>/superAdmin.jsp";
 }
</script>

</html>