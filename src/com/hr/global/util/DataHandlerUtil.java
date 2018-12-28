package com.hr.global.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import com.hr.util.Log;

public class DataHandlerUtil {
	/**
	 * 利用本地文件生成DataHandler对象
	 * @param path
	 * @return
	 */
	public static DataHandler getFile(String path) throws Exception{
		if(!new File(path).exists())throw new Exception(path+": 文件不存在");
		return new DataHandler(new FileDataSource(path)); 
	}
	/**
	 * 将DataHandler对象中的文件存到指定路劲
	 * @param file
	 * @param fullName
	 */
	public static void saveFile(DataHandler file, String fullName) {
		fullName=fullName.replace("\\",File.separator ).replace("/",File.separator );
		String dir=fullName.substring(0,fullName.lastIndexOf(File.separator));
		File d=new File(dir);
		if(!d.exists())d.mkdirs();
		File f=new File(fullName);
		FileOutputStream fos=null;
		try {
			fos=new FileOutputStream(f);
			file.writeTo(fos);
			fos.flush();  
		} catch (Exception e) {
			Log.error(e);
		}finally{
			if(null!=fos){
				try {
					fos.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
	}
}
