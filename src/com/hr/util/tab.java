package com.hr.util;

import com.hr.basic.UserVar;
import java.io.PrintStream;

public class tab
{
  public static String get(String varTname)
  {
    if (PoolConfig.currentDBType == 1)
      return varTname;
    if (PoolConfig.currentDBType == 2) {
      String Tname = ConfigInfo.databaseName + "." + varTname;
      return Tname;
    }
    String Tname = varTname;
    return Tname;
  }

  public static String get(String varTname, String dbName)
  {
    String addPrefix = "";
    String Tname="";
    if (PoolConfig.currentDBType == 1){
      Tname=varTname;
      return Tname;
    }
    if (PoolConfig.currentDBType == 2) {
      varTname.toUpperCase();
      if ((dbName != null) && (!(dbName.equals("")))) {
          Tname = dbName + ".dbo." + varTname;
      }
      return Tname;
    }
	return Tname;
  }

  public static void main(String[] anObject)
  {
    System.out.println(get("yhdm", "dagl"));
    System.out.println(get("yhdm", "zhda"));
    System.out.println(get("yhdm", "RunOffice"));
    System.out.println(get("yhdm", "oadb"));
    System.out.println(get("yhdm", "RunAdmin"));
  }
}