package com.hr.dao;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import com.hr.global.util.ArchiveUtil;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.Md5;
import com.hr.dao.PrivilegeDate;

public class PrivilegeDataInfo {
	public String getLsh(HttpServletRequest request) throws ParseException{
		BaseDataOP op=new BaseDataOP(ConnectionPool.getPool());
		PrivilegeDate pd=new PrivilegeDate();
		String nowDate=Md5.date("yyyy-MM-dd");
		long nowDays=pd.getDays(nowDate);		
		String dbName=ArchiveUtil.getDataBaseName(request.getSession());
		String dwh=ArchiveUtil.getDepartmentCode(request.getSession());
		String[] tabs=null;
		String getTabSql="select dwh2 from "+dbName+"_wsda_qx2"+" where dwh1='"+dwh+"' and startime <='"+nowDate+"' and qxsj>'"+nowDays+"'";
		String[] getTabs=op.queryColumn(getTabSql);
		if(getTabs!=null&&getTabs.length>0){
				tabs=new String[getTabs.length+1];
				tabs[0]=dbName+"_xmwj_"+dwh;
			for(int i=0;i<getTabs.length;i++){
				tabs[i+1]=dbName+"_xmwj_"+getTabs[i];
			}
			
		}else{
			tabs=new String[1];
			tabs[0]=dbName+"_xmwj_"+dwh;
		}

		String ids="";
		String getIdsSql="";
		String getLSHSql="select  lsh from "+dbName+"_wsda_qx_pro where dwh1='"+dwh+"' and startime <='"+nowDate+"' and qxsj>'"+nowDays+"'";
		String[]  getLSHs=op.queryColumn(getLSHSql);
		String[] getIds=null;
		String whereSql=" where (yjflag<>'2' or yjflag is null) ";
		for(int j=0;j<tabs.length;j++){
			getIdsSql="select lsh from "+tabs[j]+whereSql;
			getIds=op.queryColumn(getIdsSql);
			if(getIds!=null&&getIds.length>0){
				for(int k=0;k<getIds.length;k++){
					ids+=","+getIds[k];
				}
			}
		}
		//如果按项目授权有记录时，判断ids中是否包含，不包含则添加到ids中
		if(getLSHs!=null&&getLSHs.length>0){
			ids=getStringId(ids+",",getLSHs);
		}
		if(ids.endsWith(",")){
			ids=ids.substring(1, ids.length()-1);
		}else{
			if(ids.length()>1){
				ids=ids.substring(1, ids.length());
			}else{
				ids="";
			}
		}
		return ids;		
	}
	
	
	public  String getStringId(String ids,String[] lsh){
		if(lsh!=null&&lsh.length>0){
			for (int i = 0; i < lsh.length; i++) {
				if(ids.indexOf(","+lsh[i]+",")==-1){
					ids+=lsh[i]+",";	
					}
			}
		}
		if(ids.endsWith(",")){
			ids=ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
