package com.hr.global.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception������
 * @author �Ʒ�
 *
 */
public class ExceptionUtil {

	/**
	 * ���ش�����Ϣ�ַ���
	 * 
	 * @param ex
	 *            Exception
	 * @return ������Ϣ�ַ���
	 */
	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String errorMessage = sw.toString();
		pw.close();
		try {
			sw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return errorMessage;
	}

}
