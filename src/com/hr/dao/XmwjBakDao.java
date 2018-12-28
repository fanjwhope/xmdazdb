package com.hr.dao;

import com.hr.bean.XmwjBak;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;

public class XmwjBakDao extends BaseDao<XmwjBak>{
	private String tableName;
	public static String[][] tableField = {
		 { "lsh",           Constants.fieldTypeNumber,         "10", "0", "1" },
		 { "qymc",          "varchar",         "300", "0", "0" },
		 { "xmmc",          "varchar",         "400", "1", "0" },
		 { "xmbh",          "varchar",          "200", "1", "0" },
		 { "dkqx",          "varchar",          "100", "1", "0" },
		 { "xmfzr",         "varchar",         "200", "1", "0" },
		 { "xmnd",           Constants.fieldTypeNumber,         "4", "1", "0" },
		 { "dkje",          "varchar",         "200", "1", "0" },
		 { "dw",            "varchar",         "12", "1", "0" },
		 { "tbyy",           Constants.fieldTypeNumber,         "4", "1", "0" },
		 { "tbmm",           Constants.fieldTypeNumber,         "2", "1", "0" },
		 { "tbdd",           Constants.fieldTypeNumber,         "2", "1", "0" },
		 { "yjr",           "varchar",         "50", "1", "0" },
		 { "jsr",           "varchar",         "50", "1", "0" },
		 { "yjyy",           Constants.fieldTypeNumber,         "4", "1", "0" },
		 { "yjmm",           Constants.fieldTypeNumber,         "2", "1", "0" },
		 { "yjdd",           Constants.fieldTypeNumber,         "2", "1", "0" },
		 { "dasryy",         Constants.fieldTypeNumber,         "4", "1", "0" },
		 { "dasrmm",         Constants.fieldTypeNumber,         "2", "1", "0" },
		 { "dasrdd",         Constants.fieldTypeNumber,         "2", "1", "0" },
		 { "dasrjs",         "varchar",         "4", "1", "0" },
		 { "daycyy",         Constants.fieldTypeNumber,         "4", "1", "0" },
		 { "daycmm",         Constants.fieldTypeNumber,         "2", "1", "0" },
		 { "daycdd",         Constants.fieldTypeNumber,         "2", "1", "0" },
		 { "ywhc",           "varchar",         "200", "1", "0" },
		 { "ycry",           "varchar",         "40", "1", "0" },
		 { "xyjs",           "varchar",         "4", "1", "0" },
		 { "bz",             "varchar",         "7999", "1", "0" },
		 { "num_Lsh",         Constants.fieldTypeNumber,         "10", "1", "0" },
		 { "dwh",            "varchar",         "10", "1", "0" },
		 { "ajsm",           "varchar",         "7999", "1", "0" },
		 { "ljr",            "varchar",         "50", "1", "0" },
		 { "jcr",            "varchar",         "50", "1", "0" },
		 { "yjflag",         "varchar",         "1", "1", "0" },
		 { "yjlist",         "varchar",         "800", "1", "0" },
		 { "jsdw",           "varchar",         "10", "1", "0" },
		 { "by1",            "varchar",         "30", "1", "0" },
		 { "by2",            "varchar",         "200", "1", "0" },
		 { "by3",            "varchar",         "200", "1", "0" },
		 { "tabindex",        Constants.fieldTypeNumber,          "2", "0", "1" },
		 { "ip",             "varchar",         "100", "1", "0" },
		 { "ModifyManDwh",   "varchar",         "100", "1", "0" },
		 { "ModifyManCname", "varchar",         "100", "1", "0" },
		 { "curModifyTime",  "varchar",         "100", "1", "0" },
		 { "logAction",      "varchar",         "20", "1", "0" },
		 { "projectType",    "char",            "1",   "1", "0" },
		 { "square",       "char",            "5",   "1", "0" }
		 };
	
	
	public XmwjBakDao(String tableName,String dbName) {
		super(tableName,tableField,dbName);
		this.tableName=tableName;
		ArchiveUtil.createTable(tableName,tableField);
	}
	
	public int getMaxInex(String lsh){
		String  sql="select max(tabindex) from "+tableName+" where lsh="+StringHelper.getFieldSql(lsh);
		String index=querySingleData(sql);
		int num=1;
		if(!Validation.isEmpty(index)){
			num=Integer.parseInt(index)+1;
		}
		return num;
	}
	
	
}
