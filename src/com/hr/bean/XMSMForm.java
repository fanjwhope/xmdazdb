package com.hr.bean;

import java.io.Serializable;

public class XMSMForm implements Serializable {
	private String lsh; //流水号
	private String xmmc;// 项目名称
	private String ywhc;// 转贷协议号
	private String xmfzr;// 项目主管
	private String qymc;// 借款人名称
	private String by1;// 贷款国别
	private String newxmbh;// 档案编号
	private String tbsj;// 填表时间
	private String qysj;// 贷款协议签约时间
	private String dkxyh;// 贷款协议号
	private String returnqk;// 还本付息情况
	private String getqk;// 项目提款情况
	private String xxqk;// 贷款协议情况
	private String infoqk;// 其他资料情况
	private String tbr;// 填表人
	private String jcr;// 检查人
	private String smtype;// dk
	private String olddabh;// 原档案编号

	
	
	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
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

	public String getXmfzr() {
		return xmfzr;
	}

	public void setXmfzr(String xmfzr) {
		this.xmfzr = xmfzr;
	}

	public String getQymc() {
		return qymc;
	}

	public void setQymc(String qymc) {
		this.qymc = qymc;
	}

	public String getBy1() {
		return by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}

	public String getNewxmbh() {
		return newxmbh;
	}

	public void setNewxmbh(String newxmbh) {
		this.newxmbh = newxmbh;
	}

	public String getTbsj() {
		return tbsj;
	}

	public void setTbsj(String tbsj) {
		this.tbsj = tbsj;
	}

	public String getQysj() {
		return qysj;
	}

	public void setQysj(String qysj) {
		this.qysj = qysj;
	}

	public String getDkxyh() {
		return dkxyh;
	}

	public void setDkxyh(String dkxyh) {
		this.dkxyh = dkxyh;
	}

	public String getReturnqk() {
		return returnqk;
	}

	public void setReturnqk(String returnqk) {
		this.returnqk = returnqk;
	}

	public String getGetqk() {
		return getqk;
	}

	public void setGetqk(String getqk) {
		this.getqk = getqk;
	}

	public String getXxqk() {
		return xxqk;
	}

	public void setXxqk(String xxqk) {
		this.xxqk = xxqk;
	}

	public String getInfoqk() {
		return infoqk;
	}

	public void setInfoqk(String infoqk) {
		this.infoqk = infoqk;
	}

	public String getTbr() {
		return tbr;
	}

	public void setTbr(String tbr) {
		this.tbr = tbr;
	}

	public String getJcr() {
		return jcr;
	}

	public void setJcr(String jcr) {
		this.jcr = jcr;
	}

	public String getSmtype() {
		return smtype;
	}

	public void setSmtype(String smtype) {
		this.smtype = smtype;
	}

	public String getOlddabh() {
		return olddabh;
	}

	public void setOlddabh(String olddabh) {
		this.olddabh = olddabh;
	}

	@Override
	public String toString() {
		return "XMSMForm [xmmc=" + xmmc + ", ywhc=" + ywhc + ", xmfzr=" + xmfzr
				+ ", qymc=" + qymc + ", by1=" + by1 + ", newxmbh=" + newxmbh
				+ ", tbsj=" + tbsj + ", qysj=" + qysj + ", dkxyh=" + dkxyh
				+ ", returnqk=" + returnqk + ", getqk=" + getqk + ", xxqk="
				+ xxqk + ", infoqk=" + infoqk + ", tbr=" + tbr + ", jcr=" + jcr
				+ ", smtype=" + smtype + ", olddabh=" + olddabh + "]";
	}

}
