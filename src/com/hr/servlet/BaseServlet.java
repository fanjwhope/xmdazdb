package com.hr.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.data.Session_Bean;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.global.util.Validation;
import com.hr.oa.SqlSet;
import com.hr.tool.project.dao.BaseDao;
import com.hr.util.BaseDataOP;
import com.hr.util.ConfigInfo;
import com.hr.util.ConnectionPool;
import com.hr.util.Log;


public class BaseServlet extends HttpServlet {
	public static final long serialVersionUID = 1L;
	public static final String CONTENT_TYPE = "text/html; charset=GBK";

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleEncoding(request, response);
		//从请求路径中获取表名、执行类名、执行方法名开始
		String pathInfo=request.getPathInfo();
		int index=pathInfo.lastIndexOf("/");
		if(index<1){
			response.getWriter().println("您的请求路径没有包含足够的信息！");
			return;
		}
		if(pathInfo.startsWith("/")){
			pathInfo=pathInfo.substring(1);
		}
		String[] pathArr=pathInfo.split("/");
		String packageName=ConfigInfo.getVal("packageName");
		String classPath=packageName;
		for(int i=0; i<pathArr.length-1; i++){
			classPath=classPath+"."+pathArr[i];
		}
		String methodName=pathArr[pathArr.length-1];
		//从请求路径中获取表名、执行类名、执行方法名结束
			
		//实例化执行类开始
		Class<?> actionClass=null;
		Object action=null;
		try {
			actionClass=Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			if(pathArr.length==2){//调用本servlet的方法
				executeBaseMethod(request, response, methodName, pathArr[0]);
			}else{
				response.getWriter().println("您指定的执行类【"+classPath+"】不存在！");
				Log.error(e);
			}
			return;
		}
		try {
			action=actionClass.newInstance();
		} catch (Exception e) {
			response.getWriter().println("在实例化您指定的执行类【"+classPath+"】时出现错误，请确保该类有无参的构造方法！");
			Log.error(e);
			return;
		}
		//实例化执行类结束
		
		//获取执行方法开始
		Method method = null;
		try {
			method = action.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		} catch (SecurityException e) {
			response.getWriter().println("您指定的执行方法【"+methodName+"】不允许访问！");
			Log.error(e);
			return;
		} catch (NoSuchMethodException e) {
			response.getWriter().println("您指定的执行方法【"+methodName+"】不存在！");
			Log.error(e);
			return;
		}
		//获取执行方法结束
		
		//调用执行方法，进行业务逻辑处理
		Object view=null;
		try {
			view=method.invoke(action, request, response);
		} catch (Exception e) {
			response.getWriter().println("调用执行方法【"+methodName+"】时出现错误！");
			Log.error(e);
			return;
		}
		if(null!=view && view instanceof String){
			request.getRequestDispatcher((String)view).forward(request, response);
		}
	}
	
	/**
	 * 新增或者修改记录
	 */
	public void addOrUpdate(HttpServletRequest request, HttpServletResponse response, String tableName) {
		try {
			BaseDao baseDao = new BaseDao();
			baseDao.addOrUpdate(request, null, tableName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "操作记录失败！");
		}
		JsonUtil.writeJsonMsg(response, true, "操作记录成功！");
	}
	
	/**
	 * 新增记录
	 */
	public void add(HttpServletRequest request, HttpServletResponse response, String tableName) {
		try {
			BaseDao baseDao = new BaseDao();
			baseDao.add(request, null, tableName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "添加记录失败！");
		}
		JsonUtil.writeJsonMsg(response, true, "添加记录成功！");
	}

//	/**
//	 * 跳转新增记录页面
//	 */
//	public String addUI(HttpServletRequest request, HttpServletResponse response, String tableName) {
//		String url=request.getParameter("url");
//		if(!Validation.isEmpty(url)){
//			return url;
//		}
//		return null;
//	}

	/**
	 * 修改记录
	 */
	public void update(HttpServletRequest request, HttpServletResponse response, String tableName) {
		try {
			BaseDao baseDao = new BaseDao();
			baseDao.update(request, null, tableName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "修改记录失败！");
		}
		JsonUtil.writeJsonMsg(response, true, "修改记录成功！");
	}

