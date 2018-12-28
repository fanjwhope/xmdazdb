<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@page import="java.util.*,com.hr.util.*,com.hr.user.*"%>
<%
String cxt=request.getContextPath();
String cName=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getDwmc());
String zdbdbName=request.getParameter("zdbdbName");
%>
<html>
<head>
<title>项目档案管理系统</title>
<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/xmda/favicon.ico"> 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="../publicJsp/libInclude.jsp" />
<jsp:include page="heartBeat.jsp" />
<STYLE type="text/css">
.a:link {COLOR: #FFffff; text-DECORATION: none;}
.a:visited {COLOR:#ffffff ; text-DECORATION: none;}
.a:hover {COLOR: #ffffff ; text-DECORATION: none;}
TD{FONT-SIZE: 12PX;}
body {margin:0;padding:0;height:100%;font:MessageBox;}
.tdover {border-top:1px solid #fcfcfc;border-left:1px solid #fcfcfc;border-bottom:1px solid #000000;border-right:1px solid #000000;cursor:hand;background-color:transformet}
.tddefault {border-top:1px solid buttonface;border-left:1px solid buttonface;border-bottom:1px solid buttonface;border-right:1px solid buttonface;background-color:transformet;}
</STYLE>
</head>
<body class="easyui-layout"  data-options="fit:true" style="width:100%;height:100%;">
<div data-options="region:'north',border:false" style="overflow:hidden;background:ButtonFace;">
	<table style="width:100%;height:37px;border:1px solid #A9A9A9;" cellspacing="0" cellpadding="0">
		<tr>
			<td nowrap="true" style="width:55px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../userManage.jsp');"><img align="absmiddle" src="../images/top_icon6.gif" width="24" height="24"  border="0"  >用户管理</td>
			<td nowrap="true" style="width:55px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../biz.jsp');"><img align="absmiddle" src="../images/top_icon2.gif" width="24" height="24"  border="0"  >币种维护</td>
			<td nowrap="true" style="width:45px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../standard.jsp');"><img align="absmiddle" src="../images/top_icon1.gif" width="24" height="24"  border="0"  >标准表</td>
			<td nowrap="true" style="width:75px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../zdbstandard.jsp');"><img align="absmiddle" src="../images/top_icon1.gif" width="24" height="24"  border="0"  >转贷部标准表</td>
			<td nowrap="true" style="width:65px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../location.jsp');"><img align="absmiddle" src="../images/top_icon5.gif" width="24" height="24"  border="0"  >扫描件设置</td>	
			<td nowrap="true" style="width:55px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../basicRate.jsp');"><img align="absmiddle" src="../images/top_icon10.gif" width="24" height="24"  border="0"  >利率基准</td>
			<td nowrap="true" style="width:55px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../projectType.jsp');"><img align="absmiddle" src="../images/top_icon5.gif" width="24" height="24"  border="0"  >项目品种</td>
			<td nowrap="true" style="width:55px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../dbType.jsp');"><img align="absmiddle" src="../images/top_icon13.gif" width="24" height="24"  border="0"  >担保方式</td>
			<td nowrap="true" style="width:55px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrlws('webSearch.jsp?roles=0&zdbdbName=<%=zdbdbName%>');"><img align="absmiddle" src="../images/top_icon1.gif" width="24" height="24"  border="0"  >网上查询</td>
			<td nowrap="true" style="width:55px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../privilegePages/main.jsp');"><img align="absmiddle" src="../images/top_icon4.gif" width="24" height="24"  border="0"  >权限设置</td>
			<td nowrap="true" style="width:65px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../delLibrary.jsp');"><img align="absmiddle" src="../images/clear.png" width="24" height="24"  border="0"  >删除过时库</td>
			<td nowrap="true" class="tddefault" style="width:85px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goZY();"><img align="absmiddle" src="../images/icon_35.gif" width="24" height="24"  border="0"  >自营项目</td>	
			<td nowrap="true" style="width:85px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../logInfo.jsp');"><img align="absmiddle" src="../images/top_icon8.gif" width="24" height="24"  border="0"  >日志管理</td>
			<td nowrap="true" style="width:85px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../pwdManage.jsp');"><img align="absmiddle" src="../images/top_icon7.gif" width="24" height="24"  border="0"  >修改口令</td>
			<td nowrap="true" style="width:85px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onclick="closeWind();"><img align="absmiddle"  src="../images/top_icon9.gif" width="24" height="23" border="0" >&nbsp;关 闭</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table style="width:100%;height:1px;" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td style="height:1px;"></td>
		</tr>
	</table>
	<table style="width:100%;height:21px;border:1px solid #A9A9A9;" cellspacing="0">
	  <tr>
	   <td valign="top" nowrap="true">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="21px">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="0" width=500 nowrap="true">
								<table border="0" cellpadding="0" cellspacing="0" >
									<tr>	
										<td nowrap="true" height="21px" width="40" align="center"><img src="../images/top-tools.gif" width="9" height="8"><img src="../images/top-tools.gif" width="9" height="8"><img src="../images/top-tools.gif" width="9" height="8"></td>
										<td nowrap="true" height="21px" width="14" align="center"><img src="../images/top-tools8.gif" width="14" height="14"></td>
										<td nowrap="true" height="21px" width="144"> <font color="#000000"> 　 用户:<%=cName%></font></td>
										<td nowrap="true" height="21px" width="14" align="center"><img src="../images/top-tools7.gif" width="14" height="14" border="0"></td>
										<td nowrap="true" height="21px" width="163" align="center"><font color="#000000">日期:</font><font color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><%=Md5.date("yyyy-MM-dd")%></font></td>
									</tr>
								</table>
							</td>
							<td align="right" width="0" nowrap="true" width=250>
								<table border="0" cellpadding="0" cellspacing="0" >
									<tr>
										<td nowrap="true" height="21px" width="0" align="center"><img src="../images/top-tools6.gif" width="14" height="14"></td>
										<td nowrap="true" height="21px" align="center"><font color="#000000" face="Verdana, Arial, Helvetica, sans-serif">　<%=Md5.date("HH:mm")%>登录</font></td>
										<td nowrap="true" height="21px" align="center" width="20">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	   <td style="width:100%;height:30px;" align="center">版权所有：北京海润创新科技有限公司</td></td>
	  </tr>
	</table>
</div>
<div data-options="region:'center',border:false" style="overflow: hidden">
	<iframe id="frameMain" name="frameMain" src="" style="width:100%;height:100%;border:0;overflow: hidden;" scrolling="no" FRAMEBORDER="0"></iframe>
</div>
<div data-options="region:'south',border:false">
	<table nowrap="true" style="width:100%;height:21px;border-top:1px solid #A9A9A9;" cellspacing="0" cellpadding="0" >
		<tr>
			
		</tr>
	</table>
</div>
</body>
</html>
<script language="javascript">
        $(function(){
        	$('#frameMain').attr("src",'../logo.jsp');
        });
	function goUrl(url){
		$('#frameMain').attr("src",url+'?zdbdbName=<%=zdbdbName%>');
	}
	function goUrlws(url){
		$('#frameMain').attr("src",url);
	}
	function goZY(){
		window.location.href="<%=cxt%>/pages/systemMenuZY.jsp?zdbdbName=zdbzy";
	}  
	function closeWind(){
		var userAgent = navigator.userAgent;
		 window.location.href="<%=cxt%>/index.jsp";
		<%-- if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {
		   window.location.href="<%=cxt%>/index.jsp";
		  // window.location.href = '/closekiosk';
		   //var href =window.location.href;
			//var href ="about:blank"; 
			//opener.window.close();
			//window.open('','_self','');
		    // window.close();
		    //open(location, '_self').close();
			//window.open(location, '_self').close();
		} else {
		   window.opener = null;
		   window.open("", "_self");
		   window.close();
		} --%>
     }
</script>