<%@page import="com.hr.dao.BaseDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@ page import="com.hr.global.util.ArchiveUtil"%>
<%
	String zdbdbName=request.getParameter("zdbdbName");
	BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
	String deptNum=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
	String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
	String lsh=IncString.formatNull(request.getParameter("lsh"));
	
	String fields1 = "xmbh, xmfzr, xmmc, ywhc, qymc, dkje, by1, dkqx, bz, ycry, ljr"; 
	String sql1="select " + fields1 + " from "+dbName+"_xmwj_"+deptNum+" where lsh ='" + lsh + "'";
	String[] str = op.queryRow(sql1);
	String fields2 = "sort_num, damc, days, dabz"; 
	String sql2="select " + fields2 + " from "+dbName+"_xmwj_"+deptNum+" where lsh ='" + lsh + "' order by sort_num";
	String[][] tab = op.queryRowAndCol(sql2);	
%>

<HTML>
<HEAD>
<TITLE>卷内文件目录</TITLE>
</HEAD>
<BODY style="margin:0px">
	<style>
<!--
.fontB {
	font-size: 13pt;
	font-weight: bold
}
.fontInfo {
	font-size: 13pt;
}
.fontB2 {
	font-size: 12pt;
	font-weight: bold
}
.bzTD {
	height: 33px;
	width: 100px;
	font-family: "楷体_GB2312", "宋体";
}
.height1 {
	height: 40;
}
-->
</style>
	<style media=print>
.Noprint {
	display: none;
}
.PageNext {
	page-break-after: always;
}
</style>
	<table border="0" cellspacing="0" cellpadding="0" style="width:600">
		<tr>
			<TD align="center" colspan="2"><FONT color="#000000"
				style='font-size:18pt' face="黑体">卷 内 文 件 目 录</FONT>
			</TD>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<TD align="right"></TD>
		</tr>
		<tr>
			<TD colspan="2">
				<table border="0" cellPadding="0" cellSpacing="0"
					style="border-collapse:collapse;" width="691">
					<tr height="24">
						<td width="114" class="fontB">借款合同号</td>
						<td width="221" class="fontInfo"><%=str[0] %></td>
						<td width="142" class="fontB">信 贷 员</td>
						<td width="207" class="fontInfo"><%=str[1] %></td>
					</tr>
					<tr height="24">
						<td width="114" class="fontB">项目名称</td>
						<td width="221"><font class='fontInfo'><%=str[2] %></font>
						</td>
						<td width="143" class="fontB">中方实施单位</td>
						<td width="207"><font class='fontInfo'><%=str[3] %></font>
						</td>
					</tr>
					<tr height="24">
						<td width="114" class="fontB">借 款 人</td>
						<td width="221"><font class='fontInfo'><%=str[4] %></font>
						</td>
						<td width="143" class="fontB">贷款金额</td>
						<td width="207" class="fontInfo"><%=str[5] %>万元</td>
					</tr>

					<tr height="24">
						<td width="114" class="fontB">保 证 人</td>
						<td width="221" class="fontInfo"><%=str[6] %></td>
						<td width="142" class="fontB">贷款期限</td>
						<td width="207" class="fontInfo"><%=str[7] %></td>
					</tr>

					<tr height="24">
						<td width="114" class="fontB">担保方式</b>
						</td>
						<td width="221"><font class='fontInfo'><%=str[8] %></font>
						</td>
						<td width="142" class="fontB">贷款利率</td>
						<td width="207"><font class='fontInfo'><%=str[9].split("`")[3] %></font>
						</td>
					</tr>
				</table>
				<table border="2" cellspacing="0" cellpadding="0"
					style="width:640;border-collapse:collapse" bordercolor="#000000">
					<tr>
						<td width="35" align="center" class="fontB2">序号</td>
						<td width="490" align="center" class="fontB2">档案内容</td>
						<td width="35" align="center" class="fontB2">页数</td>
						<td width="80" align="center" class="fontB2">备注</td>
					</tr>
					<%for(int i = 0; i < tab.length; i++) {	%>
					<TR class=height1>
						<TD><center>
								<font style='font-size:10pt'><%=tab[i][0] %><font>
							</center>
						</TD>
						<TD><font style='font-size:10pt'><%=tab[i][1] %></font>
						</TD>
						<TD align='center'><font style='font-size:10pt'><%=tab[i][2] %></font>
						</TD>
						<TD><font style='font-size:10pt'><%=tab[i][3] %></font>
						</TD>
					</tr>
					<%} %>
					</TBODY>
				</TABLE></td>
		</tr>
	</table>
	<div style="float:left;padding:20p;">
		<table border=0 cellspacing=0 cellpadding=0 width=600>
			<tr style="height:32">
				<td class="bzTD">立卷人：</td>
				<td class="bzTD"><%=str[10] %></td>
				<td class="bzTD">复核人：</td>
				<td class="bzTD">&nbsp;</td>
				<td class="bzTD">审核人：</td>
				<td class="bzTD">&nbsp;</td>
			</tr>
			<tr>
				<td class="bzTD">审核负责人：</td>
				<td class="bzTD">&nbsp;</td>
				<td class="bzTD">签收人：</td>
				<td class="bzTD">&nbsp;</td>
				<td class="bzTD">签收负责人：</td>
				<td class="bzTD">&nbsp;</td>
			</tr>
		</table>
	</div>
</BODY>
</HTML>
