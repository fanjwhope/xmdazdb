package com.hr.dao;

import java.util.List;

import com.hr.bean.ProjectType;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.BuildSql;
import com.hr.global.util.Constants;
import com.hr.global.util.Validation;
import com.hr.global.util.BuildSql.SqlWithPlaceHolder;
import com.hr.global.util.PagingBean;
import com.hr.global.util.StringHelper;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.HrDB;

public class ProjectTypeDao extends  BaseDao<ProjectType> {
	
	private String tableName;
	 public static final String[][] tableField = {
			{ "id",           Constants.fieldTypeNumber,     "10", "0", "1" },
			{ "name",     "varchar",                          "100", "0", "0" },
			{ "flag",         "char",                           "1", "0", "0" },
			{ "sortNum",      Constants.fieldTypeNumber,      "9",  "0", "0" }
	        };
	 public ProjectTypeDao(String tableName,String dbName) {
			super(tableName,tableField,dbName);
			this.tableName=tableName;
			createTable(dbName);
		}
	 
	 @Override
		protected  void createTable(String dbName){
			if (!ifExistsTable(tableName,dbName)) {
				String createSQL = Constants.BuildCreateTabSql(tableName,
						tableField);
				String target=tableField[0][0]+" "+tableField[0][1]+"\\("+tableField[0][2]+"\\)";
				String replacement=target+" identity(1,1)";
				createSQL=createSQL.replaceFirst(target, replacement);
				getBaseDataOP(dbName).ExecSql(createSQL);
			}
		}
	
	public  void  add(ProjectType projectType){
		BuildSql buildSql=new BuildSql(projectType, tableName);
		SqlWithPlaceHolder sql=buildSql.getInsertSql(null, new String[]{"id"});
		ExecSql(sql.getSql(), sql.getStringFieldValues());
	}
	
	public  void  update(ProjectType projectType){
		BuildSql buildSql=new BuildSql(projectType, tableName);
		//需要更新的字段
		String[] fieldIncludes={"name"};
		SqlWithPlaceHolder sql=buildSql.getUpdateSql(fieldIncludes, null);
		ExecSql(sql.getSql(), sql.getStringFieldValues());
	}
	
	/**
	 * 获取最大的数字+1
	 * @return
	 */
	public int  getMaxSort(){
		String sql="select  max(sortNum) from "+tableName;
		String num=querySingleData(sql);
		int sortNum=1;
		if(!Validation.isEmpty(num)){
			sortNum=Integer.parseInt(num)+1;
		}
		return sortNum;
	}
	
	/**
	 * 排序
	 * @param sortNum
	 * @param id
	 * @return
	 */
	public  String  toSort(String sortNum,String id){
		String sql="update "+tableName+" set  sortNum="+StringHelper.getFieldSql(sortNum)+" where id="+StringHelper.getFieldSql(id);
		return sql;
	}
	
	public  void remove(String id){
		String  sql="update "+tableName+" set flag='0' where id="+StringHelper.getFieldSql(id);
		ExecSql(sql);
	}
	
	public List<ProjectType> getProjectType(){
		String fieldNames="id,name";
		String sql="select "+fieldNames+" from "+tableName+" where  flag='1'";
		String[][] obj=queryRowAndCol(sql);
		List<ProjectType> list=BeanHelper.stringArray2Object(fieldNames, obj, ProjectType.class, false);
		return list;
	}
	
	public  List<ProjectType> findAll(ProjectType projectType,PagingBean pagingBean){
		String fieldNames="id,name,sortNum,flag";
		StringBuffer  whereSql=new StringBuffer(" where  1=1 ");
		if(Validation.isEmpty(projectType.getFlag())){
			whereSql.append(" and flag="+StringHelper.getFieldSql(projectType.getFlag()));
		}
		String orderBy="";
		String tabs=tableName;
		String[][] obj = null;
		if (null != pagingBean) {
			obj = queryRowAndCol(pagingBean.getPageSize(),
					pagingBean.getCurrentPage(), fieldNames, "id", tabs,
					whereSql.toString(), orderBy);
			pagingBean.setTotalRecords(getRowSum());
		} else {
			obj = queryRowAndCol("select " + fieldNames + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		}
		List<ProjectType> list=BeanHelper.stringArray2Object(fieldNames, obj, ProjectType.class, false);
		return list;
	}
	
	public int isExit(String name){
		String sql="select id from "+tableName+" where name='"+name+"'";
		int val=queryRowNum(sql);
		return val;
	}

}
