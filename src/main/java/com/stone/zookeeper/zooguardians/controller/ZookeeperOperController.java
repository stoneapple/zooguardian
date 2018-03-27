package com.stone.zookeeper.zooguardians.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stone.zookeeper.zooguardians.entity.MNTR;
import com.stone.zookeeper.zooguardians.entity.ZKNodeInfo;
import com.stone.zookeeper.zooguardians.exception.InvalidZKAddressException;
import com.stone.zookeeper.zooguardians.service.ZookeeperService;
import com.stone.zookeeper.zooguardians.util.ZkUtils;

@Controller
public class ZookeeperOperController {

	@Autowired
	ZookeeperService zookeeperService;
	
	
	@ResponseBody
	@RequestMapping("/zookeeper/getchildren")
	public List<ZKNodeInfo> getChildren(
			@RequestParam(value="id", required = false) String id,
			@RequestParam(value="n", required = false) String n,
			@RequestParam(value="lv", required = false) String lv,
			@RequestParam(value="path", required = false) String path,
			@RequestParam(value="zkaddr", required = true) String addr){
		List<ZKNodeInfo> retList = new ArrayList<ZKNodeInfo>();
		if(id==null) {
			//根节点
			ZKNodeInfo root = new ZKNodeInfo();
			root.setId("1");
			root.setParentId("1");
			root.setIsEphemeral(false);
			root.setData("");
			root.setName("Root");
			root.setIsParent(true);
			root.setPath("/");
			retList.add(root);
		}else {
			//读取path下面的所有节点
			retList = zookeeperService.getChildren(addr, path, id);
		}
		return retList;
	}
	
	
	@ResponseBody
	@RequestMapping("/zookeeper/serverinfo")
	public MNTR getServerInfo(
			@RequestParam(value="addr", required = true) String serverAddr){
		//如果传入的是集群，则取第一个
		String server = null;
		try {
			server = ZkUtils.getFirstServerFromCluster(serverAddr);
		} catch (InvalidZKAddressException e) {
			e.printStackTrace();
		}
		String[] addrStrs = server.split(":");
		MNTR mntr = zookeeperService.getMNTRInfo(addrStrs[0], Integer.valueOf(addrStrs[1]));
		return mntr;
	}

	
	@ResponseBody
	@RequestMapping("/zookeeper/getnodeinfo")
	public ZKNodeInfo getNodeInfo(
			@RequestParam(value="zkaddr", required = true)String serverAddr,
			@RequestParam(value="path", required = true) String path){
		//如果传入的是集群，则取第一个
		String server = null;
		try {
			server = ZkUtils.getFirstServerFromCluster(serverAddr);
		} catch (InvalidZKAddressException e) {
			e.printStackTrace();
		}
		ZKNodeInfo nodeinfo = zookeeperService.getNodeInfo(server,path);
		return nodeinfo;
	}
	
	@ResponseBody
	@RequestMapping(value="/zkoperation/delete",produces="text/html;charset=UTF-8;")
	public String delete(
			@RequestParam(value="zkaddr", required = true)String serverAddr,
			@RequestParam(value="path", required = true) String path) {
		String server = null;
		try {
			server = ZkUtils.getFirstServerFromCluster(serverAddr);
		} catch (InvalidZKAddressException e) {
			e.printStackTrace();
		}
		return zookeeperService.deletePath(server,path);
	}
	
	@ResponseBody
	@RequestMapping(value="/zkoperation/deepdelete",produces="text/html;charset=UTF-8;")
	public String deepDelete(
			@RequestParam(value="zkaddr", required = true)String serverAddr,
			@RequestParam(value="path", required = true) String path) {
		String server = null;
		try {
			server = ZkUtils.getFirstServerFromCluster(serverAddr);
		} catch (InvalidZKAddressException e) {
			e.printStackTrace();
		}
		return zookeeperService.deepDeletePath(server, path);
	}
}
