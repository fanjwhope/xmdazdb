package com.hr.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MyTools {
	/**
     * 固定秘钥
     */
    static String KEY="gtrj";

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
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
     * MD5方法
     *
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text + key);
        return encodeStr;
    }
}
