package com.hr.global.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * easyUIʹ�õ�treeNodeģ��
 * @author �Ʒ�
 *
 */
public class TreeNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;// ���ڵ�����
	private String iconCls;// ǰ���Сͼ����ʽ
	private Boolean checked = false;// �Ƿ�ѡ״̬
	private Map<String, String> attributes;// ��������
	private List<TreeNode> children=new ArrayList<TreeNode>();// �ӽڵ�
	private String state = "open";// �Ƿ�չ��(open,closed)
	
	public TreeNode() {
		
	}

	public TreeNode(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
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

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void addChildren(TreeNode child){
		if(null==this.children){
			this.children=new ArrayList<TreeNode>();
		}
		this.children.add(child);
	}
}
