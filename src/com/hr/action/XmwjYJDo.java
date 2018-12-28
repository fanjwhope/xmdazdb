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
	 * �ƽ�ʱ  д��xmda_yj��    jsr�����ˡ�����ʱ���ݲ�д��  ; yjflag=1
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
				JsonUtil.writeJsonMsg(response, false, "�ƽ�������ʧ��");
				return ;
			}
		}
		JsonUtil.writeJsonMsg(response, true, "�ƽ������ͳɹ�");
	}
	
	/**
	 * �ܾ�������   yjflag=S�����ƽ��˿������ܾ���ɾ����¼  ����idɾ����¼
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
					xmwjYJDao.updateFlag(updateLsh,"0",xmwjYJ);//������
					archiveDao.updateFlag(xmwjTab,updateLsh,"S");
				}
			} catch (Exception e) {
				Log.debug(e);
				JsonUtil.writeJsonMsg(response, false, "�ܾ�ʧ��");
				return ;
			}
		}
		JsonUtil.writeJsonMsg(response, true, "�ܾ��ɹ�");
	}
	
	/**
	 * �鿴���ܾ����б���ȷ��ʱ����
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
			JsonUtil.writeJsonMsg(response, true, "�����ɹ�");
		} catch (Exception e) {
		    Log.debug(e);
		    JsonUtil.writeJsonMsg(response, false, "����ʧ��");
		}
	}
	
	/**
	 * ��ȡ���ܾ����б�
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
	 * ���պ����� yjflag=2���޸�xmda_yj��  xmda_yjlist  ����һ����¼
	 * @param request
	 * @param response
	 */
	public void receive(HttpServletRequest request, HttpServletResponse response){
		String xmwjYJTab=ArchiveUtil.getFullTableName(request, XmwjYJ.class);//��Ŀ�ƽ���
		String xmwjYJListTab=ArchiveUtil.getFullTableName(request, XmwjYJList.class);//��Ŀ�ƽ��ɹ���¼��
		String userTab=ArchiveUtil.getFullTableName(request,User.class);//�û���Ϣ��
		String projectContentTab=ArchiveUtil.getFullTableName(request,ProjectContent.class);//�����ͱ�
		
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
						
						archiveDao=new ArchiveDao(xmwjTab,dbName);//�ƽ���xmwj��
						archiveJSDao=new ArchiveDao(xmwjJSTab,dbName);//������xmwj��  �������������ĵ���Ϣ��
						archiveDao.updateFlag(xmwjTab,updateLsh,"2");
							XmwjYJ xmwjYJ=xmwjYJDao.findByLsh(lshs[i]);
						
							User jsUser=userDao.getUserByDWH(xmwjYJ.getJsdw());
							xmwjYJ.setJsr(jsUser.getDwmc());
							xmwjYJ.setJsy(year);
							xmwjYJ.setJsm(mm);
							xmwjYJ.setJsd(dd);
							
							//���޸�xmda_yj��Ϣ�����ƽ��ɹ�����Ϣд��xmda_yjList��Ȼ��xmda_yj״̬�ı䣬��xmda_yjListд��ʱ��
							xmwjYJDao.updateFlag(xmwjYJ,updateLsh);
							xmwjYJListDao.add(xmwjYJ,updateLsh,xmwjYJTab);
//��������ʱ�Ѳ���ʱ��
//xmwjYJListDao.updateTime(updateLsh);
							xmwjYJDao.updateFlag(updateLsh,"2");
							
							//���ƽ��ĵ�����Ϣд������˵����ݿ�
							archiveDao.writeJSMessage(updateLsh,xmwjJSTab);
							archiveDao.updateByJSmessage(updateLsh, xmwjJSTab);
							//�������ľ�����д������˵ĵ��������ݱ�  ����   slgl_Y200020000
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
									for (int k = 0; k < lshs.length; k++) {//�ļ���ֲ
										String xmbh=archiveDao.getXMBHByLsh(lshs[k]);
										yjFile(yjdws[i],jsdw,lshs[k],arhiveType,dataBaseName,xmbh,dbName);
									}
								}
							}
						}
				}
				JsonUtil.writeJsonMsg(response, true, "���ճɹ�");
			} catch (Exception e) {
				Log.debug(e);
				JsonUtil.writeJsonMsg(response, false, "����ʧ��");
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
	 * �����ļ� 
	 * @param oldPath  Դ�ļ���ַ
	 * @param newPath     Ŀ���ļ���ַ
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
			 if (oldfile.exists()) { //�ļ�����ʱ       
				 InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ�             
				 FileOutputStream fs = new FileOutputStream(newPath);               
				  byte[] buffer = new byte[1444];               
				 while ( (byteread = inStream.read(buffer)) != -1) {  
					       bytesum += byteread; //�ֽ��� �ļ���С            
					       fs.write(buffer, 0, byteread); 
					    }    
				   inStream.close();      
			    }   
			 flag=true;
			 } catch (Exception e) {  
				Log.debug(e+" �ļ�·����"+oldPath);
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
