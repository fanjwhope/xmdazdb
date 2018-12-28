package com.hr.dao;

import java.util.List;

import com.hr.bean.BankNode;
import com.hr.bean.User;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.BuildSql;
import com.hr.global.util.Constants;
import com.hr.global.util.PagingBean;
import com.hr.global.util.BuildSql.SqlWithPlaceHolder;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;
import com.hr.util.BaseDataOP;
import com.hr.util.ConfigInfo;
import com.hr.util.ConnectionPool;
import com.hr.util.HrDB;

public class BankNodeDao extends HrDB{
	
	private String tableName;
    private static BaseDataOP baseDataOP=new BaseDataOP(ConnectionPool.getPool());
    public static final String[][] tableField = {
		{ "id",           "varchar",                      "20", "0", "1" },
		{ "bankName",     "varchar",                     "100", "0", "0" },
		{ "flag",         "char",                          "1", "0", "0" },
		{ "sortNum",      Constants.fieldTypeNumber,      "9", "0", "0" }
        };
    
    public  static final String[][] qxTableField={
		{ "dwh1",           "varchar",                      "10", "0", "0" },
		{ "dwh2",           "varchar",                      "10", "0", "0" },
		{ "zwbz",           "varchar",                      "1", "1", "0" },
		{ "cjwbbz",         "varchar",                      "1", "1", "0" },
		{ "fjbz",           "varchar",                      "1", "1", "0" },
		{ "ldpsbz",         "varchar",                      "1", "1", "0" },
		{ "cbjgbz",         "varchar",                      "1", "1", "0" },
		{ "startime",       "varchar",                      "10", "0", "0" },
		{ "qxsj",      Constants.fieldTypeNumber,           "9", "0", "0" },
		{ "mj",             "varchar",                      "10", "1", "0" },
		{ "authorizer",     "varchar",                      "10", "1", "0" }
        };
    
        
    public  static final String[][] qx_proTableField={
    	{ "id",             "varchar",                         "100",  "0", "1" },
    	{ "dwh1",           "varchar",                          "10",  "0", "0" },
    	{ "dwmc",           "varchar",                          "100", "0", "0" },
    	{ "Lsh",      Constants.fieldTypeNumber,                "10",  "0", "1" },
    	{ "xmmc",           "varchar",                          "100", "0", "0" },
    	{ "hth",            "varchar",                          "100", "0", "0" },
    	{ "startime",       "varchar",                          "10",  "0", "0" },
    	{ "qxsj",           "varchar",                          "10",  "0", "0" },
    	{ "by1",            "varchar",                          "100", "0", "0" },
    	{ "by2",            "varchar",                          "100", "0", "0" },
    	{"authorizer",       "varchar",                          "10","1","0"}
        };
       
        
	public BankNodeDao(String tableName) {
		super(ConnectionPool.getPool());
		this.tableName=tableName;
		ArchiveUtil.createTable(tableName,tableField);
	}
	
	public BankNodeDao(Object pool, String tableName) {
		super(pool);
		this.tableName=tableName;
		ArchiveUtil.createTable(tableName,tableField);
	}
	
	public static BaseDataOP getBaseDataOP() {
		return baseDataOP;
	}
	
	public void add(BankNode bankNode){
		BuildSql  buildSql=new BuildSql(bankNode, tableName);
		SqlWithPlaceHolder  sql=buildSql.getInsertSql();
		ExecSql(sql.getSql(), sql.getStringFieldValues());
		
		//建库并插入system用户  多库时使用
		/*String createDataBaseSql="create database "+bankNode.getId();
		ExecSql(createDataBaseSql);
		String schema=ConfigInfo.getVal("schema");
		String table=BuildSql.getTableName(User.class);
		String userTableName=bankNode.getId()+"."+schema+"."+table;
		User systemUser=new User("系统管理员", "system", "8888", "Y100000000", 0, null);
		new UserDao(userTableName).add(systemUser);*/
		
		//建表     节点名称_表名    单库使用
		String table=BuildSql.getTableName(User.class);
		String userTableName=bankNode.getId().toUpperCase()+"_"+table;
		User systemUser=new User("系统管理员", "system", "8888", "Y100000000", 0, "0",null);
		new UserDao(userTableName,"zdb").add(systemUser);
		
		//新建默认权限表
		String qxTab=bankNode.getId()+"_wsda_qx2";
		ArchiveUtil.createTable(qxTab,qxTableField);
		
		String  qx_proTab=bankNode.getId()+"_wsda_qx_pro";
		ArchiveUtil.createTable(qx_proTab,qx_proTableField);
	}
	
