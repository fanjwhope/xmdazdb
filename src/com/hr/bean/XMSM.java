package com.hr.bean;

import java.io.Serializable;

public class XMSM implements Serializable {

	private long id;

	private long lsh;

	private String olddabh;

	private String controlname;

	private String controlvalue;

	private String by1;

	private long by2;

	public XMSM() {

	}

	public XMSM(long lsh, String olddabh, String controlname,
			String controlvalue) {
		super();
		this.lsh = lsh;
		this.olddabh = olddabh;
		this.controlname = controlname;
		this.controlvalue = controlvalue;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLsh() {
		return lsh;
	}

	public void setLsh(long lsh) {
		this.lsh = lsh;
	}

	public String getOlddabh() {
		return olddabh;
	}

	public void setOlddabh(String olddabh) {
		this.olddabh = olddabh;
	}

	public String getControlname() {
		return controlname;
	}

	public void setControlname(String controlname) {
		this.controlname = controlname;
	}

	public String getControlvalue() {
		return controlvalue;
	}

	public void setControlvalue(String controlvalue) {
		this.controlvalue = controlvalue;
	}

	public String getBy1() {
		return by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}

	public long getBy2() {
		return by2;
	}

	public void setBy2(long by2) {
		this.by2 = by2;
	}

	@Override
	public String toString() {
		return "XMSM [id=" + id + ", lsh=" + lsh + ", olddabh=" + olddabh
				+ ", controlname=" + controlname + ", controlvalue="
				+ controlvalue + ", by1=" + by1 + ", by2=" + by2 + "]";
	}

	
}
