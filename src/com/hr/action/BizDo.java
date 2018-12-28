package com.hr.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.Biz;
import com.hr.dao.BizDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.util.Log;

public class BizDo {
	
	private Biz getObject(HttpServletRequest request){
		Biz biz = null;
		try {
			biz = (Biz) BeanHelper.getValuesByRequest(request, Biz.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return biz;
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Biz.class);
		BizDao dao = new BizDao(tableName);
		Biz biz = getObject(request);
		try{
			if(dao.ifBizExist(biz.getBiz()))
				return;
			else
				dao.doCreate(biz);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "±“÷÷ÃÌº” ß∞‹£°");
		}
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Biz.class);
		BizDao dao = new BizDao(tableName);
		Biz biz = getObject(request);
		try{
			dao.doDelete(biz.getBiz());
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "±“÷÷…æ≥˝ ß∞‹£°");
		}
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Biz.class);
		BizDao dao = new BizDao(tableName);
		String biz1 = request.getParameter("biz1");
		String biz2=  request.getParameter("biz2");
		try{
			if(dao.ifBizExist(biz1))
				return;
			else
				dao.doUpdate(biz1, biz2);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "±“÷÷–ﬁ∏ƒ ß∞‹£°");
		}
	}
	
	public void findAll(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Biz.class);
		BizDao dao = new BizDao(tableName);
		try{
			List<Biz> list = dao.findAll();
			JsonUtil.writeJsonObject(response, list);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "±“÷÷≤È—Ø ß∞‹£°");
		}
	}
}
