package com.hr.global.util;

import com.hr.util.ConfigInfo;
import com.hr.util.Md5;
import java.io.PrintStream;

public class Constants
{
  public static final int currentDBType = ConfigInfo.currentDBType;
  public static final String fieldTypeInt = (currentDBType == 1) ? "number" : "int";
  public static final String fieldTypeNumber = (currentDBType == 1) ? "number" : "numeric";
  public static final String fieldTypetext = (currentDBType == 1) ? "CLOB" : "text";
  public static final String fieldTypeImage = (currentDBType == 1) ? "BLOB" : "image";
  public static final String fieldIncrease = (currentDBType == 1) ? "" : "IDENTITY";
  public static final String fieldDateTime = (currentDBType == 1) ? " varchar(25) " : "datetime";
  public static final String fieldIncrease2 = (currentDBType == 1) ? "" : "IDENTITY (1, 1)";
  public static final String fieldIncrease10000 = (currentDBType == 1) ? "" : "IDENTITY (10001, 1)";
  public static final String substr = (currentDBType == 1) ? "substr" : "substring";
  public static final String len = (currentDBType == 1) ? "length" : "len";
  public static final String StrConcat = (currentDBType == 1) ? "||" : "+";

  public static String getColtypeName(String coltypename)
  {
    String typename = null;
    coltypename = coltypename.toLowerCase();
    if (coltypename.equals("int")) {
      typename = "Constants.fieldTypeInt";
    } else if ((coltypename.equals("number")) || (coltypename.equals("numeric"))) {
      typename = "Constants.fieldTypeNumber";
    } else if ((coltypename.equals("clob")) || (coltypename.equals("text"))) {
      typename = "Constants.fieldTypetext";
    } else if ((coltypename.equals("blob")) || (coltypename.equals("image"))) {
      typename = "Constants.fieldTypeImage";
    } else if (coltypename.equals("datetime")) {
      typename = "Constants.fieldDateTime";
    } else if (coltypename.equals("smalldatetime")) {
      typename = "Constants.fieldDateTime";
    } else if (coltypename.equals("decimal")) {
      typename = "Constants.fieldTypeInt";
    } else if (coltypename.equals("bit")) {
      typename = "Constants.fieldTypeInt";
    } else if (coltypename.equals("char")) {
      typename = "Constants.fieldTypeInt";
    } else {
      typename = "\"" + coltypename + "\"";
      if (!(coltypename.equalsIgnoreCase("varchar"))) {
        System.out.println("不确定类型-->" + coltypename);
      }
    }
    return typename;
  }
  
  /**
   * 将数据库的NULL字段转换的方法
   * @param field
   * @param replace
   * @return
   */
  public static String nullCast(String field, Object replace){
	   return ((currentDBType == 1) ?"nvl(":"isnull(")+field+","+StringHelper.getFieldSql(replace)+")";
  }

  public static String BuildCreateTabSql(String tabName, String[][] fieldsInfo)
  {
    String returnStr = "";
    String TabSql = "";
    String primaryKeyStr = null;
    if (fieldsInfo != null)
    {
      returnStr = "create table " + tabName + "(";
      for (int i = 0; i < fieldsInfo.length; ++i) {
        String fieldName = fieldsInfo[i][0];
        String fieldType = fieldsInfo[i][1];
        String fieldLength = fieldsInfo[i][2];
        String fieldIfNull = fieldsInfo[i][3];
        fieldIfNull = (fieldIfNull.equals("0")) ? " not null " : " null ";
        String fieldIndexSign = fieldsInfo[i][4];
        if (!(fieldIndexSign.equals("0"))) {
          if (primaryKeyStr == null) primaryKeyStr = fieldName;
          else primaryKeyStr = primaryKeyStr + "," + fieldName;
        }
        boolean exec = (fieldType.equalsIgnoreCase("int")) || ((fieldType.toLowerCase().indexOf(fieldIncrease.toLowerCase()) != -1) && (fieldIncrease.trim().length() > 0)) || (fieldType.equalsIgnoreCase("CLOB")) || (fieldType.equalsIgnoreCase("text")) || (fieldType.equalsIgnoreCase("image")) || (fieldType.equalsIgnoreCase("datetime")) || (fieldType.equalsIgnoreCase("blob"));

        if (i > 0)
          TabSql = TabSql + ",";
        if ((exec) || (fieldType.indexOf("(") != -1))
          TabSql = TabSql + fieldName + " " + fieldType + " " + fieldIfNull;
        else {
          TabSql = TabSql + fieldName + " " + fieldType + "(" + fieldLength + ")" + " " + fieldIfNull;
        }
      }

      returnStr = returnStr + TabSql;
      if (primaryKeyStr != null) returnStr = returnStr + ",primary Key(" + primaryKeyStr + ")";
      returnStr = returnStr + ")";
    }
    return returnStr; }

  public static String getTextLike(String fieldName, String compStr) {
    String sql = "";
    if (ConfigInfo.currentDBType == 1)
      sql = " (instr(" + fieldName + ",'" + compStr + "')<>0 ) ";
    else {
      sql = "(" + fieldName + " like '%" + compStr + "%' ) ";
    }
    return sql;
  }

  public static String getLikeSql(String fieldName, String val, String separter) {
    if (val == null) val = "";
    if (separter == null) separter = "";

    String sql = "";
    String compStr = separter + val + separter;
    String beginStr = val + separter;
    String beginStr2 = separter + val + separter;
    String endStr = separter + val;
    String endStr2 = separter + val + separter;

    if (ConfigInfo.currentDBType == 1) {
      sql = " (instr(" + fieldName + ",'" + compStr + "')<>0 ) " + " or " + startWith(fieldName, beginStr);
    }
    else
    {
      sql = "(" + fieldName + " like '%" + compStr + "%' ) " + " or " + startWith(fieldName, beginStr);
    }

    return " (" + sql + ") ";
  }

  public static String startWith(String fieldName, String val)
  {
    String sql = "";

    sql = " (" + fieldName + " like '" + val + "%' or " + fieldName + " like '" + val + "')";
    return sql;
  }

  public static String[] getNotNullFields(String[][] fieldsInfo)
  {
    String str = null;
    for (int i = 0; i < fieldsInfo.length; ++i)
    {
      String fieldName = fieldsInfo[i][0];
      String fieldIfNull = fieldsInfo[i][3];

      if (fieldIfNull.equals("0"))
        continue;
      if (str == null) str = fieldName;
      else str = str + ";" + fieldName;
    }

    if (str != null)
    {
      return Md5.split(str, ";"); }
    return null;
  }

  public static int getFieldNum(String fieldName, String[][] fields)
  {
    int returnInt = -1;
    for (int i = 0; i < fields.length; ++i)
    {
      String tempName = fields[i][0];
      if (!(tempName.equalsIgnoreCase(fieldName)))
        continue;
      returnInt = i;
      break;
    }

    return returnInt;
  }

  public static void main(String[] args) throws Exception {
    System.out.println(getLikeSql("handler", "10023", ","));
  }
}