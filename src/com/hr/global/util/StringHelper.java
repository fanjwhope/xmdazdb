package com.hr.global.util;

import java.util.Date;
import java.util.regex.Pattern;

public class StringHelper {
	public static String getFieldSql(Object field) {
		if (field == null) {
			return null;
		} else if (field instanceof Integer || field instanceof Long
				|| field instanceof Short || field instanceof Double
				|| field instanceof Float) {
			return String.valueOf(field);
		} else if (field instanceof Date) {
			return "'" + DateFunc.dateToStr((Date) field, "yyyy-MM-dd") + "'";
		} else {
			return "'" + field + "'";
		}
	}
	
	/**
	 * ��ȡ�ֶ�ת����sql������ʽ
	 * @param field
	 * @param empty2null �Ƿ񽫿մ�ת����null
	 * @return
	 */
	public static String getFieldSql(Object field, boolean empty2null) {
		if (field == null) {
			return null;
		} else if (field instanceof Integer || field instanceof Long
				|| field instanceof Short || field instanceof Double
				|| field instanceof Float) {
			return String.valueOf(field);
		} else if (field instanceof Date) {
			return "'" + DateFunc.dateToStr((Date) field, "yyyy-MM-dd") + "'";
		} else {
			if(empty2null&&"".equals(field)){
				return null;
			}
			return "'" + field + "'";
		}
	}
	
	/**
	 * ���strΪnull�򷵻ؿմ�������ֱ�ӷ��ش�����ַ���
	 * @param str
	 * @return
	 */
	public static String null2EmptyStr(Object str){
		if(null==str){
			return "";
		}
		return str.toString();
	}
	
	/**
	 * ���strΪ�մ��򷵻�null������ֱ�ӷ��ش�����ַ���
	 * @param str
	 * @return
	 */
	public static String empty2NullStr(String str){
		if("".equals(str)){
			return null;
		}
		return str;
	}

	/**
	 * ȥ��html����
	 * 
	 * @param inputString
	 * @return
	 */
	public static String HtmltoText(String inputString) {
		String htmlStr = inputString; // ��html��ǩ���ַ���
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_ba;
		java.util.regex.Matcher m_ba;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // ����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // ����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // ����script��ǩ

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // ����style��ǩ

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // ����html��ǩ

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // ���˿ո�

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// �����ı��ַ���
	}
	
	/**
	 * �ж�ĳ���ַ����Ƿ������һ���ַ���������
	 * @param arr
	 * @param str
	 * @param isIgnoreCase �Ƿ���Դ�Сд
	 * @return
	 */
	public static boolean ifStringInArray(String[] arr, String str, boolean isIgnoreCase){
		if(null==arr||null==str){
			return false;
		}
		for(String temp:arr){
			if(isIgnoreCase){//���Դ�Сд
				if(str.equalsIgnoreCase(temp)){
					return true;
				}
			}else{//���Ǵ�Сд
				if(str.equals(temp)){
					return true;
				}
			}
		}
		return false;
	}
}
