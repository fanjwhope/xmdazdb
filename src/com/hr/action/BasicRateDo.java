package com.hr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.BasicRate;
import com.hr.dao.BasicRateDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.util.Log;

public class BasicRateDo {
	
	private BasicRate getObject(HttpServletRequest request){
		BasicRate basicRate = null;
		try {
			basicRate = (BasicRate)BeanHelper.getValuesByRequest(request, BasicRate.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return basicRate;
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, BasicRate.class);
		String dbName = request.getParameter("zdbdbName");
		BasicRateDao dao = new BasicRateDao(tableName,dbName);
		BasicRate basicRate = getObject(request);
		try{
			if(dao.ifCnameExist(basicRate))
				return;
			else
				dao.doCreate(basicRate,dbName);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "���ʻ�׼���ʧ�ܣ�");
		}
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, BasicRate.class);
		String dbName = request.getParameter("zdbdbName");
		BasicRateDao dao = new BasicRateDao(tableName,dbName);
		BasicRate basicRate = getObject(request);
		try{
			dao.doDelete(basicRate.getId());
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "���ʻ�׼ɾ��ʧ�ܣ�");
		}
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, BasicRate.class);
		String dbName = request.getParameter("zdbdbName");
		BasicRateDao dao = new BasicRateDao(tableName,dbName);
		BasicRate basicRate = getObject(request);
		try{
			if(dao.ifCnameExist(basicRate))
				return;
			else
				dao.doUpdate(basicRate);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "���ʻ�׼�޸�ʧ�ܣ�");
		}
	}
	
	public void findAll(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, BasicRate.class);
		String dbName = request.getParameter("zdbdbName");
		BasicRateDao dao = new BasicRateDao(tableName,dbName);
		try{
			List<BasicRate> all = dao.findAll();
			JsonUtil.writeJsonObject(response, all);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "���ʻ�׼��ѯʧ�ܣ�");
		}
	}
	
	public void up(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, BasicRate.class);
		String dbName = request.getParameter("zdbdbName");
		BasicRateDao dao = new BasicRateDao(tableName,dbName);
		BasicRate basicRate = getObject(request);
		try{
			dao.up(basicRate);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "���ʻ�׼����ʧ�ܣ�");
		}
	}
	
	public void down(HttpServletRequest request, HttpServletResponse response){
			String tableName = ArchiveUtil.getFullTableName(request, BasicRate.class);
			String dbName = request.getParameter("zdbdbName");
			BasicRateDao dao = new BasicRateDao(tableName,dbName);
			BasicRate basicRate = getObject(request);
			try{
				dao.down(basicRate);
			}catch (Exception e){
				Log.error(e);
				JsonUtil.writeJsonMsg(response, false, "���ʻ�׼����ʧ�ܣ�");
			}
		}
}
