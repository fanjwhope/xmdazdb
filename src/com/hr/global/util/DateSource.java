package com.hr.global.util;

import com.hr.util.IncString;
import com.hr.util.Md5;
import com.hr.util.MyList;

public class DateSource
{
  public static String SignOfNoAddSYH = "_NoAddSYH_";

  MyList numberField = new MyList();

  public StringBuffer getJSONString(String fields, String[][] dataArr, boolean fieldIsAddSYH, boolean notAddIfValueIsNull)
    throws Exception
  {
    if ((dataArr == null) || (dataArr.length == 0)) return null;
    if (fields == null) throw new Exception("标识字段不能为空！ ");
    String[] fieldArr = fields.split(",");

    int fieldNum = fieldArr.length;
    int arrLen = dataArr[0].length;
    if (fieldNum > arrLen) throw new Exception("字段的数量大于数组的长度");
    StringBuffer s_buff = new StringBuffer();

    for (int i = 0; i < dataArr.length; ++i) {
      if (i == 0) s_buff.append("{");
      else s_buff.append(",{");
      int cur = 0;

      for (int j = 0; j < fieldNum; ++j) {
        String fieldSign = fieldArr[j];
        if (!(fieldSign.equals(""))) {
          String onlyField = fieldSign;
          if (fieldIsAddSYH) fieldSign = "\"" + fieldSign + "\"";
          String tempVal = dataArr[i][j];
          tempVal = IncString.formatNull(tempVal).trim();
          if (notAddIfValueIsNull) {
            if ((tempVal == null) || (tempVal.equals(""))) continue; if (tempVal.equals("null")) {
              continue;
            }
          }
          if (onlyField.equalsIgnoreCase("title")) {
            if (tempVal.indexOf("\"") != -1) {
              tempVal = IncString.str_Replace(tempVal, "\"", "&quot;");
            }
            if (tempVal.indexOf("'") != -1) {
              tempVal = IncString.str_Replace(tempVal, "'", "&apos;");
            }
            if (tempVal.indexOf("\r\n") != -1) {
              tempVal = IncString.str_Replace(tempVal, "\r\n", "");
            }
            if (tempVal.indexOf("\r") != -1) {
              tempVal = IncString.str_Replace(tempVal, "\r", "");
            }
            if (tempVal.indexOf("\n") != -1) {
              tempVal = IncString.str_Replace(tempVal, "\n", "");
            }
          }
          String val = "";
          if (tempVal.startsWith(SignOfNoAddSYH)) {
            tempVal = tempVal.substring(SignOfNoAddSYH.length());
            val = fieldSign + ":" + tempVal;
          }
          else if (this.numberField.get(onlyField, 0) == 1) {
            val = fieldSign + ":" + tempVal;
          } else {
            val = fieldSign + ":\"" + tempVal + "\"";
          }

          if (cur == 0) s_buff.append(val);
          else s_buff.append("," + val);
          ++cur; }
      }
      s_buff.append("}");
    }
    return s_buff;
  }

  public void setNumberField(String fields) {
    if (fields == null) return;
    if (fields.indexOf(",") != -1) {
      String[] fieldArr = Md5.splitOnly(fields, ",");
      for (int i = 0; i < fieldArr.length; ++i)
        this.numberField.set(fieldArr[i], "1");
    } else {
      this.numberField.set(fields, "1"); } }

  public String getDataString(long _records, String fields, String[][] dataArr) throws Exception {
    StringBuffer s_buff = new StringBuffer();
    s_buff.append("{\"total\":\"" + _records + "\",\"rows\":[");

    StringBuffer str = getJSONString(fields, dataArr, true, false);
    if (str != null) {
      s_buff.append(str);
    }
    s_buff.append("]}");
    return s_buff.toString();
  }
}