<%@page import="com.hr.dao.BaseDao"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%
	String zdbdbName = request.getParameter("zdbdbName");
	BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
	String deptNum = IncString.formatNull(ArchiveUtil
			.getDepartmentCode(session));
	String dbName = IncString.formatNull(ArchiveUtil
			.getDataBaseName(session));
	String tbname = IncString
			.formatNull(request.getParameter("tbname")) == null ? ("zdb"
			+ "_xmwj_" + deptNum)
			: IncString.formatNull(request.getParameter("tbname"));
	String archiveType = request.getParameter("archiveType");
	if ("null".equals(archiveType) || archiveType == null) {
		archiveType = "dhgl";
	}
	String startNum = request.getParameter("startNum");
	String lsh = IncString.formatNull(request.getParameter("lsh"));
	String fields = "xmbh, xmmc, ljr, jcr,xmfzr,tbyy,tbmm,tbdd";
	String sql = "select " + fields + " from " + tbname
			+ " where lsh ='" + lsh + "'";
	String[] str = op.queryRow(sql);
	String sql2 = "select days from " + "zdb_" + archiveType + "_"
			+ deptNum + " where lsh='" + lsh + "' order by sort_num";
	String[][] str2 = op.queryRowAndCol(sql2);
	int pageNum = 0;
	if ("null".equals(startNum) || startNum == null) {
		startNum = "1";
	}
	int a = Integer.valueOf(startNum) - 1;
	for (int i = a; i < str2.length; i++) {
	try {
			pageNum += Integer.valueOf(str2[i][0]);
		
	} catch (Exception e) {

	}
	}
%>

