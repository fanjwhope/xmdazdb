<%@page import="com.hr.dao.BaseDao"%>
<%@page import="com.hr.global.util.Validation"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@ page import="com.hr.global.util.ArchiveUtil"%>
<%
	String zdbdbName = request.getParameter("zdbdbName");
	String startNum = request.getParameter("startNum");
	BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
	String deptNum = IncString.formatNull(ArchiveUtil
			.getDepartmentCode(session));
	String dbName = IncString.formatNull(ArchiveUtil
			.getDataBaseName(session));
	String lsh = IncString.formatNull(request.getParameter("lsh"));
	String type = IncString.formatNull(request.getParameter("type"));
	String tbname = IncString
			.formatNull(request.getParameter("tbname"));
	String tab1 = "zdb" + "_xmwj_" + deptNum;
	if ("2002".equals(type)) {
		type = "xmlrb";
	}
	String tab2 = "zdb" + "_" + type + "_" + deptNum;
	if (!Validation.isEmpty(tbname)) {
		tab1 = tbname;
		tab2 = tbname.split("_")[0] + "_" + type + "_"
				+ tbname.split("_")[2];
	}

	String fields1 = "qymc, xmbh, xmmc, xmfzr, dkqx, xmnd, dw, dkje, tbyy, tbmm, tbdd, ljr";
	String sql1 = "select " + fields1 + " from " + tab1
			+ " where lsh ='" + lsh + "'";
	String[] str = op.queryRow(sql1);

	String fields3 = "dw, dkje,ywhc ";
	String sql3 = "select " + fields3 + " from " + tab1
			+ " where xmbh ='" + str[1] + "'";
	String[][] str3 = op.queryRowAndCol(sql3);
	String je = "";
	Integer hj = 0;
	String xyh = "";
	if (str3.length > 1) {
		for (String[] sts : str3) {
			xyh += sts[2] + "<br>";
			if (sts[0] != null && !"null".equals(sts[0])
					&& !"".equals(sts[0])) {
				je += sts[0] + ":" + sts[1] + "��<br>";
				try{
				hj += Integer.valueOf(sts[1].trim());}catch(Exception e){
						}
			} else {
				je += sts[1] + "<br>";
				try{
				hj += Integer.valueOf(sts[1].trim().split(":")[1]
						.split("��")[0]);
				}catch(Exception e){
						}
			}
		}
		je += "�ϼ�:" + hj + "��<br>";
	} else if (str3.length == 1) {
		xyh += str3[0][2] + "<br>";
		if (str3[0][0] != null && !"null".equals(str3[0][0])
				&& !"".equals(str3[0][0])){
			je = str3[0][0] + ":" + str3[0][1] + "��";
		}else {
				je += str3[0][1] + "<br>";
			}
	}

	String fields2 = "sort_num, damc, days,'', dabz";
	String sql2 = "select " + fields2 + " from " + tab2
			+ " where lsh ='" + lsh + "' order by sort_num";
	String[][] tab = op.queryRowAndCol(sql2);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>�����ļ�Ŀ¼��ӡ</title>
<style>
td {
	word-break: break-all;
	word-wrap: break-word;
}
</style>
</head>

