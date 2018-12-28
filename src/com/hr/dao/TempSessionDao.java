package com.hr.dao;

import com.hr.bean.TempSession;
import com.hr.global.util.StringHelper;

public class TempSessionDao extends BaseDao<TempSession> {
	private String tableName;
	public static String[][] tableField = {
		{ "sessionID", "varchar", "50", "0", "1" },
		{ "UserName", "varchar", "50", "0", "0" },
		{ "dwh", "varchar", "50", "0", "0" },
		{ "priv", "varchar", "50", "1", "0" },
		{ "logonDate", "varchar", "50", "1", "0" },
		{ "other", "varchar", "200", "1", "0" },
		{ "dataBaseName", "varchar", "50", "1", "0" },
		{ "by2", "varchar", "500", "1", "0" },
		{ "by3", "varchar", "50", "1", "0" } };
	
	public TempSessionDao(String tableName,String dbName) {
		super(tableName,tableField,dbName);
		this.tableName=tableName;
		createTable(dbName);
	}
	
	public void delete(String userId, String departmentCode, String dataBaseName, String sessionId){
		String sql="delete from "+tableName+" where (userName="+StringHelper.getFieldSql(userId)
				+" and dwh="+StringHelper.getFieldSql(departmentCode)
				+" and dataBaseName="+StringHelper.getFieldSql(dataBaseName)
				+") or sessionID="+StringHelper.getFieldSql(sessionId);
		ExecSql(sql);
	}
	
}
