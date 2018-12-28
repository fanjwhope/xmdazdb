package com.hr.tool.project.dao;

import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hr.global.util.BeanHelper;
import com.hr.global.util.PagingBean;
import com.hr.global.util.Param;
import com.hr.global.util.Validation;
import com.hr.util.ConnectionPool;
import com.hr.util.HrDB;
import com.hr.util.MyHash;

public class BaseDao extends HrDB implements ToolDao {

	public BaseDao(String myTabName, Object highrunPool) {
		super(highrunPool);
	}

	public BaseDao(Object pool) {
		super(pool);
	}

	public BaseDao() {
		super(ConnectionPool.getPool());
	}

	/**
	 * 获取数据库表字段，半角逗号分隔
	 * 
	 * @return
	 */
	public String getFields(String[][] fieldsArr) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < fieldsArr.length; i++) {
			result.append(fieldsArr[i][0]);
			result.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	/**
	 * 获取数据库表字段，半角逗号分隔
	 * 
	 * @return
	 */
	public String getFields() {
		StringBuffer result = new StringBuffer();
		String[][] fieldsArr = getTableField();
		for (int i = 0; i < fieldsArr.length; i++) {
			result.append(fieldsArr[i][0]);
			result.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	/**
	 * 单表通用查询方法
	 */
	@Override
	public List<Map<String, String>> queryList(HttpServletRequest request,
			Map map, String tableName, PagingBean pagingBean) throws Exception {

		DataAdmin da = new DataAdmin(tableName, null, true);
		String[] listConditions = da.getListConditions();
		String fields = listConditions[0];
		String orderBy = listConditions[1];
		String countField = listConditions[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1");
		String sqlp = getWhereSql(request);
		whereSql.append(sqlp);
		String[][] obj;
		if (pagingBean != null) {
			obj = queryRowAndCol(pagingBean.getPageSize(),
					pagingBean.getCurrentPage(), fields, countField, tableName,
					whereSql.toString(), orderBy);
			pagingBean.setTotalRecords(getRowSum());
		} else {
			obj = queryRowAndCol("select " + fields + " from " + tableName
					+ " " + whereSql.toString());
		}
		// BeanHelper.stringArray2List(fields, obj);

		return BeanHelper.stringArray2List(fields, obj);
	}

	/**
	 * 获取Request里查询条件
	 * 
	 * @param request
	 * @return
	 */
	private String getWhereSql(HttpServletRequest request) {
		Param pm = new Param();
		java.util.Enumeration en = request.getParameterNames();

		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String val = request.getParameter(key);
			if (!Validation.isEmpty(val)) {
				if (key.startsWith("cx_jq")) {
					pm.putEqualMap(key.substring(6), val);
				}
				if (key.startsWith("cx_mh")) {
					pm.putLikeMap(key.substring(6), val);

				}
				if (key.startsWith("cx_rq")) {
					pm.putDateMap(key.substring(6), val);

				}
			}
		}

		// TODO Auto-generated method stub
		return pm.getSql();
	}

	@Override
	public void addOrUpdate(HttpServletRequest request, Map map,
			String tableName) throws Exception {
		DataAdmin da = new DataAdmin(tableName, ConnectionPool.getPool(), true);
		String key = da.getKey();
//		String key1=request.getParameter(key);
//		System.out.println(request.getParameter("name"));
		if (!Validation.isEmpty(request.getParameter("H_"+key))) {
			update(request, map, tableName);
		} else {
			add(request, map, tableName);
		}

	}

	@Override
	public void add(HttpServletRequest request, Map map, String tableName)
			throws Exception {
		addBefore(request, map, tableName);
		DataAdmin da = new DataAdmin(tableName, ConnectionPool.getPool(), true);
		da.insert(request);
		addAfter(request, map, tableName);
	}

	@Override
	public void update(HttpServletRequest request, Map map, String tableName)
			throws Exception {
		updateBefore(request, map, tableName);
		// String[][] fields = getTableField();
		// String tabName = getTableName();
		DataAdmin da = new DataAdmin(tableName, ConnectionPool.getPool(), true);
		da.update(request);
		updateAfter(request, map, tableName);
	}

	@Override
	public void delete(HttpServletRequest request, String delId, Map map,
			String tableName) throws Exception {
		deleteBefore(request, map, tableName);
		// String[][] fields = getTableField();
		// String tabName = getTableName();
		DataAdmin da = new DataAdmin(tableName, ConnectionPool.getPool(), true);
		da.delete(delId);

		deleteAfter(request, map, tableName);

	}

	public MyHash find(String id, String tableName) throws Exception {
		// String[][] fields = getTableField();
		// String tabName = getTableName();
		DataAdmin da = new DataAdmin(tableName, ConnectionPool.getPool(), true);
		return da.getOneData(id);
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] getTableField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBefore(HttpServletRequest request, Map map,
			String tableName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAfter(HttpServletRequest request, Map map,
			String tableName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBefore(HttpServletRequest request, Map map,
			String tableName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAfter(HttpServletRequest request, Map map,
			String tableName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAfter(HttpServletRequest request, Map map, String tableName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addBefore(HttpServletRequest request, Map map, String tableName) {
		// TODO Auto-generated method stub

	}

}
