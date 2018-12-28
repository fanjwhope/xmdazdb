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
		//������·���л�ȡ������ִ��������ִ�з�������ʼ
		String pathInfo=request.getPathInfo();
		int index=pathInfo.lastIndexOf("/");
		if(index<1){
			response.getWriter().println("��������·��û�а����㹻����Ϣ��");
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
		//������·���л�ȡ������ִ��������ִ�з���������
			
		//ʵ����ִ���࿪ʼ
		Class<?> actionClass=null;
		Object action=null;
		try {
			actionClass=Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			if(pathArr.length==2){//���ñ�servlet�ķ���
				executeBaseMethod(request, response, methodName, pathArr[0]);
			}else{
				response.getWriter().println("��ָ����ִ���ࡾ"+classPath+"�������ڣ�");
				Log.error(e);
			}
			return;
		}
		try {
			action=actionClass.newInstance();
		} catch (Exception e) {
			response.getWriter().println("��ʵ������ָ����ִ���ࡾ"+classPath+"��ʱ���ִ�����ȷ���������޲εĹ��췽����");
			Log.error(e);
			return;
		}
		//ʵ����ִ�������
		
		//��ȡִ�з�����ʼ
		Method method = null;
		try {
			method = action.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		} catch (SecurityException e) {
			response.getWriter().println("��ָ����ִ�з�����"+methodName+"����������ʣ�");
			Log.error(e);
			return;
		} catch (NoSuchMethodException e) {
			response.getWriter().println("��ָ����ִ�з�����"+methodName+"�������ڣ�");
			Log.error(e);
			return;
		}
		//��ȡִ�з�������
		
		//����ִ�з���������ҵ���߼�����
		Object view=null;
		try {
			view=method.invoke(action, request, response);
		} catch (Exception e) {
			response.getWriter().println("����ִ�з�����"+methodName+"��ʱ���ִ���");
			Log.error(e);
			return;
		}
		if(null!=view && view instanceof String){
			request.getRequestDispatcher((String)view).forward(request, response);
		}
	}
	
	/**
	 * ���������޸ļ�¼
	 */
	public void addOrUpdate(HttpServletRequest request, HttpServletResponse response, String tableName) {
		try {
			BaseDao baseDao = new BaseDao();
			baseDao.addOrUpdate(request, null, tableName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "������¼ʧ�ܣ�");
		}
		JsonUtil.writeJsonMsg(response, true, "������¼�ɹ���");
	}
	
	/**
	 * ������¼
	 */
	public void add(HttpServletRequest request, HttpServletResponse response, String tableName) {
		try {
			BaseDao baseDao = new BaseDao();
			baseDao.add(request, null, tableName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "��Ӽ�¼ʧ�ܣ�");
		}
		JsonUtil.writeJsonMsg(response, true, "��Ӽ�¼�ɹ���");
	}

//	/**
//	 * ��ת������¼ҳ��
//	 */
//	public String addUI(HttpServletRequest request, HttpServletResponse response, String tableName) {
//		String url=request.getParameter("url");
//		if(!Validation.isEmpty(url)){
//			return url;
//		}
//		return null;
//	}

	/**
	 * �޸ļ�¼
	 */
	public void update(HttpServletRequest request, HttpServletResponse response, String tableName) {
		try {
			BaseDao baseDao = new BaseDao();
			baseDao.update(request, null, tableName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "�޸ļ�¼ʧ�ܣ�");
		}
		JsonUtil.writeJsonMsg(response, true, "�޸ļ�¼�ɹ���");
	}

//	/**
//	 * ��ת�޸ļ�¼ҳ��
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
	 * ɾ����¼
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response, String tableName) {
		String id = request.getParameter("id");
		BaseDao baseDao = new BaseDao();
		try {
			baseDao.delete(request, id, null, tableName);
		} catch (Exception e) {
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "ɾ����¼ʧ�ܣ�");
		}
		JsonUtil.writeJsonMsg(response, true, "ɾ����¼�ɹ���");
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
	    if (jQueryHeader!= null && jQueryHeader.equalsIgnoreCase("XMLHttpRequest")) {//�����jQuery��Ajax����
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
			response.getWriter().println("BaseServlet�в�������ָ����ִ�з�����");
		}
	}
}
