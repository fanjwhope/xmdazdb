package com.hr.global.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;









import org.apache.commons.lang.StringUtils;

import com.hr.bean.User;
import com.hr.util.BaseDataOP;
import com.hr.util.ConfigInfo;

/**
 * 项目档案管理系统专用类
 * @author fei
 *
 */
public class ArchiveUtil {

	/**
	 * 获取当前用户所属的数据库名称
	 * @return
	 */
	public static String getDataBaseName(HttpSession session){
		User user=getCurrentUser(session); 
		return user.getDataBaseName();
	}
	
	/**
	 * 获取当前用户的单位号
	 * @return
	 */
	public static String getDepartmentCode(HttpSession session){
		User user=getCurrentUser(session);
		return user.getDwh();
	}
	
	/**
	 * 获取当前登录的用户
	 * @param session
	 * @return
	 */
	public static User getCurrentUser(HttpSession session){
		return (User) session.getAttribute("currentUser");
	}
 
	
	/**
	 * 移除当前登录的用户
	 * @param session
	 * @return
	 */
	public static void removeCurrentUser(HttpSession session){
		session.removeAttribute("currentUser");
	}
	
	/**
	 * 查找实体类对应的数据库表全名（即：数据库名.模式名.表名）。
	 * @param request
	 * @param entityClass
	 * @param archiveType 可选参数
	 * @param 项目档案只有一个库，将其他库合并到runAdmin库，修改后：表名=银行节点_表名。
	 * @return
	 */
	public static String getFullTableName(HttpServletRequest request, Class<?> entityClass, String... archiveType) {
		User currentUser=getCurrentUser(request.getSession());
		String dataBaseName=null;
		String fullName=null;
		String xmlDataBaseName=ConfigInfo.getVal("basicDataBase");
		String schema=ConfigInfo.getVal("schema");
		String tableName=null;
		if(null==currentUser){//用户登录前
			dataBaseName=request.getParameter("dataBaseName");
			if(null==dataBaseName){
				throw new RuntimeException("请确保HttpServletRequest请求中包含名为dataBaseName的参数！");
			}
			tableName = BuildSql.getTableName(entityClass);
		}else{//用户登录后
			dataBaseName=currentUser.getDataBaseName();
			tableName=parseTableName(request, entityClass, archiveType);
		}
		
		//多库时 String fullName= dataBaseName+"."+schema+"."+tableName;
		
		//一库时
		if(dataBaseName.equals(xmlDataBaseName)){
			fullName=tableName;
		}else{
			fullName=dataBaseName+"_"+tableName;
		}
		return fullName.toUpperCase();
	}
	
	/**
	 * 解析数据库表名称，将占位符变成实际值
	 * @param tableName
	 * @param currentUser
	 * @return
	 */
	private static String parseTableName(HttpServletRequest request, Class<?> entityClass, String... archiveType) {
		User currentUser=getCurrentUser(request.getSession());
		String tableName = BuildSql.getTableName(entityClass);
		if(tableName.contains("${DEPARTMENTCODE}")){
			tableName=tableName.replace("${DEPARTMENTCODE}", currentUser.getDwh());
		}
		
		if(tableName.contains("${ARCHIVETYPE}")){
			String value=null;
			if(null!=archiveType&&archiveType.length>0){
				value=archiveType[0];
			}else{
				value=request.getParameter("archiveType");
			}
			if(null==value){
				throw new RuntimeException("请确保HttpServletRequest请求中包含名为archiveType的参数，或者您也可以直接传递archiveType来调用获取表名的方法！");
			}
			tableName=tableName.replace("${ARCHIVETYPE}", value);
			if(tableName.startsWith("2002")){
				tableName=tableName.replaceFirst("2002", "xmlrb");
			}
		}
		return tableName;
	}
	
