package com.hr.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "XMDA_YJList")
public class XmwjYJList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 移交人
	 */
	private String yjr;
	
	/**
	 * 接收人
	 */
	private String jsr;
	
	/**
	 * 移交时间
	 */
	private String yjsj;
	
	/**
	 * 流水号
	 */
	private Long lsh;
	


	@Column(name = "yjr", nullable = false, length = 30)
	public String getYjr() {
		return this.yjr;
	}

	public void setYjr(String yjr) {
		this.yjr = yjr;
	}

	@Column(name = "jsr", nullable = false, length = 30)
	public String getJsr() {
		return this.jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	
	@Column(name = "yjsj", nullable = false, length = 50)
	public String getYjsj() {
		return this.yjsj;
	}

	public void setYjsj(String yjsj) {
		this.yjsj = yjsj;
	}

	@Column(name = "lsh", nullable = false, precision = 10, scale = 0)
	public Long getLsh() {
		return this.lsh;
	}

	public void setLsh(Long lsh) {
		this.lsh = lsh;
	}

	public XmwjYJList(String yjr, String jsr, String yjsj, Long lsh) {
		super();
		this.yjr = yjr;
		this.jsr = jsr;
		this.yjsj = yjsj;
		this.lsh = lsh;
	}

	public XmwjYJList() {
		super();
	}
	
	

}
