package com.hr.bean;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="bankNode")
public class BankNode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id  ����
	 */
	private  String  id;
	
	/**
	 * bankName ��������
	 */
	private  String  bankName;
	
	/**
	 * flag  �Ƿ���Ч  1��Ч  0��Ч
	 */
	private  String  flag="1";
	
	/**
	 * sortNum  �����ֶ�
	 */
	private  String  sortNum;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}


	public BankNode(String id, String bankName, String flag, String sortNum) {
		super();
		this.id = id;
		this.bankName = bankName;
		this.flag = flag;
		this.sortNum = sortNum;
	}

	public BankNode() {
		super();
	}
	
	

}
