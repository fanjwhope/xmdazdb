package com.hr.bean;

public class Sjyz {
	private String dabh;
	private String ywhc;
	private String dhsl = "0";
	private String dhsmj = "0";
	private String spsl = "0";
	private String spsmj = "0";
	private String lsh;
	private String time;
	private String xmmc;

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getYwhc() {
		return ywhc;
	}

	public void setYwhc(String ywhc) {
		this.ywhc = ywhc;
	}

	public String getDabh() {
		return dabh;
	}

	public void setDabh(String dabh) {
		this.dabh = dabh;
	}

	public String getDhsl() {
		return dhsl;
	}

	public void setDhsl(String dhsl) {
		this.dhsl = dhsl;
	}

	public String getDhsmj() {
		return dhsmj;
	}

	public void setDhsmj(String dhsmj) {
		this.dhsmj = dhsmj;
	}

	public String getSpsl() {
		return spsl;
	}

	public void setSpsl(String spsl) {
		this.spsl = spsl;
	}

	public String getSpsmj() {
		return spsmj;
	}

	public void setSpsmj(String spsmj) {
		this.spsmj = spsmj;
	}

	@Override
	public String toString() {
		return "sjyz [dabh=" + dabh + ", dhsl=" + dhsl + ", dhsmj=" + dhsmj
				+ ", spsl=" + spsl + ", spsmj=" + spsmj + "]";
	}

}
