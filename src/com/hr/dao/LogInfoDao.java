package com.hr.dao;

import java.util.ArrayList;
import java.util.List;

import com.hr.bean.LogInfo;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.DateFunc;
import com.hr.global.util.PagingBean;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;

public class LogInfoDao extends BaseDao<LogInfo> {
	private String tableName;
	public static String[][] tableField = {
		{ "id", Constants.fieldTypeNumber, "18,0", "0", "1" },
		{ "ip", "varchar", "100", "0", "0" },
		{ "account", "varchar", "100", "0", "0" },
		{ "password", "varchar", "100", "1", "0" },
		{ "logYear", Constants.fieldTypeNumber, "4,0", "1", "0" },
		{ "logMonth", Constants.fieldTypeNumber, "4,0", "1", "0" },
		{ "logDay", Constants.fieldTypeNumber, "4,0", "1", "0" },
		{ "logHour", Constants.fieldTypeNumber, "4,0", "1", "0" },
		{ "logMinute", Constants.fieldTypeNumber, "4,0", "1", "0" },
		{ "logSecond", Constants.fieldTypeNumber, "4,0", "1", "0" },
		{ "accessFile", "varchar", "200", "1", "0" },
		{ "finish", Constants.fieldTypeNumber, "4,0", "1", "0" },//0代表登录成功，1代表账号错误，2代表密码错误
		{ "sessionId", "varchar", "150", "0", "0" },
		{ "lastTime", "varchar", "100", "0", "0" } };
	
	@Override
	protected void createTable(String dbName){
		if (!ifExistsTable(tableName,dbName)) {
			String createSQL = Constants.BuildCreateTabSql(tableName,
					tableField);
			String target=tableField[0][0]+" "+tableField[0][1]+"\\("+tableField[0][2]+"\\)";
			String replacement=target+" identity(1,1)";
			createSQL=createSQL.replaceFirst(target, replacement);
			getBaseDataOP(dbName).ExecSql(createSQL);
		}
	}
	
	public LogInfoDao(String tableName,String dbName) {
		super(tableName,tableField,dbName);
		this.tableName=tableName;
		createTable(dbName);
	}
	
	/**
	 * 更新用户最后在线时间
	 * @param sessionID
	 */
	public void updateLastTime(String sessionID){
		String now=DateFunc.getNowDateStr("yyyy-MM-dd HH:mm:ss");
		String sql="update "+tableName+" set LastTime="+StringHelper.getFieldSql(now)
				+" where SessionID="+StringHelper.getFieldSql(sessionID);
		ExecSql(sql);
	}
	
	public List<LogInfo> queryByVO(LogInfo logInfo, PagingBean pagingBean) {
		String fields=getFields();
		StringBuffer whereSql = new StringBuffer(" where 1=1");
		if (!Validation.isEmpty(logInfo.getAccount())) {
			whereSql.append(" and Account like '%" + logInfo.getAccount() + "%'");
		}
		if (null!=logInfo.getFinish()) {
			whereSql.append(" and Finish =" + logInfo.getFinish());
		}
		String orderBy = "";
		if (null != pagingBean && !Validation.isEmpty(pagingBean.getSort())) {
			orderBy = pagingBean.getSort();
			if (!Validation.isEmpty(pagingBean.getSortOrder())) {
				orderBy = pagingBean.getSort() + " "
						+ pagingBean.getSortOrder();
			}
		}
		if (!Validation.isEmpty(orderBy)) {
			orderBy = "order by " + orderBy;
		} else {
			orderBy = "order by id desc";
		}
		List<LogInfo> list = new ArrayList<LogInfo>();
		String[][] obj = null;
		if(null!=pagingBean){
			obj = queryRowAndCol(pagingBean.getPageSize(),
					pagingBean.getCurrentPage(), fields, "id", tableName, whereSql.toString(), orderBy);
			pagingBean.setTotalRecords(getRowSum());
		}else{
			obj = queryRowAndCol("select "+fields+" from "+tableName+" "+whereSql.toString()+" "+orderBy);
		}
		// 转换成列表
		//Map<String, String> keyChanges=new HashMap<String, String>();
		//keyChanges.put("Account", "account");
		list=BeanHelper.stringArray2Object(fields, obj, LogInfo.class, false);
		return list;
	}
	
	public List<LogInfo> getAllUser(){
		String sql = "select min(id), account from " + tableName + " group by account" ;
		String[][] user = queryRowAndCol(sql);
		List<LogInfo> list = BeanHelper.stringArray2Object("id,account", user, LogInfo.class, false);
		return list;
	}
}
