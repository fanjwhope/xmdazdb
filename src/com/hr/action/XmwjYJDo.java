package com.hr.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.ProjectContent;
import com.hr.bean.User;
import com.hr.bean.Xmwj;
import com.hr.bean.XmwjYJ;
import com.hr.bean.XmwjYJList;
import com.hr.dao.ArchiveDao;
import com.hr.dao.ArchiveDepartDao;
import com.hr.dao.FilePathInfo;
import com.hr.dao.ProjectContentDao;
import com.hr.dao.UserDao;
import com.hr.dao.XmwjYJDao;
import com.hr.dao.XmwjYJListDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.util.BaseDataOP;
import com.hr.util.ConfigInfo;
import com.hr.util.ConnectionPool;
import com.hr.util.Log;
import com.hr.util.Md5;
import com.hr.util.d;

public class XmwjYJDo {
	/**
	 * 移交时  写入xmda_yj表    jsr接收人、接收时间暂不写入  ; yjflag=1
	 * @param request
	 * @param response
	 */
	public  void  add(HttpServletRequest request, HttpServletResponse response){
		String tableName=ArchiveUtil.getFullTableName(request, XmwjYJ.class);
		String xmwjTab=ArchiveUtil.getFullTableName(request, Xmwj.class);
		String dbName = request.getParameter("zdbdbName");
		XmwjYJDao  xmwjYJDao=new XmwjYJDao(tableName,dbName);
		ArchiveDao archiveDao=new ArchiveDao(xmwjTab,dbName);
		Xmwj xmwj=new Xmwj();
		String lsh=request.getParameter("lsh");
		String jsr=request.getParameter("jsr");
		String  yjr=ArchiveUtil.getCurrentUser(request.getSession()).getDwmc();
		String yjdw=ArchiveUtil.getCurrentUser(request.getSession()).getDwh();
		String  year=Md5.date("yyyy");
		String  mm=Md5.date("MM");
		String  dd=Md5.date("dd");
		String[] arr=lsh.split(",");
		xmwj.setYjflag("1");
		for (int i = 0; i < arr.length; i++) {
			XmwjYJ xmwjY=new XmwjYJ();
			xmwjY.setYjflag("1");
			xmwjY.setYjy(year);
			xmwjY.setYjm(mm);
			xmwjY.setYjd(dd);
			xmwjY.setLsh(arr[i]);
			xmwjY.setYjdw(yjdw);
			xmwjY.setYjr(yjr);
			xmwjY.setJsdw(jsr);
			xmwj.setLsh(Long.parseLong(arr[i]));
			try {
				archiveDao.update(xmwj, new String[]{"yjflag"}, null);
				xmwjYJDao.add(xmwjY,null,new String[]{"id"});
			} catch (Exception e) {
				Log.debug(e);
				JsonUtil.writeJsonMsg(response, false, "移交请求发送失败");
				return ;
			}
		}
		JsonUtil.writeJsonMsg(response, true, "移交请求发送成功");
	}
	
