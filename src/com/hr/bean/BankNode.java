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
	 * id  别名
	 */
	private  String  id;
	
	/**
	 * bankName 银行名称
	 */
	private  String  bankName;
	
	/**
	 * flag  是否有效  1有效  0无效
	 */
	private  String  flag="1";
	
	/**
	 * sortNum  排序字段
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
