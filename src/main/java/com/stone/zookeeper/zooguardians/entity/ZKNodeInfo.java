package com.stone.zookeeper.zooguardians.entity;

public class ZKNodeInfo {

	private String id;
	private String parentId;
	private String name;
	private String data;
	private boolean isParent;
	private boolean isEphemeral;
	private int childNum;
	private String path;
	
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public boolean getIsEphemeral() {
		return isEphemeral;
	}
	public void setIsEphemeral(boolean isEphemeral) {
		this.isEphemeral = isEphemeral;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getChildNum() {
		return childNum;
	}
	public void setChildNum(int childNum) {
		this.childNum = childNum;
	}
}
