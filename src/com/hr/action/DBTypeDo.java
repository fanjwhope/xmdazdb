package com.hr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.DBType;
import com.hr.dao.DBTypeDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.util.Log;

public class DBTypeDo {
	
	private DBType getObject(HttpServletRequest request){
		DBType dbType = null;
		try {
			dbType = (DBType)BeanHelper.getValuesByRequest(request, DBType.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return dbType;
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, DBType.class);
		String dbName = request.getParameter("zdbdbName");
		DBTypeDao dao = new DBTypeDao(tableName,dbName);
		DBType dbType = getObject(request);
		try{
			if(dao.ifCnameExist(dbType))
				return;
			else
				dao.doCreate(dbType,dbName);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "������ʽ���ʧ�ܣ�");
		}
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, DBType.class);
		String dbName = request.getParameter("zdbdbName");
		DBTypeDao dao = new DBTypeDao(tableName,dbName);
		DBType dbType = getObject(request);
		try{
			dao.doDelete(dbType.getId());
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "������ʽɾ��ʧ�ܣ�");
		}
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, DBType.class);
		String dbName = request.getParameter("zdbdbName");
		DBTypeDao dao = new DBTypeDao(tableName,dbName);
		DBType dbType = getObject(request);
		try{
			if(dao.ifCnameExist(dbType))
				return;
			else
				dao.doUpdate(dbType);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "������ʽ�޸�ʧ�ܣ�");
		}
	}
	
	public void findAll(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, DBType.class);
		String dbName = request.getParameter("zdbdbName");
		DBTypeDao dao = new DBTypeDao(tableName,dbName);
		try{
			List<DBType> all = dao.findAll();
			JsonUtil.writeJsonObject(response, all);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "������ʽ��ѯʧ�ܣ�");
		}
	}
	
	public void up(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, DBType.class);
		String dbName = request.getParameter("zdbdbName");
		DBTypeDao dao = new DBTypeDao(tableName,dbName);
		DBType dbType = getObject(request);
		try{
			dao.up(dbType);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "������ʽ����ʧ�ܣ�");
		}
	}
	
	public void down(HttpServletRequest request, HttpServletResponse response){
			String tableName = ArchiveUtil.getFullTableName(request, DBType.class);
			String dbName = request.getParameter("zdbdbName");
			DBTypeDao dao = new DBTypeDao(tableName,dbName);
			DBType dbType = getObject(request);
			try{
				dao.down(dbType);
			}catch (Exception e){
				Log.error(e);
				JsonUtil.writeJsonMsg(response, false, "������ʽ����ʧ�ܣ�");
			}
		}
}
