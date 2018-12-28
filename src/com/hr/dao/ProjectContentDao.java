package com.hr.dao;

import java.util.List;

import com.hr.bean.ProjectContent;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.HrDB;

public class ProjectContentDao extends BaseDao<ProjectContent> {
	public String tableName ;
	public static final String[][] tableField = {
		{"cname", "varchar", "50", "0", "0"},
		{"ename", "varchar", "15", "0", "0"},
		{"sort_num", "numeric", "9,0", "0", "1"}
	};
	

	 public ProjectContentDao(String tableName,String dbName) {
			super(tableName,tableField,dbName);
			this.tableName=tableName;
			createTable(dbName);
		}
	
	private void createTable(){
		ArchiveUtil.createTable(tableName, tableField);
	}
	
	public static BaseDataOP getBaseDataOP() {
		return new BaseDataOP(ConnectionPool.getPool());
	}
	
	public void doCreate(ProjectContent projectcontent,String dbName){
		ProjectContentDao brd = new ProjectContentDao(tableName,dbName);
		int sort_num = brd.maxSort_num(projectcontent) + 1;
		String sql = "insert into " + tableName + "(cname, ename, sort_num) values (" + 
	                  StringHelper.getFieldSql(projectcontent.getCname()) + ", " +
	                  StringHelper.getFieldSql(projectcontent.getEname()) + ", " +
	                  sort_num + ")" ;
		ExecSql(sql);
	}
	
	public void doDelete(ProjectContent projectcontent){
		String sql = "delete from " + tableName + " where ename = " + StringHelper.getFieldSql(projectcontent.getEname());
		ExecSql(sql);
	}
	
	public void doUpdate(ProjectContent projectcontent){
		String sql = "update " + tableName + " set cname = " + StringHelper.getFieldSql(projectcontent.getCname()) +
				     " where ename = " + StringHelper.getFieldSql(projectcontent.getEname())  ;
		ExecSql(sql);
	}
	
	public List<ProjectContent> findAll(){
		String fields = "cname, ename";
		String sql = "select " + fields + " from " + tableName + " order by sort_num";
		String[][] all = queryRowAndCol(sql);
		List<ProjectContent> list = BeanHelper.stringArray2Object(fields, all, ProjectContent.class, false);
		return list;
	}
	
	public List<ProjectContent> findAll(String tab){
		String fields = "cname, ename";
		String sql = "select " + fields + " from " + tab + " order by sort_num";
		String[][] all = queryRowAndCol(sql);
		List<ProjectContent> list = BeanHelper.stringArray2Object(fields, all, ProjectContent.class, false);
		return list;
	}
	
	//判断cname或ename是否已经存在
	public boolean ifExist(ProjectContent projectcontent){
		boolean flag = false;
		String sql = "select * from " + tableName + " where cname = " + StringHelper.getFieldSql(projectcontent.getCname()) +
				     " or ename = " + StringHelper.getFieldSql(projectcontent.getEname());
		if(queryRowNum(sql) != 0)
			flag = true;
		return flag;
	}
	
	//判断cname是否已经存在
		public boolean ifNameExist(ProjectContent projectcontent){
			boolean flag = false;
			String sql = "select * from " + tableName + " where cname = " + StringHelper.getFieldSql(projectcontent.getCname());
			if(queryRowNum(sql) != 0)
				flag = true;
			return flag;
		}
	
	//获取最大sort_num，不存在返回0
	public int maxSort_num(ProjectContent projectcontent){
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
	public void up(ProjectContent projectcontent){
		String sql1 = "select sort_num from " + tableName + " where ename = " + StringHelper.getFieldSql(projectcontent.getEname());
		String sql2 = "select cname from " + tableName + " where ename = " + StringHelper.getFieldSql(projectcontent.getEname());
		int num1 = Integer.parseInt(queryColumn(sql1)[0]);
		String cname1 = queryColumn(sql2)[0];
		String sql3 = "select max(sort_num) from " + tableName + " where sort_num < " + num1;
		int num2 = Integer.parseInt(queryColumn(sql3)[0]);
		String sql4 = "select cname from " + tableName + " where sort_num = " + num2;
		String cname2 = queryColumn(sql4)[0];
		String sql5 = "select ename from " + tableName + " where sort_num = " + num2;
		String ename2 = queryColumn(sql5)[0];
		String sql6 = "update " + tableName + " set cname = '" + cname2 + "', ename = '" + ename2 + "' where sort_num = " + num1 +
					  " update " + tableName + " set cname = '" + cname1 + "', ename = " + 
					   StringHelper.getFieldSql(projectcontent.getEname()) + " where sort_num = " + num2;
		ExecSql(sql6);
	}
	
	//下移
	public void down(ProjectContent projectcontent){
		String sql1 = "select sort_num from " + tableName + " where ename = " + StringHelper.getFieldSql(projectcontent.getEname());
		String sql2 = "select cname from " + tableName + " where ename = " + StringHelper.getFieldSql(projectcontent.getEname());
		int num1 = Integer.parseInt(queryColumn(sql1)[0]);
		String cname1 = queryColumn(sql2)[0];
		String sql3 = "select min(sort_num) from " + tableName + " where sort_num > " + num1;
		int num2 = Integer.parseInt(queryColumn(sql3)[0]);
		String sql4 = "select cname from " + tableName + " where sort_num = " + num2;
		String cname2 = queryColumn(sql4)[0];
		String sql5 = "select ename from " + tableName + " where sort_num = " + num2;
		String ename2 = queryColumn(sql5)[0];
		String sql6 = "update " + tableName + " set cname = '" + cname2 + "', ename = '" + ename2 + "' where sort_num = " + num1 +
					  " update " + tableName + " set cname = '" + cname1 + "', ename = " + 
					   StringHelper.getFieldSql(projectcontent.getEname()) + " where sort_num = " + num2;
		ExecSql(sql6);
	}
}
