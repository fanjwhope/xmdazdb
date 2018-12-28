package com.hr.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "${archiveType}_${departmentCode}_bak")
public class ArchiveDepartBak implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long lsh;
	private Long sortNum;
	private String damc;
	private String dalx;
	private String days;
	private String dabz;
	private String dwh;
	private Long flag;
	private String archivetime;
	private String createtime;
	private String modifyTime;
	private String by1;
	private String by2;
	private Integer isnotstd;
	private String ip;
	private int  tabindex;
	private String modifyManDwh;
	private String modifyManCname;
	private String curModifyTime;
	private String logAction;

	// Constructors

	/** default constructor */
	public ArchiveDepartBak() {
	}

	
    

	
	public ArchiveDepartBak( Long lsh, Long sortNum, String damc,
			String dalx, String days, String dabz, String dwh, Long flag,
			String archivetime, String createtime, String modifyTime,
			String by1, String by2, Integer isnotstd, String ip, int tabindex,
			String modifyManDwh, String modifyManCname, String curModifyTime,
			String logAction) {
		super();
		this.lsh = lsh;
		this.sortNum = sortNum;
		this.damc = damc;
		this.dalx = dalx;
		this.days = days;
		this.dabz = dabz;
		this.dwh = dwh;
		this.flag = flag;
		this.archivetime = archivetime;
		this.createtime = createtime;
		this.modifyTime = modifyTime;
		this.by1 = by1;
		this.by2 = by2;
		this.isnotstd = isnotstd;
		this.ip = ip;
		this.tabindex = tabindex;
		this.modifyManDwh = modifyManDwh;
		this.modifyManCname = modifyManCname;
		this.curModifyTime = curModifyTime;
		this.logAction = logAction;
	}






	// Property accessors
	@Column(name = "lsh", nullable = false, precision = 10, scale = 0)
	public Long getLsh() {
		return this.lsh;
	}

	public void setLsh(Long lsh) {
		this.lsh = lsh;
	}

	@Column(name = "sort_num", nullable = false, precision = 16, scale = 0)
	public Long getSortNum() {
		return this.sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

	@Column(name = "damc", nullable = false, length = 500)
	public String getDamc() {
		return this.damc;
	}

	public void setDamc(String damc) {
		this.damc = damc;
	}

	@Column(name = "dalx", nullable = false, length = 10)
	public String getDalx() {
		return this.dalx;
	}

	public void setDalx(String dalx) {
		this.dalx = dalx;
	}

	@Column(name = "days", length = 4)
	public String getDays() {
		return this.days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	@Column(name = "dabz", length = 100)
	public String getDabz() {
		return this.dabz;
	}

	public void setDabz(String dabz) {
		this.dabz = dabz;
	}

	@Column(name = "dwh", length = 10)
	public String getDwh() {
		return this.dwh;
	}

	public void setDwh(String dwh) {
		this.dwh = dwh;
	}

	@Column(name = "flag", precision = 10, scale = 0)
	public Long getFlag() {
		return this.flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	@Column(name = "archivetime", length = 100)
	public String getArchivetime() {
		return this.archivetime;
	}

	public void setArchivetime(String archivetime) {
		this.archivetime = archivetime;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "modifyTime", length = 20)
	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
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

	@Column(name = "isnotstd", precision = 5, scale = 0)
	public Integer getIsnotstd() {
		return this.isnotstd;
	}

	public void setIsnotstd(Integer isnotstd) {
		this.isnotstd = isnotstd;
	}
	
	@Column(name = "tabindex", precision = 2, scale = 0)
	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
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

}
