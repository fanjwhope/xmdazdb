package com.hr.action;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.BankNode;
import com.hr.bean.User;
import com.hr.bean.LogInfo;
import com.hr.bean.TempSession;
import com.hr.dao.BankNodeDao;
import com.hr.dao.LogInfoDao;
import com.hr.dao.TempSessionDao;
import com.hr.dao.UserDao;
import com.hr.global.util.ArchiveUtil;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.BuildSql;
import com.hr.global.util.DateFunc;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.TreeNode;
import com.hr.util.ConfigInfo;
import com.hr.util.Log;
import com.hr.util.MyTools;

public class UserDo {
	private static String[][] rolesArr=new String[][]{{"3","查询用户"},{"1","立卷用户"},{"2","归档管理用户"},{"4","公共用户"}};

	/**
	 * 用户登录
	 * @param request
	 * @param response
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) {
		ArchiveUtil.removeCurrentUser(request.getSession());
		String userid = request.getParameter("userid");
		String userpwd = request.getParameter("userpwd");
		String dbName = request.getParameter("zdbdbName");
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		//记录登录信息
		String ipAddress=request.getRemoteAddr();
		Calendar calendar=GregorianCalendar.getInstance();
		String accessFile=request.getRequestURI();
		String sessionID=request.getSession().getId();
		String lastTime=DateFunc.getNowDateStr("yyyy-MM-dd HH:mm:ss");
		
		//跟新用户密码 （加密） 2018-12-27
		userpwd = MyTools.md5(userpwd);
		
		
		LogInfo logInfo=new LogInfo(null, ipAddress, userid, userpwd, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
				accessFile, null, sessionID, lastTime);
		String logInfoTableName=ArchiveUtil.getFullTableName(request, LogInfo.class);
		LogInfoDao logInfoDao=new LogInfoDao(logInfoTableName,dbName);
		String[] logInfoFieldsExcludes=new String[]{"id"};
		
		UserDao dao = new UserDao(tableName,dbName);
		boolean ifExist = dao.ifUserExist(userid, userpwd);
		if (ifExist) {// 用户名、密码验证通过
			User user = dao.findById(userid);
			String dataBaseName = request.getParameter("dataBaseName");
			user.setDataBaseName(dataBaseName);
			request.getSession().setAttribute("currentUser", user);
			
			//保存用户session至基础数据库
			String basicDataBase=ConfigInfo.getVal("basicDataBase");
			String schema=ConfigInfo.getVal("schema");
			String tempSessionTableName = basicDataBase+"_"+BuildSql.getTableName(TempSession.class);
			TempSessionDao tempSessionDao = new TempSessionDao(
					tempSessionTableName,dbName);
			tempSessionDao.delete(user.getUserid(), user.getDwh(), dataBaseName, request.getSession().getId());
			TempSession tempSession = new TempSession(request.getSession()
					.getId(), user.getUserid(), user.getDwh(), null,
					DateFunc.getNowDateStr("yyyyMMdd"), null, dataBaseName,
					null, null);
			tempSessionDao.add(tempSession);

			logInfo.setFinish(0);
			logInfo.setPassword("******");
			logInfoDao.add(logInfo,null,logInfoFieldsExcludes);
			
			String url=null;
			if ("admin".equals(userid)&&basicDataBase.equalsIgnoreCase(dataBaseName)) {// 超级管理员信息存放在基础数据库中
				url="/pages/superMenu.jsp?zdbdbName=zdb";
			} else if ("system".equals(userid.toLowerCase())) {// 分行管理员
				url="/pages/systemMenu.jsp?zdbdbName=zdb";
			} else {// 普通员工
				
					url="/pages/userMenu.jsp?zdbdbName=zdb";
			}
			JsonUtil.writeJsonMsg(response, true, "登录成功！", url);
		} else {
			logInfo.setFinish(1);
			logInfoDao.add(logInfo,null,logInfoFieldsExcludes);
			JsonUtil.writeJsonMsg(response, false, "用户名或者密码错误！");
		}
	}
	
	/**
	 * 用户心跳检测，用于记录loginfo里面用户的最后在线时间
	 * @param request
	 * @param response
	 */
	public void heartBeat(HttpServletRequest request, HttpServletResponse response){
		User user=ArchiveUtil.getCurrentUser(request.getSession());
		String dbName = request.getParameter("zdbdbName");
		if(user!=null){
			String logInfoTableName=ArchiveUtil.getFullTableName(request, LogInfo.class);
			LogInfoDao logInfoDao=new LogInfoDao(logInfoTableName,dbName);
			logInfoDao.updateLastTime(request.getSession().getId());
			JsonUtil.writeJsonMsg(response, true, "更新用户最后在线时间成功！");
		}else{
			JsonUtil.writeJsonMsg(response, false, "更新用户最后在线时间失败，请确保用户已经登录！");
		}
	}
	
