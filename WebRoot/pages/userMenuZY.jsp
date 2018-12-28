<%@page import="com.hr.dao.XmwjYJDao"%>
<%@page import="com.hr.bean.XmwjYJ"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@page import="java.util.*,com.hr.util.*,com.hr.user.*"%>
<%
String cxt=request.getContextPath();
String cName=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getDwmc());
String tab=ArchiveUtil.getFullTableName(request, XmwjYJ.class);
String jsdw=ArchiveUtil.getDepartmentCode(request.getSession());
String roles=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getRoles());
String zdbdbName=request.getParameter("zdbdbName");
XmwjYJDao archiveDao=new XmwjYJDao(tab,zdbdbName);
XmwjYJ xmwjYJ=new XmwjYJ();
xmwjYJ.setJsdw(jsdw);
List<XmwjYJ> list= archiveDao.getListToReceive(xmwjYJ);
int leng=list.size();

%>
<html>
<head>
<title>项目档案管理系统</title>
<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/xmda/favicon.ico"> 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<jsp:include page="../publicJsp/libInclude.jsp" />
<jsp:include page="heartBeat.jsp" />
<!-- <STYLE type="text/css">
.a:link {COLOR: #FFffff; text-DECORATION: none;}
.a:visited {COLOR:#ffffff ; text-DECORATION: none;}
.a:hover {COLOR: #ffffff ; text-DECORATION: none;}
TD{FONT-SIZE: 12PX;}
body {margin:0;padding:0;height:100%;font:MessageBox;}
.tdover {border-top:1px solid #fcfcfc;border-left:1px solid #fcfcfc;border-bottom:1px solid #000000;border-right:1px solid #000000;cursor:hand;background-color:transformet}
.tddefault {border-top:1px solid buttonface;border-left:1px solid buttonface;border-bottom:1px solid buttonface;border-right:1px solid buttonface;background-color:transformet;}
</STYLE> -->
<STYLE type="text/css"> 
.leftpage{
       margin-top:0px; margin-bottom:0px;bgColor="#ffffff" ; 
 }
.normal{
        padding:0px;  border-left:1px solid #0094BD; border-right:1px solid #0094BD;border-top:1px solid #0094BD; border-bottom:1px solid #0094BD; width:100%;height:100%;
}
.over{
     padding:0px; border-left:1px solid #ffffff; border-right:1px solid #1B242E;border-top:1px solid #ffffff; border-bottom:1px solid #1B242E; width:100%;height:100%;
}
.down{
     padding:0px;  border-left:1px solid #1B242E; border-right:1px solid #ffffff;border-top:1px solid #1B242E; border-bottom:1px solid #ffffff; width:100%;height:100%;
}
.frot1{
     COLOR: #000000; CURSOR: hand; FONT-FAMILY: "Webdings"; FONT-SIZE: 14px
}
.navPoint{
     COLOR: white; CURSOR: hand; FONT-FAMILY: Webdings; FONT-SIZE: 9pt
 }
.a:link{
      COLOR: #FFffff; TEXT-DECORATION: none;
}
.a:visited{
       COLOR:#ffffff ; TEXT-DECORATION: none;
}
.a:hove{
      COLOR: #ffffff ; TEXT-DECORATION: none;
}
TD{
    FONT-SIZE: 12PX;
 }
.smallFont {
        FONT-FAMILY: "宋体"; FONT-SIZE: 9pt; TEXT-DECORATION: none
}
.input{valign="top";height="18px";font-size:14px;padding-top:1px;padding-left:5px;padding-right:5px;
               background-color: #0094BD; color: #ffffff;
               border-left: 0 solid #FFffff; border-right: 0 solid #000099;
               border-top: 0 solid #FFffff; border-bottom: 0 solid #000099;
               cursor:hand
               }
 
.input1{valign="top";height="18px";font-size:14px;padding-top:1px;padding-left:5px;padding-right:5px;
               background-color: #0094BD; color: #ffffff;
               border-left: 0 solid #FFffff; border-right: 0 solid #000099;
               border-top: 0 solid #FFffff; border-bottom: 0 solid #000099}
.input2{valign="top";height="18px";font-size:14px;padding-top:1px;padding-left:5px;padding-right:5px;
               background-color: ButtonFace; color: #000000;
               border-left: 0 solid #FFffff; border-right: 0 solid #000099;
               border-top: 0 solid #FFffff; border-bottom: 0 solid #000099;
               cursor:hand
               }
body {"border:none;margin:0;padding:0;height:100%;color:ButtonText;font:MessageBox;border:0;background: ButtonFace;"}
.tdover {border-top:1px solid #fcfcfc;border-left:1px solid #fcfcfc;border-bottom:1px solid #000000;border-right:1px solid #000000;cursor:hand;background-color:transformet}
.tddefault {border-top:1px solid buttonface;border-left:1px solid buttonface;border-bottom:1px solid buttonface;border-right:1px solid buttonface;background-color:transformet;}
</STYLE>

</head>
<body class="easyui-layout"  data-options="fit:true" style="width:100%;height:100%;">
<input type="hidden" value="zy" id="zyorzd">
<div data-options="region:'north',border:false" style="overflow:hidden;background:ButtonFace;">
	<table  nowrap="true" style="width:100%;height:37;background:ButtonFace;border:2px groove;" cellspacing="0">
		<tr >
			<!-- <td nowrap="true" style="width:85px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('');"><img align="absmiddle" src="../images/top_icon3.gif" width="24" height="24"  border="0"  >查询汇编</td> -->
			<% if("1".equals(roles)||"2".equals(roles)) {%>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('archiveAdd.jsp');"><img align="absmiddle" src="../images/top_icon1.gif" width="24" height="24"  border="0"  >项目著录</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('wusmj.jsp');"><img align="absmiddle" src="../images/icon_17.gif" width="24" height="24"  border="0"  >无扫描件</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../queryHuiBian.jsp');"><img align="absmiddle" src="../images/top_icon4.gif" width="24" height="24"  border="0"  >项目查询</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../printPages/printMain.jsp');"><img align="absmiddle" src="../images/top_icon5.gif" width="24" height="24"  border="0"  >编目打印</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../archiveTransfer.jsp');"><img align="absmiddle" src="../images/top_icon2.gif" width="24" height="24"  border="0"  >项目移交</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../archiveReceive.jsp');"><img align="absmiddle" src="../images/top_icon10.gif" width="24" height="24"  border="0"  >项目接收</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../transferRefuse.jsp');"><img align="absmiddle" src="../images/top_icon13.gif" width="24" height="24"  border="0"  >移交退回</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrlws('webSearch.jsp?roles=<%=roles%>&zdbdbName=zdbzy');"><img align="absmiddle" src="../images/top_icon8.gif" width="24" height="24"  border="0"  >网上查询</td>
			<td nowrap="true" class="tddefault" style="width:85px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../privilegePages/priProjectList.jsp');"><img align="absmiddle" src="../images/top_icon6.gif" width="24" height="24"  border="0"  >项目授权</td>
			<td nowrap="true" class="tddefault" style="width:85px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goZD();"><img align="absmiddle" src="../images/icon_35.gif" width="24" height="24"  border="0"  >转贷项目</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../pwdManage.jsp');"><img align="absmiddle" src="../images/top_icon7.gif" width="24" height="24"  border="0"  >修改口令</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onclick="_windowClose();"><img align="absmiddle"  src="../images/top_icon9.gif" width="24" height="23" border="0" >&nbsp;关 闭</td>
			<%}else if("3".equals(roles)){ %>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrlws('webSearch.jsp?roles=<%=roles%>&zdbdbName=zdbzy');"><img align="absmiddle" src="../images/top_icon6.gif" width="24" height="24"  border="0"  >网上查询</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onClick="goUrl('../pwdManage.jsp');"><img align="absmiddle" src="../images/top_icon7.gif" width="24" height="24"  border="0"  >修改口令</td>
			<td nowrap="true" class="tddefault" style="width:82px;height:37px;" onmouseover="this.className='tdover'" onmouseout="this.className='tddefault'" onclick="_windowClose();"><img align="absmiddle"  src="../images/top_icon9.gif" width="24" height="23" border="0" >&nbsp;关 闭</td>
			<%} %>
			<td >&nbsp;</td>
		</tr>
	</table>
	<!-- <table style="width:100%;height:1px;" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td style="height:1px;"></td>
		</tr>
	</table> -->
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
										<td nowrap="true" height="13px" width="40" align="center"><img src="../images/top-tools.gif" width="9" height="8"><img src="../images/top-tools.gif" width="9" height="8"><img src="../images/top-tools.gif" width="9" height="8"></td>
										<td nowrap="true" height="13px" width="14" align="center"><img src="../images/top-tools8.gif" width="14" height="14"></td>
										<td nowrap="true" height="21px" width="144"> <font color="#000000"> 　 用户:<%=cName%></font></td>
										<td nowrap="true" height="21px" width="14" align="center"><img src="../images/top-tools7.gif" width="14" height="14" border="0"></td>
										<td nowrap="true" height="21px" width="163" align="center"><font color="#000000">日期:</font><font color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><%=Md5.date("yyyy.MM.dd")%></font></td>
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
	   </td>
	  </tr>
	</table>
</div>
 <div id="iframeDiv" data-options="region:'center',border:false" style="overflow: hidden">
	<iframe id="frameMain" name="frameMain" src="" style="width:100%;height:100%;border:0;overflow: hidden;" scrolling="no" FRAMEBORDER="0"></iframe>
</div> 
<!-- <div data-options="region:'south',border:false">
	<table nowrap="true" style="width:100%;height:21px;border-top:1px solid #A9A9A9;" cellspacing="0" cellpadding="0">
		<tr>
			<td style="width:100%;height:30px;" align="center">版权所有：</td>
		</tr>
	</table>
</div> -->
</body>
</html>
<script language="javascript">
    $(function(){
    	 if(parseInt('<%=leng%>')>0){
    		 goUrl('../archiveReceive.jsp');
    	 } else{
    		 goUrl('../logoZY.jsp');
    	 }
     }); 
	function goUrl(url){
		$('#frameMain').attr("src",url+"?zdbdbName=zdbzy");
	}  
	function goUrlws(url){
		$('#frameMain').attr("src",url);
	}  
	function goZD(){
		window.location.href="<%=cxt%>/pages/userMenu.jsp?zdbdbName=zdb";;
	}  
	function _windowClose(){
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