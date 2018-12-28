package com.hr.tool.project.bean;

public class Field {
	
	private String name; //�ֶ�����
	
	private String type; //�ֶ����ͣ�int number text varchar����ǰ̨ҳ���xml�ļ�ʹ��
	
	private String fieldType; //�ֶ����ͣ���Ӧ�������е��ֶ����ͣ�����̨ʹ��
	
	private String len; //�ֶγ���
	
	private String allowNull;//�Ƿ�����Ϊ�� ��0-������1-����
	
	private String priKey; //������0Ϊ��������1Ϊ����
	
	private String from; //������Դ�� insert_uuid,insert_date,insert_max,insert_time,update_uuid,update_max,update_date,update_time
	
	private String cname;//�ֶ���������
	
	private String disInList;//�Ƿ����б�����ʾ���ֶ�ֵ��0-����ʾ��1-��ʾ
	
	private String modify; //�Ƿ������޸ģ�0-������1-����
	
	private String countField;//����������0-�����㣬 1-���㣬��ǰ̨ҳ���xml�ļ�ʹ��
	
	private String orderField;//�����ֶΣ�0-������ 1-���򣬽�ǰ̨ҳ���xml�ļ�ʹ��
	
	private String javaType="String";//ת����beanʱ��java���ͣ�����̨ʹ��

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
