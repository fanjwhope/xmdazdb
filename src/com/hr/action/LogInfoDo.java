package com.hr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.LogInfo;
import com.hr.dao.LogInfoDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.info.dbBean;
import com.hr.util.Log;

public class LogInfoDo {
	private LogInfo getObject(HttpServletRequest request){
		LogInfo logInfo = null;
		try {
			logInfo = (LogInfo)BeanHelper.getValuesByRequest(request, LogInfo.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return logInfo;
	}
	
	//获取登录日志列表
	public void findAll(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, LogInfo.class);
		String dbName = request.getParameter("zdbdbName");
		LogInfoDao dao = new LogInfoDao(tableName,dbName);
		LogInfo logInfo = getObject(request);
		PagingBean pagingBean = new PagingBean(request);
		String state = request.getParameter("state");
		String user = request.getParameter("user");
		try{
			if("suc".equals(state)){  //登录成功选中
				logInfo.setFinish(0);
				if(user != null)      //选择指定用户
					logInfo.setAccount(user);
			}
			else if("fail".equals(state)){  //登录失败选中
				logInfo.setFinish(1);
				if(user != null)      //选择指定用户
					logInfo.setAccount(user);
			}
			else if(user != null)     //指定用户全部记录
				logInfo.setAccount(user);
			else
				;
			List<LogInfo> all = dao.queryByVO(logInfo, pagingBean);
			JsonUtil.writeJsonGrid(response, all, pagingBean);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "登录信息列表查询失败！");
		}
	}
	
	//获取登录用户列表
	public void findAllUser(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, LogInfo.class);
		String dbName = request.getParameter("zdbdbName");
		LogInfoDao dao = new LogInfoDao(tableName,dbName);
		try{
			List<LogInfo> all = dao.getAllUser();
			JsonUtil.writeJsonObject(response, all);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "登录用户列表查询失败！");
		}
	}
	
}
