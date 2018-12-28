package com.hr.dao;

import java.util.List;

import com.hr.bean.Biz;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.StringHelper;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.HrDB;

public class BizDao extends HrDB{
	public String tableName;
	public static final String[][] tableField = {
		{"biz", "varchar", "20", "0", "0"}
	};
	
	public BizDao(Object pool, String tableName) {
		super(pool);
		this.tableName = tableName;
		createTable();
	}

	public BizDao(String tableName){
		super(ConnectionPool.getPool());
		this.tableName = tableName;
		createTable();
	}
	
	private void createTable(){
		ArchiveUtil.createTable(tableName, tableField);
	}
	
	public static BaseDataOP getBaseDataOP() {
		return new BaseDataOP(ConnectionPool.getPool());
	}
	
	public void doCreate(Biz biz){
		String sql = "insert into " + tableName + " (biz) values (" + StringHelper.getFieldSql(biz.getBiz()) + ")";
		ExecSql(sql);
	}
	
	public void doDelete(String biz){
		String sql = "delete from " + tableName + " where biz = " + StringHelper.getFieldSql(biz);
		ExecSql(sql);
	}
	
	/**
	 * 更新币种名称
	 * @param biz1：修改后的币种名
	 * @param biz2：原币种名
	 */
	public void doUpdate(String biz1, String biz2){
		String sql = "update " + tableName + " set biz = " + StringHelper.getFieldSql(biz1) +
				     " where biz = " + StringHelper.getFieldSql(biz2);
		ExecSql(sql);
	}
	
	public List<Biz> findAll(){
		String sql = "select biz from " + tableName;
		String[][] all = queryRowAndCol(sql);
		List<Biz> list = BeanHelper.stringArray2Object("biz", all, Biz.class, false);
		return list;
	}
	
	//判断biz是否已经存在
	public boolean ifBizExist(String biz){
		boolean flag = false;
		String sql = "select * from " + tableName + " where biz = " + StringHelper.getFieldSql(biz) ;
		if(queryRowNum(sql) != 0)
			flag = true;
		return flag;
	}
}
