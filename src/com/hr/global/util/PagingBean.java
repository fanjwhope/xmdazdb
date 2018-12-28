package com.hr.global.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ��ҳ�࣬�и��ַ�ҳ��ص�����
 * 
 * @author huangfei
 * 
 */
public class PagingBean {
	private int totalRecords;// ��¼����
	private int currentPage = 1;// ��ǰҳ��
	private int pageSize = 10;// ÿҳ��¼��
	private String sort;//�����ֶ�
	private String sortOrder;//����ʽ
	private int pageCounts;//ҳ����

	public PagingBean() {
	}

	public PagingBean(HttpServletRequest request) {

		String var1 = request.getParameter("page");
		if (var1 != null) {// ���õ�ǰҳ��easyUI���Ĳ�����page
			int tempInt = Integer.parseInt(var1);
			this.currentPage = (tempInt > 1 ? tempInt : 1);
		}

		String var2 = request.getParameter("rows");
		if (var2 != null) {// ����һҳ�еļ�¼����easyUI���Ĳ�����rows
			int tempInt = Integer.parseInt(var2);
			this.pageSize = (tempInt > 0 ? tempInt : 10);
		}

		String var3 = request.getParameter("sort");
		if (var3 != null && !"".equals(var3)) {// ���������ֶΣ�easyUI���Ĳ�����sort
			this.sort = var3;
		}
		
		String var4 = request.getParameter("order");
		if (var4 != null && !"".equals(var4)) {// ��������ʽ��easyUI���Ĳ�����order
			this.sortOrder = var4;
		}

		if (request.getAttribute("pageBean") == null) {
			request.setAttribute("pageBean", this);
		}

	}

	public PagingBean(int totalRecords, int currentPage, int pageSize) {
		this.totalRecords = totalRecords;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	/**
	 * ��ȡҳ����
	 * 
	 * @return
	 */
	public int getPageCounts() {
		if (this.totalRecords == 0) {
			return 0;
		}
		this.pageCounts = this.totalRecords / this.pageSize;
		if (this.totalRecords % this.pageSize > 0)
			this.pageCounts++;
		return this.pageCounts;
	}

	/**
	 * ��ȡ��ǰҳ�ĵ�һ����¼����
	 * 
	 * @return
	 */
	public int getStartIndex() {
		return (this.currentPage - 1) * this.pageSize;
	}

	/**
	 * ��ȡ��ǰҳ�����һ����¼����
	 * 
	 * @return
	 */
	public int getLastIndex() {
		int index=this.currentPage * this.pageSize - 1;
		if(index>this.totalRecords-1){
			index=this.totalRecords-1;
		}
		return index;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
