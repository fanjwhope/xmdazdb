package com.hr.global.util;

import java.text.DateFormat;
import java.util.StringTokenizer;

public class Validation {

	/**
	 * �ж��ַ����Ƿ��ǿմ�����null
	 * 
	 * @param str
	 * @return ����ַ����ǿմ�����null����true������false
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str)) {
			return false;
		}
		return true;
	}

	/**
	 * �ж��ַ��������Ƿ��ǵ����ʼ���ַ��ʽ
	 * 
	 * @param email
	 * @return �ַ��������ǵ����ʼ���ַ��ʽ����true�����򷵻�false
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
	 * �ж��ַ��������Ƿ���������ʽ
	 * 
	 * @param value
	 * @return �ַ���������������ʽ����true�����򷵻�false
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
	/**����ΰ��ӷ���
	 * �ж��ַ��������Ƿ��ǳ�������ʽ
	 * 
	 * @param value
	 * @return �ַ���������������ʽ����true�����򷵻�false
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
	 * �ж��ַ��������Ƿ���Short����
	 * 
	 * @param value
	 * @return �ַ���������Short���ͷ���true�����򷵻�false
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
	 * �ж��ַ��������Ƿ��ǲ�����
	 * 
	 * @param value
	 * @return �ַ��������ǲ����ͷ���true�����򷵻�false
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
	 * �ж��ַ��������Ƿ��Ǹ�������ʽ
	 * 
	 * @param value
	 * @return �ַ��������Ǹ�������ʽ����true�����򷵻�false
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
	 * �ж��ַ��������Ƿ��Ǹ�������ʽ
	 * 
	 * @param value
	 * @return �ַ��������Ǹ�������ʽ����true�����򷵻�false
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
	 * �ж��ַ��������Ƿ������ָ�ʽ
	 * 
	 * @param email
	 * @return �ַ������������ָ�ʽ����true�����򷵻�false
	 */
	public static boolean isNumber(String value) {
		return isDouble(value);
	}

	/**
	 * �ж��ַ��������Ƿ������ڸ�ʽ
	 * 
	 * @param email
	 * @return �ַ������������ڸ�ʽ����true�����򷵻�false
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
