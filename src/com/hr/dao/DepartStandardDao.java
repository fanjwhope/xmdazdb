package com.hr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hr.bean.DepartStandard;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.PagingBean;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;
import com.hr.util.Log;

public class DepartStandardDao extends BaseDao<DepartStandard>{
    private String tableName;
    private static String[][] tableField = {
    	{ "id",         Constants.fieldTypeNumber, "10", "0", "1" },
    	{ "dalx",        "varchar", "10", "0", "0" },
    	{ "damc",        "varchar", "200", "0", "0" },
    	{ "days",         "varchar", "4", "1", "0" },
    	{ "dabz",         "varchar", "10", "1", "0" },
    	{ "sort_num",         Constants.fieldTypeNumber, "16,1", "1", "0" },
    };
    
    public DepartStandardDao(String tableName,String dbName) {
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
	
	
	public Double getMaxSort(){
		String sql="select  max(sort_num) from "+tableName;
		String num=querySingleData(sql);
		double sortNum=1.0;
		if(!Validation.isEmpty(num)){
			sortNum=Double.parseDouble(num)+1.0;
		}
		return sortNum;
	}
	
	public  boolean IsExitSort(String sort_num){
		boolean flag=false;
		String sql="select id from "+tableName+" where sort_num="+StringHelper.getFieldSql(sort_num);
		int num=queryRowNum(sql);
		if(num>0){
			flag=true;}
		return flag;
	} 
	
	//public  String 
	public  void  sort(String id,String sort_num){
		String sql="update "+tableName+" set sort_num="+sort_num+" where id="+StringHelper.getFieldSql(id);
		ExecSql(sql);
	}
	
	public  List<DepartStandard>  getList(PagingBean pagingBean){
		String fieldNames=getFields(tableField);
		String tabs=tableName;
	    Map<String, String> map=new HashMap<String, String>();
		map.put("sort_num", "sortNum");
		StringBuffer whereSql=new StringBuffer(" where  1=1 ");
		String orderBy=" order  by sort_num  ";
		String[][] obj = null;
		if (null != pagingBean) {
			obj = queryRowAndCol(pagingBean.getPageSize(),
					pagingBean.getCurrentPage(), fieldNames, "sort_num", tabs,
					whereSql.toString(), orderBy);
			pagingBean.setTotalRecords(getRowSum());
		} else {
			obj = queryRowAndCol("select " + fieldNames + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		}
		List<DepartStandard> list=BeanHelper.stringArray2Object(fieldNames, obj, DepartStandard.class, false,map);
		return list;
	}
	
	//规范sort_num，
	//输入有效，返回类似x.x的数据
	//输入无效，则返回空字符串
	public String Norm(String sort_num){
		if(sort_num.length() > 0 && sort_num.matches("^\\d+\\.\\d+$")){
			//if(sort_num.matches("^\\d\\.\\d$"));    //匹配 数字.数字  。双斜杠前一个用于转义
		    if(sort_num.matches("^\\d+\\.\\d+$")); 	 
			else if(sort_num.matches("^\\d\\.$"))  //匹配 数字. ^\\d\\.$
				sort_num = sort_num + "0";
			else
				sort_num = "";
		}else if(sort_num.length() > 0){
			sort_num = sort_num + ".0";
		}
		return sort_num;
	}
}
