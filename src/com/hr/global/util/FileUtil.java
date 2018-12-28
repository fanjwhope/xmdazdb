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
	 * ���ص����ļ�
	 * @param response
	 * @param downloadName �ļ����ص��ͻ��˺���ʾ������
	 * @param file �����ص��ļ�
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
	 * ���ڴ����û����ļ��ı����ݡ����ϴ����ļ����棬���������ݷ�װ��һ��map
	 * @param request
	 * @param fileSaveDir keyΪ�ļ����ֶε�name����ֵ��valueΪ�ļ�����Ŀ¼���ƣ���������·����������û��Ϊĳ���ļ����ø�ֵ�Ļ��ͱ��浽Ĭ��Ŀ¼��
	 * @param fileNameWithoutSuffix keyΪ�ļ����ֶε�name����ֵ��valueΪ�ļ����ƣ������׺��������û��Ϊĳ���ļ����ø�ֵ�Ļ���ʹ�ÿͻ��˵��ļ���
	 * @return �����ݷ�װ��map����map�У��ļ��ֶε�value�Ǹ��ļ���������GPK��·�������ļ�������
	 * ���ص�map�л����⺬��һ������formHandleMessage��key�����汣���Ŵ����ʱ���ܳ��ֵĴ�����Ϣ��
	 */
	public static Map<String, String> handleFormWithFile(HttpServletRequest request, Map<String, String> fileSaveDir, Map<String, String> fileNameWithoutSuffix){
		return handleFormWithFile(request, fileSaveDir, fileNameWithoutSuffix, null);
	}
	
	/**
	 * ���ڴ����û����ļ��ı����ݡ����ϴ����ļ����棬���������ݷ�װ��һ��map
	 * @param request
	 * @param fileSaveDir keyΪ�ļ����ֶε�name����ֵ��valueΪ�ļ�����Ŀ¼���ƣ���������·����������û��Ϊĳ���ļ����ø�ֵ�Ļ��ͱ��浽Ĭ��Ŀ¼��
	 * @param fileNameWithoutSuffix keyΪ�ļ����ֶε�name����ֵ��valueΪ�ļ����ƣ������׺��������û��Ϊĳ���ļ����ø�ֵ�Ļ���ʹ�ÿͻ��˵��ļ���
	 * @param encoding ������ʱ���õı����ʽ
	 * @return �����ݷ�װ��map����map�У��ļ��ֶε�value�Ǹ��ļ���������GPK��·�������ļ�������
	 * ���ص�map�л����⺬��һ������formHandleMessage��key�����汣���Ŵ����ʱ���ܳ��ֵĴ�����Ϣ��
	 */
	public static Map<String, String> handleFormWithFile(HttpServletRequest request, Map<String, String> fileSaveDir, Map<String, String> fileNameWithoutSuffix, String encoding){
		Map<String, String> formData=new HashMap<String, String>();	
		String zdbdbName=request.getParameter("zdbdbName");
		BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
		String gpkDir=op.queryRowAndCol("select path from zdb_wsda_gpk where flag='y'")[0][0];//�����ݿ��ѯgpkʹ��
		/*LocationDao dao = new LocationDao(tableName);
		String gpkDir=dao.getPath();*/
		
		//��ȡ�ļ�Ĭ�ϴ��·���������ϴ��ļ���С�Ĳ���
		String defaultDir = request.getSession().getServletContext().getInitParameter("defaultDir");
		if (Validation.isEmpty(defaultDir)){
			defaultDir = ArchiveUtil.getDataBaseName(request.getSession())+d.fgh()+"xmda";
		}
		int fileMaxSize=1024;
		String fileMaxSizeStr = request.getSession().getServletContext().getInitParameter("fileMaxSize");
		if (!Validation.isEmpty(fileMaxSizeStr)){
			fileMaxSize=Integer.parseInt(fileMaxSizeStr);
		}
		
		//��ʼ�����
		boolean haveFile=false;
		File temp=new File(request.getSession().getServletContext().getRealPath("/temp"));
		if(!temp.exists()){
			temp.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory(4096, temp);
        ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(fileMaxSize*1024*1024);//����������ļ���С
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
					haveFile=true;//�û��������ļ�
					
					//��ȡ�ļ�����Ŀ¼
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
					
					// ��ȡ�ļ���
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
					formData.put(fieldName, relativePath);//�����ļ����GPK��·����map
					
					//�����ļ�
					item.write(new File(gpkDir+relativePath));
					//ɾ����ͼƬ�ļ���
					String imgPath = gpkDir+File.separator+saveDir+File.separator+nameWithoutSuffix;
					File img = new File(imgPath);
					File[] files = img.listFiles();
					if(img.exists()){
						for(int i = 0; i < files.length; i++)
							files[i].delete(); //ɾ��ͼƬĿ¼�µ�ͼƬ�ļ�
						img.delete();    //ɾ��ͼƬ�����ļ���
					}
				}
			}
		} catch (FileUploadException e) {
			Log.error(e);
			formData.put("formHandleMessage", "�ϴ����ļ�����������ļ���С�����������ļ�Ϊ"+fileMaxSize+"M��");
			return formData;
		} catch (Exception e) {
			Log.error(e);
			formData.put("formHandleMessage", "�ϴ��ļ��Ĺ����г��ִ���");
			return formData;
		}
		
		if(!haveFile){
			formData.put("formHandleMessage", "û���ϴ��κ��ļ���");
		}
		return formData;
	}

	public static void move(String source, String destination)
			throws IOException {
		(new File(destination)).mkdirs();
		// ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
		File[] file = (new File(source)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// �����ļ�
				copyFile(file[i], new File(destination +File.separator+ file[i].getName()));
			}
			if (file[i].isDirectory()) {
				// ����Ŀ¼
				String sorceDir = source + File.separator + file[i].getName();
				String targetDir = destination + File.separator + file[i].getName();
				copyDirectiory(sorceDir, targetDir);
			}
		}

		del(source);
	}

	public static void copyFile(File sourcefile, File targetFile)
			throws IOException {

		// �½��ļ����������������л���
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);

		// �½��ļ���������������л���
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// ��������
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}

		// ˢ�´˻���������
		outbuff.flush();

		// �ر���
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();

	}

	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {

		// �½�Ŀ��Ŀ¼

		(new File(targetDir)).mkdirs();

		// ��ȡԴ�ļ��е��µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// Դ�ļ�
				File sourceFile = file[i];
				// Ŀ���ļ�
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());

				copyFile(sourceFile, targetFile);

			}

			if (file[i].isDirectory()) {
				// ׼�����Ƶ�Դ�ļ���
				String dir1 = sourceDir + file[i].getName();
				// ׼�����Ƶ�Ŀ���ļ���
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
		File f = new File(filepath);// �����ļ�·��
		if (f.exists() && f.isDirectory()) {// �ж����ļ�����Ŀ¼
			if (f.listFiles().length > 0) {// ��Ŀ¼��û���ļ���ֱ��ɾ��
				{// ��������ļ��Ž����飬���ж��Ƿ����¼�Ŀ¼
					File delFile[] = f.listFiles();
					int i = f.listFiles().length;
					for (int j = 0; j < i; j++) {
						if (delFile[j].isDirectory()) {
							del(delFile[j].getAbsolutePath());// �ݹ����del������ȡ����Ŀ¼·��
						}
						delFile[j].delete();// ɾ���ļ�
					}
				}
			}
			f.delete();
		}

	}
}