//	/**
//	 * 跳转修改记录页面
//	 */
//	public String updateUI(HttpServletRequest request,
//			HttpServletResponse response, String tableName) {
//		String id = request.getParameter("id");
//		BaseDao baseDao = new BaseDao();
//		MyHash myHash=new MyHash();
//		try {
//			if(!Validation.isEmpty(id)){
//				myHash = baseDao.find(id, tableName);
//			}
//		} catch (Exception e) {
//			Log.error(e);
//		}
//		request.setAttribute("map", myHash);
//
//		String url=request.getParameter("url");
//		if(!Validation.isEmpty(url)){
//			return url;
//		}
//		return null;
//	}

	/**
	 * 删除记录
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response, String tableName) {
		String id = request.getParameter("id");
		BaseDao baseDao = new BaseDao();
		try {
			baseDao.delete(request, id, null, tableName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "删除记录失败！");
		}
		JsonUtil.writeJsonMsg(response, true, "删除记录成功！");
	}
	
	public void list(HttpServletRequest request, HttpServletResponse response, String tableName){
		List<Map<String, String>> list=new ArrayList<Map<String,String>>(0);
		BaseDao baseDao = new BaseDao();
		PagingBean pagingBean = new PagingBean(request);
		try {
			list=baseDao.queryList(request, null, tableName, pagingBean);
		 }catch (Exception e) {
			Log.error(e);	
		 }
		JsonUtil.writeJsonGrid(response, list, pagingBean);
	}
	
	public static <T> T getObject(Class<T> classObj, HttpServletRequest request) {
		T obj = null;
		try {
			obj = BeanHelper.getValuesByRequest(request,
					classObj, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return obj;
	}

	public String getMethod(HttpServletRequest request) {
		String method = request.getParameter("method");
		if (Validation.isEmpty(method)) {
			method = "listUI";
		}
		return method;
	}

	public static int getCurrentUserId(HttpServletRequest request) {
		Session_Bean sessionBean = (Session_Bean) request.getSession()
				.getAttribute("bean_sess");
		return sessionBean.getUserid_Sess();
	}

	public static String getCurrentUserName(HttpServletRequest request) {
		Session_Bean sessionBean = (Session_Bean) request.getSession()
				.getAttribute("bean_sess");
		String userName = sessionBean.getCnname_Sess();
		return userName;
	}
	
	public static int getMydeptId(HttpServletRequest request) {
		Session_Bean sessionBean = (Session_Bean) request.getSession()
				.getAttribute("bean_sess");
		return sessionBean.getDeptId_Sess();
	}
	
	public static String getMydeptName(HttpServletRequest request) {
		String deptName=SqlSet.getDeptOfMan(getCurrentUserId(request), new BaseDataOP(ConnectionPool.getPool()));
		return deptName;
	}

	public void handleEncoding(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		response.setContentType(CONTENT_TYPE);
		String jQueryHeader=request.getHeader("X-Requested-With");
	    if (jQueryHeader!= null && jQueryHeader.equalsIgnoreCase("XMLHttpRequest")) {//如果是jQuery的Ajax请求
	        request.setCharacterEncoding("UTF-8");
	    } else {
	        request.setCharacterEncoding("GBK");
	    }
	}

	private void executeBaseMethod(HttpServletRequest request, HttpServletResponse response, String methodName, String tableName) throws ServletException, IOException{
		if (methodName.equalsIgnoreCase("addOrUpdate")) {
			addOrUpdate(request, response, tableName);	
		}else if(methodName.equalsIgnoreCase("add")){
			add(request, response, tableName);	
		}else if(methodName.equalsIgnoreCase("update")){
			update(request, response, tableName);	
		}else if(methodName.equalsIgnoreCase("delete")){
			delete(request, response, tableName);	
		}else if(methodName.equalsIgnoreCase("list")){
			list(request, response, tableName);	
		}else{
			response.getWriter().println("BaseServlet中不存在您指定的执行方法！");
		}
	}
}
