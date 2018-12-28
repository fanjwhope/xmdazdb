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
			JsonUtil.writeJsonMsg(response, true, "���нڵ����óɹ�");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "���нڵ�����ʧ��");
		}
		
	}
	
	public   void  update(HttpServletRequest request, HttpServletResponse response){
		//String tableName=ArchiveUtil.getFullTableName(request, BankNode.class);
		BankNodeDao  bnd=new BankNodeDao(tableName);
		BankNode bankNode=getObject(request);
		try {
			bnd.update(bankNode);
			JsonUtil.writeJsonMsg(response, true, "���нڵ���³ɹ�");
		} catch (Exception e) {
			Log.debug(e);
			JsonUtil.writeJsonMsg(response, false, "���нڵ����ʧ��");
		}
	}
	/**
	 * ���нڵ��Ƿ���Ч
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
	 * �������нڵ��ѯ���й���Ա��Ϣ
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
	 * �޸ĸýڵ������й���Ա����Ϣ
	 * @param request
	 * @param response
	 */
	public  void  updateNodeAdmin(HttpServletRequest request, HttpServletResponse response){
		String nodeId=request.getParameter("nodeId");
		String pwd=request.getParameter("pwd");
		BankNodeDao  bnd=new BankNodeDao(tableName);
		bnd.updateNodeAdmin(nodeId,pwd);
		JsonUtil.writeJsonMsg(response, true, "�޸ĳɹ�");
	}
	
	/**
	 *����ID��ȡһ���ڵ���Ϣ
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
	 * ���˱����Ƿ��Ѿ����
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
	 * ��ȡ���е����нڵ���Ϣ
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
