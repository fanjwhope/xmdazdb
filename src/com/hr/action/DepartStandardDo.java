package com.hr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.DepartStandard;
import com.hr.dao.DepartStandardDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.Validation;
import com.hr.util.ConfigInfo;
import com.hr.util.Log;


public class DepartStandardDo {
	 public void  getList(HttpServletRequest request, HttpServletResponse response){
		 String tableName=getTableName(request);
		 String dbName = request.getParameter("zdbdbName");
		 DepartStandardDao departStandardDao=new DepartStandardDao(tableName,dbName);
		 List<DepartStandard> list=departStandardDao.getList(null);
		 JsonUtil.writeJsonObject(response, list);
	 }
	 
	 public void sort(HttpServletRequest request, HttpServletResponse response){
		 String tableName=getTableName(request);
		 String dbName = request.getParameter("zdbdbName");
		 DepartStandardDao departStandardDao=new DepartStandardDao(tableName,dbName);
		 String sort=request.getParameter("sort");
		 String id=request.getParameter("id");
		 String[] sorts = sort.split("/");
		 String[] ids = id.split("/");
		 try {
			 for(int i = 0; i < sorts.length; i++){
				 if("".equals(departStandardDao.Norm(sorts[i])))   //序号不规范，跳出本次循环
					 continue;
				 else					 
					 departStandardDao.sort(ids[i], departStandardDao.Norm(sorts[i]));
			 }
			 JsonUtil.writeJsonMsg(response, true, "存盘成功");
		 } catch (Exception e) {
			 Log.debug(e);
			 JsonUtil.writeJsonMsg(response, false, "存盘失败");
		 }
	 }
	 
	 public  void add(HttpServletRequest request, HttpServletResponse response){
		 String tableName=getTableName(request);
		 String dbName = request.getParameter("zdbdbName");
		 DepartStandardDao departStandardDao=new DepartStandardDao(tableName,dbName);
		 DepartStandard departStandard=getObject(request);
		 departStandard.setSortNum(departStandardDao.getMaxSort());
		 try {
			 departStandardDao.add(departStandard,null,new String[]{"id"});
			 JsonUtil.writeJsonMsg(response, true, "增加成功");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "增加失败");
		}
	 }
	 
	 public void  getOne(HttpServletRequest request, HttpServletResponse response){
		 String tableName=getTableName(request);
		 String dbName = request.getParameter("zdbdbName");
		 DepartStandardDao departStandardDao=new DepartStandardDao(tableName,dbName);
		 String id=request.getParameter("id");
		 DepartStandard departStandard=departStandardDao.findById(id);
		 JsonUtil.writeJsonObject(response, departStandard);
	 }
	 
	 public  void update(HttpServletRequest request, HttpServletResponse response){
		 String tableName=getTableName(request);
		 String dbName = request.getParameter("zdbdbName");
		 DepartStandardDao departStandardDao=new DepartStandardDao(tableName,dbName);
		 DepartStandard departStandard=getObject(request);
		 try {
			 departStandardDao.update(departStandard,new String[]{"damc","dalx"},null);
			 JsonUtil.writeJsonMsg(response, true, "修改成功");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "修改失败");
		}
		 
	 }
	 
	 public  void  del(HttpServletRequest request, HttpServletResponse response){
		 String tableName=getTableName(request);
		 String dbName = request.getParameter("zdbdbName");
		 DepartStandardDao departStandardDao=new DepartStandardDao(tableName,dbName);
		 String  id=request.getParameter("id");
		 try {
			 departStandardDao.deleteById(id);
			 JsonUtil.writeJsonMsg(response, true, "删除成功");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "删除失败");
		}
		 
	 }
	 
	 private  DepartStandard  getObject(HttpServletRequest request){
		 DepartStandard departStandard = null;
			try {
				departStandard = (DepartStandard) BeanHelper.getValuesByRequest(request, DepartStandard.class, false);
			} catch (Exception e) {
				Log.error(e);
			}
			return departStandard;
	 }
	 
	 
	 private String  getTableName(HttpServletRequest request){
		 String dataBaseName=ArchiveUtil.getDataBaseName(request.getSession());
	     String schema=ConfigInfo.getVal("schema");
	     String archiveType=request.getParameter("archiveType");
	     String departCode=request.getParameter("departCode");
	     String tableName=dataBaseName+"_"+departCode+"_"+archiveType;
	     return  tableName;
	 }
}

