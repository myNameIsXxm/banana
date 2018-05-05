package org.xixi.model;

import java.util.ArrayList;
import java.util.List;

public class TreeModel {
	private Long id;
	private Long parentId;
	private String text;
	private int isLast;// 1 是底级  0 不是
	private String state="open";
	private List<TreeModel> children = new ArrayList<TreeModel>();
	
	public TreeModel(Long id, Long parentId, String text,int isLast) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.isLast=isLast;
	}
	
	public TreeModel(Long id, Long parentId, String text,int isLast,String state) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.isLast=isLast;
		this.state=state;
	}
	
	public TreeModel(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getIsLast() {
		return isLast;
	}
	public void setIsLast(int isLast) {
		this.isLast = isLast;
	}
	public List<TreeModel> getChildren() {
		return children;
	}
	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
