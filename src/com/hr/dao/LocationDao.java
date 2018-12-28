package com.hr.dao;

import java.util.List;

import com.hr.bean.Location;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.HrDB;
import com.hr.util.Log;

public class LocationDao extends BaseDao{
	public String tableName;
	public static final String[][] tableField = {
		{"path", "varchar", "200", "0", "1"},
		{"mmgz", "varchar", "40", "0", "0"},
		{"flag", "varchar", "1", "0", "0"}
	};
	public LocationDao(String tableName,String dbName) {
		super(tableName,tableField,dbName);
		this.tableName=tableName;
		//ʹ�ù��췽������ xmwj��
		createTable(tableName,tableField,dbName);
	}
	
	
	public static BaseDataOP getBaseDataOP(String dbName) {
		return BaseDao.getBaseDataOP(dbName);
	}
	
	//�ж�����ӵ�·���Ƿ��Ѵ���
	public boolean ifExistPath(String path){
		boolean bool = false;
		String sql = "select * from " + tableName + " where path = '" + path + "'" ;
		if(queryRowNum(sql) != 0)
			bool = true;
		return bool;
	}
	
	//�ж���·��path2(��ͬ�ھ�·��path1)�Ƿ����
	public boolean ifExistPath(String path1, String path2){
		boolean bool = false;
		String sql = "select * from " + tableName + " where path != '" + path1 + "' and path = '" + path2 + "'" ;
		if(queryRowNum(sql) != 0)
			bool = true;
		return bool;
	}
	
	//�жϱ��'y'�Ƿ����
	public boolean ifExistFlag(){
		boolean bool = false;
		String sql = "select * from " + tableName + " where flag = 'y'" ;
		if(queryRowNum(sql) != 0)
			bool = true;
		return bool;
	}
	
	public void doCreate(Location location){
		String sql = "insert into " + tableName + " (path, mmgz, flag) values (" + 
					  StringHelper.getFieldSql(location.getPath()) + ", '', " + 
					  StringHelper.getFieldSql(location.getFlag()) + ")";
		ExecSql(sql);
	}
	
	public void doDelete(Location location){
		String sql = "delete from " + tableName + " where path = " + StringHelper.getFieldSql(location.getPath());
		ExecSql(sql);
	}
	
	/**
	 * ����·��
	 * @param path1��ԭ·��
	 * @param flag1: ԭ���
	 * @param path2����·��
	 * @param flag2���±��
	 */
	public void doUpdate(String path1, String path2, String flag2){
		String sql = "update " + tableName + " set path = '" + path2 + "', flag = '" + flag2 + "' where path = '" +
					 path1 + "'";
		ExecSql(sql);
	}
	
	public List<Location> findAll(){
		String sql = "select path, flag from " + tableName ;
		String[][] all = queryRowAndCol(sql);
		List<Location> list = BeanHelper.stringArray2Object("path, flag", all, Location.class, false);
		return list;
	}
	
	public String getPath(){
		String sql = "select path from " + tableName + " where flag = 'y'";
		String path = queryRow(sql)[0];
		return path;
	}
	
}
