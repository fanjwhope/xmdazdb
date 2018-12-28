package com.hr.global.util;
import java.security.*;
import com.hr.util.Md5;
public class HashMD5 {

    public HashMD5() {
    }


    public static String MD5(String str){
            String returnStr =null;
            try {
                        MessageDigest alga=MessageDigest.getInstance("MD5");
                        alga.update(str.getBytes());
                        byte[] digesta=alga.digest();
                        returnStr = byte2hex(digesta);
                  }catch (NoSuchAlgorithmException ex){
                         returnStr =Md5.md5(20);
                  }
                  return returnStr;
      }

      public static String byte2hex(byte[] b) {             //二行制转字符串

                String hs="";
                String stmp="";
                for (int n=0;n<b.length;n++) {
                stmp=(Integer.toHexString(b[n] & 0XFF));
                if (stmp.length()==1) hs=hs+"0"+stmp;
                else hs=hs+stmp;
                if (n<b.length-1) hs=hs+"";
                }
                return hs;
      }
      public static  void main(String[] args){

      }
}
