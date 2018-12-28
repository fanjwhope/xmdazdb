package com.hr.global.util;

import com.hr.util.IncString;
import javax.servlet.http.HttpServletRequest;
import com.hr.util.MyHash;
public class CommonFuc {
	public static String getStrAttribute(HttpServletRequest request,
			String varName) {
		String tempV = (String) request.getAttribute(varName);
		if (tempV == null)
			return "";
		return tempV;
	}

	public static String sVar(HttpServletRequest request, String varName) {
		if(varName.equals("siteId")){
			return "20130724162356ihw7r";
		}		
		String tempV = request.getParameter(varName);
		if (tempV == null)
			return "";
		String lower = tempV.toLowerCase();
		if (lower.indexOf("javascript") != -1) {
			tempV = "";
		}

		return IncString.formatNull(tempV);
	}

	public static int iVar(HttpServletRequest request, String varName) {
		return IncString.formatInt(request.getParameter(varName));
	}

	public static long toLong(String str, long defaultValue) {
		long l_val = defaultValue;
		try {
			str = str.trim();
			l_val = Long.parseLong(str);
		} catch (Exception ex) {
		}
		return l_val;
	}
  //将一个数组的数据加入到另外一个数组中
   public static String[][] _addToArr(String[][] destinctArr,String[][] addDataArr){
        if(addDataArr==null || addDataArr.length==0){
            if(destinctArr!=null)return (String[][])destinctArr.clone();
            else return null;
        }
        if(destinctArr==null || destinctArr.length==0){
            return (String[][])addDataArr.clone();
        }
        int len1 =destinctArr.length;
        int len2 =addDataArr.length;

        String msg[][]=new String[len1+len2][];
        System.arraycopy(destinctArr,0,msg,0,len1);
        System.arraycopy(addDataArr,0,msg,len1,len2);
        return msg;
   }
   public static String[][] _addToArr(String[][] destinctArr,String[] addDataArr){
        if(addDataArr==null || addDataArr.length==0){
              return destinctArr;
        }
        String[][] newArr=new String[1][];
        newArr[0]=addDataArr;
        return _addToArr(destinctArr,addDataArr);
   }

  public static  String[] _addToArr(String[] arr,String str){
        return _addToArr(arr,new String[]{str});
   }

  public static String[] _addToArr(String[] destinctArr,String[] addDataArr){
        if(addDataArr==null || addDataArr.length==0)return (String[])destinctArr.clone();
        if(destinctArr==null || destinctArr.length==0)return (String[])addDataArr.clone();
        int len1 =destinctArr.length;
        int len2 =addDataArr.length;
        String msg[]=new String[len1+len2];
        System.arraycopy(destinctArr,0,msg,0,len1);
        System.arraycopy(addDataArr,0,msg,len1,len2);
        return msg;
   }
   //将二维数组转化为hash表
   public static MyHash  _ArrToHash(String[][] arr,int keyCur,int valCur){
        MyHash hash=new MyHash();
        int len=0;
        if(arr!=null)len=arr.length;
        for(int j=0;j<len;j++){
             String tempKey =arr[j][keyCur];
             if(tempKey==null || tempKey.trim().equals(""))continue;
             hash.set(tempKey,arr[j][valCur]);
        }
        return hash;
  }
}
