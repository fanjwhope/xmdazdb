package com.hr.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XMDA_YJ")
public class XmwjYJ implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	/**
	 * ����
	 */
	private long id;
	
	/**
	 * �ƽ���λ
	 */
	private String yjdw;
	
	/**
	 * ���յ�λ
	 */
	private String jsdw;
	
	/**
	 * ��־  1��ʾ������   2��ʾ�������  0��ʾ�ܾ����������߿���ʱɾ��������¼
	 */
	private String yjflag;
	
	/**
	 * �ƽ���
	 */
	private String yjr;
	private String yjy;
	private String yjm;
	private String yjd;
	
	/**
	 * ������
	 */
	private String jsr;
	private String jsy;
	private String jsm;
	private String jsd;
	
	private String lsh;
	
	
	
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "yjdw", nullable = false, length = 10)
	public String getYjdw() {
		return this.yjdw;
	}

	public void setYjdw(String yjdw) {
		this.yjdw = yjdw;
	}

	@Column(name = "jsdw", nullable = false, length = 10)
	public String getJsdw() {
		return this.jsdw;
	}

	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}
	
	@Column(name = "yjflag", nullable = false, length = 1)
	public String getYjflag() {
		return this.yjflag;
	}

	public void setYjflag(String yjflag) {
		this.yjflag = yjflag;
	}

	@Column(name = "yjr", length = 10)
	public String getYjr() {
		return this.yjr;
	}

	public void setYjr(String yjr) {
		this.yjr = yjr;
	}

	@Column(name = "yjy", length = 4)
	public String getYjy() {
		return this.yjy;
	}

	public void setYjy(String yjy) {
		this.yjy = yjy;
	}

	@Column(name = "yjm", length = 2)
	public String getYjm() {
		return this.yjm;
	}

	public void setYjm(String yjm) {
		this.yjm = yjm;
	}

	@Column(name = "yjd", length = 2)
	public String getYjd() {
		return this.yjd;
	}

	public void setYjd(String yjd) {
		this.yjd = yjd;
	}

	@Column(name = "jsr", length = 10)
	public String getJsr() {
		return this.jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

	@Column(name = "jsy", length = 4)
	public String getJsy() {
		return this.jsy;
	}

	public void setJsy(String jsy) {
		this.jsy = jsy;
	}

	@Column(name = "jsm", length = 2)
	public String getJsm() {
		return this.jsm;
	}

	public void setJsm(String jsm) {
		this.jsm = jsm;
	}

	@Column(name = "jsd", length = 2)
	public String getJsd() {
		return this.jsd;
	}

	public void setJsd(String jsd) {
		this.jsd = jsd;
	}

	

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	

	public XmwjYJ(long id, String yjdw, String jsdw, String yjflag, String yjr,
			String yjy, String yjm, String yjd, String jsr, String jsy,
			String jsm, String jsd, String lsh) {
		super();
		this.id = id;
		this.yjdw = yjdw;
		this.jsdw = jsdw;
		this.yjflag = yjflag;
		this.yjr = yjr;
		this.yjy = yjy;
		this.yjm = yjm;
		this.yjd = yjd;
		this.jsr = jsr;
		this.jsy = jsy;
		this.jsm = jsm;
		this.jsd = jsd;
		this.lsh = lsh;
	}

	public XmwjYJ() {
		super();
	}
	
	

}
