package com.hr.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.Xmwj;
import com.hr.dao.ArchiveDao;
import com.hr.dao.FilePathInfo;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;

public class wusmj {
	public void wqmysmj(HttpServletRequest request, HttpServletResponse response) {
		String dwh = ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao("zdb_xmwj_" + dwh,dbName);
		List<Xmwj> list = archiveDao.getList(new Xmwj(), new PagingBean());
		List<Xmwj> result = new ArrayList<Xmwj>();
		FilePathInfo filePathInfo = new FilePathInfo();
		for (Xmwj x : list) {
			int spgl=filePathInfo.getRealPath(String.valueOf(x.getLsh()), "spgl", dwh,
					x.getXmbh(), "zdb", "1",dbName).getAllFileNames().size();
			int dhgl=filePathInfo.getRealPath(String.valueOf(x.getLsh()), "dhgl", dwh,
					x.getXmbh(), "zdb", "1",dbName).getAllFileNames().size();
			if(spgl==0&&dhgl==0){
				result.add(x);
			}
		}
		JsonUtil.writeJsonGrid(response, result, new PagingBean());
	}
	
	public void bfmysmj(HttpServletRequest request, HttpServletResponse response) {
		String dwh = ArchiveUtil.getDepartmentCode(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		ArchiveDao archiveDao = new ArchiveDao("zdb_xmwj_" + dwh,dbName);
		List<Xmwj> list = archiveDao.getList(new Xmwj(), new PagingBean());
		List<Xmwj> result = new ArrayList<Xmwj>();
		FilePathInfo filePathInfo = new FilePathInfo();
		for (Xmwj x : list) {
			int spgl=filePathInfo.getRealPath(String.valueOf(x.getLsh()), "spgl", dwh,
					x.getXmbh(), "zdb", "1",dbName).getAllFileNames().size();
			int dhgl=filePathInfo.getRealPath(String.valueOf(x.getLsh()), "dhgl", dwh,
					x.getXmbh(), "zdb", "1",dbName).getAllFileNames().size();
			if((spgl==0&&dhgl!=0)||(spgl!=0&&dhgl==0)){
				result.add(x);
			}
		}
		JsonUtil.writeJsonGrid(response, result, new PagingBean());
	}
}
