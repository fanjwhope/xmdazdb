package com.hr.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "${departmentCode}_{archiveType}")
public class DepartStandard implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	
	/**
	 * 档案来源
	 */
	private String dalx;
	
	/**
	 * 档案名称
	 */
	private String damc;
	
	private String days;
	private String dabz;
	
	/**
	 * 排序字段
	 */
	private Double sortNum;

	// Constructors

	/** default constructor */
	public DepartStandard() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	@Column(name = "dalx", nullable = false, length = 10)
	public String getDalx() {
		return this.dalx;
	}

	public void setDalx(String dalx) {
		this.dalx = dalx;
	}

	@Column(name = "damc", nullable = false, length = 200)
	public String getDamc() {
		return this.damc;
	}

	public void setDamc(String damc) {
		this.damc = damc;
	}

	@Column(name = "days", length = 4)
	public String getDays() {
		return this.days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	@Column(name = "dabz", length = 30)
	public String getDabz() {
		return this.dabz;
	}

	public void setDabz(String dabz) {
		this.dabz = dabz;
	}

	@Column(name = "sort_num", precision = 16, scale = 1)
	public Double getSortNum() {
		return this.sortNum;
	}

	public void setSortNum(Double sortNum) {
		this.sortNum = sortNum;
	}

}