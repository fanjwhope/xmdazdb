package com.hr.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tempSession")
public class TempSession implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private String sessionId;
	private String userName;
	private String dwh;
	private String priv;
	private String logonDate;
	private String other;
	private String dataBaseName;
	private String by2;
	private String by3;

	// Constructors

	/** default constructor */
	public TempSession() {
	}

	/** minimal constructor */
	public TempSession(String sessionId, String userName, String dwh) {
		this.sessionId = sessionId;
		this.userName = userName;
		this.dwh = dwh;
	}

	/** full constructor */
	public TempSession(String sessionId, String userName, String dwh,
			String priv, String logonDate, String other, String dataBaseName,
			String by2, String by3) {
		this.sessionId = sessionId;
		this.userName = userName;
		this.dwh = dwh;
		this.priv = priv;
		this.logonDate = logonDate;
		this.other = other;
		this.dataBaseName = dataBaseName;
		this.by2 = by2;
		this.by3 = by3;
	}

	// Property accessors
	@Id
	@Column(name = "sessionID")
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "UserName")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "dwh")
	public String getDwh() {
		return this.dwh;
	}

	public void setDwh(String dwh) {
		this.dwh = dwh;
	}

	@Column(name = "priv")
	public String getPriv() {
		return this.priv;
	}

	public void setPriv(String priv) {
		this.priv = priv;
	}

	@Column(name = "logonDate")
	public String getLogonDate() {
		return this.logonDate;
	}

	public void setLogonDate(String logonDate) {
		this.logonDate = logonDate;
	}

	@Column(name = "other")
	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Column(name = "dataBaseName")
	public String getDataBaseName() {
		return this.dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	@Column(name = "by2")
	public String getBy2() {
		return this.by2;
	}

	public void setBy2(String by2) {
		this.by2 = by2;
	}

	@Column(name = "by3")
	public String getBy3() {
		return this.by3;
	}

	public void setBy3(String by3) {
		this.by3 = by3;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sessionId == null) ? 0 : sessionId.hashCode());
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
		TempSession other = (TempSession) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}
	
}