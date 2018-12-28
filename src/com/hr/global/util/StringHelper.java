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
	 * 获取字段转换成sql语句的样式
	 * @param field
	 * @param empty2null 是否将空串转换成null
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
	 * 如果str为null则返回空串，否则直接返回传入的字符串
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
	 * 如果str为空串则返回null，否则直接返回传入的字符串
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
	 * 去除html代码
	 * 
	 * @param inputString
	 * @return
	 */
	public static String HtmltoText(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
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
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // 过滤空格

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}
	
	/**
	 * 判断某个字符串是否存在于一个字符串数组中
	 * @param arr
	 * @param str
	 * @param isIgnoreCase 是否忽略大小写
	 * @return
	 */
	public static boolean ifStringInArray(String[] arr, String str, boolean isIgnoreCase){
		if(null==arr||null==str){
			return false;
		}
		for(String temp:arr){
			if(isIgnoreCase){//忽略大小写
				if(str.equalsIgnoreCase(temp)){
					return true;
				}
			}else{//考虑大小写
				if(str.equals(temp)){
					return true;
				}
			}
		}
		return false;
	}
}
