package com.hr.dao;

import java.util.List;

import com.hr.bean.BasicRate;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;

public class BasicRateDao extends BaseDao<BasicRate> {
	public String tableName ;
	public static final String[][] tableField = {
		{"id", "numeric", "9,0", "0", "1"},
		{"cname", "varchar", "100", "0", "0"},
		{"sort_num", "numeric", "9,0", "0", "0"}
	};
	
	public BasicRateDao(Object pool, String tableName,String dbName) {
		super(tableName,tableField,dbName);
		this.tableName = tableName;
		createTable(dbName);
	}

	public BasicRateDao(String tableName,String dbName){
		super(tableName,tableField,dbName);
		this.tableName = tableName;
		createTable(dbName);
	}
	
	@Override
	protected void createTable(String dbName){
		if (!ifExistsTable(tableName,dbName)) {
			String createSQL = Constants.BuildCreateTabSql(tableName,
					tableField);
			String target = tableField[0][0] + " " + tableField[0][1] + "\\(" + tableField[0][2] + "\\)";
			String replace = target + " identity(1,1)";
			createSQL = createSQL.replaceFirst(target, replace);
			getBaseDataOP(dbName).ExecSql(createSQL);
		}
	}
	
	
	public void doCreate(BasicRate basicrate,String dbName){
		BasicRateDao brd = new BasicRateDao(tableName,dbName);
		int sort_num = brd.MAX_Sort_num(basicrate) + 1;
		String sql = "insert into " + tableName + "(cname, sort_num) values (" + 
	                  StringHelper.getFieldSql(basicrate.getCname()) + ", " +
	                  sort_num + ")" ;
		ExecSql(sql);
	}
	
	public void doDelete(int id){
		String sql = "delete from " + tableName + " where id = " + StringHelper.getFieldSql(id);
		ExecSql(sql);
	}
	
	public void doUpdate(BasicRate basicrate){
		String sql = "update " + tableName + " set cname = " + StringHelper.getFieldSql(basicrate.getCname()) +
				     " where id = " + StringHelper.getFieldSql(basicrate.getId())  ;
		ExecSql(sql);
	}
	
	public List<BasicRate> findAll(){
		String fields = "id, cname";
		String sql = "select " + fields + " from " + tableName + " order by sort_num";
		String[][] all = queryRowAndCol(sql);
		List<BasicRate> list = BeanHelper.stringArray2Object(fields, all, BasicRate.class, false);
		return list;
	}
	
	//判断cname是否已经存在
	public boolean ifCnameExist(BasicRate basicrate){
		boolean flag = false;
		String sql = "select * from " + tableName + " where cname = " + StringHelper.getFieldSql(basicrate.getCname()) ;
		if(queryRowNum(sql) != 0)
			flag = true;
		return flag;
	}
	
	//获取最大sort_num，不存在返回0
	public int MAX_Sort_num(BasicRate basicrate){
		String sql = "select sort_num from " + tableName + " order by sort_num desc";
		String[] str = queryRow(sql);
		int max = 0;
		if(str[0] == null)
			return 0;
		else
			max = Integer.parseInt(str[0]);
		return max;
	}
	
	//上移
	public void up(BasicRate basicrate){
		//获取选中利率的sort_num
		String sql1 = "select sort_num from " + tableName + " where id = " + StringHelper.getFieldSql(basicrate.getId());
		int num1 = Integer.parseInt(queryColumn(sql1)[0]);
		//获取选中利率的上一条利率的sort_num
		String sql2 = "select max(sort_num) from " + tableName + " where sort_num < " + num1;
		int num2 = Integer.parseInt(queryColumn(sql2)[0]);
		//获取上一条利率的sort_num对应的id
		String sql3 = "select id from " + tableName + " where sort_num = " + num2;
		int id2 = Integer.parseInt(queryColumn(sql3)[0]);
		//交换两条记录的sort_num
		String sql4 = "update " + tableName + " set sort_num = " + num2 + " where id = " + StringHelper.getFieldSql(basicrate.getId()) +
				      " update " + tableName + " set sort_num = " + num1 + " where id = " + id2;
		ExecSql(sql4);
	}
	
	//下移
	public void down(BasicRate basicrate){
		String sql1 = "select sort_num from " + tableName + " where id = " + StringHelper.getFieldSql(basicrate.getId());
		int num1 = Integer.parseInt(queryColumn(sql1)[0]);
		String sql2 = "select min(sort_num) from " + tableName + " where sort_num > " + num1;
		int num2 = Integer.parseInt(queryColumn(sql2)[0]);
		String sql3 = "select id from " + tableName + " where sort_num = " + num2;
		int id2 = Integer.parseInt(queryColumn(sql3)[0]);
		String sql4 = "update " + tableName + " set sort_num = " + num2 + " where id = " + StringHelper.getFieldSql(basicrate.getId()) +
				      " update " + tableName + " set sort_num = " + num1 + " where id = " + id2;
		ExecSql(sql4);
	}
	
}