	public void updatePWD(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		User user = getObject(request);
		try{
			//跟新用户密码 （加密） 2018-12-27
			user.setUserpwd(MyTools.md5(user.getUserpwd())) ;
			
			dao.updatePassword(user);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "密码修改失败！");
		}
	}
	
	//当前用户密码验证
	public void getPwd(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		User user = getObject(request);
		try{
			//跟新用户密码 （加密） 2018-12-27
			user.setUserpwd(MyTools.md5(user.getUserpwd())) ;
			
			if(dao.ifUserExist(user))
				JsonUtil.writeJsonMsg(response, true, "", "1");  //返回1，密码验证通过
			else
				JsonUtil.writeJsonMsg(response, true, "密码错误！", "0");  //返回0，密码验证失败
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "密码验证失败！");
		}
	}
	
	//获取所有部门名
	public void getAllDep(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		try{
			List<User> all = dao.getAllDepartment();
			JsonUtil.writeJsonObject(response, all);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "部门名获取失败！");
		}
	}
	
	//获取所有部门及下属用户
	public void getDep(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		try{
			List<User> all = dao.getAll();
			JsonUtil.writeJsonObject(response, all);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "部门获取失败！");
		}
	}
	
	public void getAllUser(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		//User currentUser=(User)request.getSession().getAttribute("currentUser");
		//BankNodeDao bankNodeDao=new BankNodeDao(ConfigInfo.getVal("bankNode"));
		//BankNode bankNode=bankNodeDao.getOne(currentUser.getDataBaseName());
		//TreeNode root=new TreeNode("1", bankNode.getBankName());
		try{
			/*String[][] all = dao.getDepartment();
			if(all!=null&&all.length>0){
				for(int i=0;i<all.length;i++){
					TreeNode child=new TreeNode();
					child.setId(all[i][3]);
					child.setText(all[i][1]);
					child.setState("closed");
					root.addChildren(child);
				}
			}
			JsonUtil.writeJsonObject(response, root);*/
			String all=dao.getUser();
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(all);
		}catch (Exception e){
			Log.error(e);
			JsonUtil.writeJsonMsg(response, false, "部门获取失败！");
		}
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		System.out.println("UserDo.java  update"+ tableName +"   "+dbName);
		
		UserDao dao = new UserDao(tableName,dbName);
		String id1 = request.getParameter("id1");
		String id2 = request.getParameter("id2");
		String dwmc = request.getParameter("dwmc");
		String pwd = request.getParameter("pwd");
		String roles= request.getParameter("roles");
		String dwmcOld = request.getParameter("dwmcOld");
		
		try{
			
			//跟新用户密码 （加密） 2018-12-27
			pwd = MyTools.md5(pwd) ;
			
			if(dao.ifExits(id1, id2))
				JsonUtil.writeJsonMsg(response, false, "用户名已存在！");
			else{	
				if(ArchiveUtil.isInteger(roles)){//如果是纯数字，更新用户角色；否则不更新用户角色
					dao.doUpdate(id1, id2, dwmc, pwd,roles);
				}else{
					dao.doUpdate(id1, id2, dwmc, pwd);
				}
				JsonUtil.writeJsonMsg(response, true, "添加成功！");
			}
		}catch (Exception e){
			Log.error(e);
		}
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		String dwh = request.getParameter("dwh");
		try{
			dao.doDelete(dwh);
		}catch (Exception e){
			Log.error(e);
		}
	}
	
	//添加根用户
	public void insertF(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		String id = request.getParameter("id");
		String dwmc = request.getParameter("dwmc");
		String pwd = request.getParameter("pwd");
		String roles= request.getParameter("roles");
		String dwh = dao.getDep();
		int num = 0;
		try{
			
			//跟新用户密码 （加密） 2018-12-27
			pwd = MyTools.md5(pwd) ;
			
			if(dao.ifNewExits(id))
				JsonUtil.writeJsonMsg(response, false, "用户名已存在！");
			else if("dac".equals(id) & "档案处".equals(dwmc)){
				dwh = "Y600010000";
				dao.doCreate(id, dwmc, pwd, dwh, num,roles);
				JsonUtil.writeJsonMsg(response, true, "添加成功！", dwh);
			}
			else{
				dao.doCreate(id, dwmc, pwd, dwh, num,roles);
				JsonUtil.writeJsonMsg(response, true, "添加成功！", dwh);
			}
		}catch (Exception e){
			Log.error(e);
		}
	}
	
	//添加子用户
	public void insertC(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		String id = request.getParameter("id");
		String dwmc = request.getParameter("dwmc");
		String pwd = request.getParameter("pwd");
		String dwh = request.getParameter("dwh");
		String roles= request.getParameter("roles");
		String dwh1 = dao.getDepCld(dwh);
		int num = Integer.parseInt(dwh1.substring(6, 10));
		try{
			//跟新用户密码 （加密） 2018-12-27
			pwd = MyTools.md5(pwd) ;
			
			if(dao.ifNewExits(id))
				JsonUtil.writeJsonMsg(response, false, "用户名已存在！");
			else{
				dao.doCreate(id, dwmc, pwd, dwh1, num,roles);
				JsonUtil.writeJsonMsg(response, true, "添加成功！", dwh1);
			}
		}catch (Exception e){
			Log.error(e);
		}
	}
	
	public void getOne(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		String dwh = request.getParameter("dwh");
		String[][]  obj=rolesArr;
		try{
		User user=	dao.getUserByDWH(dwh);
		String roles=user.getRoles();
		for(int i=0;i<obj.length;i++){
			if( obj[i][0].equals(roles)){
				user.setRoles(obj[i][1]);
				break;
			}
		}
			JsonUtil.writeJsonObject(response, user);
		}catch (Exception e){
			Log.error(e);
		}
	}
	
	public  void getOneByUserId(HttpServletRequest request, HttpServletResponse response){
		String tableName = ArchiveUtil.getFullTableName(request, User.class);
		String dbName = request.getParameter("zdbdbName");
		UserDao dao = new UserDao(tableName,dbName);
		String id = request.getParameter("id");
		String[][]  obj=rolesArr;
		try{
		User user=	dao.getOneByUserId(id);
		String roles=user.getRoles();
		for(int i=0;i<obj.length;i++){
			if( obj[i][0].equals(roles)){
				user.setRoles(obj[i][1]);
				break;
			}
		}
			JsonUtil.writeJsonObject(response, user);
		}catch (Exception e){
			Log.error(e);
		}
	}
	
	/**
	 * 角色及标示    一级管理员  -1，二级管理员 0，立卷用户  1，归档人员 2，查询用户   3，公共用户 4
	 * @param request
	 * @param response
	 */
	public void getRoles(HttpServletRequest request, HttpServletResponse response){
		String fieldNames="id,text";
		String[][]  obj=rolesArr;
		String json=ArchiveUtil.arrToJson(obj, fieldNames);
		response.setContentType("text/html;charset=GBK");
		try {
			response.getWriter().write("["+json+"]");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
		   Log.debug(e);
		}
	}
	
	private User getObject(HttpServletRequest request){
		User user = null;
		try {
			user = (User) BeanHelper.getValuesByRequest(request, User.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return user;
	}
	
  
}
