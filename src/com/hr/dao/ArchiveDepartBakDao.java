package com.hr.dao;

import com.hr.bean.ArchiveDepartBak;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;

public class ArchiveDepartBakDao extends BaseDao<ArchiveDepartBak>{
	private String tableName;
	public static String[][] tableField = {
		{ "lsh",           Constants.fieldTypeNumber, "10,0", "0", "0" },
		{ "sort_num",      Constants.fieldTypeNumber, "16,0", "0", "0" },
		{ "damc",          "varchar", "500", "0", "0" },
		{ "dalx",           "varchar", "10", "1", "0" },
		{ "days",           "varchar", "4", "1", "0" },
		{ "dabz",           "varchar", "100", "1", "0" },
		{ "dwh",            "varchar", "10", "1", "0" },
		{ "flag",           Constants.fieldTypeNumber, "10,0", "1", "0" },
		{ "archivetime",    "varchar", "100", "1", "0" },
		{ "createtime",     "varchar", "20", "1", "0" },
		{ "modifyTime",     "varchar", "20", "1", "0" },
		{ "by1",            "varchar", "30", "1", "0" },
		{ "by2",            "varchar", "200", "1", "0" },
		{ "isnotstd",        Constants.fieldTypeNumber, "5,0", "1", "0" },
		{ "tabindex",        Constants.fieldTypeNumber,  "2", "1", "0" },
		{ "ip",             "varchar",         "100", "1", "0" },
		{ "ModifyManDwh",   "varchar",         "100", "1", "0" },
		{ "ModifyManCname", "varchar",         "100", "1", "0" },
	    { "curModifyTime",  "varchar",         "100", "1", "0" },
		{ "logAction",      "varchar",         "20", "1", "0" }
	};
	

	
	public ArchiveDepartBakDao(String tableName,String dbName) {
		super(tableName,tableField,dbName);
		this.tableName=tableName;
		ArchiveUtil.createTable(tableName,tableField);
	}
	
	public int getMaxIndex(String lsh,long sortNum){
		String  sql="select max(tabindex) from "+tableName+" where lsh="+lsh+" and sort_num="+sortNum;
		String index=querySingleData(sql);
		int num=1;
		if(!Validation.isEmpty(index)){
			num=Integer.parseInt(index)+1;
		}
		return num;
	}


}
