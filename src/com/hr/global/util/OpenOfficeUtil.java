package com.hr.global.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class OpenOfficeUtil {

	private static String OpenOfficeBasePath = "C:\\Program Files\\OpenOffice 4";

	private static final String cmd_service = "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";

	/**
	 * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice下载地址为
	 * http://www.openoffice.org/
	 * 
	 * <pre>
	 * 方法示例: 
	 * String sourcePath = "F:\\office\\source.doc"; 
	 * String destFile = "F:\\pdf\\dest.pdf"; 
	 * Converter.office2PDF(sourcePath, destFile);
	 * </pre>
	 * 
	 * @param sourceFile
	 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
	 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc
	 * @param destFile
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
	 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0,
	 *         则表示操作成功; 返回1, 则表示转换失败
	 * @throws IOException
	 */
	public static Boolean office2PDF(String sourceFile, String destFile)
			throws IOException {

		File inputFile = new File(sourceFile);
		if (!inputFile.exists()) {
			throw new FileNotFoundException("sourceFile所指定的文件不存在！");// 找不到源文件,
																	// 则返回-1
		}
		// 如果目标路径不存在, 则新建该路径
		File outputFile = new File(destFile);
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		String OpenOffice_HOME = OpenOfficeBasePath;// 这里是OpenOffice的安装目录,
		// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
		if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
			OpenOffice_HOME += "\\";
		}
		// 启动OpenOffice的服务指令
		String command = OpenOffice_HOME + cmd_service;
		System.out.println(command);

		OpenOfficeConnection connection = null;
		DocumentConverter converter = null;
		Process pro = null;
		try {
			// connect to an OpenOffice.org instance running on port 8100
			connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
			connection.connect();

			converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
		} catch (ConnectException e) {
			pro = Runtime.getRuntime().exec(command);
			connection.connect();
			converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
		} finally {
			if (null != connection) {
				// close the connection
				connection.disconnect();
				connection = null;
			}
			if (null != pro) {
				// 关闭OpenOffice服务的进程
				pro.destroy();
			}
		}
		return true;
	}

	public static String getOpenOfficeBasePath() {
		return OpenOfficeBasePath;
	}

	public static void setOpenOfficeBasePath(String openOfficeBasePath) {
		OpenOfficeBasePath = openOfficeBasePath;
	}

	 public static void main(String args[]) {
	 try {
	 Boolean res=office2PDF(
	 "C:\\Documents and Settings\\Administrator\\桌面\\aa.odt",
	 "C:\\Documents and Settings\\Administrator\\桌面\\222.pdf");
	 System.out.println(res);
	 } catch (Exception e) {
	 e.printStackTrace();
	 }
	 }

}