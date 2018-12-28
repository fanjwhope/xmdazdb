package com.hr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.ZdbStandard;
import com.hr.dao.ZdbStandardDao;
import com.hr.global.util.JsonUtil;

public class ZdbStandardDo {
	private String tableName ="zdb_zdbsp";

	
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDao zdbStandardDao=new ZdbStandardDao(tableName, dbName);
		List<ZdbStandard> list = zdbStandardDao.getAll();
		JsonUtil.writeJsonObject(response, list);
	}

	public void moveUp(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDao zdbStandardDao=new ZdbStandardDao(tableName, dbName);
		zdbStandardDao.moveUp(request.getParameter("id"));
		JsonUtil.writeJsonMsg(response, true, "���Ʋ����ɹ���");
	}

	public void moveDown(HttpServletRequest request,
			HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDao zdbStandardDao=new ZdbStandardDao(tableName, dbName);
		zdbStandardDao.moveDown(request.getParameter("id"));
		JsonUtil.writeJsonMsg(response, true, "���Ʋ����ɹ���");
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDao zdbStandardDao=new ZdbStandardDao(tableName, dbName);
		zdbStandardDao.delete(request.getParameter("id"));
		JsonUtil.writeJsonMsg(response, true, "ɾ�������ɹ���");
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDao zdbStandardDao=new ZdbStandardDao(tableName, dbName);
		ZdbStandard obj = new ZdbStandard();
		obj.setSpnr(request.getParameter("spnr"));
		obj.setTabindex(Integer.parseInt(zdbStandardDao.getMaxTabIndex()) + 1);
		obj.setTest(0);
		zdbStandardDao.add(obj);
		JsonUtil.writeJsonMsg(response, true, "��Ӳ����ɹ���");
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("zdbdbName");
		ZdbStandardDao zdbStandardDao=new ZdbStandardDao(tableName, dbName);
		zdbStandardDao.update(request.getParameter("id"), request.getParameter("spnr"));
		JsonUtil.writeJsonMsg(response, true, "���²����ɹ���");
	}
}
