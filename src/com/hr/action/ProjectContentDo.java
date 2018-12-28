package com.hr.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.Location;
import com.hr.bean.ProjectContent;
import com.hr.dao.FilePathInfo;
import com.hr.dao.LocationDao;
import com.hr.dao.ProjectContentDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.Validation;
import com.hr.util.Log;
import com.hr.util.d;

public class ProjectContentDo {

	private ProjectContent getObject(HttpServletRequest request) {
		ProjectContent projectContent = null;
		try {
			projectContent = (ProjectContent) BeanHelper.getValuesByRequest(
					request, ProjectContent.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return projectContent;
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request,
				ProjectContent.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectContentDao dao = new ProjectContentDao(tableName,dbName);
		ProjectContent projectContent = getObject(request);
		try {
			if (dao.ifExist(projectContent))
				return;
			else
				dao.doCreate(projectContent,dbName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "项目内容类型添加失败！");
		}
	}

	public void del(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request,
				ProjectContent.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectContentDao dao = new ProjectContentDao(tableName,dbName);
		ProjectContent projectContent = getObject(request);
		try {
			dao.doDelete(projectContent);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "项目内容类型删除失败！");
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request,
				ProjectContent.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectContentDao dao = new ProjectContentDao(tableName,dbName);
		ProjectContent projectContent = getObject(request);
		try {
			if (dao.ifNameExist(projectContent))
				return;
			else
				dao.doUpdate(projectContent);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "项目内容类型修改失败！");
		}
	}

	public void find(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request,
				ProjectContent.class);
		String dbName = request.getParameter("zdbdbName");
		ProjectContentDao dao = new ProjectContentDao(tableName,dbName);
		try {
			List<ProjectContent> all = dao.findAll();
			JsonUtil.writeJsonObject(response, all);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "项目内容类型查询失败！");
		}
	}

	public void findToArchive(HttpServletRequest request,
			HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request,
				ProjectContent.class);
		String dbName = ArchiveUtil.getDataBaseName(request.getSession());
		String zdbdbName = request.getParameter("zdbdbName");
		if (tableName.split("_").length < 2) {
			dbName = request.getParameter("dbName");
			tableName = request.getParameter("dbName") + "_" + tableName;
		}
		// 发现存在Y200020007类似并非以0结尾文件夹，作此修改
		String dwh = ArchiveUtil.getCurrentUser(request.getSession()).getDwh();
		// String
		// dwh=ArchiveUtil.getCurrentUser(request.getSession()).getDwh().substring(0,
		// 6)+"0000";
		ProjectContentDao dao = new ProjectContentDao(tableName,zdbdbName);
		List<ProjectContent> all = new ArrayList<ProjectContent>(0);
		String lsh = request.getParameter("lsh");
		String xmbh = request.getParameter("xmbh");
		try {
			if (Validation.isEmpty(lsh) || lsh.equals("null")) {
				all = dao.findAll();
			} else {
				all = dao.findAll();
				FilePathInfo fileParh = new FilePathInfo();
				String str;
				for (int i = 0; i < all.size(); i++) {
					str = fileParh.getRealPath(lsh, all.get(i).getEname(), dwh,
							xmbh, dbName, null,zdbdbName).getFileName();
					String flag = "1";
					if (!Validation.isEmpty(str)) {
						all.get(i).setFlag(flag);
					} else {
						all.get(i).setFlag("0");
					}
				}
			}
			JsonUtil.writeJsonObject(response, all);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "项目内容类型查询失败！");
		}
	}

	public void up(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request,
				ProjectContent.class);
		String zdbdbName = request.getParameter("zdbdbName");
		ProjectContentDao dao = new ProjectContentDao(tableName,zdbdbName);
		ProjectContent projectContent = getObject(request);
		try {
			dao.up(projectContent);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "项目内容类型上移失败！");
		}
	}

	public void down(HttpServletRequest request, HttpServletResponse response) {
		String tableName = ArchiveUtil.getFullTableName(request,
				ProjectContent.class);
		String zdbdbName = request.getParameter("zdbdbName");
		ProjectContentDao dao = new ProjectContentDao(tableName,zdbdbName);
		ProjectContent projectContent = getObject(request);
		try {
			dao.down(projectContent);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "项目内容类型下移失败！");
		}
	}
}
