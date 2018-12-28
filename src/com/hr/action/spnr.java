package com.hr.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.ZdbStandardDao;
import com.hr.global.util.JsonUtil;

public class spnr {
	public void getType(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDao zdbStandardDao=new ZdbStandardDao("zdb_zdbsp",dbName);	
		JsonUtil.writeJsonObject(response, zdbStandardDao.getAll());
	}
}
