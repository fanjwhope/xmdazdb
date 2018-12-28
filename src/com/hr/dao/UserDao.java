package com.hr.dao;

import java.util.List;

import com.hr.bean.User;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.Constants;
import com.hr.global.util.StringHelper;
import com.hr.global.util.Validation;

public class UserDao extends BaseDao<User> {
	private String tableName;
	public static String[][] tableField = {
		{ "userid", "varchar", "8", "0", "1" },
		{ "dwmc", "varchar", "20", "0", "0" },
		{ "userpwd", "varchar", "8", "0", "0" },
		{ "dwh", "varchar", "10", "0", "0" },
		{ "num_sort", Constants.fieldTypeNumber, "9,0", "1", "0" },
		{ "roles", Constants.fieldTypeNumber, "9,0", "1", "0" }};
	
	
	public UserDao(String tableName,String dbName) {
		super(tableName,tableField,dbName);
		this.tableName=tableName;
		createTable(dbName);
	}
	
	public boolean ifUserExist(String userId, String password){
		String sql="select count(*) from "+tableName+" where userid="+StringHelper.getFieldSql(userId)+
				 " and userpwd="+StringHelper.getFieldSql(password);
		String strCount=querySingleData(sql);
		int count=Integer.parseInt(strCount);
		return count>0?true:false;
	}
	
	public void updatePassword(User user){
		String sql = "update " + tableName + " set userpwd = " + StringHelper.getFieldSql(user.getUserpwd()) +
				     " where userid = " + StringHelper.getFieldSql(user.getUserid());
		ExecSql(sql);
	}
	
	//判断用户名-密码是否存在
	public boolean ifUserExist(User user){
		boolean flag = false;
		String sql = "select * from " + tableName + " where userid = " + StringHelper.getFieldSql(user.getUserid()) +
				     " and userpwd = " + StringHelper.getFieldSql(user.getUserpwd());
		if(queryRowNum(sql) != 0)
			flag = true;
		return flag;
	}
	
	//获取单位名
	public List<User> getAllDepartment(){
		String sql = "select dwmc, dwh from " + tableName + " where dwh like '" + "%0000'" + " and dwh != '" + "Y100000000'";
		String[][] all = queryRowAndCol(sql);
		List<User> list = BeanHelper.stringArray2Object("dwmc, dwh", all, User.class, false);
		return list;
	}
	
	//获取单位名及下属用户名
	public List<User> getAll(){
		String sql = "select dwmc, dwh from " + tableName + " where dwh != '" + "Y100000000'";
		String[][] all = queryRowAndCol(sql);
		List<User> list = BeanHelper.stringArray2Object("dwmc, dwh", all, User.class, false);
		return list;
	}
	
	public User getUserByDWH(String dwh){
		String keys="dwmc,dwh,userid,userpwd,num_sort,roles";
		String sql="select "+keys+" from "+tableName+" where dwh="+StringHelper.getFieldSql(dwh);
		User user=BeanHelper.stringArray2Object(keys, queryLine(sql), User.class, false);
		return user;
	}
	
	public User getOneByUserId(String userId){
		String keys="dwmc,dwh,userid,userpwd,num_sort,roles";
		String sql="select "+keys+" from "+tableName+" where userid="+StringHelper.getFieldSql(userId);
		User user=BeanHelper.stringArray2Object(keys, queryLine(sql), User.class, false);
		return user;
	}
	
	//获取所有单位信息
	public String getUser(){
		String fields = getFields();
		String sql="select " + fields + " from "+tableName+" where dwh like '%0000%' and dwh<>'Y100000000' order by dwh";
		String subVal="";
		String json="[";
		String[][] val=queryRowAndCol(sql);
		String[][] valInfo=null;
		if(val!=null&&val.length>0){
			for(int i=0;i<val.length;i++){
				json+="{"+"\""+"text"+"\""+":"+"\""+val[i][1]+"\""+","+"\""+"id"+"\""+":"+"\""+val[i][3]+"\""+"," +"\"iconCls\":"+"\"icon-folder\"";
				subVal=val[i][3].substring(1, val[i][3].length()-4);
				sql="select " + fields + " from "+tableName+" where dwh like 'Y"+subVal+"%' and dwh<>'"+val[i][3]+"' order by dwh";
				valInfo=queryRowAndCol(sql);
				if(valInfo!=null&&valInfo.length>0){
				json+=","+"\""+"children"+"\""+":[";
					for(int j=0;j<valInfo.length;j++){
						json+="{"+"\""+"text"+"\""+":"+"\""+valInfo[j][1]+"\""+","+"\""+"id"+"\""+":"+"\""+valInfo[j][3]+"\""+"," +"\"iconCls\":"+"\"icon-user\"}";
						if(j!=valInfo.length-1){
							json+=",";
						}
					}
				json+="]";
				}
				json+="}";
				if(i!=val.length-1){
					json+=",";
				}
			}
		}
		json+="]";
		
		String dbName = tableName.split("_")[0];
		String sq = "select bankName from bankNode where id = '" + dbName + "'";
		String bankName = queryRow(sq)[0];
		json = "[{" + "\"" + "text" + "\"" + ":" + "\"" + bankName + "\"" + "," + "\"" + "id" + "\"" + ":" + "\"1\"" + "," +"\"iconCls\":"+"\"icon-home\","
				+ "\""+ "children" + "\"" + ":" + json + "}]";
		return json;
	}
	
