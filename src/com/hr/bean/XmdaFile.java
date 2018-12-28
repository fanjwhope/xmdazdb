package com.hr.bean;

import java.util.ArrayList;
import java.util.List;

public class XmdaFile {
	String fileName;
	Integer totalPage;
	Integer currentPage;
	List<String> allFileNames=new ArrayList<String>();

	

	public List<String> getAllFileNames() {
		return allFileNames;
	}

	public void setAllFileNames(List<String> allFileNames) {
		this.allFileNames = allFileNames;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

}
