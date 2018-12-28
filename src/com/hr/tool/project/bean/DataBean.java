package com.hr.tool.project.bean;

import javax.servlet.http.HttpServletRequest;
import com.hr.util.*;
import com.hr.table.*;

public class DataBean {

  java.util.Properties hash =new java.util.Properties();
  public static final String NOUPDATE="****NOT<->UPDATE****";

  public String  fieldIncrease ="";
  public String[][] curFields =null;
  private static boolean isUpdate=false;
  public java.util.ArrayList  primaryKey=null;
  public java.util.ArrayList fieldList=null;
  public java.util.ArrayList notnullfield=null;
  private DataBean(String[][]  fields){
         initBean(fields);
  }
  private DataBean(HttpServletRequest request,String[][] fields){
        initBean(fields);
        if(curFields==null)return ;
        int len =curFields.length ;
        String[] vals =new String[len];
        for(int j=0;j<len ;j++)
        {
            String tempFieldName =curFields[j][0];
            vals[j] =IncString.formatNull(request.getParameter(tempFieldName));
        }
        setVals(vals);
  }
  public String ListToString(java.util.List list,String limitStr){
      String str="";
      if(!list.isEmpty()){
           int len =list.size();
           for(int i=0;i<len;i++){
               str+=limitStr+list.get(i);
           }
           if(str.startsWith(limitStr)){
               str=str.substring(1);
           }
      }
      return str;
  }
  public String ListToStringNotContainStr(java.util.List list,String limitStr,String notContainStr){
      String str="";
      if(!list.isEmpty()){
           int len =list.size();
           for(int i=0;i<len;i++){
               String temp=(String)list.get(i);
               if(DataBean.NOUPDATE.equals(notContainStr)){
                   temp=getVal(temp);
               }
               if (!notContainStr.equals(temp))
                   str += limitStr + list.get(i);

           }
           if(str.startsWith(limitStr)){
               str=str.substring(1);
           }
      }
      return str;
  }

  public static DataBean getDataBean(HttpServletRequest request,String[][] fields){
       isUpdate=false;
      return new DataBean(request,fields);
  }

  public static DataBean myDb(String[] vals,String[][] fields)
  {
        isUpdate=false;
        DataBean dbB =new DataBean(fields);
        dbB.setVals(vals);
        return dbB;
  }

  public static DataBean getUpdateDataBean(String[][] fields){
         isUpdate=true;
         return new DataBean(fields);
  }
  public static DataBean getEmptyDataBean(String[][] fields){
        isUpdate=false;
        return new DataBean(fields);
  }



  /*  ----------初始化 行bean------------ */
  private boolean initBean(String[][] fields){
        if(fields==null)return false;
        primaryKey = new java.util.ArrayList();
        fieldList = new java.util.ArrayList();
        notnullfield = new java.util.ArrayList();

        int FLen =fields.length;
        curFields =fields;
        for(int j=0;j<FLen ;j++){
            String tempVal ="";
            String tempFieldName =fields[j][0].toLowerCase();
            String fieldType = fields[j][1];
            int fieldLen = IncString.formatInt(fields[j][2]);
            boolean isCanNull = IncString.formatInt(fields[j][3]) == 0 ? false : true;
            boolean isPrimaryKey = IncString.formatInt(fields[j][4]) == 0 ? false : true;

            if (isPrimaryKey) {
                  primaryKey.add(tempFieldName);//之前加了两次
            }else  if (fieldType.indexOf(Constants.fieldIncrease) != -1
                        && Constants.fieldIncrease.trim().length()>0) {
                fieldIncrease = tempFieldName;
                primaryKey.add(tempFieldName);
            }
            fieldList.add(tempFieldName);

            if(!isUpdate){
                if (!isCanNull) {
                    notnullfield.add(tempFieldName);

                    if (fieldType.equalsIgnoreCase(Constants.fieldTypeNumber) ||
                        fieldType.equalsIgnoreCase(Constants.fieldTypeInt)) {
                        tempVal = "0";

                    }
                    if (fieldType.equalsIgnoreCase("varchar") &&
                        ConfigInfo.currentDBType == 1) {
                        tempVal = " ";
                    }

                }
            }else{
                tempVal=NOUPDATE;
            }
            setVal(tempFieldName,tempVal);
        }
        return true;
  }

  public boolean setVals(String[] vals)
  {
      if(curFields==null || vals==null)return false;
      int len =curFields.length ;
      if(vals.length != len)return false;
      for(int j=0;j<len;j++)
      {
           String tempFieldName =curFields[j][0];
           String tempFieldtype =curFields[j][1];
           boolean isCanNull = IncString.formatInt(curFields[j][3]) == 0 ? false : true;
           String v=IncString.formatNull(vals[j]);
           if(v.length() ==0 && !isCanNull){
              continue;
           }
           if(v!=null && v.length()>0){
               if(tempFieldtype.equalsIgnoreCase(Constants.fieldTypetext)){
                   setObject(tempFieldName, new ClobImpl(v));
               }else{
                   setVal(tempFieldName, v);
               }
           }
      }
      return true;
  }


  public String getVal(String key,String defaultVal){
     return hash.getProperty(key.toLowerCase(),defaultVal);
  }
  public String getVal(String key){
     return getVal(key,"");
  }
  public int getVal(String key,int defaultVal){
    return IncString.formatNull(getVal(key),defaultVal);
  }
  public void setVal(String key,String value){
       hash.setProperty(key.toLowerCase(),value);
  }
  public void setObject(String key,Object value){
      hash.put(key.toLowerCase(),value);
  }
  public Object getObject(String key){
      return hash.get(key.toLowerCase());
  }
}