<BODY style="margin:0px">
	<center>
		<table border="0" cellspacing="0" cellpadding="0" style="width:800">
			<tr>
				<TD align="center" colspan="2"><FONT color="#000000"
					style='font-size:18pt' face="����">�� �� �� �� Ŀ ¼</FONT></TD>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<TD align="right"></TD>
			</tr>
			<tr>
				<TD colspan="2" >
					<table style="TABLE-LAYOUT: fixed; BORDER-COLLAPSE: collapse"
						cellSpacing="0" cellPadding="0" width="800" border="0" id="table1">
						<tr>
							<td width="379">
								<table cellSpacing="0" cellPadding="0" width="118%" border="0"
									id="table2">
									<tr>
										<td noWrap width="70"><b>�����:</b></td>
										<td width="379"><%=str[0]%></td>
									</tr>
								</table>
							</td>
							<td width="379">
								<table cellSpacing="0" cellPadding="0" width="100%" border="0"
									id="table3">
									<tr>
										<td noWrap width="80"><b>��Ŀ���:</b></td>
										<td width="275"><%=str[1]%></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="44">
							<td width="379">
								<table cellSpacing="0" cellPadding="0" width="100%" border="0"
									id="table3">
									<tr>
										<td noWrap width="80"><b>��Ŀ����:</b></td>
										<td width="275"><%=str[2]%></td>
									</tr>
								</table>
							</td>
							<td width="379">
								<table cellSpacing="0" cellPadding="0" width="100%" border="0"
									id="table3">
									<tr>
										<td noWrap width="80"><b>��Ŀ������:</b></td>
										<td width="275"><%=str[3]%></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="379"><b>����:</b>  <%=str[4]%></td>
							<td width="379">
								<table cellSpacing="0" cellPadding="0" width="100%" border="0"
									id="table3">
									<tr>
										<td noWrap width="80"><b>��Ŀ���:</b></td>
										<td width="275"><%=str[5]%>��</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="379">
								<table cellSpacing="0" cellPadding="0" width="100%" border="0"
									id="table3">
									<tr>
										<td noWrap width="80"><b>���ʱ��:</b></td>
										<td width="275"><%
									if (!("0".equals(str[8]) || "0".equals(str[9]) || "0"
											.equals(str[10]))) {
								%>
								<%=str[8]%>��<%=str[9]%>��<%=str[10]%>�� <%
									}
								%></td>
									</tr>
								</table>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>
								<table cellSpacing="0" cellPadding="0" width="118%" border="0"
									id="table2">
									<tr>
										<td noWrap width="70"><b>���:</b></td>
										<td width="328"><%=je%></td>
									</tr>
								</table>
							</td>
							<td>
								<table cellSpacing="0" cellPadding="0" width="118%" border="0"
									id="table2">
									<tr>
										<td noWrap width="70"><b>Э���:</b></td>
										<td width="328"><%=xyh%></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table border="2" cellspacing="0" cellpadding="0"
						style="width:640;border-collapse:collapse;" bordercolor="#000000">
						<tr>
							<td width='5%' align='center' height='29'><b>��&nbsp;��</b></td>
							<td width='65%' align='center'><b>�ļ�ժҪ</b></td>
							<td width='7%' align='center'><b>ҳ��</b></td>
							<td width='13%' align='center' nowrap><b>ҳ��</b></td>
							<td width='10%' align='center'><b>��&nbsp;ע</b></td>
						</tr>
						<%
							if (tab != null) {
								int m=1;
								int j=0;
								try{
									j=Integer.valueOf(startNum)-1;
								}catch(Exception e){
								
								}
								int s=0;
								int e=0;
								Boolean flag=true;
								for (int i = j; i < (tab.length > 26 ? tab.length : 26); i++) {
									if (i < tab.length) {
									flag=true;
									try{
										s=e+1;
									}catch(Exception e1){
										flag=false;
									}
									try{
										e+=Integer.valueOf(tab[i][2]);
									}catch(Exception e2){
										flag=false;
									}
						%>
						<tr>
							<td width='5%' align='center' height='29'><%=m%></td>
							<td width='65%'>&nbsp;&nbsp;<%=tab[i][1]%></td>
							<td width='7%' align='center'><%=tab[i][2]%></td>
							<td width='13%' align='center' nowrap><%if(flag){%><%=s%>-<%=e%><%}else{} %></td>
							<td width='10%' align='center'><%=tab[i][4]%></td>
						</tr>
						<%
							} else {
						%>
						<tr>
							<td width='5%' align='center' height='29'>&nbsp;</td>
							<td width='65%'>&nbsp;</td>
							<td width='7%' align='center'>&nbsp;</td>
							<td width='13%' align='center' nowrap>&nbsp;</td>
							<td width='10%' align='center'>&nbsp;</td>
						</tr>
						<%
							}
							m++;
								}
							}
						%>
					</TABLE>
				</td>
			</tr>
			<%
							if (zdbdbName.equals("zdbzy")) { %>
			<tr>
				<td><table style="border-collapse:separate; border-spacing:0px 15px;"><tr><td><b>������:</b></td><td width="160"><%=str[11]%></td>
				<td><b>������:</b></td><td width="160"></td>
				<td><b>�����:</b></td><td width="160"></td></tr>
				<tr ><td><b>��˸�����:</b></td><td width="160"></td>
				<td><b>ǩ����:</b></td><td width="160"></td>
				<td><b>ǩ�ո�����:</b></td><td width="160"></td></tr>
				</table></td>
			</tr><%}%>
		</table>
		
	</center>
</body>
</html>
