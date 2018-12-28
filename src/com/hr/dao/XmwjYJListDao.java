package com.hr.dao;

import java.util.List;

import com.hr.bean.XmwjYJ;
import com.hr.bean.XmwjYJList;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;
import com.hr.util.Md5;

public class XmwjYJListDao extends BaseDao<XmwjYJList>{
	private String tableName;
	public static String[][] tableField = {
		{ "lsh",        Constants.fieldTypeNumber, "10,0", "0", "0" },
		{ "yjr",        "varchar", "50", "0", "0" },
		{ "jsr",        "varchar", "50", "0", "0" },
		{ "yjsj",       "varchar", "50", "1", "0" },
	};
	
	
	public XmwjYJListDao(String tableName,String dbName) {
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

   public List<XmwjYJList>   getList(XmwjYJList xmwjYJList,String lsh){
	   String  fieldNames=getFields(tableField);
		String tabs=tableName;
		StringBuffer whereSql=new StringBuffer(" where  lsh="+StringHelper.getFieldSql(lsh));
		String	orderBy = "";//"order by id  ";
		String[][] obj = null;
			obj = queryRowAndCol("select " + fieldNames + " from " + tabs + " "
					+ whereSql + " " + orderBy);
		
		List<XmwjYJList>  list=BeanHelper.stringArray2Object(fieldNames, obj, XmwjYJList.class, false);
		return list;
   }
   /**
    * 2015-10-9
    * 插入语句加上yjsj字段
    * 查询语句后面加上,GETDATE() as yjsj
    * @param xmwjYJ
    * @param updateLSH
    * @param xmwjYJTab
    */
   public  void  add(XmwjYJ xmwjYJ,String updateLSH,String xmwjYJTab){
	   String sql="insert into "+tableName+" (lsh,yjr,jsr,yjsj) "
                    +" select lsh,yjr,jsr,GETDATE() as yjsj from "+xmwjYJTab
                    +" where lsh in("+updateLSH+")  and yjflag='1'";
	   ExecSql(sql);
   }
   
   public  void  updateTime(String updateLSH){
	   String yjsj=Md5.date("yyyy年MM月dd日HH时mm分ss秒");
	   String  sql="update "+tableName+" set yjsj='"+yjsj+"' where lsh in("+updateLSH+")  and yjsj is null ";
	   ExecSql(sql);
   }
}
