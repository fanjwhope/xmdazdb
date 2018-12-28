package com.hr.dao;

import com.hr.global.util.BeanHelper;
import com.hr.global.util.BuildSql;
import com.hr.global.util.Constants;
import com.hr.global.util.GenericsUtils;
import com.hr.global.util.StringHelper;
import com.hr.global.util.BuildSql.SqlWithPlaceHolder;
import com.hr.util.BaseDataOP;
import com.hr.util.BaseDataOPFactory;
import com.hr.util.ConnectionPool;
import com.hr.util.HrDB;

public class BaseDao<T> {
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
	private String tableName;
	public String[][] tableField;
	private static BaseDataOP baseDataOP=(BaseDataOP) BaseDataOPFactory.getBaseDataOP("zdb",
			"C:\\DB\\db.xml");

	public BaseDao(String tableName, String[][] tableField, String dbName) {
		baseDataOP = (BaseDataOP) BaseDataOPFactory.getBaseDataOP(dbName,"C:\\DB\\db.xml");
		this.tableName = tableName;
		this.tableField = tableField;
	}
	

	public void add(T entity) {
		BuildSql buildSql = new BuildSql(entity, tableName);
		SqlWithPlaceHolder sql = buildSql.getInsertSql();
		baseDataOP.ExecSql(sql.getSql(), sql.getStringFieldValues());
	}

	/**
	 * 插入记录 fieldIncludes与fieldExcludes二者均不为null时，以fieldIncludes为主；二者均为null时，
	 * 表示操纵所有属性。
	 * 
	 * @param entity
	 * @param fieldIncludes
	 *            不插入的属性
	 * @param fieldExcludes
	 *            插入的属性
	 */
	public void add(T entity, String[] fieldIncludes, String[] fieldExcludes) {
		BuildSql buildSql = new BuildSql(entity, tableName);
		SqlWithPlaceHolder sql = buildSql.getInsertSql(fieldIncludes,
				fieldExcludes);
		baseDataOP.ExecSql(sql.getSql(), sql.getStringFieldValues());
	}

	/**
	 * 以主键为条件更新记录，适用于单主键
	 * 
	 * @param entity
	 */
	public void update(T entity) {
		BuildSql buildSql = new BuildSql(entity, tableName);
		SqlWithPlaceHolder sql = buildSql.getUpdateSql();
		baseDataOP.ExecSql(sql.getSql(), sql.getStringFieldValues());
	}

	/**
	 * 以主键为条件更新记录，适用于单主键
	 * fieldIncludes与fieldExcludes二者均不为null时，以fieldIncludes为主；二者均为null时
	 * ，表示操纵所有属性。
	 * 
	 * @param entity
	 * @param fieldIncludes
	 *            不更新的属性
	 * @param fieldExcludes
	 *            更新的属性
	 */
	public void update(T entity, String[] fieldIncludes, String[] fieldExcludes) {
		BuildSql buildSql = new BuildSql(entity, tableName);
		SqlWithPlaceHolder sql = buildSql.getUpdateSql(fieldIncludes,
				fieldExcludes);
		baseDataOP.ExecSql(sql.getSql(), sql.getStringFieldValues());
	}

	/**
	 * 通过Id查找记录，适用于单主键
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Object id) {
		String fields = getFields();
		String primaryKey = BuildSql.getPrimaryKeyName(entityClass);
		String sql = "select " + fields + " from " + tableName + " where "
				+ primaryKey + "=" + StringHelper.getFieldSql(id);
		String[][] arr1 = baseDataOP.queryRowAndCol(sql);
		String[] arr2 = null;
		if (null != arr1 && arr1.length > 0) {
			arr2 = arr1[0];
		} else {
			return null;
		}
		T en = BeanHelper.stringArray2Object(fields, arr2, entityClass, false);
		return en;
	}

	/**
	 * 通过Id删除记录，适用于单主键
	 * 
	 * @param id
	 * @return
	 */
	public void deleteById(Object id) {
		String primaryKey = BuildSql.getPrimaryKeyName(entityClass);
		String sql = "delete from " + tableName + " where " + primaryKey + "="
				+ StringHelper.getFieldSql(id);
		baseDataOP.ExecSql(sql);
	}

	public static BaseDataOP getBaseDataOP(String dbName) {
		baseDataOP = (BaseDataOP) BaseDataOPFactory.getBaseDataOP(dbName,
				"C:\\DB\\db.xml");
		return baseDataOP;
	}

