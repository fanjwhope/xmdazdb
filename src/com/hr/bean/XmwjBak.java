package com.hr.bean;

import javax.persistence.Column;
import javax.persistence.Table;


@Table(name = "xmwj_${departmentCode}_bak")
public class XmwjBak implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long lsh;
	private String qymc;
	private String xmmc;
	private String xmbh;
	private String dkqx;
	private String xmfzr;
	private String xmnd;
	private String dkje;
	private String dw;
	private String tbyy;
	private String tbmm;
	private String tbdd;
	private String yjr;
	private String jsr;
	private String yjyy;
	private String yjmm;
	private String yjdd;
	private String dasryy;
	private String dasrmm;
	private String dasrdd;
	private String dasrjs;
	private String daycyy;
	private String daycmm;
	private String daycdd;
	private String ywhc;
	private String ycry;
	private String xyjs;
	private String bz;
	private Long numLsh;
	private String dwh;
	private String ajsm;
	private String ljr;
	private String jcr;
	private String yjflag;
	private String yjlist;
	private String jsdw;
	private String by1;
	private String by2;
	private String by3;
	private String ip;
	private int  tabindex;
	private String modifyManDwh;
	private String modifyManCname;
	private String curModifyTime;
	private String logAction;
	private String square;
	private String projectType;

	// Constructors

	
	
	/** default constructor */
	public XmwjBak() {
	}

	/** minimal constructor */
	public XmwjBak(long lsh, String qymc) {
		this.lsh = lsh;
		this.qymc = qymc;
	}

	/** full constructor */
	
	
	
	 @Column(name = "lsh", nullable = false, precision = 10, scale = 0)
	 public long getLsh() {
		 return lsh;
	 }
	 
	 public XmwjBak(long lsh, String qymc, String xmmc, String xmbh, String dkqx,
			String xmfzr, String xmnd, String dkje, String dw, String tbyy,
			String tbmm, String tbdd, String yjr, String jsr, String yjyy,
			String yjmm, String yjdd, String dasryy, String dasrmm, String dasrdd,
			String dasrjs, String daycyy, String daycmm, String daycdd, String ywhc,
			String ycry, String xyjs, String bz, Long numLsh, String dwh,
			String ajsm, String ljr, String jcr, String yjflag, String yjlist,
			String jsdw, String by1, String by2, String by3, String ip,
			int tabindex, String modifyManDwh, String modifyManCname,
			String curModifyTime, String logAction, String square,
			String projectType) {
		super();
		this.lsh = lsh;
		this.qymc = qymc;
		this.xmmc = xmmc;
		this.xmbh = xmbh;
		this.dkqx = dkqx;
		this.xmfzr = xmfzr;
		this.xmnd = xmnd;
		this.dkje = dkje;
		this.dw = dw;
		this.tbyy = tbyy;
		this.tbmm = tbmm;
		this.tbdd = tbdd;
		this.yjr = yjr;
		this.jsr = jsr;
		this.yjyy = yjyy;
		this.yjmm = yjmm;
		this.yjdd = yjdd;
		this.dasryy = dasryy;
		this.dasrmm = dasrmm;
		this.dasrdd = dasrdd;
		this.dasrjs = dasrjs;
		this.daycyy = daycyy;
		this.daycmm = daycmm;
		this.daycdd = daycdd;
		this.ywhc = ywhc;
		this.ycry = ycry;
		this.xyjs = xyjs;
		this.bz = bz;
		this.numLsh = numLsh;
		this.dwh = dwh;
		this.ajsm = ajsm;
		this.ljr = ljr;
		this.jcr = jcr;
		this.yjflag = yjflag;
		this.yjlist = yjlist;
		this.jsdw = jsdw;
		this.by1 = by1;
		this.by2 = by2;
		this.by3 = by3;
		this.ip = ip;
		this.tabindex = tabindex;
		this.modifyManDwh = modifyManDwh;
		this.modifyManCname = modifyManCname;
		this.curModifyTime = curModifyTime;
		this.logAction = logAction;
		this.square = square;
		this.projectType = projectType;
	}
	 @Column(name = "tabindex", precision = 2, scale = 0)
	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
	}

	public void setLsh(long lsh) {
		 this.lsh = lsh;
	 }

	@Column(name = "qymc", nullable = false, length = 300)
	public String getQymc() {
		return this.qymc;
	}


	public void setQymc(String qymc) {
		this.qymc = qymc;
	}

	@Column(name = "xmmc", length = 400)
	public String getXmmc() {
		return this.xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	@Column(name = "xmbh", length = 200)
	public String getXmbh() {
		return this.xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	@Column(name = "dkqx", length = 100)
	public String getDkqx() {
		return this.dkqx;
	}

	public void setDkqx(String dkqx) {
		this.dkqx = dkqx;
	}

	@Column(name = "xmfzr", length = 200)
	public String getXmfzr() {
		return this.xmfzr;
	}

	public void setXmfzr(String xmfzr) {
		this.xmfzr = xmfzr;
	}

	@Column(name = "xmnd", precision = 4, scale = 0)
	public String getXmnd() {
		return this.xmnd;
	}

	public void setXmnd(String xmnd) {
		this.xmnd = xmnd;
	}

	@Column(name = "dkje", length = 200)
	public String getDkje() {
		return this.dkje;
	}

	public void setDkje(String dkje) {
		this.dkje = dkje;
	}

	@Column(name = "dw", length = 12)
	public String getDw() {
		return this.dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	@Column(name = "tbyy", precision = 4, scale = 0)
	public String getTbyy() {
		return this.tbyy;
	}

	public void setTbyy(String tbyy) {
		this.tbyy = tbyy;
	}

	@Column(name = "tbmm", precision = 2, scale = 0)
	public String getTbmm() {
		return this.tbmm;
	}

	public void setTbmm(String tbmm) {
		this.tbmm = tbmm;
	}

	@Column(name = "tbdd", precision = 2, scale = 0)
	public String getTbdd() {
		return this.tbdd;
	}

	public void setTbdd(String tbdd) {
		this.tbdd = tbdd;
	}

	@Column(name = "yjr", length = 10)
	public String getYjr() {
		return this.yjr;
	}

	public void setYjr(String yjr) {
		this.yjr = yjr;
	}

	@Column(name = "jsr", length = 10)
	public String getJsr() {
		return this.jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

	@Column(name = "yjyy", precision = 4, scale = 0)
	public String getYjyy() {
		return this.yjyy;
	}

	public void setYjyy(String yjyy) {
		this.yjyy = yjyy;
	}

	@Column(name = "yjmm", precision = 2, scale = 0)
	public String getYjmm() {
		return this.yjmm;
	}

	public void setYjmm(String yjmm) {
		this.yjmm = yjmm;
	}

	@Column(name = "yjdd", precision = 2, scale = 0)
	public String getYjdd() {
		return this.yjdd;
	}

	public void setYjdd(String yjdd) {
		this.yjdd = yjdd;
	}

	@Column(name = "dasryy", precision = 4, scale = 0)
	public String getDasryy() {
		return this.dasryy;
	}

	public void setDasryy(String dasryy) {
		this.dasryy = dasryy;
	}

	@Column(name = "dasrmm", precision = 2, scale = 0)
	public String getDasrmm() {
		return this.dasrmm;
	}

	public void setDasrmm(String dasrmm) {
		this.dasrmm = dasrmm;
	}

	@Column(name = "dasrdd", precision = 2, scale = 0)
	public String getDasrdd() {
		return this.dasrdd;
	}

	public void setDasrdd(String dasrdd) {
		this.dasrdd = dasrdd;
	}

	@Column(name = "dasrjs", length = 4)
	public String getDasrjs() {
		return this.dasrjs;
	}

	public void setDasrjs(String dasrjs) {
		this.dasrjs = dasrjs;
	}

	@Column(name = "daycyy", precision = 4, scale = 0)
	public String getDaycyy() {
		return this.daycyy;
	}

	public void setDaycyy(String daycyy) {
		this.daycyy = daycyy;
	}

	@Column(name = "daycmm", precision = 2, scale = 0)
	public String getDaycmm() {
		return this.daycmm;
	}

	public void setDaycmm(String daycmm) {
		this.daycmm = daycmm;
	}

	@Column(name = "daycdd", precision = 2, scale = 0)
	public String getDaycdd() {
		return this.daycdd;
	}

	public void setDaycdd(String daycdd) {
		this.daycdd = daycdd;
	}

	@Column(name = "ywhc", length = 200)
	public String getYwhc() {
		return this.ywhc;
	}

	public void setYwhc(String ywhc) {
		this.ywhc = ywhc;
	}

	@Column(name = "ycry", length = 40)
	public String getYcry() {
		return this.ycry;
	}

	public void setYcry(String ycry) {
		this.ycry = ycry;
	}

	@Column(name = "xyjs", length = 4)
	public String getXyjs() {
		return this.xyjs;
	}

	public void setXyjs(String xyjs) {
		this.xyjs = xyjs;
	}

	@Column(name = "bz", length = 7999)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name = "num_lsh", precision = 10, scale = 0)
	public Long getNumLsh() {
		return this.numLsh;
	}

	public void setNumLsh(Long numLsh) {
		this.numLsh = numLsh;
	}

	@Column(name = "dwh", length = 10)
	public String getDwh() {
		return this.dwh;
	}

	public void setDwh(String dwh) {
		this.dwh = dwh;
	}

	@Column(name = "ajsm", length = 7999)
	public String getAjsm() {
		return this.ajsm;
	}

	public void setAjsm(String ajsm) {
		this.ajsm = ajsm;
	}

	@Column(name = "ljr", length = 50)
	public String getLjr() {
		return this.ljr;
	}

	public void setLjr(String ljr) {
		this.ljr = ljr;
	}

	@Column(name = "jcr", length = 50)
	public String getJcr() {
		return this.jcr;
	}

	public void setJcr(String jcr) {
		this.jcr = jcr;
	}

	@Column(name = "yjflag", length = 1)
	public String getYjflag() {
		return this.yjflag;
	}

	public void setYjflag(String yjflag) {
		this.yjflag = yjflag;
	}

	@Column(name = "yjlist", length = 800)
	public String getYjlist() {
		return this.yjlist;
	}

	public void setYjlist(String yjlist) {
		this.yjlist = yjlist;
	}

	@Column(name = "jsdw", length = 10)
	public String getJsdw() {
		return this.jsdw;
	}

	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}

	@Column(name = "by1", length = 30)
	public String getBy1() {
		return this.by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}

	@Column(name = "by2", length = 200)
	public String getBy2() {
		return this.by2;
	}

	public void setBy2(String by2) {
		this.by2 = by2;
	}

	@Column(name = "by3", length = 200)
	public String getBy3() {
		return this.by3;
	}

	public void setBy3(String by3) {
		this.by3 = by3;
	}

	@Column(name = "ip", length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "ModifyManDwh", length = 100)
	public String getModifyManDwh() {
		return this.modifyManDwh;
	}

	public void setModifyManDwh(String modifyManDwh) {
		this.modifyManDwh = modifyManDwh;
	}

	@Column(name = "ModifyManCname", length = 100)
	public String getModifyManCname() {
		return this.modifyManCname;
	}

	public void setModifyManCname(String modifyManCname) {
		this.modifyManCname = modifyManCname;
	}

	@Column(name = "curModifyTime", length = 100)
	public String getCurModifyTime() {
		return this.curModifyTime;
	}

	public void setCurModifyTime(String curModifyTime) {
		this.curModifyTime = curModifyTime;
	}

	@Column(name = "logAction", length = 20)
	public String getLogAction() {
		return this.logAction;
	}

	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}

	@Column(name = "square", length = 1)
	public String getSquare() {
		return this.square;
	}

	public void setSquare(String square) {
		this.square = square;
	}

	@Column(name = "projectType", length = 5)
	public String getProjectType() {
		return this.projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

}