<%@page import="com.hr.dao.BaseDao"%>
<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@ include file="printset.jsp"%>
<%
String zdbdbName=request.getParameter("zdbdbName");
BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
String cName=IncString.formatNull(ArchiveUtil.getCurrentUser(session).getDwmc());
String deptNum=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
int beginPage=IncString.formatInt(request.getParameter("beginPage"));
int endPage=IncString.formatInt(request.getParameter("endPage"));
int rowsNum=IncString.formatInt(request.getParameter("rowsNum"));
String[][] val=null;
String sql="select xmnd,xmbh,dasryy,dasrmm,dasrdd,xyjs from "+"zdb_xmwj_"+deptNum;
val=op.queryRowAndCol(sql);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <script src="../js/jquery-1.8.0.min.js"></script>
    <script src="../js/jquery.PrintArea.js"></script>
	<script>
	$(function(){
        $("#btnPrint").click(function(){ $("#printContent").printArea(); });
	});
	</script>
</head>
<BODY>
 <!--input type="button" id="btnPrint" style="margin-left:270px;margin-bottom:20px" value="打印"/-->
 <div id="printContent" >
 		<%
 			if(val!=null&&val.length>0){
	 		for(int i=beginPage;i<=endPage;i++){
	 	%>
<p align="center"><b><font style='font-size:18pt'>项目档案流水登记表</font></b></p>
<center><table border="2" bordercolor="#000000" cellspacing="0"  cellpadding="0" style="width:950;border-collapse:collapse">
  <colgroup>
    <col style="WIDTH: 44pt; mso-width-source: userset; mso-width-alt: 1888" width="59">
    <col style="WIDTH: 55pt; mso-width-source: userset; mso-width-alt: 2336" width="73">
    <col style="WIDTH: 125pt; mso-width-source: userset; mso-width-alt: 5344" width="167">
    <col style="WIDTH: 71pt; mso-width-source: userset; mso-width-alt: 3008" width="94">
    <col style="WIDTH: 67pt; mso-width-source: userset; mso-width-alt: 2848" width="89">
    <col style="WIDTH: 74pt; mso-width-source: userset; mso-width-alt: 3136" width="98">
    <col style="WIDTH: 94pt; mso-width-source: userset; mso-width-alt: 4000" width="125">
    <col style="WIDTH: 88pt; mso-width-source: userset; mso-width-alt: 3744" width="117">
    <col style="WIDTH: 54pt" width="72">
  <tbody>
    <tr height="17" style="HEIGHT: 12.75pt; mso-height-source: userset">
      <td  height="49" style="border-style: solid; border-color: #000000" bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" align="center" rowspan="2"><b>顺序号</b></td>
      <td  bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" style="border-style: solid; border-color: #000000" align="center" rowspan="2"><b>年度</b></td>
      <td  bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" style="border-style: solid; border-color: #000000" align="center" rowspan="2"><b>项目编号</b></td>
      <td align="center"  colSpan="2" style="border-style: solid; border-color: #000000" bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000"><b>档
        案 收 入</b></td>
      <td class="xl31" style="border-style: solid; border-color: #000000" bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" align="center" colspan="3"><b>档
        案 移 出</b></td>
      <td class="xl38" style="border-style: solid; border-color: #000000" bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" align="center"><b>现有档案</b></td>
    </tr>
    <tr height="32" style="HEIGHT: 24pt; mso-height-source: userset">
      <td  bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" style="border-style: solid; border-color: #000000" align="center"><b>日期</b></td>
      <td  bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" style="border-style: solid; border-color: #000000" align="center"><b>卷数</b></td>
      <td  bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" style="border-style: solid; border-color: #000000" align="center"><b>日期</b></td>
      <td  bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" style="border-style: solid; border-color: #000000" align="center"><b>移往何处</b></td>
      <td  bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" style="border-style: solid; border-color: #000000" align="center"><b>移出原因</b></td>
      <td  style="border-style: solid; border-color: #000000" bordercolor="#000000" bordercolorlight="#FFFFFF" bordercolordark="#000000" align="center"><b>卷数</b></td>
    </tr>
  </tbody>

			    <%
			    	
			    		for(int j=(i-1)*rowsNum;j<(i-1)*rowsNum+22;j++){
			    		if(j>=val.length){
			    %>
			    	<tr>
						<td align="center" style='height:18.0pt' >&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
				    </tr>
			    <%	
			    		}else{
			    %>
				    <tr>
						<td align="center" style='height:18.0pt'><center><font style='font-size:10pt'><%=j+1%>&nbsp;</font></center></td>
						<td align="center" style='height:18.0pt'><center><font style='font-size:10pt'><%=val[j][0]%></font></center></td>
						<td align="center" style='height:18.0pt'><font style='font-size:10pt'><%=val[j][1]%></font></td>
						<%
							String day = val[j][2]+"."+val[j][3]+"."+val[j][4];
							if("0".equals(val[j][2]) || "0".equals(val[j][3]) || "0".equals(val[j][4]))
			    				day = "";
			    		%>
						<td align="center" style='height:18.0pt'><font style='font-size:10pt'><%=day%></font></td>
						<td align="center" style='height:18.0pt'><font style='font-size:10pt'><%=val[j][5]%></font></td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
						<td align="center" style='height:18.0pt'>&nbsp;</td>
				    </tr>
			    <%
			    		}
			    %>
			    <%
			    		}
			    %>
			</table></center>
	 	<%
	 		}
	 		}
 		%>
		
</div>
</BODY>
</HTML>