	/**
	 * 获取表字段组成的字符串，以半角逗号分隔
	 * 
	 * @return
	 */
	public String getFields() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < tableField.length; i++) {
			result.append(tableField[i][0]);
			result.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	/**
	 * 如果表不存在则创建表
	 * 
	 * @param tableField
	 */
	protected void createTable(String dbName) {
		baseDataOP = (BaseDataOP) BaseDataOPFactory.getBaseDataOP(dbName,
				"C:\\DB\\db.xml");
		String[] tab = tableName.split("\\.");
		if (tab.length == 1) {
			if (!baseDataOP.ifExistsTab(tableName)) {
				String createSQL = Constants.BuildCreateTabSql(tableName,
						tableField);
				baseDataOP.ExecSql(createSQL);
			}
		} else if (tab.length == 3) {
			if (!baseDataOP.ifExistsTab(tab[2], tab[0])) {
				String createSQL = Constants.BuildCreateTabSql(tableName,
						tableField);
				baseDataOP.ExecSql(createSQL);
			}
		}
	}

	/**
	 * 获取表字段组成的字符串，以半角逗号分隔
	 * 
	 * @param tableField
	 * @return
	 */
	public static String getFields(String[][] tableField) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < tableField.length; i++) {
			result.append(tableField[i][0]);
			result.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	/**
	 * 如果表不存在则创建表
	 * 
	 * @param tableName
	 * @param tableField
	 */
	public static void createTable(String tableName, String[][] tableField,String dbName) {
		baseDataOP = (BaseDataOP) BaseDataOPFactory.getBaseDataOP(dbName,
				"C:\\DB\\db.xml");
		String[] tab = tableName.split("\\.");
		if (tab.length == 1) {
			if (!baseDataOP.ifExistsTab(tableName)) {
				String createSQL = Constants.BuildCreateTabSql(tableName,
						tableField);
				baseDataOP.ExecSql(createSQL);
			}
		} else if (tab.length == 3) {
			if (!baseDataOP.ifExistsTab(tab[2], tab[0])) {
				String createSQL = Constants.BuildCreateTabSql(tableName,
						tableField);
				baseDataOP.ExecSql(createSQL);
			}
		}
	}

	/**
	 * 判断数据库表是否存在
	 * 
	 * @param tableName
	 * @return
	 */
	public static boolean ifExistsTable(String tableName,String dbName) {
		baseDataOP = (BaseDataOP) BaseDataOPFactory.getBaseDataOP(dbName,
				"C:\\DB\\db.xml");
		String[] tab = tableName.split("\\.");
		boolean res = false;
		if (tab.length == 1) {
			return baseDataOP.ifExistsTab(tableName);
		} else if (tab.length == 3) {
			return baseDataOP.ifExistsTab(tab[2], tab[0]);
		}
		return res;
	}

	public boolean ExecSql(String sql) {
		return baseDataOP.ExecSql(sql);
	}
	
	public void ExecSql(String sql,String[] filedvalues) {
		 baseDataOP.ExecSql(sql,filedvalues);
	}

	public String[] queryLine(String sql) {
		return baseDataOP.queryLine(sql);
	}

	public String[][] queryRowAndCol(String sql) {
		return baseDataOP.queryRowAndCol(sql);
	}

	public String[][] queryRowAndCol(int size, int curpage,
			String fieldNames, String str1, String tabs, String wheresql,
			String orderBy) {
		return baseDataOP.queryRowAndCol(size, curpage, fieldNames, str1, tabs,
				wheresql, orderBy);
	}

	public int getRowSum(){
		return baseDataOP.getRowSum();
	}
	
	public int queryRowNum(String sql){
		return baseDataOP.queryRowNum(sql);
	}
	
	public String querySingleData(String sql){
		return baseDataOP.querySingleData(sql);
	}
	
	public String[] queryRow(String sql){
		return baseDataOP.queryRow(sql);
	}
	
	public String[] queryColumn(String sql){
		return baseDataOP.queryColumn(sql);
	}
	
	public static void main(String[] args) {
		System.out.println(ifExistsTable("zdb_yhxx","zdb"));
		String[][] result=new BaseDao("", new String[][]{}, "zdb").queryRowAndCol("select * from zdb_yhxx");
		for(String[] strs:result){
			for(String str:strs){
				System.out.println(str);
			}
		}
		String[][] result2=new BaseDao("", new String[][]{}, "zdbzy").queryRowAndCol("select * from zdb_yhxx");
		for(String[] strs:result2){
			for(String str:strs){
				System.err.println(str);
			}
		}
	}
}
