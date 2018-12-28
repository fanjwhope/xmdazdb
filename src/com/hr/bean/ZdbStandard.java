package com.hr.bean;

import javax.persistence.Table;

@Table(name="zdbsp")
public class ZdbStandard {
	
	private Integer id;
	private String spnr;
	private Integer tabindex;
	private Integer test;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSpnr() {
		return spnr;
	}
	public void setSpnr(String spnr) {
		this.spnr = spnr;
	}
	public Integer getTabindex() {
		return tabindex;
	}
	public void setTabindex(Integer tabindex) {
		this.tabindex = tabindex;
	}
	public Integer getTest() {
		return test;
	}
	public void setTest(Integer test) {
		this.test = test;
	}
	
	
}
