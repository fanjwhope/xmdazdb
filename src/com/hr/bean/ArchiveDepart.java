package com.hr.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "${archiveType}_${departmentCode}")
public class ArchiveDepart implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
    
	private Long id;
    /**
     * 卷内容所属项目流水
     */
	private Long lsh;
	
	/**
	 * 排序字段
	 */
	private Long sortNum;
	
	/**
	 * 档案名称
	 */
	private String damc;
	
	/**
	 * 子号
	 */
	private String dalx;
	
	/**
	 * 页码
	 */
	private String days;
	
	/**
	 * 备注
	 */
	private String dabz;
	
	/**
	 * 单位号
	 */
	private String dwh;
	private Long flag;
	
	/**
	 * 填写时间
	 */
	private String archivetime;
	private String createtime;
	private String modifyTime;
	private String by1;
	private String by2;
	
	/**
	 * 是否属于标准
	 */
	private Integer isnotstd;

	// Constructors

	/** default constructor */
	public ArchiveDepart() {
	}

	
    
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	
	

	public ArchiveDepart(Long id, Long lsh, Long sortNum, String damc,
			String dalx, String days, String dabz, String dwh, Long flag,
			String archivetime, String createtime, String modifyTime,
			String by1, String by2, Integer isnotstd) {
		super();
		this.id = id;
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



	@Override
	public String toString() {
		return "ArchiveDepart [id=" + id + ", lsh=" + lsh + ", sortNum="
				+ sortNum + ", damc=" + damc + ", dalx=" + dalx + ", days="
				+ days + ", dabz=" + dabz + ", dwh=" + dwh + ", flag=" + flag
				+ ", archivetime=" + archivetime + ", createtime=" + createtime
				+ ", modifyTime=" + modifyTime + ", by1=" + by1 + ", by2="
				+ by2 + ", isnotstd=" + isnotstd + "]";
	}

	
}