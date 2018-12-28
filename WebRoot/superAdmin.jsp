<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*,com.hr.util.*,com.hr.user.*"%>
<%session.removeAttribute("bean_sess");
%>
<html>
<head>
<title>欢迎使用项目档案管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%@ include file="/publicJsp/libInclude.jsp" %>
</head>
<BODY topmargin="0" leftmargin="0" style="overflow:hidden;">
 <form   method='post'  id="findex" action="<%=cxt%>/do/UserDo/login">
    <input type="hidden" value="" name="oklogin" >
    <input type="hidden" value="" name="cd" >
   <table border="0" cellpadding="0" cellspacing="0" width="900px" height="400px" align="center" valign="center" bordercolorlight="#FFFFCC" bordercolordark="#CC6600">
     <tr>
     	<td width="100%" height="30px">&nbsp;</tds>
     </tr>
     <tr>
	   <td width="100%" height="443px" align="center">
	       <table border="0" cellpadding="0" cellspacing="0" width="900px" height="384px" background="images/login1.png" align="center">
	         <tr>
	           <td width="500px" height="40px"></td>
	           <td width="350px" height="40px"></td>
	           <td width="50px" height="40px"></td>
	         </tr>
	         <tr>
	         	<td width="500px">&nbsp;</td>
	         	<td width="350px" align="left">
	         		<table border="0" cellpadding="0" cellspacing="0" width="100%">
	         			<tr>
				           <td width="170px" height="34px">
				             <p align="right"><font style="font-size:10pt;" color="#2c88d8">&nbsp;</font></td>
				           <td width="180px" height="34px"><input type="hidden" style="border: 1 solid #A4BED4;width:140px;height:19" value="<%=ConfigInfo.getVal("basicDataBase") %>" name="dataBaseName"></td>
				         </tr>
				         <tr>
				           <td width="170px" height="34px">
				             <p align="right"><font style="font-size:10pt" color="#2c88d8">&nbsp;</font></td>
				           <td width="180px" height="34px"><input type="text" value="" name="userid" id="userid"  maxlength="8" size="19"  style="border: 1 solid #A4BED4;width:140px;height:19"></td>
				         </tr>
				         <tr>
				           <td width="170px" height="34px">
				             <p align="right"><font style="font-size:10pt" color="#2c88d8">&nbsp;</font></td>
				             <td width="180px" height="34px"><input type="password" value="" name="userpwd" id="userpwd" maxlength="8" size="19" style="border: 1 solid #A4BED4;width:140px;height:19"></td>
				         </tr>
				       
				         <tr height="40px">
				           <td width="350px" colspan="2" align="left">
				            <table border="0" cellpadding="0" cellspacing="0" width="100%">
				         	     <tr>
							         <td width="85px" height="20px">&nbsp;</td>
							          <td width="265px"  height="20px" valign="bottom">
							           <IMG STYLE="cursor:hand" border="0" src="images/login_qd.jpg" width="50" height="19" id="id_qd" onclick="save();">&#12288;&#12288;
							           <IMG STYLE="cursor:hand" border="0" src="images/login_ex.jpg" width="50" height="19"  onclick="window.close();">&#12288;&#12288;
							           <IMG STYLE="cursor:hand" border="0" src="images/login_fh.jpg" width="50" height="19"  onclick="goIndex();">&#12288;&#12288;
							          </td>
						         </tr>
				         	  </table>
	         	           </td>
				         </tr>
	         		</table>
	         	</td>
	         	<td width="50px" height="50px">&nbsp;</td>
	         </tr>
	         <tr>
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
		$('form#findex').form('submit', {
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.success) {
					window.location.href='<%=cxt%>'+json.param;
				}else{
					$.messager.show({
						title : '提示',
						msg : json.msg
					});
				}
			}
		});
}
 
 function goIndex(){
	 window.location.href="<%=cxt%>/index.jsp";
 }
</script>

</html>