	/**
	 * 判断是否建表
	 * @param tableName
	 * @param tableField
	 */
	  public static void  createTable(String tableName,String[][] tableField){
		  BaseDataOP bd=new BaseDataOP(null);
		  String[] tab=tableName.split("\\.");
		  if(tab.length==1){
			  if (!bd.ifExistsTab(tableName)) {
					String createSQL = Constants.BuildCreateTabSql(tableName,
							tableField);
					bd.ExecSql(createSQL);
				}
		  }else if(tab.length==3){
			  if (!bd.ifExistsTab(tab[2],tab[0])) {
					String createSQL = Constants.BuildCreateTabSql(tableName,
							tableField);
					bd.ExecSql(createSQL);
				}
		  }
	    }
		
	  public  static  void  createTableAndLSH(String tableName,String[][] tableField,String[][]  lsh){
		  BaseDataOP bd=new BaseDataOP(null);
		  String[] tab=tableName.split("\\.");
		  if(tab.length==1){
			  if (!bd.ifExistsTab(tableName)) {
					String createSQL = Constants.BuildCreateTabSql(tableName,
							tableField);
					bd.ExecSql(createSQL);
				}
			  if(!bd.ifExistsTab("xmda_lsh")){
				  String createSQL = Constants.BuildCreateTabSql("xmda_lsh",
							lsh);
					bd.ExecSql(createSQL);
				}
		  }else if(tab.length==3){
			  if (!bd.ifExistsTab(tab[2],tab[0])) {
					String createSQL = Constants.BuildCreateTabSql(tableName,
							tableField);
					bd.ExecSql(createSQL);
				}
			  //创建流水表
			  String  lshTab=tab[0]+"."+tab[1]+"."+"xmda_lsh";
			  if (!bd.ifExistsTab("xmda_lsh",tab[0])) {
					String createSQL = Constants.BuildCreateTabSql(lshTab,
							lsh);
					bd.ExecSql(createSQL);
				}
		  }
	  }
	  
	  
	 /* public static String getIpAddr(HttpServletRequest request) {
	        String ip = request.getHeader("X-Real-IP");
	        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
	            return ip;
	        }
	        ip = request.getHeader("X-Forwarded-For");
	        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
	        // 多次反向代理后会有多个IP值，第一个为真实IP。
	        int index = ip.indexOf(',');
	            if (index != -1) {
	                return ip.substring(0, index);
	            } else {
	                return ip;
	            }
	        } else {
	             return request.getRemoteAddr();
	        }
	    }*/
	  
	  public static String getIpAddr(HttpServletRequest request) {  
		    String ip = request.getHeader("x-forwarded-for");  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("Proxy-Client-IP");  
		    }  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		    }  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		    }  
		    return ip;  
		} 
	  
	  public static String  arrToJson(String[][] obj,String fieldNames){
			StringBuffer json=new StringBuffer();
			for (int i = 0; i < obj.length; i++) {
				json.append("{");
				json.append(arrToJson(obj[i],fieldNames));
				json.append("},");
			}
			if(!Validation.isEmpty(json.toString())){
				json.deleteCharAt(json.length()-1);
			}
			return json.toString();
		}
		
		public static String  arrToJson(String[] obj,String fieldNames){
			StringBuffer json=new StringBuffer();
			String[] fieldName=fieldNames.split(",");
			for (int i = 0; i < fieldName.length; i++) {
				json.append("\""+fieldName[i]+"\":"+"\""+obj[i]+"\",");
			}
			if(!Validation.isEmpty(json.toString())){
				json.deleteCharAt(json.length()-1);
			}
			return json.toString();
		}
		
		public static String  arrToString(String[] arr){
			StringBuffer sb=new StringBuffer();
			if(arr!=null&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					sb.append(arr[i]+",");
				}
			}
			return sb.deleteCharAt(sb.length()-1).toString();
		}
	  
		/*  
		  * 判断是否为整数   
		  * @param str 传入的字符串   
		  * @return 是整数返回true,否则返回false   
		*/  
	  public static boolean isInteger(String str) {     
		   Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");     
		   return pattern.matcher(str).matches();     
		  }  
	  

}
