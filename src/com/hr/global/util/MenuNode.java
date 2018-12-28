package com.hr.global.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单节点模型
 * @author 黄飞
 *
 */
public class MenuNode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;// 节点名称
	private String url;// 节点对应的页面路径
	private Map<String, String> attributes;// 其他参数
	private List<MenuNode> children=new ArrayList<MenuNode>();// 子节点
	private int tabIndex;
	
	public MenuNode() {
		
	}

	public MenuNode(String id, String name, String url, String tabIndex) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.tabIndex=Integer.parseInt(tabIndex);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(String key, String value) {
		if(null==this.attributes){
			this.attributes=new HashMap<String, String>();
		}
		this.attributes.put(key, value);
	}

	public List<MenuNode> getChildren() {
		if(null==this.children){
			this.children=new ArrayList<MenuNode>();
		}
		return children;
	}
	
	public List<MenuNode> getSortedChildren() {
		if(null==this.children){
			this.children=new ArrayList<MenuNode>();
		}
		Collections.sort(children, new MenuComparator());
		return children;
	}

	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}
	
	public void addChildren(MenuNode child){
		if(null==this.children){
			this.children=new ArrayList<MenuNode>();
		}
		this.children.add(child);
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
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
		MenuNode other = (MenuNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
