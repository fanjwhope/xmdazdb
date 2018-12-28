package com.hr.global.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页类，有各种分页相关的属性
 * 
 * @author huangfei
 * 
 */
public class PagingBean {
	private int totalRecords;// 记录总数
	private int currentPage = 1;// 当前页码
	private int pageSize = 10;// 每页记录数
	private String sort;//排序字段
	private String sortOrder;//排序方式
	private int pageCounts;//页面数

	public PagingBean() {
	}

	public PagingBean(HttpServletRequest request) {

		String var1 = request.getParameter("page");
		if (var1 != null) {// 设置当前页，easyUI传的参数是page
			int tempInt = Integer.parseInt(var1);
			this.currentPage = (tempInt > 1 ? tempInt : 1);
		}

		String var2 = request.getParameter("rows");
		if (var2 != null) {// 设置一页中的记录数，easyUI传的参数是rows
			int tempInt = Integer.parseInt(var2);
			this.pageSize = (tempInt > 0 ? tempInt : 10);
		}

		String var3 = request.getParameter("sort");
		if (var3 != null && !"".equals(var3)) {// 设置排序字段，easyUI传的参数是sort
			this.sort = var3;
		}
		
		String var4 = request.getParameter("order");
		if (var4 != null && !"".equals(var4)) {// 设置排序方式，easyUI传的参数是order
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
	 * 获取页面数
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
	 * 获取当前页的第一条记录索引
	 * 
	 * @return
	 */
	public int getStartIndex() {
		return (this.currentPage - 1) * this.pageSize;
	}

	/**
	 * 获取当前页的最后一条记录索引
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
