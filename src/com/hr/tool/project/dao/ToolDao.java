package com.hr.tool.project.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hr.global.util.PagingBean;
import com.hr.tool.project.bean.DataBean;
import com.hr.util.MyHash;

public interface ToolDao {
	public List<Map<String, String>> queryList(HttpServletRequest request, Map map, String tableName, PagingBean pagingBean) throws Exception;
	
	public void addOrUpdate(HttpServletRequest request, Map map, String tableName) throws Exception;

	public void add(HttpServletRequest request, Map map, String tableName) throws Exception;

	public void update(HttpServletRequest request, Map map, String tableName) throws Exception;

	public void delete(HttpServletRequest request, String delId, Map map, String tableName)
			throws Exception;

	public MyHash find(String id, String tableName) throws Exception;
	
	public void addBefore(HttpServletRequest request, Map map, String tableName);

	public void addAfter(HttpServletRequest request, Map map, String tableName);

	public void updateBefore(HttpServletRequest request, Map map, String tableName);

	public void updateAfter(HttpServletRequest request, Map map, String tableName);

	public void deleteBefore(HttpServletRequest request, Map map, String tableName);

	public void deleteAfter(HttpServletRequest request, Map map, String tableName);

	public String getTableName();

	public String[][] getTableField();

}
