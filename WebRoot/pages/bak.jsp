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
<title>��ӡ�����ļ�������</title>
</head>
<body style="text-align: center">
	<center>
		<table border="0" width="650" id="table2" height="223">
			<tr>
				<td colspan="4" height="87">
					<p align="center">
						<b> <span
							style="font-size:22.0pt;font-family:����_GB2312;color:black">���ڱ�����</span></b>
				</td>
			</tr>
			<tr>
				<td width="18%" valign=bottom><font face="����_GB2312"
					style="font-size: 14pt">��Ŀ��ţ�</font></td>
				<td width="32%" valign=bottom
					style='border-bottom-style:solid; border-bottom-width:1px'><%=str[0]%>
				</td>
				<td width="18%" valign=bottom><font style="font-size: 14pt"
					face="����_GB2312">��Ŀ���ƣ�</font></td>
				<td width="29%" valign=bottom
					style='border-bottom-style:solid; border-bottom-width:1px'><%=str[1]%>
				</td>
			</tr>
			<tr>
				<td width="18%" valign=bottom></td>
				<td width="32%" valign=bottom></td>
				<td width="18%" valign=bottom><font style="font-size: 14pt"
					face="����_GB2312">����ҳ����</font></td>
				<td width="29%"
					style='border-bottom-style:solid; border-bottom-width:1px'><%=pageNum%></td>
			</tr>
			<tr>
				<td width="97%" colspan="4">
					<p align="center">
						<font face="����_GB2312" size="5"><b>�����Ŵ�Ա��������</b></font>
				</td>
			</tr>
		</table>
		<table border="1" style='border-collapse:collapse' width="581"
			id="table1" height="256">
			<tr>
				<td width="93" height="55"></td>
				<td width="72" height="55">
					<p align="center">
						<span style="font-size: 14.0pt; font-family: ����_GB2312">������<span
							lang="EN-US" style="font-size:14.0pt;font-family:����_GB2312">(</span>�־����Ŵ�Ա<span
							lang="EN-US">)</span></span>
				</td>
				<td width="72" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:����_GB2312"> �Ŵ�Ա���</span>
				</td>
				<td width="74" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:����_GB2312"> �Ŵ�Ա���
						</span>
				</td>
				<td width="72" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:����_GB2312"> �Ŵ�Ա���</span>
				</td>
				<td width="73" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:����_GB2312"> �Ŵ�Ա���</span>
				</td>
				<td width="79" height="55">
					<p align="center">
						<span style="font-size:14.0pt;font-family:����_GB2312"> �Ŵ�Ա���
						</span>
				</td>
			</tr>
			<tr>
				<td width="93" height="48"><b> <span
						style="font-size:14.0pt;font-family:����_GB2312">�Ŵ�Ա</span></b></td>
				<td width="72" height="48"></td>
				<td width="72" height="48"></td>
				<td width="74" height="48"></td>
				<td height="48" width="72"></td>
				<td height="48" width="73"></td>
				<td height="48" width="79"></td>
			</tr>
			<tr>
				<td width="93" height="49"><b> <span
						style="font-size:14.0pt;font-family:����_GB2312">���ʱ��</span></b></td>
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
						<b> <span style="font-size:14.0pt;font-family:����_GB2312">˫��</span></b>
					</p>
					<p class="MsoNormal" align="center" style="text-align:center">
						<b> <span style="font-size:14.0pt;font-family:����_GB2312">ȷ��</span></b>
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
					style="font-size: 14.0pt; font-family: ����_GB2312">ע:1.�������ˡ�һ���С����ʱ�䡱�����ʱ�䡱��
						<br> &nbsp;&nbsp;&nbsp;
						2.��˫��ȷ�ϡ���ԭ�Ŵ�Ա�ͽ����Ŵ�Ա��ͬǩ��ȷ�ϣ��������ˡ����������ܴ������Ŵ�Ա��ͬǩ��ȷ�ϡ������Ŵ�Ա��ǩ��ȷ��ǰӦ�����������Ŀ�����Ƿ�͵���Ŀ¼�����
						<br> &nbsp;&nbsp;&nbsp; 3.��������һʽ���ݣ��������д�һ�ݣ��ۺϴ���һ�ݡ�
				</span></td>
			</tr>
			<tr>
				<td width="534" colspan="3"><b> <span
						style="font-size: 14.0pt; font-family: ����_GB2312">������Ҫ˵��������</span></b></td>
				<td width="119"></td>
			</tr>
			<tr>
				<td width="444" rowspan="5"></td>
			</tr>
			<tr>
				<td width="18" align="right" height="34"></td>
				<td width="66" align="right" height="34"><font face="����_GB2312">�����ˣ�</font></td>
				<td width="119" height="34"><%=str[2]%></td>
			</tr>
			<tr>
				<td width="18" align="right" height="33"></td>
				<td width="66" align="right" height="33"><font face="����_GB2312">�����ˣ�</font></td>
				<td width="119" height="33"><%=str[4]%></td>
			</tr>
			<tr>
				<td width="18" align="right" height="30"></td>
				<td width="66" align="right" height="30"><font face="����_GB2312">����ˣ�</font></td>
				<td width="119" height="30"><%=str[3]%></td>
			</tr>
			<tr>
				<td width="18"></td>
				<td width="189" colspan="2"><font face="����_GB2312"><%=str[5]%>��<%=str[6]%>��<%=str[7]%>��</font></td>
			</tr>
		</table>
	</center>
</body>
</html>

