package com.hr.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="xmpz")
public class ProjectType implements java.io.Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	
	/**
	 * flag  是否有效  1有效  0无效
	 */
	private String flag="1";
	private String sortNum;

	/** default constructor */
	public ProjectType() {
	}

	/** minimal constructor */
	public ProjectType(String id, String name, String sortNum) {
		this.id = id;
		this.name = name;
		this.sortNum = sortNum;
	}

	/** full constructor */
	public ProjectType(String id, String name, String flag, String sortNum) {
		this.id = id;
		this.name = name;
		this.flag = flag;
		this.sortNum = sortNum;
	}

	// Property accessors

	@Id
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "sortNum", nullable = false, length = 5)
	public String getSortNum() {
		return this.sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProjectType))
			return false;
		ProjectType castOther = (ProjectType) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getFlag() == castOther.getFlag()) || (this.getFlag() != null
						&& castOther.getFlag() != null && this.getFlag()
						.equals(castOther.getFlag())))
				&& ((this.getSortNum() == castOther.getSortNum()) || (this
						.getSortNum() != null && castOther.getSortNum() != null && this
						.getSortNum().equals(castOther.getSortNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getFlag() == null ? 0 : this.getFlag().hashCode());
		result = 37 * result
				+ (getSortNum() == null ? 0 : this.getSortNum().hashCode());
		return result;
	}

}