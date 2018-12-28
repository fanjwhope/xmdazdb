package com.hr.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="yhxx")
public class User {
	private String dwmc;
	private String userid;
	private String userpwd;
	private String dwh;
	private Integer numSort;
	private String roles;
	private String dataBaseName;
	
	public User(){
		
	}


	public User(String dwmc, String userid, String userpwd, String dwh,
			Integer numSort, String roles, String dataBaseName) {
		super();
		this.dwmc = dwmc;
		this.userid = userid;
		this.userpwd = userpwd;
		this.dwh = dwh;
		this.numSort = numSort;
		this.roles = roles;
		this.dataBaseName = dataBaseName;
	}
    
	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	@Id
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getDwh() {
		return dwh;
	}

	public void setDwh(String dwh) {
		this.dwh = dwh;
	}

	@Column(name="num_sort")
	public Integer getNumSort() {
		return numSort;
	}

	public void setNumSort(Integer numSort) {
		this.numSort = numSort;
	}

	@Transient
	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "User [dwmc=" + dwmc + ", userid=" + userid + ", userpwd="
				+ userpwd + ", dwh=" + dwh + ", numSort=" + numSort
				+ ", roles=" + roles + ", dataBaseName=" + dataBaseName + "]";
	}

	
}
