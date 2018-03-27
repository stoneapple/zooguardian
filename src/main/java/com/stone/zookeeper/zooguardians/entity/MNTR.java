package com.stone.zookeeper.zooguardians.entity;

public class MNTR {

	private String addr;
	private String role;
	private String zknodes;
	private String empernal;
	private String watches;
	private String connections;
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getZknodes() {
		return zknodes;
	}
	public void setZknodes(String zknodes) {
		this.zknodes = zknodes;
	}
	public String getEmpernal() {
		return empernal;
	}
	public void setEmpernal(String empernal) {
		this.empernal = empernal;
	}
	public String getConnections() {
		return connections;
	}
	public void setConnections(String connections) {
		this.connections = connections;
	}
	public String getWatches() {
		return watches;
	}
	public void setWatches(String watches) {
		this.watches = watches;
	}
}
