package com.hr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.ProjectType;
import com.hr.dao.ProjectTypeDao;
import com.hr.data.Session_Bean;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.BuildSql;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.global.util.Validation;
import com.hr.util.Log;

public class ProjectTypeDo {
	//private  String tableName="ydBank.dbo.xmpz";
	public  void  add(HttpServletRequest request, HttpServletResponse response){
		String tableName=ArchiveUtil.getFullTableName(request, ProjectType.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectTypeDao  atd=new ProjectTypeDao(tableName,dbName);
		ProjectType archiveType=getObject(request);
		try {
			archiveType.setSortNum(String.valueOf(atd.getMaxSort()));
			atd.add(archiveType);
			JsonUtil.writeJsonMsg(response, true, "项目品种添加成功");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "项目品种添加失败");
		}
		
	}
	
	public   void  update(HttpServletRequest request, HttpServletResponse response){
		String tableName=ArchiveUtil.getFullTableName(request, ProjectType.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectTypeDao  atd=new ProjectTypeDao(tableName,dbName);
		ProjectType archiveType=getObject(request);
		try {
			atd.update(archiveType);
			JsonUtil.writeJsonMsg(response, true, "项目品种更新成功");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "项目品种更新失败");
		}
	}
	/**
	 * 删除项目品种
	 * @param request
	 * @param response
	 */
	public  void  del(HttpServletRequest request, HttpServletResponse response){
		String tableName=ArchiveUtil.getFullTableName(request, ProjectType.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectTypeDao  atd=new ProjectTypeDao(tableName,dbName);
		String id=request.getParameter("id");
		try {
			atd.remove(id);
		} catch (Exception e) {
			Log.debug(e);
		}
		
	}
	
	public  void isExit(HttpServletRequest request, HttpServletResponse response){
		String tableName=ArchiveUtil.getFullTableName(request, ProjectType.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectTypeDao  atd=new ProjectTypeDao(tableName,dbName);
		String name=request.getParameter("name");
		try {
			int num=atd.isExit(name);
			if(num>0){
				JsonUtil.writeJsonMsg(response, true, "1");
			}else{
				JsonUtil.writeJsonMsg(response, true, "0");
			}
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "1");
		}
	}
	//不分页
	public  void  getAllProjectType(HttpServletRequest request, HttpServletResponse response){
		String tableName=ArchiveUtil.getFullTableName(request, ProjectType.class);
		String dbNameSearch=request.getParameter("dbNameSearch");
		String dbName = request.getParameter("zdbdbName");
		if(!Validation.isEmpty(dbNameSearch)){
			tableName=dbNameSearch+"_"+ BuildSql.getTableName(ProjectType.class);
		}
		ProjectTypeDao  atd=new ProjectTypeDao(tableName,dbName);
		try {
		    List<ProjectType> list=atd.getProjectType();
			JsonUtil.writeJsonObject(response, list);
		} catch (Exception e) {
			Log.debug(e);
		}
	}
	
	/**
	 * 获取所有的项目品种,分页
	 * @param request
	 * @param response
	 */
	public  void  getList(HttpServletRequest request, HttpServletResponse response){
		String tableName=ArchiveUtil.getFullTableName(request, ProjectType.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectTypeDao  atd=new ProjectTypeDao(tableName,dbName);
		ProjectType projectType=getObject(request);
		projectType.setFlag("1");
		PagingBean pagingBean = new PagingBean(request);
		try {
			List<ProjectType> list=atd.findAll(projectType, pagingBean);
			JsonUtil.writeJsonObject(response, list);
		} catch (Exception e) {
			Log.debug(e);
		}
	}
	
	
	
	
	
	
	private ProjectType getObject(HttpServletRequest request) {
		ProjectType archiveType = null;
		try {
			archiveType = (ProjectType) BeanHelper.getValuesByRequest(request, ProjectType.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return archiveType;
	}
	
	public int getCurrentUserId(HttpServletRequest request) {
		Session_Bean sessionBean = (Session_Bean) request.getSession()
				.getAttribute("bean_sess");
		return sessionBean.getUserid_Sess();
	}


}
