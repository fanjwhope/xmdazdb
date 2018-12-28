package com.hr.action;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.hr.bean.BankNode;
import com.hr.bean.User;
import com.hr.dao.ArchiveDao;
import com.hr.dao.BankNodeDao;
import com.hr.dao.BaseDao;
import com.hr.dao.PrivilegeDataInfo;
import com.hr.dao.PrivilegeDate;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.global.util.Validation;
import com.hr.table.Constants;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.IncString;
import com.hr.util.Log;
import com.hr.util.Md5;

public class PrivilegeInfo extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrivilegeInfo() {

	}

	private int rowNum;

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		((ServletRequest) response).setCharacterEncoding("GBK");
		String method = request.getParameter("method");
		if (("add").equals(method)) {
			try {
				add(request, response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (("getSearchInfo").equals(method)) {
			try {
				getSearchInfo(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (("del").equals(method)) {
			try {
				del(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (("getDeptInfo").equals(method)) {
			try {
				getDeptInfo(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (("getVisiterInfo").equals(method)) {
			try {
				getVisiterInfo(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (("addByProject").equals(method)) {
			try {
				addByProject(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (("getListByPro").equals(method)) {
			try {
				getListByPro(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (("delPriForPro").equals(method)) {
			try {
				delPriForPro(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (("getWebSearch").equals(method)) {
			try {
				getWebSearch(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("getAllBankNode".equals(method)) {
			try {
				getAllBankNode(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/******
	 * 按访问者增加权限
	 * 
	 * @throws Exception
	 *******/
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrivilegeDate pd = new PrivilegeDate();
		String privilegeDate = IncString.formatNull(request
				.getParameter("privilegeDate"));
		String visiterid = request.getParameter("visiterids");
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		String pigeonholessid = request.getParameter("pigeonholessids");	
		String[] visiterids = visiterid.split(",");
		String[] pigeonholessids = pigeonholessid.split(",");
		String dbName = IncString.formatNull(ArchiveUtil
				.getDataBaseName(request.getSession()));
		String sql = "";
		String countSql = "";
		int countNum = 0;
		String nowDate = Md5.date("yyyy-MM-dd");
		long nowDays = pd.getDays(nowDate);
		int nowYear = IncString.formatInt(Md5.date("yyyy"));
		int nowMonth = IncString.formatInt(Md5.date("MM"));
		int nowDay = IncString.formatInt(Md5.date("dd"));
		long privilegeDays = 0;
		if (privilegeDate.equals("一天")) {
			privilegeDays = 1;
		} else if (privilegeDate.equals("三天")) {
			privilegeDays = 3;
		} else if (privilegeDate.equals("一周")) {
			privilegeDays = 7;
		} else if (privilegeDate.equals("两周")) {
			privilegeDays = 14;
		} else if (privilegeDate.equals("一个月")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 0));
		} else if (privilegeDate.equals("三个月")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 2));
		} else if (privilegeDate.equals("半年")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 5));
		} else if (privilegeDate.equals("一年")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 11));
		} else if (privilegeDate.equals("三年")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 35));
		} else if (privilegeDate.equals("永久")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 119));
		}
		String qxsj = String.valueOf(nowDays + privilegeDays);
		if (visiterids != null && visiterids.length > 0) {
			for (int i = 0; i < visiterids.length; i++) {
				if (pigeonholessids != null && pigeonholessids.length > 0) {
					for (int j = 0; j < pigeonholessids.length; j++) {
						countSql = "select count(*) from " + dbName
								+ "_wsda_qx2" + " where dwh1='" + visiterids[i]
								+ "' and dwh2='" + pigeonholessids[j] + "'";
						countNum = IncString.formatInt(op
								.querySingleData(countSql));
						if (countNum > 0) {
							sql = "delete from " + dbName + "_wsda_qx2"
									+ " where dwh1='" + visiterids[i]
									+ "' and dwh2='" + pigeonholessids[j] + "'";
							op.Exec(sql);
						}
						sql = "insert into "
								+ dbName
								+ "_wsda_qx2"
								+ " (dwh1,dwh2,startime,qxsj,mj,zwbz,cjwbbz,fjbz,ldpsbz,cbjgbz) values ('"
								+ visiterids[i] + "','" + pigeonholessids[j]
								+ "','" + nowDate + "','" + qxsj
								+ "','','','','','','')";
						op.Exec(sql);
					}
				}
			}
		}
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write("OK");
	}

	/***********
	 * 删除权限
	 * 
	 * @throws Exception
	 *************/
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String delId = IncString.formatNull(request.getParameter("delId"));
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		String[] idInfo = null;
		String dbName = IncString.formatNull(ArchiveUtil
				.getDataBaseName(request.getSession()));
		String sql = "";
		String dwh1 = "";
		String dwh2 = "";
		if (delId.equals("all")) {
			sql = "delete from " + dbName + "_wsda_qx2";
			op.Exec(sql);
		} else {
			idInfo = delId.split(":");
			if (idInfo != null && idInfo.length == 2) {
				dwh1 = IncString.formatNull(idInfo[0]);
				dwh2 = IncString.formatNull(idInfo[1]);
			}
			sql = "delete from " + dbName + "_wsda_qx2" + " where dwh1='"
					+ dwh1 + "' and dwh2='" + dwh2 + "'";
			op.Exec(sql);
		}
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write("OK");
	}

	/****** 按项目删除权限 ********/
	public void delPriForPro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String delId = IncString.formatNull(request.getParameter("id"));
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		String[] idInfo = null;
		String dbName = IncString.formatNull(ArchiveUtil
				.getDataBaseName(request.getSession()));
		String sql = "";
		idInfo = delId.split(",");
		if (idInfo != null && idInfo.length > 0) {
			for (int i = 0; i < idInfo.length; i++) {
				sql = "delete from " + dbName + "_wsda_qx_pro" + " where id='"
						+ idInfo[i] + "'";
				op.Exec(sql);
			}
		}
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write("OK");
	}

	/******** 按项目增加权限 ************/
	public void addByProject(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dbName = IncString.formatNull(ArchiveUtil
				.getDataBaseName(request.getSession()));
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		//获取当前登录用户的userId
		String userId = ArchiveUtil.getCurrentUser(request.getSession())
				.getUserid();
		String[][] tabField = { { "id", "varchar", "100", "1", "0" },
				{ "dwh1", "varchar", "10", "0", "1" },
				{ "dwmc", "varchar", "100", "1", "0" },
				{ "Lsh", "numeric", "10", "0", "1" },
				{ "xmmc", "varchar", "100", "1", "0" },
				{ "hth", "varchar", "100", "1", "0" },
				{ "startime", "varchar", "10", "1", "0" },
				{ "qxsj", "varchar", "10", "1", "0" },
				{ "by1", "varchar", "100", "1", "0" },
				{ "by2", "varchar", "100", "1", "0" },
				{ "authorizer", "varchar", "10", "1", "0" } };
		String tableName = dbName + "_wsda_qx_pro";
		
		if (op.ifExistsTab(tableName) == false) {
			String createSql = Constants.BuildCreateTabSql(tableName, tabField);
			op.ExecSql(createSql);
		}
		PrivilegeDate pd = new PrivilegeDate();
		String privilegeDate = IncString.formatNull(request
				.getParameter("privilegeDate"));
		String visiterid = request.getParameter("visiterIds");
		String projectIds = request.getParameter("projectIds");
		String projectNames = request.getParameter("projectNames");
		String projectBhs = request.getParameter("projectBhs");
		String visiterIdsNames = request.getParameter("visiterIdsNames");
		String dwhs=request.getParameter("dwhs"); // update 2018-03-28
		String author=userId;// update 2018-03-28
		String nId = ""; 
		String[] visiterIdsName = visiterIdsNames.split(",");
		String[] visiterids = visiterid.split(",");
		String[] pigeonholessids = projectIds.split(",");
		String[] projectName = projectNames.split(",");
		String[] projectBh = projectBhs.split(",");
		String[] authors=dwhs.split(","); // update 2018-03-28
		
		String sql = "";
		String countSql = "";
		int countNum = 0;
		String nowDate = Md5.date("yyyy-MM-dd");
		long nowDays = pd.getDays(nowDate);
		int nowYear = IncString.formatInt(Md5.date("yyyy"));
		int nowMonth = IncString.formatInt(Md5.date("MM"));
		int nowDay = IncString.formatInt(Md5.date("dd"));
		long privilegeDays = 0;
		if (privilegeDate.equals("一天")) {
			privilegeDays = 1;
		} else if (privilegeDate.equals("三天")) {
			privilegeDays = 3;
		} else if (privilegeDate.equals("一周")) {
			privilegeDays = 7;
		} else if (privilegeDate.equals("两周")) {
			privilegeDays = 14;
		} else if (privilegeDate.equals("一个月")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 0));
		} else if (privilegeDate.equals("三个月")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 2));
		} else if (privilegeDate.equals("半年")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 5));
		} else if (privilegeDate.equals("一年")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 11));
		} else if (privilegeDate.equals("三年")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 35));
		} else if (privilegeDate.equals("永久")) {
			privilegeDays = pd.getDays(nowDate,
					pd.getFeaDate(nowYear, nowMonth, nowDay, 119));
		}
		String qxsj = String.valueOf(nowDays + privilegeDays);
		if (visiterids != null && visiterids.length > 0) {
			for (int i = 0; i < visiterids.length; i++) {
				if (pigeonholessids != null && pigeonholessids.length > 0) {
					for (int j = 0; j < pigeonholessids.length; j++) {
						nId = Md5.date("yyyyMMddhhmmss") + Md5.md5(10);
						countSql = "select count(*) from " + dbName
								+ "_wsda_qx_pro" + " where dwh1='"
								+ visiterids[i] + "' and Lsh='"
								+ pigeonholessids[j] + "'";
						countNum = IncString.formatInt(op
								.querySingleData(countSql));
						if (countNum > 0) {
							sql = "delete from " + dbName + "_wsda_qx_pro"
									+ " where dwh1='" + visiterids[i]
									+ "' and Lsh='" + pigeonholessids[j] + "'";
							op.Exec(sql);
						}
						//获取dwh 对应的用户名称  
						if(userId.equalsIgnoreCase("SYSTEM") && authors!=null && authors.length>j && authors[j]!=""){
							String dwh=authors[j].split("_")[2];
							String[] temp =op.queryLine("select userId  from "+dbName+"_YHXX where dwh='"+dwh+"'");
							Log.debug("你有没有查询userID： "+"select userId  from "+dbName+"_YHXX where dwh='"+dwh+"'");
							author=(temp!=null&& temp[0]!=null && temp[0]!="")?temp[0]:author;
						}
						sql = "insert into "
								+ dbName
								+ "_wsda_qx_pro"
								+ " (dwh1,Lsh,startime,qxsj,by1,by2,dwmc,xmmc,hth,id,authorizer) values ('"
								+ visiterids[i] + "','" + pigeonholessids[j]
								+ "','" + nowDate + "','" + qxsj + "','','','"
								+ visiterIdsName[i] + "','" + projectName[j]
								+ "','" + projectBh[j] + "','" + nId + "','"
								+ author + "')"; //user update  2018-03-08 
						Log.debug("执行插入语句:"+sql);
						op.Exec(sql);
					}
				}
			}
		}
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write("OK");
	}

	/****** 获取当前分行名称 *******/
	public void getDeptInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dbName = IncString.formatNull(request.getParameter("dbName"));
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		String returnVal = "";
		String sql = "select dwh,dwmc from " + dbName + "_YHXX"
				+ " where dwh in (select distinct(dwh1) from " + dbName
				+ "_wsda_qx2" + ")";
		String[][] val = op.queryRowAndCol(sql);
		returnVal = "[";
		if (val != null && val.length > 0) {
			for (int i = 0; i < val.length; i++) {
				if (i == val.length - 1) {
					returnVal += "{" + "\"" + "id" + "\"" + ":" + "\""
							+ val[i][0] + "\"" + "," + "\"" + "text" + "\""
							+ ":" + "\"" + val[i][1] + "\"" + "}";
				} else {
					returnVal += "{" + "\"" + "id" + "\"" + ":" + "\""
							+ val[i][0] + "\"" + "," + "\"" + "text" + "\""
							+ ":" + "\"" + val[i][1] + "\"" + "},";
				}
			}
		}
		returnVal += "]";
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write(returnVal);
	}

	/****** 获取案卷信息 *******/
	public void getSearchInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = ArchiveUtil.getCurrentUser(request.getSession());
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		String dwh = user.getDwh();
		String roles = user.getRoles();
		String page = request.getParameter("page");
		String row = request.getParameter("rows");
		String dbName = IncString.formatNull(request.getParameter("dbName"));
		String projectId = IncString.formatNull(request
				.getParameter("projectId"));
		String projectUser = IncString.formatNull(request
				.getParameter("projectUser"));
		
		String xmfzr= IncString.formatNull(request
				.getParameter("xmfzr"));
		
		String whereSql = " where 1=1 ";
		String json = "";
		int total = 0;
		if (!projectId.equals("")) {
			whereSql += " and xmbh like '%" + projectId + "%' ";
		}
		if (!projectUser.equals("")) {
			whereSql += " and qymc like '%" + projectUser + "%'";
		}
		if(!xmfzr.equals("")){
			whereSql += " and xmfzr like '%" + xmfzr + "%'";
		}
		
		String fieldNames = "qymc,xmmc,by2,xmbh,xmfzr,lsh,dwh"; //2018-03-29  查询显示时多查询dwh
		String countName = "lsh";
		String orderby = " order by lsh"; 
		String columns = "qymc,xmmc,by2,xmbh,xmfzr,lsh,dwh,tabname"; ////2018-03-29 (dwh)
		whereSql += " and (yjflag<>'2' or yjflag is  null) ";
		if ("0".equals(roles)) {// 节点系统管理员
			String getTabSql = "  select name from " + "sysobjects"
					+ " where xtype='u' and name like '" + dbName
					+ "_xmwj_%' and name not like '%_bak'";
			String[] tab = op.queryColumn(getTabSql);
			if (tab != null && tab.length > 0) {
				for (int i = 0; i < tab.length; i++) {
					tab[i] = tab[i];
					// tab[i]=dbName+"_"+tab[i];
				}
			}
			List listpr = getList(op, page, row, fieldNames, countName, tab,
					whereSql, orderby, columns, tab);
			total = getRowNum();
			String[] colNameArr = { "qymc", "xmmc", "by2", "xmbh", "xmfzr","lsh","dwh", "tabname" };  //2018-03-29  查询显示时多查询dwh
			json = getListToJosn(listpr, colNameArr, 1);
			json = "{\"total\":" + total + ",\"rows\":[" + json + "]}";
		} else {
			String tab = dbName + "_xmwj_" + dwh;
			if (!op.ifExistsTab(tab)) {
				ArchiveDao archiveDao = new ArchiveDao(tab,zdbdbName);
			}
			String[][] obj = op.queryRowAndCol(Integer.parseInt(row),
					Integer.parseInt(page), fieldNames, "lsh", tab, whereSql,
					orderby);
			total = getRowNum();
			json = ArchiveUtil.arrToJson(obj, fieldNames);
			json = "{\"total\":" + total + ",\"rows\":[" + json + "]}";
		}
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write(json);
	}

	/*********** 获取访问者树状json **************/
	public void getVisiterInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dbName = IncString.formatNull(request.getParameter("dbName"));
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		String sql = "select dwmc,dwh from " + dbName + "_yhxx"
				+ " where dwh like '%0000%' and dwh<>'Y100000000' order by dwh";
		String subVal = "";
		String json = "[";
		String[][] val = op.queryRowAndCol(sql);
		String[][] valInfo = null;
		if (val != null && val.length > 0) {
			for (int i = 0; i < val.length; i++) {
				json += "{" + "\"" + "id" + "\"" + ":" + "\"" + val[i][1]
						+ "\"" + "," + "\"" + "text" + "\"" + ":" + "\""
						+ val[i][0] + "\"";
				subVal = val[i][1].substring(1, val[i][1].length() - 4);
				sql = "select dwmc,dwh from " + dbName + "_yhxx"
						+ " where dwh like '%" + subVal + "%' and dwh<>'"
						+ val[i][1] + "'";
				valInfo = op.queryRowAndCol(sql);
				if (valInfo != null && valInfo.length > 0) {
					json += "," + "\"" + "children" + "\"" + ":[";
					for (int j = 0; j < valInfo.length; j++) {
						json += "{" + "\"" + "id" + "\"" + ":" + "\""
								+ valInfo[j][1] + "\"" + "," + "\"" + "text"
								+ "\"" + ":" + "\"" + valInfo[j][0] + "\""
								+ "}";
						if (j != valInfo.length - 1) {
							json += ",";
						}
					}
					json += "]";
				}
				json += "}";
				if (i != val.length - 1) {
					json += ",";
				}
			}
		}
		json += "]";
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write(json);
	}

	/******** 获取已按项目号分配的权限列表 ************/
	public void getListByPro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dbName = IncString.formatNull(ArchiveUtil
				.getDataBaseName(request.getSession()));
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		String page = request.getParameter("page");
		String row = request.getParameter("rows");
		User user = ArchiveUtil.getCurrentUser(request.getSession());
		String userId = user.getUserid();
		String roles = user.getRoles();
		String fieldNames = "id,dwh1,dwmc,lsh,xmmc,hth,startime,qxsj,by1,by2,authorizer";
		String countName = "id";
		String orderby = " order by dwh1";
		String tabName = dbName + "_wsda_qx_pro";
		String columns = "select id,dwh1,dwmc,lsh,xmmc,hth,startime,qxsj,by1,by2,authorizer from "
				+ dbName + "_wsda_qx_pro";
		String whereSql = " where 1=1";
		if (!"0".equals(roles)) {
			whereSql += " and authorizer='" + userId + "'";
		}
		String sqlnum = "select count(id) from " + dbName + "_wsda_qx_pro"
				+ whereSql;
		int num = IncString.formatInt(op.querySingleData(sqlnum));
		List listpr = getList(op, page, row, fieldNames, countName, tabName,
				whereSql, orderby, columns);
		int total = num;
		String[] colNameArr = { "id", "dwh1", "dwmc", "lsh", "xmmc", "hth",
				"startime", "qxsj", "by1", "by2", "authorizer" };
		String json = getListToJosn(listpr, colNameArr, 1);
		json = "{\"total\":" + total + ",\"rows\":[" + json + "]}";
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write(json);

	}

	/************* 网上查询 **************/
	public void getWebSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrivilegeDataInfo pdi = new PrivilegeDataInfo();
		String zdbdbName= request.getParameter("zdbdbName");
		BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
		String dbName = IncString.formatNull(ArchiveUtil
				.getDataBaseName(request.getSession()));
		String userId = IncString.formatNull(ArchiveUtil
				.getDepartmentCode(request.getSession()));
		String dbNameSearch = IncString.formatNull(request
				.getParameter("dbNameSearch"));
		if (dbNameSearch != null && dbNameSearch != "") {
			dbName = dbNameSearch;
		}
		String page = request.getParameter("page");
		String row = request.getParameter("rows");
		String roles = request.getParameter("roles");

		String qymc = IncString.formatNull(request.getParameter("qymc"));
		String xmfzr = IncString.formatNull(request.getParameter("xmfzr"));
		String xmmc = IncString.formatNull(request.getParameter("xmmc"));
		String dkqx = IncString.formatNull(request.getParameter("dkqx"));
		String ywhc = IncString.formatNull(request.getParameter("ywhc"));
		String by1 = IncString.formatNull(request.getParameter("by1"));
		String xmbh = IncString.formatNull(request.getParameter("xmbh"));
		String by2 = IncString.formatNull(request.getParameter("by2"));
		String tbDate = IncString.formatNull(request.getParameter("tbDate"));
		String tbDateE = IncString.formatNull(request.getParameter("tbDateE"));
		String dkje = IncString.formatNull(request.getParameter("dkje"));
		String projectType = IncString.formatNull(request
				.getParameter("projectType"));
		String square = IncString.formatNull(request.getParameter("square"));

		String whereSql = " where 1=1 ";
		if (!qymc.equals("")) {
			whereSql += " and qymc like '%" + qymc + "%'";
		}
		if (!xmfzr.equals("")) {
			whereSql += " and xmfzr like '%" + xmfzr + "%'";
		}
		if (!xmmc.equals("")) {
			whereSql += " and xmmc like '%" + xmmc + "%'";
		}
		if (!dkqx.equals("")) {
			whereSql += " and dkqx like '%" + dkqx + "%'";
		}
		if (!ywhc.equals("")) {
			whereSql += " and ywhc like '%" + ywhc + "%'";
		}
		if (!by1.equals("")) {
			whereSql += " and by1 like '%" + by1 + "%'";
		}
		if (!xmbh.equals("")) {
			whereSql += " and xmbh like '%" + xmbh + "%'";
		}
		if (!by2.equals("")) {
			whereSql += " and by2 like '%" + by2 + "%'";
		}
		if (!tbDate.equals("")) {
			String[] tbDates = tbDate.split("-");
			whereSql += " and (tbyy>'" + tbDates[0] + "' or (tbyy='"
					+ tbDates[0] + "' and tbmm>'" + tbDates[1]
					+ "') or (tbyy='" + tbDates[0] + "' and tbmm='"
					+ tbDates[1] + "' and tbdd>='" + tbDates[2] + "'))";
		}
		if (!tbDateE.equals("")) {
			String[] tbDateEs = tbDateE.split("-");
			whereSql += " and (tbyy<'" + tbDateEs[0] + "' or (tbyy='"
					+ tbDateEs[0] + "' and tbmm<'" + tbDateEs[1]
					+ "') or (tbyy='" + tbDateEs[0] + "' and tbmm='"
					+ tbDateEs[1] + "' and tbdd<='" + tbDateEs[2] + "'))";

		}
		if (!dkje.equals("")) {
			whereSql += " and dkje='%" + dkje + "%'";
		}
		if (!projectType.equals("")) {
			whereSql += " and projectType='" + projectType + "'";
		}
		if (!square.equals("")) {
			if (!square.equals("2")) {
				whereSql += " and square='" + square + "'";
			}
		}
		whereSql += " and (yjflag<>'2' or yjflag is null) ";

		String fieldNames = "lsh,xmbh,qymc,xmmc,dkje,dasryy,dasrmm,dasrdd,tbyy,tbmm,tbdd,ywhc,xmfzr,dw,yjflag";
		String columns = "lsh,xmbh,qymc,xmmc,dkje,dasryy,dasrmm,dasrdd,tbyy,tbmm,tbdd,ywhc,xmfzr,dw,yjflag,tbname,dwmc";
		String endSql;
		String pageSql;

		String sql;
		StringBuffer sqls = new StringBuffer();
		List<String> list;
		if ("0".equals(roles)||"-1".equals(roles)) {
			list = getWebSearchAllTableForSystemUser(op, dbName);
		} else {
			list = getWebSearchAllTable(op, userId, dbName);
		}
		for (int i = 0; i < list.size(); i++) {
			String[] tempArray = list.get(i).split("_");//distinct
			String getUserNameSql = "SELECT  dwmc=STUFF(( SELECT ','+dwmc FROM "
					+ dbName
					+ "_yhxx WHERE dwh = A.dwh  FOR XML PATH('')),1,1,'')  FROM "
					+ dbName
					+ "_yhxx as A where dwh='"
					+ tempArray[tempArray.length - 1] + "'";
			sql = " union  select " + fieldNames + ",'" + list.get(i)
					+ "' as tbname,(" + getUserNameSql + ") as dwmc  from ";
			sqls.append(sql).append(list.get(i) + " ").append(whereSql);
		}
		Map<String, String> projects = getWebSearchProject(op, userId, dbName);
		for (String key : projects.keySet()) {//distinct
			String[] tempArray = projects.get(key).split("_");
			String getUserNameSql = "SELECT  dwmc=STUFF(( SELECT ','+dwmc FROM "
					+ dbName
					+ "_yhxx WHERE dwh = A.dwh  FOR XML PATH('')),1,1,'')  FROM "
					+ dbName
					+ "_yhxx as A where dwh='"
					+ tempArray[tempArray.length - 1] + "'";
			sql = " union select " + fieldNames + ",'" + projects.get(key)
					+ "' as tbname,(" + getUserNameSql + ") as dwmc  from ";
			sqls.append(sql).append(projects.get(key)).append(whereSql)
					.append(" and lsh='" + key + "'");
		}
		String resultSql = sqls.toString().replaceFirst("union", " ");
		pageSql = "select count(*) from(" + resultSql + ") t";
		endSql = "SELECT TOP " + row + " t1.* FROM (" + resultSql
				+ ") t1 WHERE lsh NOT IN ( SELECT TOP " + Integer.valueOf(row)
				* (Integer.valueOf(page) - 1) + " t.lsh FROM (" + resultSql
				+ ") t ORDER BY lsh ) ORDER BY lsh";

		String[][] totalPage = op.queryRowAndCol(pageSql);
		Log.debug("pageSql "+ pageSql);
		String[][] result = op.queryRowAndCol(endSql);
		Log.debug("endSql "+ endSql);
		
		List<Map<String,String>> data=new ArrayList<Map<String,String>>();
		String resultColum="lsh,xmbh,qymc,xmmc,dkje,dasryy,dasrmm,dasrdd,tbyy,tbmm,tbdd,ywhc,xmfzr,dw,yjflag,tbname,dwmc";
		for(String[] str:result){
			HashMap<String, String> m=new HashMap<String, String>();
			int i=0;
			for(String col:resultColum.split(",")){
				m.put(col, str[i]);
				i++;
			}
			data.add(m);
		}
		PagingBean pagingBean=new PagingBean();
		pagingBean.setTotalRecords(Integer.valueOf(totalPage[0][0]));
		pagingBean.setCurrentPage(Integer.valueOf(page));
		pagingBean.setPageSize(Integer.valueOf(row));
		JsonUtil.writeJsonGrid(response, data, pagingBean);
		
		/*Map<Integer, String[]> newResult = new HashMap<Integer, String[]>();
		if (str != null) {
			for (int k = 0; k < str.length; k++) {
				//if (!newResult.containsKey(IncString.formatInt(str[k][0]))) {
					newResult.put(IncString.formatInt(str[k][0]), str[k]);
				//}
			}
			TreeMap<Integer, String[]> re = new TreeMap(newResult);
			// 修改分页的总记录数
			String[] queryColumns = columns.split(",");
			Map<String, String> map = null;
			List ListData = new ArrayList();
			String[] val = null;
			Iterator<Map.Entry<Integer, String[]>> it = re.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, String[]> entry = it.next();
				map = new HashMap<String, String>();
				val = entry.getValue();
				if (val != null && val.length > 0) {
					for (int m = 0; m < val.length; m++) {
						map.put(queryColumns[m], val[m]);
					}
					ListData.add(map);
				}
			}
			String[] colNameArr = { "lsh", "xmbh", "qymc", "xmmc", "dkje",
					"dasryy", "dasrmm", "dasrdd", "tbyy", "tbmm", "tbdd",
					"ywhc", "xmfzr", "dw", "yjflag", "tbname", "dwmc" };
			String json = getListToJosn(ListData, colNameArr, 1);
			json = "{\"total\":" + totalPage[0][0] + ",\"rows\":[" + json
					+ "]}";
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(json);

		}
*/
	}

	/**
	 * 获取所有有权限查询的单个项目
	 * 
	 * @param op
	 *            数据库连接
	 * @param userId
	 *            用户单位号
	 * @param dbName
	 *            数据库名
	 * @return 返回一个MAP，map中k，v分别表示项目流水号和项目所在表名
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public Map<String, String> getWebSearchProject(BaseDataOP op,
			String userId, String dbName) throws NumberFormatException,
			ParseException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String sql = "SELECT Lsh,startime,qxsj,t2.dwh FROM " + dbName
					+ "_wsda_qx_pro t1 left join " + dbName + "_yhxx t2 on "
					+ "t1.authorizer=t2.userid where t1.dwh1='" + userId + "'";
			String[][] result = op.queryRowAndCol(sql);
			PrivilegeDate pd = new PrivilegeDate();
			if (result != null) {
				for (int i = 0; i < result.length; i++) {
					if (pd.getDays(result[i][1]) - Long.valueOf(result[i][2]) <= 0) {
						map.put(result[i][0], dbName + "_xmwj_" + result[i][3]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取所有有权限查询的表
	 * 
	 * @param op
	 * @param userId
	 * @param dbName
	 * @return 返回所有表名
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public List<String> getWebSearchAllTable(BaseDataOP op, String userId,
			String dbName) throws NumberFormatException, ParseException {
		String sql2 = "select name from sysobjects where xtype='u' and name like '"
				+ dbName + "_xmwj_%' and name not like '%_bak'";
		String[][] result2 = op.queryRowAndCol(sql2);
		List<String> list2 = new ArrayList<String>();
		PrivilegeDate pd = new PrivilegeDate();
		for (int i = 0; i < result2.length; i++) {
			for (int m = 0; m < result2[i].length; m++) {
				list2.add(result2[i][m].toLowerCase());
			}
		}
		List<String> list = new ArrayList<String>();
		String[][] result = new String[][] {};
		String sql = "select dwh2,startime,qxsj from " + dbName
				+ "_wsda_qx2 where dwh1='" + userId + "'";
		result = op.queryRowAndCol(sql);
		if (list2.contains((dbName + "_xmwj_" + userId).toLowerCase())) {
			list.add((dbName + "_xmwj_" + userId).toLowerCase());
		}
		if (result != null&&result.length>0) {
			for (int i = 0; i < result.length; i++) {
				if (list2.contains((dbName + "_xmwj_" + result[i][0])
						.toLowerCase())
						&& pd.getDays(result[i][1])
								- Long.valueOf(result[i][2]) <= 0) {
					list.add((dbName + "_xmwj_" + result[i][0]).toLowerCase());
				}
			}
		}
		return list;
	}

	public List<String> getWebSearchAllTableForSystemUser(BaseDataOP op,
			String dbName) throws NumberFormatException, ParseException {
		String sql = "select name from sysobjects where xtype='u' and name like '"
				+ dbName + "_xmwj_%' and name not like '%_bak'";
		String[][] result = op.queryRowAndCol(sql);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < result.length; i++) {
			for (int m = 0; m < result[i].length; m++) {
				list.add(result[i][m].toLowerCase());
			}
		}
		return list;
	}

	@Test
	public void test() throws NumberFormatException, ParseException {
		
		/*
		 * StringBuffer sqls = new StringBuffer(); String sql = " select " + "*"
		 * + " from "; String whereSql = "where 1=1"; List<String> list = new
		 * PrivilegeInfo().getWebSearchAllTable(op, "Y200020038",
		 * "shandongBank");
		 * 
		 * for (int i = 0; i < list.size(); i++) { if (i != list.size() - 1) {
		 * sqls.append(sql).append(list.get(i) + " ").append(whereSql)
		 * .append(" union "); } else { sqls.append(sql).append(list.get(i) +
		 * " ").append(whereSql); } } System.out.println(sqls);
		 */
		/*Map<String, String> map = getWebSearchProject(op, "Y200020007",
				"shandongBank");
		for (String key : map.keySet()) {
			System.out.println("key= " + key + " and value= " + map.get(key));
		}*/
	}

	public List getSearchList(BaseDataOP op, String page, String row,
			String fieldNames, String countName, String[] tabName,
			String whereSql, String orderby, String queryColumns, String[] tName) {
		int pages = IncString.formatInt(page);
		int rows = IncString.formatInt(row);
		String[][] result = op.queryRowAndCol(rows, pages, fieldNames,
				countName, tabName, whereSql, orderby, tabName);
		Map<Integer, String[]> newResult = new HashMap<Integer, String[]>();
		if (result == null) {
			return null;
		}
		for (int k = 0; k < result.length; k++) {
			if (!newResult.containsKey(IncString.formatInt(result[k][0]))) {
				newResult.put(IncString.formatInt(result[k][0]), result[k]);
			}
		}
		TreeMap<Integer, String[]> re = new TreeMap(newResult);
		// 修改分页的总记录数
		int num = op.getRowSum();
		setRowNum(getTotal(op, fieldNames, countName, tabName, whereSql,
				orderby, queryColumns, tName));
		String[] columns = queryColumns.split(",");
		Map<String, String> map = null;
		List ListData = new ArrayList();
		String[] val = null;
		Iterator<Map.Entry<Integer, String[]>> it = re.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String[]> entry = it.next();
			map = new HashMap<String, String>();
			val = entry.getValue();
			if (val != null && val.length > 0) {
				for (int m = 0; m < val.length; m++) {
					map.put(columns[m], val[m]);
				}
				ListData.add(map);
			}
		}

		// for (int i = 0; i < result.length; i++) {
		// map = new HashMap<String, String>();
		// for (int j = 0; j < columns.length; j++) {
		// map.put(columns[j], result[i][j]);
		// }
		// ListData.add(map);
		// }
		return ListData;
	}

	/**
	 * 获取总行数
	 * 
	 * @param op
	 * @param fieldNames
	 * @param countName
	 * @param tabName
	 * @param whereSql
	 * @param orderby
	 * @param queryColumns
	 * @param tName
	 * @return
	 */
	public int getTotal(BaseDataOP op, String fieldNames, String countName,
			String[] tabName, String whereSql, String orderby,
			String queryColumns, String[] tName) {
		int total = 0;
		String[][] result = op.queryRowAndCol(100000, 1, fieldNames, countName,
				tabName, whereSql, orderby, tabName);
		Map<Integer, String[]> newResult = new HashMap<Integer, String[]>();

		if (result != null) {

			for (int k = 0; k < result.length; k++) {
				if (!newResult.containsKey(IncString.formatInt(result[k][0]))) {
					newResult.put(IncString.formatInt(result[k][0]), result[k]);
				}
			}
			TreeMap<Integer, String[]> re = new TreeMap(newResult);
			// 修改分页的总记录数
			int num = op.getRowSum();
			setRowNum(num);
			String[] columns = queryColumns.split(",");
			Map<String, String> map = null;
			List ListData = new ArrayList();
			String[] val = null;
			Iterator<Map.Entry<Integer, String[]>> it = re.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, String[]> entry = it.next();
				map = new HashMap<String, String>();
				val = entry.getValue();
				if (val != null && val.length > 0) {
					for (int m = 0; m < val.length; m++) {
						map.put(columns[m], val[m]);
					}
					ListData.add(map);
				}
			}

			total = ListData.size();

		}

		return total;
	}

	public void getAllBankNode(HttpServletRequest request,
			HttpServletResponse response) {
		BankNodeDao bnd = new BankNodeDao(BankNodeDo.tableName);
		List<BankNode> list = bnd.getAllBankNode();
		JsonUtil.writeJsonObject(response, list);
	}

	public List getList(BaseDataOP op, String page, String row,
			String fieldNames, String countName, String[] tabName,
			String whereSql, String orderby, String queryColumns, String[] tName) {
		int pages = IncString.formatInt(page);
		int rows = IncString.formatInt(row);
		String[][] result = op.queryRowAndCol(rows, pages, fieldNames,
				countName, tabName, whereSql, orderby, tabName);
		setRowNum(op.getRowSum());
		String[] columns = queryColumns.split(",");
		Map<String, String> map = null;
		List ListData = new ArrayList();
		if (result == null) {
			return null;
		}
		for (int i = 0; i < result.length; i++) {
			map = new HashMap<String, String>();
			for (int j = 0; j < columns.length; j++) {
				map.put(columns[j], result[i][j]);
			}
			ListData.add(map);
		}
		return ListData;
	}

	public List getList(BaseDataOP op, String page, String row,
			String fieldNames, String countName, String tabName,
			String whereSql, String orderby, String queryColumns) {
		int pages = IncString.formatInt(page);
		int rows = IncString.formatInt(row);
		String[][] result = op.queryRowAndCol(rows, pages, fieldNames,
				countName, tabName, whereSql, orderby);
		String[] columns = op.queryColumnNames(queryColumns);
		Map<String, String> map = null;
		List ListData = new ArrayList();
		if (result == null) {
			return null;
		}
		for (int i = 0; i < result.length; i++) {
			map = new HashMap<String, String>();
			for (int j = 0; j < columns.length; j++) {
				map.put(columns[j], result[i][j]);

			}
			ListData.add(map);
		}
		return ListData;
	}

	public List getList(BaseDataOP op, String queryData, String queryColumns) {
		String[][] result = op.queryRowAndCol(queryData);
		String[] columns = op.queryColumnNames(queryColumns);
		Map<String, String> map = null;
		List ListData = new ArrayList();
		if (result == null) {
			return null;
		}
		for (int i = 0; i < result.length; i++) {
			map = new HashMap<String, String>();
			for (int j = 0; j < columns.length; j++) {
				map.put(columns[j], result[i][j]);

			}
			ListData.add(map);
		}
		return ListData;
	}

	public String getListToJosn(List list, String[] colNameArr, int total) {
		if (list == null || list.size() < 1 || total < 1) {
			return "";
		}
		StringBuffer josn = new StringBuffer("");
		String key = "";
		String[] valArr = new String[colNameArr.length];
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Set set = map.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mapentry = (Map.Entry) iterator.next();
				key = (String) mapentry.getKey();
				for (int j = 0; j < colNameArr.length; j++) {
					if (key.toLowerCase().equals(colNameArr[j].toLowerCase())) {
						valArr[j] = String.valueOf(mapentry.getValue())
								.replace("\r", "").replace("\n", "");
					}
				}
			}
			josn.append(arrToString(valArr, colNameArr));
		}
		josn = new StringBuffer(josn.substring(0, josn.length() - 1));
		return josn.toString();
	}

	public String arrToString(String[] arr, String[] columns) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < arr.length; i++) {
			sb.append("\"");
			sb.append(columns[i]);
			sb.append("\":");
			sb.append("\"");
			sb.append(arr[i]);
			sb.append("\",");
		}
		sb = new StringBuffer(sb.substring(0, sb.length() - 1));
		sb.append("},");
		return sb.toString();
	}
}