	/**
	 * 拒绝后设置   yjflag=S；但移交人看到被拒绝后，删除记录  根据id删除记录
	 * @param request
	 * @param response
	 */
	public void  refuse(HttpServletRequest request, HttpServletResponse response){
		String xmwjYJTab=ArchiveUtil.getFullTableName(request, XmwjYJ.class);
		String dataBaseName=ArchiveUtil.getDataBaseName(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		//String schema=ConfigInfo.getVal("schema");
		XmwjYJDao  xmwjYJDao=new XmwjYJDao(xmwjYJTab,dbName);
		Xmwj xmwj=new Xmwj();
		String yjdw=request.getParameter("yjdw");
		String jsdw=ArchiveUtil.getCurrentUser(request.getSession()).getDwh();
		String  jsr=ArchiveUtil.getCurrentUser(request.getSession()).getDwmc();
		String[] yjdws=yjdw.split(",");
		xmwj.setYjflag("S");
		String  year=Md5.date("yyyy");
		String  mm=Md5.date("MM");
		String  dd=Md5.date("dd");
		XmwjYJ xmwjYJ=new XmwjYJ();
		xmwjYJ.setJsy(year);
		xmwjYJ.setJsm(mm);
		xmwjYJ.setJsd(dd);
		xmwjYJ.setJsr(jsr);
		for (int i = 0; i < yjdws.length; i++) {
			try {
				String xmwjTab=dataBaseName+"_"+"xmwj_"+yjdws[i];
				ArchiveDao archiveDao=new ArchiveDao(xmwjTab,dbName);
				String[] lshs=xmwjYJDao.getLSHbYXmwjYJ(yjdws[i],jsdw);
				if(lshs!=null){
					String updateLsh="'"+lshs[0]+"'";
					if(lshs.length>1){
						for (int j = 1; j < lshs.length; j++) {
							updateLsh+=","+"'"+lshs[j]+"'";
						}
					} 
					xmwjYJDao.updateFlag(updateLsh,"0",xmwjYJ);//被拒收
					archiveDao.updateFlag(xmwjTab,updateLsh,"S");
				}
			} catch (Exception e) {
				Log.debug(e);
				JsonUtil.writeJsonMsg(response, false, "拒绝失败");
				return ;
			}
		}
		JsonUtil.writeJsonMsg(response, true, "拒绝成功");
	}
	
	/**
	 * 查看被拒绝的列表点击确定时操作
	 * @param request
	 * @param response
	 */
	public void confirmRefuse(HttpServletRequest request, HttpServletResponse response){
		String xmwjYJTab=ArchiveUtil.getFullTableName(request, XmwjYJ.class);
		String dbName = request.getParameter("zdbdbName");
		XmwjYJDao  xmwjYJDao=new XmwjYJDao(xmwjYJTab,dbName);
		String jsdw=request.getParameter("jsdw");
		String yjdw=ArchiveUtil.getCurrentUser(request.getSession()).getDwh();
		try {
			xmwjYJDao.deleteByYjdw(jsdw,yjdw);
			JsonUtil.writeJsonMsg(response, true, "操作成功");
		} catch (Exception e) {
		    Log.debug(e);
		    JsonUtil.writeJsonMsg(response, false, "操作失败");
		}
	}
	
	/**
	 * 获取被拒绝的列表
	 * @param request
	 * @param response
	 */
	public void getRefuseList(HttpServletRequest request, HttpServletResponse response){
		String xmwjYJTab=ArchiveUtil.getFullTableName(request, XmwjYJ.class);
		String dbName = request.getParameter("zdbdbName");
		XmwjYJDao  xmwjYJDao=new XmwjYJDao(xmwjYJTab,dbName);
		String yjdw=ArchiveUtil.getCurrentUser(request.getSession()).getDwh();
		try {
		   List<XmwjYJ>	list=xmwjYJDao.getReuseList(yjdw);
		    JsonUtil.writeJsonObject(response, list);
		} catch (Exception e) {
		    Log.debug(e);
		}
		
	}
	
	/**
	 * 接收后设置 yjflag=2；修改xmda_yj表  xmda_yjlist  新增一条记录
	 * @param request
	 * @param response
	 */
	public void receive(HttpServletRequest request, HttpServletResponse response){
		String xmwjYJTab=ArchiveUtil.getFullTableName(request, XmwjYJ.class);//项目移交表
		String xmwjYJListTab=ArchiveUtil.getFullTableName(request, XmwjYJList.class);//项目移交成功记录表
		String userTab=ArchiveUtil.getFullTableName(request,User.class);//用户信息表
		String projectContentTab=ArchiveUtil.getFullTableName(request,ProjectContent.class);//卷类型表
		
		String dataBaseName=ArchiveUtil.getDataBaseName(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		ProjectContentDao projectContentDao=new  ProjectContentDao(projectContentTab,dbName);
		//String schema=ConfigInfo.getVal("schema");
		UserDao userDao=new UserDao(userTab,dbName);
		XmwjYJDao  xmwjYJDao=new XmwjYJDao(xmwjYJTab,dbName);
		XmwjYJListDao xmwjYJListDao=new XmwjYJListDao(xmwjYJListTab,dbName);
		String yjdw=request.getParameter("yjdw");
		String jsdw=ArchiveUtil.getCurrentUser(request.getSession()).getDwh();
		String[] yjdws=yjdw.split(",");
		String  year=Md5.date("yyyy");
		String  mm=Md5.date("MM");
		String  dd=Md5.date("dd");
			try {
				for (int i = 0; i < yjdws.length; i++) {
					String[] lshs=xmwjYJDao.getLSHbYXmwjYJ(yjdws[i],jsdw);
					if(lshs!=null){
						String updateLsh=lshs[0];
						if(lshs.length>1){
							for (int j = 1; j < lshs.length; j++) {
								updateLsh+=","+lshs[j];
							}
						} 
						String xmwjTab = "";
						String xmwjJSTab = "";
						ArchiveDao archiveDao = null;
						ArchiveDao archiveJSDao = null;
						xmwjTab=dataBaseName+"_"+"xmwj_"+yjdws[i];
						xmwjJSTab=dataBaseName+"_"+"xmwj_"+jsdw;
						
						archiveDao=new ArchiveDao(xmwjTab,dbName);//移交人xmwj表
						archiveJSDao=new ArchiveDao(xmwjJSTab,dbName);//接收人xmwj表  ；创建接收人文档信息表
						archiveDao.updateFlag(xmwjTab,updateLsh,"2");
							XmwjYJ xmwjYJ=xmwjYJDao.findByLsh(lshs[i]);
						
							User jsUser=userDao.getUserByDWH(xmwjYJ.getJsdw());
							xmwjYJ.setJsr(jsUser.getDwmc());
							xmwjYJ.setJsy(year);
							xmwjYJ.setJsm(mm);
							xmwjYJ.setJsd(dd);
							
							//先修改xmda_yj信息，将移交成功的信息写入xmda_yjList，然后将xmda_yj状态改变，给xmda_yjList写入时间
							xmwjYJDao.updateFlag(xmwjYJ,updateLsh);
							xmwjYJListDao.add(xmwjYJ,updateLsh,xmwjYJTab);
//插入数据时已插入时间
//xmwjYJListDao.updateTime(updateLsh);
							xmwjYJDao.updateFlag(updateLsh,"2");
							
							//将移交的档案信息写入接收人的数据库
							archiveDao.writeJSMessage(updateLsh,xmwjJSTab);
							archiveDao.updateByJSmessage(updateLsh, xmwjJSTab);
							//将档案的卷内容写入接收人的档案卷内容表  例如   slgl_Y200020000
							List<ProjectContent> list= projectContentDao.findAll(projectContentTab);
							if(list.size()>0){
								String arhiveType="";
								for (int j = 0; j <list.size(); j++) {
									if("2002".equals(list.get(j).getEname())){
										arhiveType="xmlrb";
									}else{
										arhiveType=list.get(j).getEname();
									}
									String yjContentTab=dataBaseName+"_"+arhiveType+"_"+yjdws[i];
									String jsContentTab=dataBaseName+"_"+arhiveType+"_"+jsdw;
									ArchiveDepartDao archiveDepartDao=new ArchiveDepartDao(jsContentTab,dbName);
									ArchiveUtil.createTable(yjContentTab, ArchiveDepartDao.tableField);
									archiveDepartDao.writeJSMessage(updateLsh,yjContentTab);
									for (int k = 0; k < lshs.length; k++) {//文件移植
										String xmbh=archiveDao.getXMBHByLsh(lshs[k]);
										yjFile(yjdws[i],jsdw,lshs[k],arhiveType,dataBaseName,xmbh,dbName);
									}
								}
							}
						}
				}
				JsonUtil.writeJsonMsg(response, true, "接收成功");
			} catch (Exception e) {
				Log.debug(e);
				JsonUtil.writeJsonMsg(response, false, "接收失败");
		}
	}

	public  void  getListToReceive(HttpServletRequest request, HttpServletResponse response){
		String xmwjYJtab=ArchiveUtil.getFullTableName(request,  XmwjYJ.class);
		String dbName = request.getParameter("zdbdbName");
		XmwjYJDao xmwjYJDao=new XmwjYJDao(xmwjYJtab,dbName);
		XmwjYJ xmwjY = getObject(request);
		try {
		List<XmwjYJ> list=xmwjYJDao.getListToReceive(xmwjY);
			JsonUtil.writeJsonObject(response, list);
		} catch (Exception e) {
		   Log.debug(e);
		}
	}
	
	private XmwjYJ  getObject(HttpServletRequest request){
		XmwjYJ xmwjY = null;
		try {
			xmwjY = (XmwjYJ) BeanHelper.getValuesByRequest(request, XmwjYJ.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return xmwjY;
    }
	
	public   boolean  yjFile(String yjdw,String jsdw,String lsh,String arhiveType,String bankName,String xmbh,String zdbdbName){
		boolean flag=false;
		FilePathInfo filePathInfo=new FilePathInfo();
		String oldFile=filePathInfo.getRealPath( lsh, arhiveType, yjdw, xmbh, bankName,null,zdbdbName).getFileName();
		if(oldFile!=null&&!"".equals(oldFile)){
			String suffix=oldFile.split("\\.")[1];
			BaseDataOP baseDataOP=new BaseDataOP(ConnectionPool.getPool());
			String newFile=d.getGpkSavePath(baseDataOP)+File.separator+bankName+File.separator+"xmda"+File.separator+arhiveType+"_"+lsh+"."+suffix;
			if(!oldFile.equals(newFile)){
				flag=copyFile(oldFile,newFile);
			}else{
				flag=true;
			}
		}
			return flag;
	} 
	
	/**
	 * 复制文件 
	 * @param oldPath  源文件地址
	 * @param newPath     目标文件地址
	 * @return
	 */
	public static boolean  copyFile(String oldPath,String newPath){
		boolean flag=false;
		 File newfile = new File(newPath);
		if(newfile.exists()){
			newfile.delete();
		}
		 try {           
			 int bytesum = 0;          
			 int byteread = 0;         
			 File oldfile = new File(oldPath);       
			 if (oldfile.exists()) { //文件存在时       
				 InputStream inStream = new FileInputStream(oldPath); //读入原文件             
				 FileOutputStream fs = new FileOutputStream(newPath);               
				  byte[] buffer = new byte[1444];               
				 while ( (byteread = inStream.read(buffer)) != -1) {  
					       bytesum += byteread; //字节数 文件大小            
					       fs.write(buffer, 0, byteread); 
					    }    
				   inStream.close();      
			    }   
			 flag=true;
			 } catch (Exception e) {  
				Log.debug(e+" 文件路径："+oldPath);
				 e.printStackTrace();      
			} 
		 return flag;
	}
	
	public static void main(String[] args) {
		//String a="1,";
		//String[] arr =a.split(",");
		//System.out.println(arr.length);
		//copyFile("d:\\123.pdf", "d:\\456.pdf");
	}
}
