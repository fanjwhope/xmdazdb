package com.hr.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MyTools {
	/**
     * �̶���Կ
     */
    static String KEY="gtrj";

    /**
     * MD5����
     *
     * @param text ����
     * @return ����
     * @throws Exception
     */
    public static String md5(String text)  {
        try {
            return md5(text, KEY);
        }catch (Exception e){
            e.printStackTrace();
            return text;
        }
    }

    /**
     * MD5����
     *
     * @param text ����
     * @param key ��Կ
     * @return ����
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        //���ܺ���ַ���
        String encodeStr= DigestUtils.md5Hex(text + key);
        return encodeStr;
    }
}