	public  void update(BankNode bankNode){
		BuildSql  buildSql=new BuildSql(bankNode, tableName);
		String[] fieldIncludes={"bankName"};
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
	
	public  void  isAvailable(String id,String flag){
		String sql="update "+tableName+" set flag="+StringHelper.getFieldSql(flag)+" where id="+StringHelper.getFieldSql(id);
		ExecSql(sql);
		
	}
	
	public  BankNode  getOne(String id){
		String keys="id,bankName,sortNum,flag";
		String sql="select  "+keys+" from "+tableName+" where id="+StringHelper.getFieldSql(id);
		BankNode bn=BeanHelper.stringArray2Object(keys, queryLine(sql), BankNode.class, false);
		return bn;
	}
	
	public  int  getSortNum(){
		String  sql="select  max(sortNum) from "+tableName;
		String  num=querySingleData(sql);
		int sortNum=1;
		if(!Validation.isEmpty(num)){
			sortNum=Integer.parseInt(num)+1;
		}
		return sortNum;
	}
	
	public User getgetNodeAdmin(String nodeId){
		String fields=getFields(UserDao.tableField);
		String  sql="select "+fields+" from "+nodeId+"_YHXX "+" where dwmc='系统管理员'";
		String[] obj=queryLine(sql);
		User user=BeanHelper.stringArray2Object(fields, obj, User.class, false);
		return user;
	}
	
	public void  updateNodeAdmin(String nodeId,String pwd){
		String sql="update "+nodeId+"_YHXX "+" set userpwd='"+pwd+"'"+" where dwmc='系统管理员'";
		ExecSql(sql);
	}
	
	public static void main(String[] args) {
		//BankNodeDao bd=new BankNodeDao("runAdmin.dbo.bankNode");
		//int s=bd.getSortNum();
		//System.out.println(s);
		
		/*String bankNode="HUBEIBANK";
		String qxTab=bankNode+"_wsda_qx2";
		ArchiveUtil.createTable(qxTab,qxTableField);
		
		String  qx_proTab=bankNode+"_wsda_qx_pro";
		ArchiveUtil.createTable(qx_proTab,qx_proTableField);*/
	}
	
	public int isExist(String id){
		String  sql="select id from "+tableName+" where id="+StringHelper.getFieldSql(id);
		int a=queryRowNum(sql);
		return a;
		
	}
	
	public List<BankNode>  getAllBankNode(){
		String fieldNames="id,bankName";
		String[][] obj = queryRowAndCol("select " + fieldNames + " from " + tableName + " where flag='1' order by sortNum ");
		List<BankNode> list=BeanHelper.stringArray2Object(fieldNames, obj, BankNode.class, false);
		return list;
	}
	
	public  List<BankNode>  findAll(BankNode bankNode,PagingBean pagingBean){
		String fieldNames="id,bankName,sortNum,flag";
		StringBuffer  whereSql=new StringBuffer(" where 1=1");
		if(!Validation.isEmpty(bankNode.getFlag())){
			whereSql.append(" and flag="+StringHelper.getFieldSql(bankNode.getFlag()));
		}
		String orderBy="  order  by  sortNum ";
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
		List<BankNode> list=BeanHelper.stringArray2Object(fieldNames, obj, BankNode.class, false);
		return list;
	}
	
	
	/**
	 * 获取表字段组成的字符串，以半角逗号分隔
	 * @return
	 */
	public String getFields(String[][] tableFields) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < tableFields.length; i++) {
			result.append(tableFields[i][0]);
			result.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

}