<html>
<head>
<title>打印卷内文件备考表</title>
</head>
<body style="text-align: center">
	<center>
		<table border="0" width="650" id="table2" height="223">
			<tr>
				<td colspan="4" height="87">
					<p align="center">
						<b> <span
							style="font-size:22.0pt;font-family:仿宋_GB2312;color:black">卷内备考表</span></b>
				</td>
			</tr>
			<tr>
				<td width="18%" valign=bottom><font face="仿宋_GB2312"
					style="font-size: 14pt">项目编号：</font></td>
				<td width="32%" valign=bottom
					style='border-bottom-style:solid; border-bottom-width:1px'><%=str[0]%>
				</td>
				<td width="18%" valign=bottom><font style="font-size: 14pt"
					face="仿宋_GB2312">项目名称：</font></td>
				<td width="29%" valign=bottom
					style='border-bottom-style:solid; border-bottom-width:1px'><%=str[1]%>
				</td>
			</tr>
			<tr>
				<td width="18%" valign=bottom></td>
				<td width="32%" valign=bottom></td>
				<td width="18%" valign=bottom><font style="font-size: 14pt"
					face="仿宋_GB2312">本卷页数：</font></td>
				<td width="29%"
					style='border-bottom-style:solid; border-bottom-width:1px'><%=pageNum%></td>
			</tr>
			<tr>
				<td width="97%" colspan="4">
					<p align="center">
						<font face="仿宋_GB2312" size="5"><b>经办信贷员变更情况表</b></font>
				</td>
			</tr>
		</table>
		<table border="1" style='border-collapse:collapse' width="581"
			id="table1" height="256">
			<tr>
				<td width="93" height="55"></td>
				<td width="72" height="55">
					<p align="center">
						<span style="font-size: 14.0pt; font-family: 仿宋_GB2312">立卷人<span
							lang="EN-US" style="font-size:14.0pt;font-family:仿宋_GB2312">(</span>现经办信贷员<span
							lang="EN-US">)</span></span>
				</td>
				<td width="72" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:仿宋_GB2312"> 信贷员变更</span>
				</td>
				<td width="74" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:仿宋_GB2312"> 信贷员变更
						</span>
				</td>
				<td width="72" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:仿宋_GB2312"> 信贷员变更</span>
				</td>
				<td width="73" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:仿宋_GB2312"> 信贷员变更</span>
				</td>
				<td width="79" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:仿宋_GB2312"> 信贷员变更
						</span>
				</td>
			</tr>
			<tr>
				<td width="93" height="48"><b> <span
						style="font-size:14.0pt;font-family:仿宋_GB2312">信贷员</span></b></td>
				<td width="72" height="48"></td>
				<td width="72" height="48"></td>
				<td width="74" height="48"></td>
				<td height="48" width="72"></td>
				<td height="48" width="73"></td>
				<td height="48" width="79"></td>
			</tr>
			<tr>
				<td width="93" height="49"><b> <span
						style="font-size:14.0pt;font-family:仿宋_GB2312">变更时间</span></b></td>
				<td width="72" height="49"></td>
				<td width="72" height="49"></td>
				<td width="74" height="49"></td>
				<td height="49" width="72"></td>
				<td height="49" width="73"></td>
				<td height="49" width="79"></td>
			</tr>
			<tr>
				<td width="93" rowspan="2">
					<p class="MsoNormal" align="center" style="text-align:center">
						<b> <span style="font-size:14.0pt;font-family:仿宋_GB2312">双方</span></b>
					</p>
					<p class="MsoNormal" align="center" style="text-align:center">
						<b> <span style="font-size:14.0pt;font-family:仿宋_GB2312">确认</span></b>
					</p>
					<p>
				</td>
				<td width="72" height="49"></td>
				<td width="72" height="49"></td>
				<td width="74" height="49"></td>
				<td width="72" height="49"></td>
				<td width="73" height="49"></td>
				<td width="79" height="49"></td>
			</tr>
			<tr>
				<td width="72" height="49"></td>
				<td width="72" height="49"></td>
				<td width="74" height="49"></td>
				<td width="72" height="49"></td>
				<td width="73" height="49"></td>
				<td width="79" height="49"></td>
			</tr>
		</table>
		<table border="0" width="650" id="table3">
			<tr>
				<td style="line-height: 200%" width="654" colspan="4">&nbsp;<span
					style="font-size: 14.0pt; font-family: 仿宋_GB2312">注:1.“立卷人”一栏中“变更时间”填“立卷时间”；
						<br> &nbsp;&nbsp;&nbsp;
						2.“双方确认”由原信贷员和接任信贷员共同签字确认；“立卷人”下则由主管处长和信贷员共同签字确认。接任信贷员在签字确认前应检查所接手项目档案是否和档案目录相符。
						<br> &nbsp;&nbsp;&nbsp; 3.“备考表”一式两份，档案卷中存一份，综合处存一份。
				</span></td>
			</tr>
			<tr>
				<td width="534" colspan="3"><b> <span
						style="font-size: 14.0pt; font-family: 仿宋_GB2312">本卷需要说明的问题</span></b></td>
				<td width="119"></td>
			</tr>
			<tr>
				<td width="444" rowspan="5"></td>
			</tr>
			<tr>
				<td width="18" align="right" height="34"></td>
				<td width="66" align="right" height="34"><font face="仿宋_GB2312">立卷人：</font></td>
				<td width="119" height="34"><%=str[2]%></td>
			</tr>
			<tr>
				<td width="18" align="right" height="33"></td>
				<td width="66" align="right" height="33"><font face="仿宋_GB2312">经办人：</font></td>
				<td width="119" height="33"><%=str[4]%></td>
			</tr>
			<tr>
				<td width="18" align="right" height="30"></td>
				<td width="66" align="right" height="30"><font face="仿宋_GB2312">检查人：</font></td>
				<td width="119" height="30"><%=str[3]%></td>
			</tr>
			<tr>
				<td width="18"></td>
				<td width="189" colspan="2"><font face="仿宋_GB2312"><%=str[5]%>年<%=str[6]%>月<%=str[7]%>日</font></td>
			</tr>
		</table>
	</center>
</body>
</html>

