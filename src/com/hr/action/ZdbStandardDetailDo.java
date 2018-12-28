package com.hr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.ZdbStandardDetail;
import com.hr.dao.ZdbStandardDetailDao;
import com.hr.global.util.JsonUtil;
import com.hr.info.dbBean;

public class ZdbStandardDetailDo {
	 

	
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String tableName ="zdb_zdbxmnr_"+request.getParameter("id");
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDetailDao zdbStandardDetailDao = new ZdbStandardDetailDao(tableName,dbName);
		List<ZdbStandardDetail> list = zdbStandardDetailDao.getAll();
		JsonUtil.writeJsonObject(response, list);
	}

	public void moveUp(HttpServletRequest request, HttpServletResponse response) {
		String tableName ="zdb_zdbxmnr_"+request.getParameter("id");
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDetailDao zdbStandardDetailDao = new ZdbStandardDetailDao(tableName,dbName);
		zdbStandardDetailDao.moveUp(request.getParameter("dalx"));
		JsonUtil.writeJsonMsg(response, true, "上移操作成功！");
	}

	public void moveDown(HttpServletRequest request,
			HttpServletResponse response) {
		String tableName ="zdb_zdbxmnr_"+request.getParameter("id");
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDetailDao zdbStandardDetailDao = new ZdbStandardDetailDao(tableName,dbName);
		zdbStandardDetailDao.moveDown(request.getParameter("dalx"));
		JsonUtil.writeJsonMsg(response, true, "下移操作成功！");
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		String tableName ="zdb_zdbxmnr_"+request.getParameter("id");
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDetailDao zdbStandardDetailDao = new ZdbStandardDetailDao(tableName,dbName);
		zdbStandardDetailDao.delete(request.getParameter("dalx"));
		JsonUtil.writeJsonMsg(response, true, "删除操作成功！");
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		String tableName ="zdb_zdbxmnr_"+request.getParameter("id");
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDetailDao zdbStandardDetailDao = new ZdbStandardDetailDao(tableName,dbName);
		ZdbStandardDetail obj = new ZdbStandardDetail();
		obj.setDamc(request.getParameter("damc"));
		obj.setSort_num(Integer.parseInt(zdbStandardDetailDao.getMaxsort_num()) + 1);
		zdbStandardDetailDao.add(obj);
		JsonUtil.writeJsonMsg(response, true, "添加操作成功！");
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		String tableName ="zdbxmnr_"+request.getParameter("id");
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDetailDao zdbStandardDetailDao = new ZdbStandardDetailDao(tableName,dbName);
		zdbStandardDetailDao.update(request.getParameter("dalx"), request.getParameter("damc"));
		JsonUtil.writeJsonMsg(response, true, "更新操作成功！");
	}
}
