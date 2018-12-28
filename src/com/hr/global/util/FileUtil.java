package com.hr.global.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.hr.bean.Location;
import com.hr.dao.BaseDao;
import com.hr.dao.LocationDao;
import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.Log;
import com.hr.util.d;

public class FileUtil {
	/**
	 * 下载单个文件
	 * @param response
	 * @param downloadName 文件下载到客户端后显示的名称
	 * @param file 欲下载的文件
	 * @throws IOException 
	 */
	public static void download(HttpServletResponse response, String downloadName, File file) throws IOException{
		downloadName=new String(downloadName.getBytes("GBK"),"ISO-8859-1");

		response.setHeader( "Content-Disposition", "attachment;filename="  + downloadName);

		response.setHeader("Content-disposition","attachment; filename="+downloadName);
		response.setContentType("application/octet-stream");
		BufferedInputStream bis = null;  
		BufferedOutputStream bos = null;  
	    try {  
	        bis = new BufferedInputStream(new FileInputStream(file));  
	        bos = new BufferedOutputStream(response.getOutputStream());  
	  
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	  
	        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff,0,bytesRead);  
	        }  
	    } finally {  
	        if (bis != null)  
	            bis.close();  
	        if (bos != null)  
	            bos.close();  
	    }  
	}
	
	/**
	 * 用于处理用户带文件的表单数据。将上传的文件保存，并将表单数据封装到一个map
	 * @param request
	 * @param fileSaveDir key为文件表单字段的name属性值，value为文件保存目录名称（无需完整路径）。假如没有为某个文件设置该值的话就保存到默认目录下
	 * @param fileNameWithoutSuffix key为文件表单字段的name属性值，value为文件名称（无需后缀）。假如没有为某个文件设置该值的话就使用客户端的文件名
	 * @return 表单数据封装的map。该map中，文件字段的value是该文件保存后相对GPK的路径（含文件名）。
	 * 返回的map中还额外含有一个名叫formHandleMessage的key，里面保存着处理表单时可能出现的错误信息。
	 */
	public static Map<String, String> handleFormWithFile(HttpServletRequest request, Map<String, String> fileSaveDir, Map<String, String> fileNameWithoutSuffix){
		return handleFormWithFile(request, fileSaveDir, fileNameWithoutSuffix, null);
	}
	
	/**
	 * 用于处理用户带文件的表单数据。将上传的文件保存，并将表单数据封装到一个map
	 * @param request
	 * @param fileSaveDir key为文件表单字段的name属性值，value为文件保存目录名称（无需完整路径）。假如没有为某个文件设置该值的话就保存到默认目录下
	 * @param fileNameWithoutSuffix key为文件表单字段的name属性值，value为文件名称（无需后缀）。假如没有为某个文件设置该值的话就使用客户端的文件名
	 * @param encoding 解析表单时采用的编码格式
	 * @return 表单数据封装的map。该map中，文件字段的value是该文件保存后相对GPK的路径（含文件名）。
	 * 返回的map中还额外含有一个名叫formHandleMessage的key，里面保存着处理表单时可能出现的错误信息。
	 */
	public static Map<String, String> handleFormWithFile(HttpServletRequest request, Map<String, String> fileSaveDir, Map<String, String> fileNameWithoutSuffix, String encoding){
		Map<String, String> formData=new HashMap<String, String>();	
		String zdbdbName=request.getParameter("zdbdbName");
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String gpkDir=op.queryRowAndCol("select path from zdb_wsda_gpk where flag='y'")[0][0];//单数据库查询gpk使用
		/*LocationDao dao = new LocationDao(tableName);
		String gpkDir=dao.getPath();*/
		
		//获取文件默认存放路径和允许上传文件大小的参数
		String defaultDir = request.getSession().getServletContext().getInitParameter("defaultDir");
		if (Validation.isEmpty(defaultDir)){
			defaultDir = ArchiveUtil.getDataBaseName(request.getSession())+d.fgh()+"xmda";
		}
		int fileMaxSize=1024;
		String fileMaxSizeStr = request.getSession().getServletContext().getInitParameter("fileMaxSize");
		if (!Validation.isEmpty(fileMaxSizeStr)){
			fileMaxSize=Integer.parseInt(fileMaxSizeStr);
		}
		
		//开始处理表单
		boolean haveFile=false;
		File temp=new File(request.getSession().getServletContext().getRealPath("/temp"));
		if(!temp.exists()){
			temp.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory(4096, temp);
        ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(fileMaxSize*1024*1024);//设置允许的文件大小
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					if(null!=encoding&&!"".equals(encoding)){
						formData.put(item.getFieldName(), item.getString(encoding));
					}else{
						formData.put(item.getFieldName(), item.getString());
					}
				} else {
					haveFile=true;//用户表单包含文件
					
					//获取文件保存目录
					String fieldName=item.getFieldName();
					String saveDir=null;
					if(null!=fileSaveDir){
						saveDir=fileSaveDir.get(fieldName);
					}
					if(Validation.isEmpty(saveDir)){
						saveDir=defaultDir;
					}
					saveDir=saveDir.replace("\\", File.separator);
					saveDir=saveDir.replace("/", File.separator);
					if(saveDir.startsWith(File.separator)){
						saveDir=saveDir.substring(File.separator.length());
					}
					if(saveDir.endsWith(File.separator)){
						saveDir=saveDir.substring(0,saveDir.length()-File.separator.length());
					}
					File dirFile = new File(gpkDir+File.separator+saveDir);
					if (!dirFile.exists()) {
						dirFile.mkdirs();
					}
					
					// 获取文件名
					String clientFileName = item.getName();
					clientFileName = clientFileName.replace('\\', '/');
					String[] pathParts = clientFileName.split("/");
					String fileName = pathParts[pathParts.length - 1];
					String nameWithoutSuffix=null;
					if(null!=fileNameWithoutSuffix){
						nameWithoutSuffix=fileNameWithoutSuffix.get(fieldName);
					}
					if(!Validation.isEmpty(nameWithoutSuffix)){
						String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
						fileName = nameWithoutSuffix + "." + suffix;
					}
					String relativePath=File.separator+saveDir+File.separator+fileName;
					formData.put(fieldName, relativePath);//保存文件相对GPK的路径到map
					
					//保存文件
					item.write(new File(gpkDir+relativePath));
					//删除旧图片文件夹
					String imgPath = gpkDir+File.separator+saveDir+File.separator+nameWithoutSuffix;
					File img = new File(imgPath);
					File[] files = img.listFiles();
					if(img.exists()){
						for(int i = 0; i < files.length; i++)
							files[i].delete(); //删除图片目录下的图片文件
						img.delete();    //删除图片所在文件夹
					}
				}
			}
		} catch (FileUploadException e) {
			Log.error(e);
			formData.put("formHandleMessage", "上传的文件超出允许的文件大小，允许的最大文件为"+fileMaxSize+"M！");
			return formData;
		} catch (Exception e) {
			Log.error(e);
			formData.put("formHandleMessage", "上传文件的过程中出现错误！");
			return formData;
		}
		
		if(!haveFile){
			formData.put("formHandleMessage", "没有上传任何文件！");
		}
		return formData;
	}

	public static void move(String source, String destination)
			throws IOException {
		(new File(destination)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(source)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 复制文件
				copyFile(file[i], new File(destination +File.separator+ file[i].getName()));
			}
			if (file[i].isDirectory()) {
				// 复制目录
				String sorceDir = source + File.separator + file[i].getName();
				String targetDir = destination + File.separator + file[i].getName();
				copyDirectiory(sorceDir, targetDir);
			}
		}

		del(source);
	}

	public static void copyFile(File sourcefile, File targetFile)
			throws IOException {

		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}

		// 刷新此缓冲的输出流
		outbuff.flush();

		// 关闭流
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();

	}

	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {

		// 新建目标目录

		(new File(targetDir)).mkdirs();

		// 获取源文件夹当下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());

				copyFile(sourceFile, targetFile);

			}

			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();

				copyDirectiory(dir1, dir2);
			}
		}

	}

	/**
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public static void del(String filepath) throws IOException {
		File f = new File(filepath);// 定义文件路径
		if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
			if (f.listFiles().length > 0) {// 若目录下没有文件则直接删除
				{// 若有则把文件放进数组，并判断是否有下级目录
					File delFile[] = f.listFiles();
					int i = f.listFiles().length;
					for (int j = 0; j < i; j++) {
						if (delFile[j].isDirectory()) {
							del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
						}
						delFile[j].delete();// 删除文件
					}
				}
			}
			f.delete();
		}

	}
}
