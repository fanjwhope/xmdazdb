package com.hr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.BankNodeDao;
import com.hr.data.Session_Bean;
import com.hr.bean.BankNode;
import com.hr.bean.User;
import com.hr.global.util.BeanHelper;
import com.hr.global.util.JsonUtil;
import com.hr.global.util.PagingBean;
import com.hr.util.Log;

public class BankNodeDo {
	public static String tableName="bankNode";
	
	public  void  add(HttpServletRequest request, HttpServletResponse response){
		//String tableName=ArchiveUtil.getFullTableName(request, BankNode.class);
		BankNodeDao  bnd=new BankNodeDao(tableName);
		BankNode bankNode=getObject(request);
		try {
			bankNode.setSortNum(String.valueOf(bnd.getSortNum()));
			bnd.add(bankNode);
			JsonUtil.writeJsonMsg(response, true, "银行节点设置成功");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "银行节点设置失败");
		}
		
	}
	
	public   void  update(HttpServletRequest request, HttpServletResponse response){
		//String tableName=ArchiveUtil.getFullTableName(request, BankNode.class);
		BankNodeDao  bnd=new BankNodeDao(tableName);
		BankNode bankNode=getObject(request);
		try {
			bnd.update(bankNode);
			JsonUtil.writeJsonMsg(response, true, "银行节点更新成功");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "银行节点更新失败");
		}
	}
	/**
	 * 银行节点是否有效
	 * @param request
	 * @param response
	 */
	public  void  isAvailable(HttpServletRequest request, HttpServletResponse response){
		//String tableName=ArchiveUtil.getFullTableName(request, BankNode.class);
		BankNodeDao  bnd=new BankNodeDao(tableName);
		String id=request.getParameter("id");
		String flag=request.getParameter("flag");
		try {
			bnd.isAvailable(id,flag);
		} catch (Exception e) {
			Log.debug(e);
		}
		
	}
	
	/**
	 * 根据银行节点查询银行管理员信息
	 * @param request
	 * @param response
	 */
	public  void getNodeAdmin(HttpServletRequest request, HttpServletResponse response){
		String nodeId=request.getParameter("nodeId");
		BankNodeDao  bnd=new BankNodeDao(tableName);
		User user=bnd.getgetNodeAdmin(nodeId);
		JsonUtil.writeJsonObject(response, user);
		
	}
	
	/**
	 * 修改该节点下银行管理员的信息
	 * @param request
	 * @param response
	 */
	public  void  updateNodeAdmin(HttpServletRequest request, HttpServletResponse response){
		String nodeId=request.getParameter("nodeId");
		String pwd=request.getParameter("pwd");
		BankNodeDao  bnd=new BankNodeDao(tableName);
		bnd.updateNodeAdmin(nodeId,pwd);
		JsonUtil.writeJsonMsg(response, true, "修改成功");
	}
	
	/**
	 *根据ID获取一个节点信息
	 * @param request
	 * @param response
	 */
	public  void getOne(HttpServletRequest request, HttpServletResponse response){
		//String tableName=ArchiveUtil.getFullTableName(request, BankNode.class);
		BankNodeDao  bnd=new BankNodeDao(tableName);
		String id=request.getParameter("id");
		try {
			BankNode bankNode=bnd.getOne(id);
			JsonUtil.writeJsonObject(response, bankNode);
		} catch (Exception e) {
			Log.debug(e);
		}
	}
	
	/**
	 * 检查此别名是否已经添加
	 * @param request
	 * @param response
	 */
	public  void check(HttpServletRequest request, HttpServletResponse response){
		//String tableName=ArchiveUtil.getFullTableName(request, BankNode.class);
		BankNodeDao  bnd=new BankNodeDao(tableName);
		String  id=request.getParameter("id");
		try {
			int num=bnd.isExist(id);
			if(num==0){
				JsonUtil.writeJsonMsg(response, true, "");
			}else{
				JsonUtil.writeJsonMsg(response, false, "");
			}
		} catch (Exception e) {
			Log.debug(e);
		}
	}
	
	/**
	 * 获取所有的银行节点信息
	 * @param request
	 * @param response
	 */
	public  void  getList(HttpServletRequest request, HttpServletResponse response){
		//String tableName=ArchiveUtil.getFullTableName(request, BankNode.class);
		BankNodeDao  bnd=new BankNodeDao(tableName);
		BankNode bankNode=getObject(request);
		bankNode.setFlag("");
		PagingBean pagingBean = new PagingBean(request);
		try {
			List<BankNode> list=bnd.findAll(bankNode, pagingBean);
			JsonUtil.writeJsonGrid(response, list, pagingBean);
		} catch (Exception e) {
			Log.debug(e);
		}
	}
	
	public  void  getAllBankNode(HttpServletRequest request, HttpServletResponse response){
		//String tableName=ArchiveUtil.getFullTableName(request, BankNode.class);
		BankNodeDao  bnd=new BankNodeDao(tableName);
		try {
			List<BankNode> list=bnd.getAllBankNode();
			JsonUtil.writeJsonObject(response, list);
		} catch (Exception e) {
			Log.debug(e);
		}
		
	}
	
	
	private BankNode getObject(HttpServletRequest request) {
		BankNode bankNode = null;
		try {
			bankNode = (BankNode) BeanHelper.getValuesByRequest(request, BankNode.class, false);
		} catch (Exception e) {
			Log.error(e);
		}
		return bankNode;
	}
	

}
