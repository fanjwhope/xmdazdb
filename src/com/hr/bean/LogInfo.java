package com.hr.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "LogInfo")
public class LogInfo implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Long id;
	private String ip;
	private String account;
	private String password;
	private Integer logYear;
	private Integer logMonth;
	private Integer logDay;
	private Integer logHour;
	private Integer logMinute;
	private Integer logSecond;
	private String accessFile;
	private Integer finish;
	private String sessionId;
	private String lastTime;

	// Constructors

	/** default constructor */
	public LogInfo() {
	}

	/** minimal constructor */
	public LogInfo(Long id, String ip, String account, String sessionId,
			String lastTime) {
		this.id = id;
		this.ip = ip;
		this.account = account;
		this.sessionId = sessionId;
		this.lastTime = lastTime;
	}

	/** full constructor */
	public LogInfo(Long id, String ip, String account, String password,
			Integer logYear, Integer logMonth, Integer logDay, Integer logHour,
			Integer logMinute, Integer logSecond, String accessFile,
			Integer finish, String sessionId, String lastTime) {
		this.id = id;
		this.ip = ip;
		this.account = account;
		this.password = password;
		this.logYear = logYear;
		this.logMonth = logMonth;
		this.logDay = logDay;
		this.logHour = logHour;
		this.logMinute = logMinute;
		this.logSecond = logSecond;
		this.accessFile = accessFile;
		this.finish = finish;
		this.sessionId = sessionId;
		this.lastTime = lastTime;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ip", nullable = false, length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "Account", nullable = false, length = 100)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "Password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "LogYear")
	public Integer getLogYear() {
		return this.logYear;
	}

	public void setLogYear(Integer logYear) {
		this.logYear = logYear;
	}

	@Column(name = "LogMonth")
	public Integer getLogMonth() {
		return this.logMonth;
	}

	public void setLogMonth(Integer logMonth) {
		this.logMonth = logMonth;
	}

	@Column(name = "LogDay")
	public Integer getLogDay() {
		return this.logDay;
	}

	public void setLogDay(Integer logDay) {
		this.logDay = logDay;
	}

	@Column(name = "LogHour")
	public Integer getLogHour() {
		return this.logHour;
	}

	public void setLogHour(Integer logHour) {
		this.logHour = logHour;
	}

	@Column(name = "LogMinute")
	public Integer getLogMinute() {
		return this.logMinute;
	}

	public void setLogMinute(Integer logMinute) {
		this.logMinute = logMinute;
	}

	@Column(name = "LogSecond")
	public Integer getLogSecond() {
		return this.logSecond;
	}

	public void setLogSecond(Integer logSecond) {
		this.logSecond = logSecond;
	}

	@Column(name = "AccessFile", length = 200)
	public String getAccessFile() {
		return this.accessFile;
	}

	public void setAccessFile(String accessFile) {
		this.accessFile = accessFile;
	}

	@Column(name = "Finish")
	public Integer getFinish() {
		return this.finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}

	@Column(name = "SessionID", nullable = false, length = 150)
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "LastTime", nullable = false, length = 100)
	public String getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LogInfo other = (LogInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}