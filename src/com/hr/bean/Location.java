package com.hr.bean;

import javax.persistence.Table;

@Table(name="wsda_gpk")
public class Location {
	private String path;  //gpk·��
	private String mmgz;  //
	private String flag;  //y,�洢·����Ψһ����n,ɨ���·��
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMmgz() {
		return mmgz;
	}
	public void setMmgz(String mmgz) {
		this.mmgz = mmgz;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
