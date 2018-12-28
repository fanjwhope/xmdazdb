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
	 * ��Office�ĵ�ת��ΪPDF. ���иú�����Ҫ�õ�OpenOffice, OpenOffice���ص�ַΪ
	 * http://www.openoffice.org/
	 * 
	 * <pre>
	 * ����ʾ��: 
	 * String sourcePath = "F:\\office\\source.doc"; 
	 * String destFile = "F:\\pdf\\dest.pdf"; 
	 * Converter.office2PDF(sourcePath, destFile);
	 * </pre>
	 * 
	 * @param sourceFile
	 *            Դ�ļ�, ����·��. ������Office2003-2007ȫ����ʽ���ĵ�, Office2010��û����. ����.doc,
	 *            .docx, .xls, .xlsx, .ppt, .pptx��. ʾ��: F:\\office\\source.doc
	 * @param destFile
	 *            Ŀ���ļ�. ����·��. ʾ��: F:\\pdf\\dest.pdf
	 * @return �����ɹ�������ʾ��Ϣ. ������� -1, ��ʾ�Ҳ���Դ�ļ�, ��url.properties���ô���; ������� 0,
	 *         ���ʾ�����ɹ�; ����1, ���ʾת��ʧ��
	 * @throws IOException
	 */
	public static Boolean office2PDF(String sourceFile, String destFile)
			throws IOException {

		File inputFile = new File(sourceFile);
		if (!inputFile.exists()) {
			throw new FileNotFoundException("sourceFile��ָ�����ļ������ڣ�");// �Ҳ���Դ�ļ�,
																	// �򷵻�-1
		}
		// ���Ŀ��·��������, ���½���·��
		File outputFile = new File(destFile);
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		String OpenOffice_HOME = OpenOfficeBasePath;// ������OpenOffice�İ�װĿ¼,
		// ������ļ��ж�ȡ��URL��ַ���һ���ַ����� '\'�������'\'
		if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
			OpenOffice_HOME += "\\";
		}
		// ����OpenOffice�ķ���ָ��
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
				// �ر�OpenOffice����Ľ���
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
	 "C:\\Documents and Settings\\Administrator\\����\\aa.odt",
	 "C:\\Documents and Settings\\Administrator\\����\\222.pdf");
	 System.out.println(res);
	 } catch (Exception e) {
	 e.printStackTrace();
	 }
	 }

}