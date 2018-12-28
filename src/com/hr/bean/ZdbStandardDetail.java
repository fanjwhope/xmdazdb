package com.hr.bean;

import javax.persistence.Table;


@Table(name="zdbxmnr_${id}")
public class ZdbStandardDetail {
	private Integer dalx;
	private String damc;
	private String days;
	private String dabz;
	private Integer sort_num;
	public Integer getDalx() {
		return dalx;
	}
	public void setDalx(Integer dalx) {
		this.dalx = dalx;
	}
	public String getDamc() {
		return damc;
	}
	public void setDamc(String damc) {
		this.damc = damc;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getDabz() {
		return dabz;
	}
	public void setDabz(String dabz) {
		this.dabz = dabz;
	}
	public Integer getSort_num() {
		return sort_num;
	}
	public void setSort_num(Integer sort_num) {
		this.sort_num = sort_num;
	}
}
