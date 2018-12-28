package com.hr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hr.bean.ArchiveDepart;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;
import com.ibm.sslight.tools.mainExit;

public class ArchiveDepartDao extends BaseDao<ArchiveDepart>{
	private String tableName;
	public static String[][] tableField = {
		{ "id",        Constants.fieldTypeNumber, "10,0", "0", "0" },
		{ "lsh",        Constants.fieldTypeNumber, "10,0", "0", "0" },
		{ "sort_num",   Constants.fieldTypeNumber, "16,0", "0", "0" },
		{ "damc",       "varchar", "500", "0", "0" },
		{ "dalx",        "varchar", "10", "1", "0" },
		{ "days",        "varchar", "4", "1", "0" },
		{ "dabz",        "varchar", "100", "1", "0" },
		{ "dwh",         "varchar", "10", "1", "0" },
		{ "flag",        Constants.fieldTypeNumber, "10,0", "1", "0" },
		{ "archivetime", "varchar", "100", "1", "0" },
		{ "createtime",  "varchar", "20", "1", "0" },
		{ "modifyTime",  "varchar", "20", "1", "0" },
		{ "by1",         "varchar", "30", "1", "0" },
		{ "by2",         "varchar", "200", "1", "0" },
		{ "isnotstd",    Constants.fieldTypeNumber, "5,0", "1", "0" }
	};
	
	public static String[][] tableFieldZY = {
		{ "id",        Constants.fieldTypeNumber, "10,0", "0", "0" },
		{ "lsh",        Constants.fieldTypeNumber, "10,0", "0", "0" },
		{ "sort_num",   Constants.fieldTypeNumber, "16,0", "0", "0" },
		{"damc",       "varchar", "500", "0", "0" },
		{ "dalx",        "varchar", "10", "1", "0" },
		{ "days",        "varchar", "4", "1", "0" },
		{ "dabz",        "varchar", "100", "1", "0" },
		{ "dwh",         "varchar", "10", "1", "0" }
	};
	
	
	public ArchiveDepartDao(String tableName,String dbName) {
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
		
	public void del(long lsh,long sort_num){
		String sql="delete  from "+tableName+" where lsh="+StringHelper.getFieldSql(lsh)
				  +"and sort_num="+StringHelper.getFieldSql(sort_num);
		System.out.println(sql);
		ExecSql(sql);
	}
	public void  delAll(long lsh){
		String sql="delete  from "+tableName+" where lsh="+StringHelper.getFieldSql(lsh);
		ExecSql(sql);
	}
	
	public ArchiveDepart  getOne(String lsh,String sort_num){
		String fieldNames=getFields(tableFieldZY);
		String  sql="select "+fieldNames+" from "+tableName+" where lsh="+StringHelper.getFieldSql(lsh)
				  +"and  sort_num="+StringHelper.getFieldSql(sort_num);
		ArchiveDepart archiveDepart=BeanHelper.stringArray2Object(fieldNames, queryLine(sql), ArchiveDepart.class, false);
		return archiveDepart;
	}
	
	public int getSortNum(String lsh,String sort_num){
		String sql="select max(sort_num) from "+tableName+" where lsh="+StringHelper.getFieldSql(lsh);
		String num=querySingleData(sql);
		int sortNum=1;
		if(!Validation.isEmpty(num)){
			sortNum=Integer.parseInt(num)+1;
		}
		return sortNum;
	}
	
	public List<ArchiveDepart> getList(String lsh){
		String fieldNames=getFields(tableFieldZY);
	    Map<String, String> map=new HashMap<String, String>();
	    map.put("sort_num", "sortNum");
		String tabs=tableName;
		StringBuffer whereSql=new StringBuffer(" where  lsh="+StringHelper.getFieldSql(lsh));
		String orderBy=" order  by lsh,sort_num  ";
		String[][] obj = null;
		obj = queryRowAndCol("select distinct " + fieldNames + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		System.out.println("select distinct " + fieldNames + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		 List<ArchiveDepart> list=BeanHelper.stringArray2Object(fieldNames, obj, ArchiveDepart.class, false,map);
		 return list;
	}
	
	public int getRowNum(String lsh){
		String sql="select sort_num from "+tableName+" where lsh="+StringHelper.getFieldSql(lsh);
		int num=queryRowNum(sql);
		return num;
	}
	
	
	
	
	public  void writeJSMessage(String updateLSHS,String yjContentTab){
		String delSql="delete from "+tableName+" where lsh in ("+updateLSHS+")";
		String sql=" insert into "+tableName+"(lsh,sort_num,damc,dalx,days,dabz,dwh) "
		          +"select lsh,sort_num,damc,dalx,days,dabz,dwh  from "+yjContentTab+" where lsh in("+updateLSHS+")";
		ExecSql(delSql);
		ExecSql(sql);
	}
	
	@Override
	public void add(ArchiveDepart archiveDepart){
		String sql="insert into "+tableName+"( lsh, sort_num, damc, dalx, days, dabz, dwh )"
				+ " values('"+archiveDepart.getLsh()+"',"			
				+"'"+archiveDepart.getSortNum()+"',"
				+"'"+archiveDepart.getDamc()+"',"
				+"'"+archiveDepart.getDalx()+"',"
				+"'"+archiveDepart.getDays()+"',"
				+"'"+archiveDepart.getDabz()+"',"
				+"'"+archiveDepart.getDwh()+"')";
		System.out.println("insert:"+sql);
		ExecSql(sql);
	}
	
	public static void main(String[] args) {
		System.out.println(getFields(tableField));
	}
}
