package com.hr.tool.project.bean;

import java.util.List;

public class Table {
	
	private String name; //����
	
	private String cname;//��������
	
	private String countField; //�����������ֶ���������̨ʹ��
	
	private String orderField; //�����ֶκ�˳����ʽ������̨ʹ��
	
	private String rowsPerPage;//ÿҳ��ʾ����
	
	private String orderType; //����ʽ����ǰ̨ҳ���xml�ļ�ʹ��
	
	private List<Field> fields;//�ֶμ���

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
