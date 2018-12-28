package com.hr.bean;

import javax.persistence.Table;

@Table(name="biz")
public class Biz {
	private String biz;

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}
	
}
