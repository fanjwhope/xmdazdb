package com.hr.dao;

import java.util.List;

import com.hr.bean.Biz;
import com.hr.bean.ZdbStandard;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;

public class ZdbStandardDao extends BaseDao<ZdbStandard> {

	private String tableName;
	private static String[][] tableField = {
			{ "id", Constants.fieldTypeNumber, "10", "0", "1" },
			{ "spnr", "varchar", "10", "0", "0" },
			{ "tabindex", Constants.fieldTypeNumber, "10", "0", "1" },
			{ "test", Constants.fieldTypeNumber, "10", "0", "1" }, };

	public ZdbStandardDao(String tableName,String dbName) {
		super(tableName, tableField,dbName);
		this.tableName = tableName;
		createTable(dbName);
	}

	@Override
	protected void createTable(String dbName) {
		if (!ifExistsTable(tableName,dbName)) {
			String createSQL = Constants.BuildCreateTabSql(tableName,
					tableField);
			String target = tableField[0][0] + " " + tableField[0][1] + "\\("
					+ tableField[0][2] + "\\)";
			String replacement = target + " identity(1,1)";
			createSQL = createSQL.replaceFirst(target, replacement);
			getBaseDataOP(dbName).ExecSql(createSQL);
		}
	}

	public List<ZdbStandard> getAll() {
		String sql = "select * from " + tableName + " order by tabindex";
		String[][] all = queryRowAndCol(sql);
		List<ZdbStandard> list = BeanHelper.stringArray2Object(
				"id,spnr,tabindex,test", all, ZdbStandard.class, false);
		return list;
	}

	public void moveUp(String id) {
		// 获取选中利率的tabindex
		String sql1 = "select tabindex from " + tableName + " where id = " + id;
		int num1 = Integer.parseInt(queryColumn(sql1)[0]);
		// 获取选中利率的上一条利率的tabindex
		String sql2 = "select max(tabindex) from " + tableName
				+ " where tabindex < " + num1;
		int num2 = Integer.parseInt(queryColumn(sql2)[0]);
		// 获取上一条利率的tabindex对应的id
		String sql3 = "select id from " + tableName + " where tabindex = "
				+ num2;
		int id2 = Integer.parseInt(queryColumn(sql3)[0]);
		// 交换两条记录的tabindex
		String sql4 = "update " + tableName + " set tabindex = " + num2
				+ " where id = " + id + " update " + tableName
				+ " set tabindex = " + num1 + " where id = " + id2;
		ExecSql(sql4);
	}

	public void moveDown(String id) {
		String sql1 = "select tabindex from " + tableName + " where id = " + id;
		int num1 = Integer.parseInt(queryColumn(sql1)[0]);
		String sql2 = "select min(tabindex) from " + tableName
				+ " where tabindex > " + num1;
		int num2 = Integer.parseInt(queryColumn(sql2)[0]);
		String sql3 = "select id from " + tableName + " where tabindex = "
				+ num2;
		int id2 = Integer.parseInt(queryColumn(sql3)[0]);
		String sql4 = "update " + tableName + " set tabindex = " + num2
				+ " where id = " + id + " update " + tableName
				+ " set tabindex = " + num1 + " where id = " + id2;
		ExecSql(sql4);
	}

	public void delete(String id) {
		String sql = "delete from " + tableName + " where id=" + id;
		ExecSql(sql);
	}

	public void add(ZdbStandard obj) {
		String sql = "insert into " + tableName
				+ "(spnr,tabindex,test) values('" + obj.getSpnr() + "',"
				+ obj.getTabindex() + "," + obj.getTest() + ")";
		ExecSql(sql);
	}

	public String getMaxTabIndex() {
		String sql = "select max(tabindex) from " + tableName;
		String result=queryRowAndCol(sql)[0][0];
		return ""==result?"0":result;
	}
	
	public void update(String id,String spnr){
		String sql="update "+tableName+" set spnr='"+spnr+"' where id="+id;
		ExecSql(sql);
	}

}
