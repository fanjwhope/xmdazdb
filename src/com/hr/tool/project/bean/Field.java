package com.hr.tool.project.bean;

public class Field {
	
	private String name; //字段名称
	
	private String type; //字段类型：int number text varchar，仅前台页面和xml文件使用
	
	private String fieldType; //字段类型，对应常量类中的字段类型，仅后台使用
	
	private String len; //字段长度
	
	private String allowNull;//是否允许为空 ：0-不允许；1-允许
	
	private String priKey; //主键：0为非主键，1为主键
	
	private String from; //数据来源： insert_uuid,insert_date,insert_max,insert_time,update_uuid,update_max,update_date,update_time
	
	private String cname;//字段中文名称
	
	private String disInList;//是否在列表中显示该字段值：0-不显示，1-显示
	
	private String modify; //是否允许修改：0-不允许，1-允许
	
	private String countField;//计算行数：0-不计算， 1-计算，仅前台页面和xml文件使用
	
	private String orderField;//排序字段：0-不排序， 1-排序，仅前台页面和xml文件使用
	
	private String javaType="String";//转换成bean时的java类型，仅后台使用

	public Field(){
		
	}
	
	public Field(String name, String type, String len, String allowNull,
			String priKey, String from, String cname, String disInList,
			String modify) {
		super();
		this.name = name;
		this.type = type;
		this.len = len;
		this.allowNull = allowNull;
		this.priKey = priKey;
		this.from = from;
		this.cname = cname;
		this.disInList = disInList;
		this.modify = modify;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLen() {
		return len;
	}

	public void setLen(String len) {
		this.len = len;
	}

	public String getAllowNull() {
		return allowNull;
	}

	public void setAllowNull(String allowNull) {
		this.allowNull = allowNull;
	}

	public String getPriKey() {
		return priKey;
	}

	public void setPriKey(String priKey) {
		this.priKey = priKey;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getDisInList() {
		return disInList;
	}

	public void setDisInList(String disInList) {
		this.disInList = disInList;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
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

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

}
