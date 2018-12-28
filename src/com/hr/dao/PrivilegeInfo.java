package com.hr.dao;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import com.hr.global.util.ArchiveUtil;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.IncString;
import com.hr.util.Log;
import com.hr.util.Md5;
import com.hr.util.tab;

public class PrivilegeInfo {
	/**
	 * @param args
	 */
	/*********获得权限列表*********/
	public String[][] getPrivilegeList(String dbName,String searchDwh,String zdbdbName){
		BaseDataOP op =BaseDao.getBaseDataOP(zdbdbName);
		PrivilegeDate pd=new PrivilegeDate();
		String nowDate=Md5.date("yyyy-MM-dd");
		String whereSql="";
		if(!searchDwh.equals("")){
			whereSql+=" and dwh1='"+searchDwh+"'";
		}
		String qxsj="";
		try {
			qxsj = String.valueOf(pd.getDays(nowDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql="select dwh1,dwh2 from "+dbName+"_wsda_qx2"+" where startime <='"+Md5.date("yyyy-MM-dd")+"' and qxsj >='"+qxsj+"'"+whereSql;
		String[][] val=op.queryRowAndCol(sql);
		return val;
	}
	/********根据单位号获得中文名称************/
	public String getDwmc(String dbName,String dwh,String zdbdbName){
		BaseDataOP op =BaseDao.getBaseDataOP(zdbdbName);
		String sql="select dwmc from "+dbName+"_yhxx"+" where dwh='"+dwh+"'";
		String dwmc=IncString.formatNull(op.querySingleData(sql));
		return dwmc;
	}
	/*********获得部门下所有用户**********/
	public String[][] getUsers(String dbName,String dwh,String zdbdbName){
		BaseDataOP op =BaseDao.getBaseDataOP(zdbdbName);
		String likeDwh=dwh.substring(0, dwh.length()-1);
		String sql="select dwmc,userid,dwh from "+dbName+"_yhxx"+" where dwh<>'Y100000000'";//"select dwmc,userid,dwh from "+tab.get("yhxx", dbName)+" where dwh like '%"+likeDwh+"%' and dwh<>'"+dwh+"'";
		String[][] val=op.queryRowAndCol(sql);
		return val;	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
