package com.hr.tool.project.bean;

import java.util.List;

public class Table {
	
	private String name; //表名
	
	private String cname;//表中文名
	
	private String countField; //计算行数的字段名，仅后台使用
	
	private String orderField; //排序字段和顺序倒序方式，仅后台使用
	
	private String rowsPerPage;//每页显示行数
	
	private String orderType; //排序方式，仅前台页面和xml文件使用
	
	private List<Field> fields;//字段集合

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountField() {
		return countField;
	}

	public void setCountField(String countField) {
		this.countField = countField;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(String rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
