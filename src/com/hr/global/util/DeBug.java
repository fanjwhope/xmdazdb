package com.hr.global.util;

import com.hr.util.ConfigInfo;
import com.hr.util.Log;

public class DeBug {
	private static boolean error=false;
	private static boolean debug=false;
	static{
		error=ConfigInfo.getbVal("error");
		debug=ConfigInfo.getbVal("debug");
	}
	public static void error(Object o){
		if(error){
			if(o instanceof Exception)Log.error((Exception)o);
			else Log.error(o.toString());
		}
	}
	public static void debug(Object o){
		if(debug){
			Log.debug(o);
		}
	}
	public static void main(String[] args){
		System.out.println(ConfigInfo.getbVal("error")==true);
	}
}

