package com.hr.dao;

import java.util.List;

import com.hr.bean.Xmwj;
import com.hr.bean.XmwjYJ;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;

public class XmwjYJDao extends BaseDao<XmwjYJ> {
	private String tableName;
	public static String[][] tableField = {
		{ "id",         Constants.fieldTypeNumber, "10", "0", "1" },
		{ "lsh",        Constants.fieldTypeNumber, "10", "0", "0" },
		{ "yjflag",     "varchar",  "1", "0", "0" },
		{ "yjdw",       "varchar", "10", "0", "0" },
		{ "yjr",        "varchar", "50", "1", "0" },
		{ "yjy",        "varchar",  "4", "1", "0" },
		{ "yjm",        "varchar",  "2", "1", "0" },
		{ "yjd",        "varchar",  "2", "1", "0" },
		{ "jsdw",       "varchar", "10", "1", "0" },
		{ "jsr",        "varchar", "50", "1", "0" },
		{ "jsy",        "varchar",  "4", "1", "0" },
		{ "jsm",        "varchar",  "2", "1", "0" },
		{ "jsd",        "varchar",  "2", "1", "0" }
	};
	
	
	public XmwjYJDao(String tableName,String dbName) {
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
	
	public List<XmwjYJ> getList(XmwjYJ xmwjYJ){
		String  fieldNames=getFields(tableField);
		String tabs=tableName;
		StringBuffer whereSql=new StringBuffer(" where  1=1 ");
		String  orderBy = "order by id ";
		if(!Validation.isEmpty(xmwjYJ.getJsdw())){
			whereSql.append("");
		}
		String[][] obj = null;
			obj = queryRowAndCol("select " + fieldNames + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		List<XmwjYJ> list=BeanHelper.stringArray2Object(fieldNames, obj, XmwjYJ.class, false);
		return list;
		
	}
	
	
	
	public List<XmwjYJ> getListToReceive(XmwjYJ xmwjYJ){
		String  queryFields=" distinct yjdw,yjr,yjy,yjm,yjd";
		String fieldNames ="yjdw,yjr,yjy,yjm,yjd";
		String tabs=tableName;
		StringBuffer whereSql=new StringBuffer(" where yjflag='1'  ");
		String  orderBy = "order by yjy,yjm,yjd ";
		if(!Validation.isEmpty(xmwjYJ.getJsdw())){
			whereSql.append(" and jsdw="+StringHelper.getFieldSql(xmwjYJ.getJsdw()));
		}
		String[][] obj = null;
			obj = queryRowAndCol("select " + queryFields + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		List<XmwjYJ> list=BeanHelper.stringArray2Object(fieldNames, obj, XmwjYJ.class, false);
		return list;
	}
	
	/**
	 * 根据jsdw、yjdw查询所有移交给他的lsh信息
	 * @param XmwjYJ
	 * @return
	 */
	public  String[]  getLSHbYXmwjYJ(String yjdw,String jsdw){
		String sql="select lsh from "+tableName+" where yjdw ="
	             +StringHelper.getFieldSql(yjdw)
	             +" and jsdw="+StringHelper.getFieldSql(jsdw)+" and yjflag='1'";
		String[] lsh=queryColumn(sql);
		if(lsh.length>0&&lsh[0]!=null){
			return lsh;
		}else{
			return null;
		}
	}
	
	public void  updateFlag(XmwjYJ xmwjYJ,String updateLSHs){
		String sql="update "+tableName+" set  jsr="+StringHelper.getFieldSql(xmwjYJ.getJsr())
				 +",jsy="+StringHelper.getFieldSql(xmwjYJ.getJsy())
				 +",jsm="+StringHelper.getFieldSql(xmwjYJ.getJsm())
				 +",jsd="+StringHelper.getFieldSql(xmwjYJ.getJsd())
				 +" where yjflag='1' and lsh in("+updateLSHs+")";
		ExecSql(sql);
	}
	
	public void  updateFlag(String updateLSHs,String flag,XmwjYJ xmwjYJ){
		String sql="update "+tableName+" set yjflag='"+flag+"'"
				   +" ,jsr="+StringHelper.getFieldSql(xmwjYJ.getJsr())
				   +" ,jsy="+StringHelper.getFieldSql(xmwjYJ.getJsy())
				   +" ,jsm="+StringHelper.getFieldSql(xmwjYJ.getJsm())
				   +" ,jsd="+StringHelper.getFieldSql(xmwjYJ.getJsd())
	               +" where yjflag='1' and lsh in("+updateLSHs+")";
		ExecSql(sql);
	}
	
	public void  updateFlag(String updateLSHs,String flag){
		String sql="update "+tableName+" set yjflag='"+flag+"' where yjflag='1' and lsh in("+updateLSHs+")";
		ExecSql(sql);
	}
	
	public XmwjYJ   findByLsh(String lsh){
		String fieldNames=getFields(tableField);
		String sql="select "+fieldNames+" from "+tableName+" where lsh="+StringHelper.getFieldSql(lsh)+" and yjflag='1'";
		String[] obj=queryLine(sql);
		if(obj.length>0&&obj[0]!=null){
			XmwjYJ xmwjYJ=BeanHelper.stringArray2Object(getFields(tableField), obj, XmwjYJ.class, false);
			return xmwjYJ;
		}else{
			return null;
		}
	}
	
    public void  deleteByYjdw(String jsdw,String yjdw){
		String sql="delete   from "+tableName+" where yjflag='0' and jsdw="+StringHelper.getFieldSql(jsdw)+" and yjdw="+StringHelper.getFieldSql(yjdw);
		ExecSql(sql);
	}
    
    public  List<XmwjYJ>   getReuseList(String yjdw){
    	String  queryFields=" distinct jsdw,jsr,jsy,jsm,jsd";
		String fieldNames ="jsdw,jsr,jsy,jsm,jsd";
		String tabs=tableName;
		StringBuffer whereSql=new StringBuffer(" where yjflag='0'  ");
		String  orderBy = "order by jsy,jsm,jsd ";
		if(!Validation.isEmpty(yjdw)){
			whereSql.append(" and yjdw="+StringHelper.getFieldSql(yjdw));
		}
		String[][] obj = null;
			obj = queryRowAndCol("select " + queryFields + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		List<XmwjYJ> list=BeanHelper.stringArray2Object(fieldNames, obj, XmwjYJ.class, false);
		return list;
    	
    }
    
}
