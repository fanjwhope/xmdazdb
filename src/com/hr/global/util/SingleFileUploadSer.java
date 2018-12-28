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
	private static String defaultDir;// Ĭ�ϴ��Ŀ¼
	private static int fileMaxSize;//������ļ���С
	private static String gpkDir;//ϵͳ�����ϴ��ļ�������Ŵ�
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		BaseDataOP op = new BaseDataOP(ConnectionPool.getPool());
		gpkDir=d.getGpkSavePath(op);
		// ��web.xml�л�ȡ��Ŀ¼����
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
		// ����������л�ȡ����·��
		String saveDir = request.getParameter("saveDir");
		if (Validation.isEmpty(saveDir)) {
			saveDir = defaultDir;
		}
		// �趨�ϴ��ļ�����·��
		String currentPath = saveDir + File.separator
				+ DateFunc.getNowDateStr("yyyy-MM");
		// ���webӦ�õ��ϴ�·��
		String currentDirPath = gpkDir+File.separator+currentPath;
		// �ж��ļ����Ƿ���ڣ��������򴴽�
		File dirTest = new File(currentDirPath);
		if (!dirTest.exists()) {
			dirTest.mkdirs();
		}
		// �ļ������ļ���ʵ·��
		String fileUrl = "";
		//����commons-fileupload�������ʱ����Ŀ¼
		File temp=new File(getServletContext().getRealPath("/temp"));
		if(!temp.exists()){
			temp.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory(4096, temp);
        ServletFileUpload upload = new ServletFileUpload(factory);
//		DiskFileUpload upload = new DiskFileUpload();
		upload.setSizeMax(fileMaxSize*1024*1024);//����������ļ���С
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
				// ��ȡ�ļ�����������
				String fileNameLong = uplFile.getName();
				fileNameLong = fileNameLong.replace('\\', '/');
				String[] pathParts = fileNameLong.split("/");
				String fileName = pathParts[pathParts.length - 1];
				// ��ȡ�ļ���չ��
				String ext = getExtension(fileName);
				// �����ϴ��ļ���
				fileName = UUID.randomUUID().toString() + "." + ext;
				fileUrl = currentPath + File.separator + fileName;
				uplFile.write(new File(dirTest, fileName));
			} else {
				JsonUtil.writeJsonMsg(response, false, "û���ϴ��κ��ļ�����ѡ���ļ����ٵ���ϴ���");
				return;
			}
		} catch (FileUploadException e) {
			JsonUtil.writeJsonMsg(response, false, "�ϴ����ļ�����������ļ���С�����������ļ�Ϊ"+fileMaxSize+"M��");
			return;
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "�ϴ��ļ��Ĺ����г��ִ���");
			return;
		}
		String dispatcherUrl=request.getParameter("dispatcherUrl");
		if(!Validation.isEmpty(dispatcherUrl)){
			request.setAttribute("fileUrl", fileUrl.replace("\\", "/"));
			request.getRequestDispatcher(dispatcherUrl).forward(request, response);
		}else{
			JsonUtil.writeJsonMsg(response, true, "�ϴ��ļ��ɹ���", fileUrl.replace("\\", "/"));
		}
	}
	
	/**
	 * ��ȡ��չ���ķ���
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
