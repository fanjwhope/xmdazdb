package com.hr.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



public class OpRequestWrap extends HttpServletRequestWrapper {
      
	
	 public OpRequestWrap(HttpServletRequest request) {
	        super(request);
	    }
	 
	    private static String format(String name) {
	    	if(name.length()<=200){
	    		String str=name.toLowerCase();
	    		if(cleanSql(str).equals("true")){//true表示含有关键字
	    			return "Dangerous characters in parameter! ";
	    		}else{
	    			return name;
	    		}
	    	}else{
	    		return name;
	    	}
	    }
	    
	    private static String cleanSql(String name){
	    	String value=new String(name);
	    	String keys="insert|select|'|chr(|*|update|delete|and|or|where|create|truncate|from|alter";
	    	String[] keyarray = keys.split("[|]");
	    	boolean flag=false;//是否含有sql关键字
	    	for(int i=0;i<keyarray.length;i++){
	    		if(name.indexOf(keyarray[i])!=-1){
	    			flag=true;//是
		    		value=value.replace(keyarray[i], "");
		    	}
	    	}
	    	if(flag){
	    		return "true";
	    	}else{
	    		return value;
	    	}
	    }
	    
	    /**
	     *
	     * @param name
	     * @return
	     */
	    @Override
	    public Object getAttribute(String name) {
	        Object value = super.getAttribute(name);
	        if (value instanceof String) {
	            value = format(String.valueOf(value));
	        }
	        return value;
	    }
	    
	    @Override
	    public String getHeader(String name) {
	        String value = super.getHeader(name);
	        if (value == null)
	            return null;
	        return format(value);
	    }
	 
	    /**
	     * 重写getParameter方法
	     *
	     * @param name
	     * @return
	     */
	    @Override
	    public String getParameter(String name) {
	        String value = super.getParameter(name);
	        if (value == null)
	            return null;
	        return format(value);
	    }
	 
	    /**
	     *
	     * @param name
	     * @return
	     */
	    @Override
	    public String[] getParameterValues(String name) {
	        String[] values = super.getParameterValues(name);
	        if (values != null) {
	            for (int i = 0; i < values.length; i++) {
	                values[i] = format(values[i]);
	            }
	        }
	        return values;
	    }
	    
	   /* @Override
	    public Map  getParameterMap(){
	    	Map properties = super.getParameterMap();
	        // 返回值Map
	        Iterator entries = properties.entrySet().iterator();
	        Map.Entry entry;
	        String name = "";
	        String value = "";
	        while (entries.hasNext()) {
	            entry = (Map.Entry) entries.next();
	            name = (String) entry.getKey();
	            Object valueObj = entry.getValue();
	            if(null == valueObj){
	            	value = "";
	            }else if(valueObj instanceof String[]){
	                String[] values = (String[])valueObj;
	                for(int i=0;i<values.length;i++){
	                    value = format(values[i]) + ",";
	                }
	                valueObj = value.substring(0, value.length()-1);
	            }else{
	                value = format(valueObj.toString());
	            }
	            ((Entry) entries).setValue(value);
	        }
	        return properties;
	    }*/
	 
	    /**
	     * @return
	     */
		/*public Map<String,?> getParameterMap() {
	 
	        HashMap<String,?> paramMap = (HashMap<String,?>) super.getParameterMap();
	        paramMap = (HashMap<String,?>) paramMap.clone();
	 
	        for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
	            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>) iterator.next();
	            String [] values = entry.getValue();
	            for (int i = 0; i < values.length; i++) {
	                if(values[i] instanceof String){
	                    values[i] = format(values[i]);
	                }
	            }
	            entry.setValue(values);
	        }
	        return paramMap;
	    }*/
	    public static void main(String[] args) {
			System.out.println(format("deleteselectUpdatedrop123selectchr(39)"));
	    	//System.out.println("delchr(39)slad".replace("chr(", ""));
	    	//System.out.println("Update1".toLowerCase().replace("update", ""));
			//System.out.println("1|123|1111".split("|").length);
		}
	    
	}

