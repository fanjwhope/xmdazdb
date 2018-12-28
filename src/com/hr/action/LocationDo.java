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
			JsonUtil.writeJsonMsg(response, false, "路径添加失败！");
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
			JsonUtil.writeJsonMsg(response, false, "路径删除失败！");
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
			if(dao.ifExistPath(path1, path2))    //新路径已存在
				return;
			else{
				if(flag1.equals(flag2))        //标记相同
					dao.doUpdate(path1, path2, flag2);
				else{
					if("n".equals(flag2))    //新路径不是存储路径
						dao.doUpdate(path1, path2, flag2);
					else{
						if(dao.ifExistFlag())    //存储路径已存在
							return;
						else
							dao.doUpdate(path1, path2, flag2);
					}
				}
			}
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "路径更新失败！");
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
			JsonUtil.writeJsonMsg(response, false, "路径获取失败！");
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
    		JsonUtil.writeJsonMsg(response, true, "文件上传成功！");
    	} catch(Exception e){
    		Log.debug(e);
    		JsonUtil.writeJsonMsg(response, false, "文件上传失败！");
    	}
	}
}
