package com.hr.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.search.ReqExclScorer;

import com.hr.bean.Location;
import com.hr.dao.LocationDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.FileUtil;
import com.hr.global.util.JsonUtil;
import com.hr.util.Log;

public class LocationDo {
	private Location getObject(HttpServletRequest request){
		Location location = null;
		try {
			location = (Location) BeanHelper.getValuesByRequest(request, Location.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return location;
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Location.class);
		String zdbdbName=request.getParameter("zdbdbName");
		LocationDao dao = new LocationDao(tableName,zdbdbName);
		Location location = getObject(request);
		try{
			if("y".equals(location.getFlag())){ 
				if(dao.ifExistPath(location.getPath()) || dao.ifExistFlag())
					return;
				else
					dao.doCreate(location);
			}
			else{
				if(dao.ifExistPath(location.getPath()))
					return;
				else
					dao.doCreate(location);
			}
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "·�����ʧ�ܣ�");
		}
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Location.class);
		String zdbdbName=request.getParameter("zdbdbName");
		LocationDao dao = new LocationDao(tableName,zdbdbName);
		Location location = getObject(request);
		try{
			dao.doDelete(location);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "·��ɾ��ʧ�ܣ�");
		}
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Location.class);
		String zdbdbName=request.getParameter("zdbdbName");
		LocationDao dao = new LocationDao(tableName,zdbdbName);
		String path1 = request.getParameter("path1");
		String flag1 = request.getParameter("flag1");
		String path2 = request.getParameter("path2");
		String flag2 = request.getParameter("flag2");
		try{
			if(dao.ifExistPath(path1, path2))    //��·���Ѵ���
				return;
			else{
				if(flag1.equals(flag2))        //�����ͬ
					dao.doUpdate(path1, path2, flag2);
				else{
					if("n".equals(flag2))    //��·�����Ǵ洢·��
						dao.doUpdate(path1, path2, flag2);
					else{
						if(dao.ifExistFlag())    //�洢·���Ѵ���
							return;
						else
							dao.doUpdate(path1, path2, flag2);
					}
				}
			}
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "·������ʧ�ܣ�");
		}
	}
	
	public void find(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, Location.class);
		String zdbdbName=request.getParameter("zdbdbName");
		LocationDao dao = new LocationDao(tableName,zdbdbName);
		try{
			List<Location> all = dao.findAll();
			JsonUtil.writeJsonObject(response, all);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "·����ȡʧ�ܣ�");
		}
	}
	
	public void upload(HttpServletRequest request, HttpServletResponse response){
    	String filename = request.getParameter("filename");
    	try{
    		Map<String, String> pmap=new HashMap<String, String>();
    		//Map<String, String> fileSaveDir=new HashMap<String, String>();
    		//fileSaveDir.put("file", path);
    		Map<String, String> fileNameWithoutSuffix=new HashMap<String, String>();
    		fileNameWithoutSuffix.put("file", filename);
    		pmap=FileUtil.handleFormWithFile(request, null, fileNameWithoutSuffix,"UTF-8");
    		//String path=d.getGpkSavePath(new BaseDataOP(ConnectionPool.getPool()))+pmap.get("file");
    		JsonUtil.writeJsonMsg(response, true, "�ļ��ϴ��ɹ���");
    	} catch(Exception e){
    		Log.debug(e);
    		JsonUtil.writeJsonMsg(response, false, "�ļ��ϴ�ʧ�ܣ�");
    	}
	}
}