	public String[][] getDepartment(){
		String sql="select " +  getFields() + " from "+tableName+" where dwh like '%0000%' and dwh<>'Y100000000' order by dwh";
		String[][] val=queryRowAndCol(sql);
         return val;
	}
	//根据userid更新信息
	public void doUpdate(String id1, String id2, String dwmc, String pwd) {
		String sql = "update " + tableName + " set userid = '" + id2
				+ "', dwmc = '" + dwmc + "', userpwd = '" + pwd + "'"
				+ "  where userid = '" + id1 + "'";
		// 添加数据 2018-05-16
		try {
			getBaseDataOP("zdbzy").Exec(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExecSql(sql);
	}

	public void doUpdate(String id1, String id2, String dwmc, String pwd,
			String roles) {
		String sql = "update " + tableName + " set userid = '" + id2
				+ "', dwmc = '" + dwmc + "', userpwd = '" + pwd + "',roles="
				+ roles + "  where userid = '" + id1 + "'";
		// 添加数据 2018-05-16
		try {
			getBaseDataOP("zdbzy").Exec(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExecSql(sql);
	}

	//添加用户
	public void doCreate(String id, String dwmc, String pwd, String dwh, int sort,String roles){
		String sql = "insert into " + tableName + " (userid, dwmc, userpwd, dwh, num_sort,roles)" + " values ('" + id + "', '" + 
					 dwmc + "', '" + pwd + "', '" + dwh + "', " + sort +",'"+roles+ "')";
		//添加数据  2018-05-16
		try {
			getBaseDataOP("zdbzy").Exec(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExecSql(sql);
	}
	
	//返回新单位号
	public String getDep(){
		String sql = "select max(dwh) from  " + tableName + " where dwh like 'Y2%0000'";
		String dwh = queryRow(sql)[0];
		if(Validation.isEmpty(dwh)){
			dwh="y20001";
		}
		int num = Integer.parseInt(dwh.substring(1, 6)) + 1;
		String dwh1 = "Y" + Integer.toString(num) + "0000";
		return dwh1;
	}
	
	//根据所选部门的单位号，返回新的下属用户单位号
	public String getDepCld(String dwh){
		String flag = dwh.substring(0, 6);
		String sql1 = "select max(dwh) from " + tableName + " where dwh like '" + flag + "%'";
		String dwh1 = queryRow(sql1)[0];   //当前所选单位下属用户最后面的人员的单位号
		int num = Integer.parseInt(dwh1.substring(5, 10)) + 1;
		String dwh2 = flag.substring(0, 5) + Integer.toString(num);
		return dwh2;
	}
	
	public void doDelete(String dwh) {
		String sql = "";
		if (Integer.parseInt(dwh.substring(6, 10)) == 0) // 部门根用户
			sql = "delete from " + tableName + " where dwh like '"
					+ dwh.substring(0, 6) + "%'";
		else
			sql = "delete from " + tableName + " where dwh = '" + dwh + "'";

		// 添加数据 2018-05-16
		try {
			getBaseDataOP("zdbzy").Exec(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExecSql(sql);
	}
	
	//查询修改后的id（与原id不同）是否已存在
	public boolean ifExits(String id1, String id2){
		boolean flag = false;
		String sql = "select * from " + tableName + " where userid != '" + id1 + "' and userid = '" + id2 + "'";
		if(queryRowNum(sql) != 0)
			flag = true;
		return flag;
	}
	
	//查询添加的id是否已存在
	public boolean ifNewExits(String id){
		boolean flag = false;
		String sql = "select * from " + tableName + " where userid = '" + id + "'";
		if(queryRowNum(sql) != 0)
			flag = true;
		return flag;
	}
	
	
}
