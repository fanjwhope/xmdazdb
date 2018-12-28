<%@page import="com.hr.dao.BaseDao"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@ page import="com.hr.global.util.ArchiveUtil"%>
<%@ include file="printset.jsp"%>
<%
	String zdbdbName = request.getParameter("zdbdbName");
	BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
	String deptNum = IncString.formatNull(ArchiveUtil
			.getDepartmentCode(session));
	String dbName = IncString.formatNull(ArchiveUtil
			.getDataBaseName(session));
	String xmbh = request.getParameter("xmbh");
	String sign = request.getParameter("sign");
	String prefix = request.getParameter("prefix");
	String endfix = request.getParameter("endfix");
	String bjType = request.getParameter("bjType");
	String width = "";
	if ("thick".equals(bjType))
		width = "160px";
	else if ("middle".equals(bjType))
		width = "130px";
	else
		width = "100px";

	String sql = "";
	String[][] str = null;
	if ("".equals(xmbh)) {
		sql = "select qymc, xmmc, xmbh from " + "zdb_xmwj_" + deptNum
				+ " where (yjflag != '2' or yjflag is  null)";
		str = op.queryRowAndCol(sql);
	} else {
		sql = "select qymc, xmmc, xmbh from "
				+ "zdb_xmwj_"
				+ deptNum
				+ " where (yjflag != '2' or yjflag is  null) and xmbh = '"
				+ xmbh + "'";
		str = op.queryRowAndCol(sql);
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>打印案卷背脊</title>
<style>
/**/ /**
*    打印相关
*/
@media print {
	.notprint {
		display: none;
	}
	.PageNext {
		page-break-after: always;
	}
}

@media screen {
	.notprint {
		display: inline;
		cursor: hand;
	}
}

.text1 {
	width: 120px;
	overflow: hidden;
	text-overflow: ellipsis;
}

.text2 {
	width: 80px;
	overflow: hidden;
	text-overflow: ellipsis;
}
</style>
<script src="../js/jquery-1.8.0.min.js"></script>
<script src="../js/jquery.PrintArea.js"></script>
<script>
	$(function() {
		$("#btnPrint").click(function() {
			$("#printContent").printArea();
		});
	});
</script>
</head>
<body style="margin:0px">
	<!--input type="button" id="btnPrint" style="margin-left:270px;margin-bottom:20px" value="打印"/-->
	<div id="printContent">
		<table border="0" width="570" cellspacing="0">
			<%
				for (int i = 0; i < str.length; i++) {
					String sm = "";
					if (str != null) {
						String mc = str[i][1].replaceAll("\\(", "\\︵")
								.replaceAll("\\（", "\\︵").replaceAll("\\)", "\\︶")
								.replaceAll("\\）", "\\︶");	
						for (int m = 0; m < mc.length(); m++) {
							if (m % 18 == 0 &&m!=0) {
								sm += "</td><td><br><br><br><br><br>";
							}
							sm += (mc.charAt(m) + "<br>");
						}
					}
					sm+="</td>";
					String sm2 = "";
					if (str != null) {
						String mc2 = str[i][0].replaceAll("\\(", "\\︵")
								.replaceAll("\\（", "\\︵").replaceAll("\\)", "\\︶")
								.replaceAll("\\）", "\\︶");	
						for (int m = 0; m < mc2.length(); m++) {
							if (m % 18 == 0 && m!=0) {
								sm += "</td><td><br><br><br><br><br>";
							}
							sm2 += (mc2.charAt(m) + "<br>");
						}
					}
					sm2+="</td>";
			%>
			<tr>
				<td width="25%" align='middle'>
					<table border="0" width="<%=width%>"
						style="border: 1 solid #000000">
						<tr>
							<td width="100%" height="98" align='middle'><b><font
									style='font-size:12pt'><%=prefix%><%=str[i][2]%><%=endfix%></font></b>
							</td>
						</tr>
						<tr>
							<td width="100%" height="604"
								style="border-top: 1 solid #000000; border-bottom: 1 solid #000000;font-family:宋体;font-size:18pt;"
								align='middle'>
								<!--中间的最小表格-->
								<table border='0' dir=rtl style='font-family:宋体;font-size:18pt;'>
									<tr valign='top'>
										<td style="WORD-WRAP: break-word" WIDTH="20px">借<br>款<br>人<br>
											<br>:<br><%=sm2%>
										</td>
										<td><font color='#FFFFFF'> <br> <br> <br>
												<br> <br></font><br> <br> <br> <br> <br>
											<br> <br> <br> <br> <br> <br> <br>
											<br> <br> <br> <br> <br> <br> <br>
										<td><font color='#FFFFFF'>&nbsp</font>
										<td style="WORD-WRAP: break-word" WIDTH="20px">项<br>目<br>名<br>称<br>：<br><%=sm%><br>
											<br></tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%" height="80" align='middle'><input type=text
								size=10 title='此处内容可以编辑！ '
								style='font-size:12pt;text-align:center;border:0px'
								value='贷款审批卷'><br> <input type=text size=10
								title='此处内容可以编辑！ '
								style='font-size:12pt;text-align:center;border:0px'
								value='（<%=sign%>）'></td>
						</tr>
					</table>
				</td>
				<td width="25%" align='middle'>
					<table border="0" width="<%=width%>"
						style="border: 1 solid #000000">
						<tr>
							<td width="100%" align='middle' height="98"><b><font
									style='font-size:12pt'><%=prefix%><%=str[i][2]%><%=endfix%></font></b>
							</td>
						</tr>
						<tr>
							<td width="100%" height="604"
								style="border-top: 1 solid #000000; border-bottom: 1 solid #000000;font-family:宋体;font-size:18pt;"
								align='middle'>
								<!--中间的最小表格-->
								<table border='0' dir=rtl style='font-family:宋体;font-size:18pt;'>
									<tr valign='top'>
										<td style="WORD-WRAP: break-word" WIDTH="20px">借<br>款<br>人<br>
											<br>:<br><%=sm2%>
										</td>
										<td><font color='#FFFFFF'> <br> <br> <br>
											<br> <br></font><br> <br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<br>
										<td><font color='#FFFFFF'>&nbsp</font></td>
										<td style="WORD-WRAP: break-word" WIDTH="20px">项<br>目<br>名<br>称<br>：<br><%=sm%><br> <br>
										</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%" height="80" style="border-top: 3" align='middle'><input
								type=text size=10 title='此处内容可以编辑！ '
								style='font-size:12pt;text-align:center;border:0px'
								value='贷款贷后卷'><br> <input type=text size=10
								title='此处内容可以编辑！ '
								style='font-size:12pt;text-align:center;border:0px'
								value='（<%=sign%>）'></td>
						</tr>
					</table>
				</td>

			</tr>
			<%
				if (i != str.length - 1) {
			%>
			<tr>
				<td>
					<DIV STYLE="page-break-before:always"></DIV>
				</td>
			</tr>
			<%
				}
				}
			%>
		</table>
	</div>
</body>
</html>
