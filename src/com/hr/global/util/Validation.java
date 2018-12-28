package com.hr.global.util;

import java.text.DateFormat;
import java.util.StringTokenizer;

public class Validation {

	/**
	 * 判断字符串是否是空串或者null
	 * 
	 * @param str
	 * @return 如果字符串是空串或者null返回true，否则false
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串参数是否是电子邮件地址格式
	 * 
	 * @param email
	 * @return 字符串参数是电子邮件地址格式返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		if (email.indexOf("list:") != -1)
			email = email.substring(5, email.length());
		if (isEmpty(email))
			return false;
		String[] invalid_c = { "~", "`", "!", "#", "$", "%", "^", "&", "*",
				"(", ")", "+", "=", "<", ">", "?", " ", ":", ";", "\"", "{",
				"}", "[", "]", "\\", "|", "/" };
		for (int i = 0; i < email.length() - 1; i++) {
			if (email.regionMatches(true, i, "@@", 0, 2))
				return false;
		}
		for (int j = 0; j < invalid_c.length; j++) {
			for (int i = 0; i < email.length(); i++) {
				if (email.regionMatches(true, i, invalid_c[j], 0, 1))
					return false;
			}
		}
		if ((email.substring(0, 1).equals("@"))
				|| (email.substring(email.length() - 1, email.length())
						.equals("@")))
			return false;
		StringTokenizer st = new StringTokenizer(email, "@");
		if (st.countTokens() != 2)
			return false;
		st.nextElement();
		StringTokenizer st1 = new StringTokenizer((String) st.nextElement(),
				".");
		return st1.countTokens() >= 2;
	}

	/**
	 * 判断字符串参数是否是整数格式
	 * 
	 * @param value
	 * @return 字符串参数是整数格式返回true，否则返回false
	 */
	public static boolean isInteger(String value) {
		if(null==value){
			return false;
		}
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}
	/**兰中伟添加方法
	 * 判断字符串参数是否是长整数格式
	 * 
	 * @param value
	 * @return 字符串参数是整数格式返回true，否则返回false
	 */
	public static boolean isLong(String value) {
		if(null==value){
			return false;
		}
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}
	/**
	 * 判断字符串参数是否是Short类型
	 * 
	 * @param value
	 * @return 字符串参数是Short类型返回true，否则返回false
	 */
	public static boolean isShort(String value) {
		if(null==value){
			return false;
		}
		try {
			Short.parseShort(value);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}
	
	/**
	 * 判断字符串参数是否是布尔型
	 * 
	 * @param value
	 * @return 字符串参数是布尔型返回true，否则返回false
	 */
	public static boolean isBoolean(String value) {
		if(null==value){
			return false;
		}
		try {
			Boolean.parseBoolean(value);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	/**
	 * 判断字符串参数是否是浮点数格式
	 * 
	 * @param value
	 * @return 字符串参数是浮点数格式返回true，否则返回false
	 */
	public static boolean isDouble(String value) {
		if(null==value){
			return false;
		}
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}
	
	/**
	 * 判断字符串参数是否是浮点数格式
	 * 
	 * @param value
	 * @return 字符串参数是浮点数格式返回true，否则返回false
	 */
	public static boolean isFloat(String value) {
		if(null==value){
			return false;
		}
		try {
			Float.parseFloat(value);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	/**
	 * 判断字符串参数是否是数字格式
	 * 
	 * @param email
	 * @return 字符串参数是数字格式返回true，否则返回false
	 */
	public static boolean isNumber(String value) {
		return isDouble(value);
	}

	/**
	 * 判断字符串参数是否是日期格式
	 * 
	 * @param email
	 * @return 字符串参数是日期格式返回true，否则返回false
	 */
	public static boolean isDate(String str) {
		if(null==str){
			return false;
		}
		boolean flag = true;
		try {
			DateFormat.getDateInstance().parse(str);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
