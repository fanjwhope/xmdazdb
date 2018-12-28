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
	
	//��ȡ��¼��־�б�
	public void findAll(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, LogInfo.class);
		String dbName = request.getParameter("zdbdbName");
		LogInfoDao dao = new LogInfoDao(tableName,dbName);
		LogInfo logInfo = getObject(request);
		PagingBean pagingBean = new PagingBean(request);
		String state = request.getParameter("state");
		String user = request.getParameter("user");
		try{
			if("suc".equals(state)){  //��¼�ɹ�ѡ��
				logInfo.setFinish(0);
				if(user != null)      //ѡ��ָ���û�
					logInfo.setAccount(user);
			}
			else if("fail".equals(state)){  //��¼ʧ��ѡ��
				logInfo.setFinish(1);
				if(user != null)      //ѡ��ָ���û�
					logInfo.setAccount(user);
			}
			else if(user != null)     //ָ���û�ȫ����¼
				logInfo.setAccount(user);
			else
				;
			List<LogInfo> all = dao.queryByVO(logInfo, pagingBean);
			JsonUtil.writeJsonGrid(response, all, pagingBean);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "��¼��Ϣ�б��ѯʧ�ܣ�");
		}
	}
	
	//��ȡ��¼�û��б�
	public void findAllUser(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, LogInfo.class);
		String dbName = request.getParameter("zdbdbName");
		LogInfoDao dao = new LogInfoDao(tableName,dbName);
		try{
			List<LogInfo> all = dao.getAllUser();
			JsonUtil.writeJsonObject(response, all);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "��¼�û��б��ѯʧ�ܣ�");
		}
	}
	
}
