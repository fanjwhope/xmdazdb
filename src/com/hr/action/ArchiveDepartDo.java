package com.hr.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.ArchiveDepart;
import com.hr.bean.ArchiveDepartBak;
import com.hr.bean.DepartStandard;
import com.hr.bean.User;
import com.hr.bean.ZdbStandardDetail;
import com.hr.dao.ArchiveDepartBakDao;
import com.hr.dao.ArchiveDepartDao;
import com.hr.dao.DepartStandardDao;
import com.hr.dao.FilePathInfo;
import com.hr.dao.ZdbStandardDetailDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;
import com.hr.util.Log;
import com.hr.util.Md5;

public class ArchiveDepartDo {

	public void add(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("----------------start----------------");
		String archiveType = request.getParameter("archiveType");
		String lsh = request.getParameter("lsh");
		String isnotstd = request.getParameter("isnotstd");
		String dbName=request.getParameter("zdbdbName");
		String archiveDepartTable = ArchiveUtil.getFullTableName(request,
				ArchiveDepart.class);
		ArchiveDepartDao archiveDepartDao = new ArchiveDepartDao(
				archiveDepartTable,dbName);
		User user = ArchiveUtil.getCurrentUser(request.getSession());
		String dwh = user.getDwh();
		String[] sort_num = request.getParameterValues("sort_num");
		String[] dalx = request.getParameterValues("dalx");
		String[] damc = request.getParameterValues("damc");
		String[] days = request.getParameterValues("days");
		String[] archivetime = request.getParameterValues("archivetime");
		String[] dabz = request.getParameterValues("dabz");
		for(int i = 0; i < damc.length; i++){
			System.out.println("sort_num:"+sort_num[i]);
			System.out.println("damc:"+damc[i]);
			System.out.println("dalx:"+dalx[i]);
			System.out.println("days:"+days[i]);
		}
		try {
			archiveDepartDao.delAll(Long.parseLong(lsh));
			System.out.println("rownum:"+sort_num.length);
			for (int i = 0; i < damc.length; i++) {
				ArchiveDepart archiveDepart = new ArchiveDepart();
				if (archivetime == null) {
					archiveDepart.setArchivetime(null);
				} else {
					archiveDepart.setArchivetime(archivetime[i]);
				}
				archiveDepart.setDabz(dabz[i]);
				archiveDepart.setDalx(dalx[i]);
				archiveDepart.setDamc(damc[i]);
				archiveDepart.setDays(days[i]);
				archiveDepart.setDwh(dwh);
				archiveDepart.setLsh(Long.parseLong(lsh));
				if (!Validation.isEmpty(isnotstd)) {
					archiveDepart.setIsnotstd(1);
				}
				Long snum=(long)i;
				try {
					snum=Long.parseLong(sort_num[i]);
				} catch (Exception e) {
					snum=(long)i;
				}		
				archiveDepart.setSortNum(snum);
				archiveDepartDao
						.add(archiveDepart);
			}
			System.out.println("----------------end----------------");
			JsonUtil.writeJsonMsg(response, true, "存盘成功");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "存盘失败");
		}
	}

	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String archiveType = request.getParameter("archiveType");
		String lsh = request.getParameter("lsh");
		String isnotstd = request.getParameter("isnotstd");
		String dbName=request.getParameter("zdbdbName");
		String dwh = ArchiveUtil.getDepartmentCode(request.getSession());
		String dataBaseName = ArchiveUtil.getDataBaseName(request.getSession());
		// String schema=ConfigInfo.getVal("schema");
		String archiveDepartTable = ArchiveUtil.getFullTableName(request,
				ArchiveDepart.class);// 页面记得传入archiveType
		ArchiveDepartDao archiveDepartDao = new ArchiveDepartDao(
				archiveDepartTable,dbName);
		int num = archiveDepartDao.getRowNum(lsh);
		List<ArchiveDepart> list = new ArrayList<ArchiveDepart>(0);
		try {
			if (num < 1 && (!"1".equals(isnotstd))) {// 当没有档案信息且为标准状态时，查询标准表展示
				// 将标准信息写入
				String tail = dwh.substring(0, 6) + "0000";
				String departStandardTable = dataBaseName + "_" + tail + "_"
						+ archiveType;
				DepartStandardDao departStandardDao = new DepartStandardDao(
						departStandardTable,dbName);
				List<DepartStandard> standardList = departStandardDao
						.getList(null);
				for (int i = 0; i < standardList.size(); i++) {
					ArchiveDepart archiveDepart = new ArchiveDepart();
					archiveDepart.setLsh(Long.parseLong(lsh));
					archiveDepart.setDalx(standardList.get(i).getDalx());
					archiveDepart.setDamc(standardList.get(i).getDamc());
					archiveDepart.setDwh(dwh);
					archiveDepart.setSortNum((i + 1l));
					archiveDepart.setIsnotstd(1);
					// archiveDepartDao.add(archiveDepart,null,new
					// String[]{"id"});
					list.add(archiveDepart);
				}
				// list=archiveDepartDao.getList(lsh);
				JsonUtil.writeJsonObject(response, list);
			} else {// 有文档信息时，直接查询该项目下的卷文件
				list = archiveDepartDao.getList(lsh);
				JsonUtil.writeJsonObject(response, list);
			}
		} catch (Exception e) {
			Log.debug(e);
		}
	}

	public void getListForZDB(HttpServletRequest request, HttpServletResponse response) {
		String archiveType = request.getParameter("archiveType");
		String lsh = request.getParameter("lsh");
		String isnotstd = request.getParameter("isnotstd");
		String dbName=request.getParameter("zdbdbName");
		String archiveTypeZDB=request.getParameter("archiveTypeZDB");
		String dwh = ArchiveUtil.getDepartmentCode(request.getSession());
		String dataBaseName = ArchiveUtil.getDataBaseName(request.getSession());
		// String schema=ConfigInfo.getVal("schema");
		String archiveDepartTable = ArchiveUtil.getFullTableName(request,
				ArchiveDepart.class);// 页面记得传入archiveType
		ArchiveDepartDao archiveDepartDao = new ArchiveDepartDao(
				archiveDepartTable,dbName);
		int num = archiveDepartDao.getRowNum(lsh);
		List<ArchiveDepart> list = new ArrayList<ArchiveDepart>(0);
		try {
			if (num < 1 && (!"1".equals(isnotstd))) {// 当没有档案信息且为标准状态时，查询标准表展示
				// 将标准信息写入
				String tail = dwh.substring(0, 6) + "0000";
				String departStandardTable = dbName+"_zdbxmnr_"+archiveTypeZDB;
				ZdbStandardDetailDao departStandardDao = new ZdbStandardDetailDao(
						departStandardTable,dbName);
				List<ZdbStandardDetail> standardList = departStandardDao.getAll();
				for (int i = 0; i < standardList.size(); i++) {
					ArchiveDepart archiveDepart = new ArchiveDepart();
					archiveDepart.setLsh(Long.parseLong(lsh));
					archiveDepart.setDalx(String.valueOf(standardList.get(i).getDalx()));
					archiveDepart.setDamc(standardList.get(i).getDamc());
					archiveDepart.setDwh(dwh);
					archiveDepart.setSortNum((i + 1l));
					archiveDepart.setIsnotstd(1);
					// archiveDepartDao.add(archiveDepart,null,new
					// String[]{"id"});
					list.add(archiveDepart);
				}
				// list=archiveDepartDao.getList(lsh);
				JsonUtil.writeJsonObject(response, list);
			} else {// 有文档信息时，直接查询该项目下的卷文件
				list = archiveDepartDao.getList(lsh);
				JsonUtil.writeJsonObject(response, list);
			}
		} catch (Exception e) {
			Log.debug(e);
		}
	}
	
	//todo 显示数据  2018-08-15
	public void getListShow(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("显示文件集合：");
		String lsh = request.getParameter("lsh");
		String tbname = request.getParameter("tbname");
		String dbName=request.getParameter("zdbdbName");
		String archiveDepartTable = ArchiveUtil.getFullTableName(request,
				ArchiveDepart.class);// 页面记得传入archiveType
		if (!Validation.isEmpty(tbname)) {
			archiveDepartTable = tbname;
		}
		Log.error("显示文件集合：tableName:" +archiveDepartTable + ",dbName: "+dbName);
		ArchiveDepartDao archiveDepartDao = new ArchiveDepartDao(
				archiveDepartTable,dbName);
		List<ArchiveDepart> list = new ArrayList<ArchiveDepart>(0);
		try {
			list = archiveDepartDao.getList(lsh);
			//JsonUtil.writeJsonObject(response, list); 2018-08-15之前的
			//修改为 ： 2018-08-15 start
			if(list!=null && list.size()>0){
				System.out.println("OK");
				Log.error("OK,查询到了数据"+list.size() +": "+list.toString());
				JsonUtil.writeJsonObject(response, list);
			}else{
				//1.查询移交表  根据lsh 查询移交表 获取最开始的著录单位
				String sql="select top 1 yjdw from zdb_xmda_yj where lsh='"+lsh+"' order by id";
				System.out.println("移交表 文件最开始的著录单位："+sql);
				Log.error("移交表 文件最开始的著录单位："+sql);
				String yjdw=archiveDepartDao.querySingleData(sql);
				String[] tableNameList=tbname.split("_");
				if(tableNameList!=null && tableNameList.length==3){
					tbname=tableNameList[0]+"_"+tableNameList[1]+"_"+yjdw;
				}
				//2.查询
				String fieldNames=archiveDepartDao.getFields(archiveDepartDao.tableFieldZY);
				String sql1="select "+fieldNames+" from "+tbname+"  where  lsh='"+lsh+"'  order  by lsh,sort_num  ";
				System.out.println("移交表 文件最开始的著录单位："+sql1);
				String[][] obj = archiveDepartDao.queryRowAndCol(sql1);
			    Map<String, String> map=new HashMap<String, String>();
			    map.put("sort_num", "sortNum");
				list=BeanHelper.stringArray2Object(fieldNames, obj, ArchiveDepart.class, false,map);
				JsonUtil.writeJsonObject(response, list);
			}
			//修改为 ： 2018-08-15 end 
		} catch (Exception e) {
			Log.debug(e);
		}
	}
	


	public void del(HttpServletRequest request, HttpServletResponse response) {
		String archiveDepartTable = ArchiveUtil.getFullTableName(request,
				ArchiveDepart.class);
		String dbName=request.getParameter("zdbdbName");
		ArchiveDepartDao archiveDepartDao = new ArchiveDepartDao(
				archiveDepartTable,dbName);
		ArchiveDepart archiveDepart = getObject(request);
		try {
			archiveDepartDao.del(archiveDepart.getLsh(),
					archiveDepart.getSortNum());
		} catch (Exception e) {
			Log.debug(e);
		}
	}

	public void delAll(HttpServletRequest request, HttpServletResponse response) {
		String archiveDepartTable = ArchiveUtil.getFullTableName(request,
				ArchiveDepart.class);
		String dbName=request.getParameter("zdbdbName");
		ArchiveDepartDao archiveDepartDao = new ArchiveDepartDao(
				archiveDepartTable,dbName);
		ArchiveDepart archiveDepart = getObject(request);
		try {
			archiveDepartDao.delAll(archiveDepart.getLsh());
		} catch (Exception e) {
			Log.debug(e);
		}
	}

	private ArchiveDepart getObject(HttpServletRequest request) {
		ArchiveDepart archiveDepart = null;
		try {
			archiveDepart = (ArchiveDepart) BeanHelper.getValuesByRequest(
					request, ArchiveDepart.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return archiveDepart;
	}

	public ArchiveDepartBak ArchiveDepartToBak(ArchiveDepart archiveDepart) {
		ArchiveDepartBak archiveDepartBak = new ArchiveDepartBak();
		archiveDepartBak.setLsh(archiveDepart.getLsh());
		archiveDepartBak.setDalx(archiveDepart.getDalx());
		archiveDepartBak.setDamc(archiveDepart.getDamc());
		archiveDepartBak.setDays(archiveDepart.getDays());
		archiveDepartBak.setDabz(archiveDepart.getDabz());
		archiveDepartBak.setSortNum(archiveDepart.getSortNum());
		archiveDepartBak.setDwh(archiveDepart.getDwh());
		archiveDepartBak.setFlag(archiveDepart.getFlag());
		archiveDepartBak.setArchivetime(archiveDepart.getArchivetime());
		archiveDepartBak.setIsnotstd(archiveDepart.getIsnotstd());
		archiveDepartBak.setLogAction("update");
		return archiveDepartBak;
	}

	public void isExitPdf(HttpServletRequest request,
			HttpServletResponse response) {
		String lsh = request.getParameter("lsh");
		String archiveType = request.getParameter("archiveType");
		String xmbh = request.getParameter("xmbh");
		String bankName = request.getParameter("dbName");
		String zdbdbName =request.getParameter("zdbdbName");
		if(bankName==null||"".equals(bankName)){
			bankName=ArchiveUtil.getDataBaseName(request.getSession());
		}
		String yjdw = ArchiveUtil.getCurrentUser(request.getSession()).getDwh();
		FilePathInfo filePathInfo = new FilePathInfo();
		String oldFile = filePathInfo.getRealPath(lsh, archiveType, yjdw, xmbh,
				bankName,null,zdbdbName).getFileName();
		if (oldFile != null) {
			File old = new File(oldFile);
			if (old.exists()) {
				JsonUtil.writeJsonMsg(response, true, "true");
				return;
			}
		}
		JsonUtil.writeJsonMsg(response, true, "false");

	}
}
