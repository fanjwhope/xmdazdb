package com.hr.global.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.Log;
import com.hr.util.d;

public class SingleFileUploadSer extends HttpServlet {
	private static String defaultDir;// 默认存放目录
	private static int fileMaxSize;//允许的文件大小
	private static String gpkDir;//系统所有上传文件公共存放处
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		BaseDataOP op = new BaseDataOP(ConnectionPool.getPool());
		gpkDir=d.getGpkSavePath(op);
		// 从web.xml中获取根目录名称
		defaultDir = getInitParameter("defaultDir");
		if (defaultDir == null){
			defaultDir = "/uploadFile/";
		}
		defaultDir=defaultDir.replace("/", File.separator);
		if(!defaultDir.startsWith(File.separator)){
			defaultDir=File.separator+defaultDir;
		}
		if(!defaultDir.endsWith(File.separator)){
			defaultDir=defaultDir+File.separator;
		}
		
		String fileMaxSizeStr = getInitParameter("fileMaxSize");
		if (Validation.isEmpty(fileMaxSizeStr)){
			fileMaxSizeStr = "15";
		}
		fileMaxSize=Integer.parseInt(fileMaxSizeStr);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException, IOException {
		request.setCharacterEncoding("GBK");
		response.setContentType(CONTENT_TYPE);
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException, IOException {
		request.setCharacterEncoding("GBK");
		response.setContentType(CONTENT_TYPE);
		// 从请求参数中获取保存路径
		String saveDir = request.getParameter("saveDir");
		if (Validation.isEmpty(saveDir)) {
			saveDir = defaultDir;
		}
		// 设定上传文件保存路径
		String currentPath = saveDir + File.separator
				+ DateFunc.getNowDateStr("yyyy-MM");
		// 获得web应用的上传路径
		String currentDirPath = gpkDir+File.separator+currentPath;
		// 判断文件夹是否存在，不存在则创建
		File dirTest = new File(currentDirPath);
		if (!dirTest.exists()) {
			dirTest.mkdirs();
		}
		// 文件名和文件真实路径
		String fileUrl = "";
		//设置commons-fileupload组件的临时工作目录
		File temp=new File(getServletContext().getRealPath("/temp"));
		if(!temp.exists()){
			temp.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory(4096, temp);
        ServletFileUpload upload = new ServletFileUpload(factory);
//		DiskFileUpload upload = new DiskFileUpload();
		upload.setSizeMax(fileMaxSize*1024*1024);//设置允许的文件大小
//		upload.setSizeThreshold(4096);
//		upload.setRepositoryPath(getServletContext().getRealPath("/temp"));
		FileItem uplFile = null;
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					uplFile = item;
					break;
				}
			}
			if (null != uplFile) {
				// 获取文件名并做处理
				String fileNameLong = uplFile.getName();
				fileNameLong = fileNameLong.replace('\\', '/');
				String[] pathParts = fileNameLong.split("/");
				String fileName = pathParts[pathParts.length - 1];
				// 获取文件扩展名
				String ext = getExtension(fileName);
				// 设置上传文件名
				fileName = UUID.randomUUID().toString() + "." + ext;
				fileUrl = currentPath + File.separator + fileName;
				uplFile.write(new File(dirTest, fileName));
			} else {
				JsonUtil.writeJsonMsg(response, false, "没有上传任何文件，请选择文件后再点击上传！");
				return;
			}
		} catch (FileUploadException e) {
			JsonUtil.writeJsonMsg(response, false, "上传的文件超出允许的文件大小，允许的最大文件为"+fileMaxSize+"M！");
			return;
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "上传文件的过程中出现错误！");
			return;
		}
		String dispatcherUrl=request.getParameter("dispatcherUrl");
		if(!Validation.isEmpty(dispatcherUrl)){
			request.setAttribute("fileUrl", fileUrl.replace("\\", "/"));
			request.getRequestDispatcher(dispatcherUrl).forward(request, response);
		}else{
			JsonUtil.writeJsonMsg(response, true, "上传文件成功！", fileUrl.replace("\\", "/"));
		}
	}
	
	/**
	 * 获取扩展名的方法
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
