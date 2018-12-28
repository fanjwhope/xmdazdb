package com.hr.dao;

import java.util.List;

import com.hr.bean.ZdbStandardDetail;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;

public class ZdbStandardDetailDao extends BaseDao<ZdbStandardDetail> {
	private String tableName;
	private static String[][] tableField = {
			{ "dalx", Constants.fieldTypeNumber, "10", "0", "1" },
			{ "damc", "varchar", "200", "0", "0" },
			{ "days", "varchar", "4", "0", "0" },
			{ "dabz", "varchar", "200", "0", "0" },
			{ "sort_num", Constants.fieldTypeNumber, "10", "0", "0" }, };

	public ZdbStandardDetailDao(String tableName,String dbName) {
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

	public List<ZdbStandardDetail> getAll() {
		String sql = "select * from " + tableName + " order by sort_num";
		String[][] all = queryRowAndCol(sql);
		List<ZdbStandardDetail> list = BeanHelper.stringArray2Object(
				"dalx,damc,days,dabz,sort_num", all, ZdbStandardDetail.class,
				false);
		return list;
	}

	public void moveUp(String dalx) {
		// 获取选中利率的sort_num
		String sql1 = "select sort_num from " + tableName + " where dalx = " + dalx;
		int num1 = Integer.parseInt(queryColumn(sql1)[0]);
		// 获取选中利率的上一条利率的sort_num
		String sql2 = "select max(sort_num) from " + tableName
				+ " where sort_num < " + num1;
		int num2 = Integer.parseInt(queryColumn(sql2)[0]);
		// 获取上一条利率的sort_num对应的dalx
		String sql3 = "select dalx from " + tableName + " where sort_num = "
				+ num2;
		int dalx2 = Integer.parseInt(queryColumn(sql3)[0]);
		// 交换两条记录的sort_num
		String sql4 = "update " + tableName + " set sort_num = " + num2
				+ " where dalx = " + dalx + " update " + tableName
				+ " set sort_num = " + num1 + " where dalx = " + dalx2;
		ExecSql(sql4);
	}

	public void moveDown(String dalx) {
		String sql1 = "select sort_num from " + tableName + " where dalx = " + dalx;
		int num1 = Integer.parseInt(queryColumn(sql1)[0]);
		String sql2 = "select min(sort_num) from " + tableName
				+ " where sort_num > " + num1;
		int num2 = Integer.parseInt(queryColumn(sql2)[0]);
		String sql3 = "select dalx from " + tableName + " where sort_num = "
				+ num2;
		int dalx2 = Integer.parseInt(queryColumn(sql3)[0]);
		String sql4 = "update " + tableName + " set sort_num = " + num2
				+ " where dalx = " + dalx + " update " + tableName
				+ " set sort_num = " + num1 + " where dalx = " + dalx2;
		ExecSql(sql4);
	}

	public void delete(String dalx) {
		String sql = "delete from " + tableName + " where dalx=" + dalx;
		ExecSql(sql);
	}

	public void add(ZdbStandardDetail obj) {
		String sql = "insert into " + tableName + "(damc,days,dabz,sort_num) values("
				+ "'" + obj.getDamc() + "','',''," + obj.getSort_num() + ")";
		ExecSql(sql);
	}

	public String getMaxDalx() {
		String sql = "select max(dalx) from " + tableName;
		String result = queryRowAndCol(sql)[0][0];
		return "" == result ? "0" : result;
	}

	public String getMaxsort_num() {
		String sql = "select max(sort_num) from " + tableName;
		String result = queryRowAndCol(sql)[0][0];
		return "" == result ? "0" : result;
	}

	public void update(String dalx, String damc) {
		String sql = "update " + tableName + " set damc='" + damc + "' where dalx="
				+ dalx;
		ExecSql(sql);
	}
}
