package com.stone.zookeeper.zooguardians.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import com.stone.zookeeper.zooguardians.cache.ZkClientCacheFactory;
import com.stone.zookeeper.zooguardians.common.Constants;
import com.stone.zookeeper.zooguardians.entity.InteligentZKClient;
import com.stone.zookeeper.zooguardians.entity.MNTR;
import com.stone.zookeeper.zooguardians.entity.ZKNodeInfo;

@Component
public class ZookeeperService {

	public MNTR getMNTRInfo(String serverIp,int port) {
		TelnetClient telnetClient = new TelnetClient("vt200");  //指明Telnet终端类型，否则会返回来的数据中文会乱码
        telnetClient.setDefaultTimeout(5000); //socket延迟时间：5000ms
        PrintStream pStream = null;
        MNTR mntr = new MNTR();
        mntr.setAddr(serverIp+":"+port);
        try {
			telnetClient.connect(serverIp,port);
	        InputStream inputStream = telnetClient.getInputStream(); //读取命令的流
	        pStream = new PrintStream(telnetClient.getOutputStream());  //写命令的流
	        pStream.print("mntr");//传输用户名,改写你的用户名,该用户名属于TelnetClients组 
	        pStream.flush();
	        String line = null;
	        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
	        line = br.readLine();
	        while(line != null) {
	        	if(line.startsWith(Constants.MNTR_ZK_SERVER_STATE_KEY)) {
	        		mntr.setRole(line.substring(Constants.MNTR_ZK_SERVER_STATE_KEY.length()+1).trim());
	        	}else if(line.startsWith(Constants.MNTR_ZK_ZNODE_COUNT_KEY)) {
	        		mntr.setZknodes(line.substring(Constants.MNTR_ZK_ZNODE_COUNT_KEY.length()+1).trim());
	        	}else if(line.startsWith(Constants.MNTR_ZK_EPHEMERALS_COUNT_KEY)) {
	        		mntr.setEmpernal(line.substring(Constants.MNTR_ZK_EPHEMERALS_COUNT_KEY.length()+1).trim());
	        	}else if(line.startsWith(Constants.MNTR_ZK_WATCH_COUNT_KEY)) {
	        		mntr.setWatches(line.substring(Constants.MNTR_ZK_WATCH_COUNT_KEY.length()+1).trim());
	        	}else if(line.startsWith(Constants.MNTR_ZK_NUM_ALIVE_CONNECTIONS_KEY)) {
	        		mntr.setConnections(line.substring(Constants.MNTR_ZK_NUM_ALIVE_CONNECTIONS_KEY.length()+1).trim());
	        	}
	        	line = br.readLine();
	        }
	        pStream.println("exit"); //写命令
	        pStream.flush(); //将命令发送到telnet Server
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        if(null != pStream) {
	            pStream.close();
	        }
	        try {
	        	if(null != telnetClient) {
	        		telnetClient.disconnect();
	        	}
			} catch (IOException e) {
				e.printStackTrace();
				telnetClient = null;
			}
		}
        return mntr;
	}
	
	//读取某个路径下的所有子节点数据
	public List<ZKNodeInfo> getChildren(String server,String path,String parentId){
		InteligentZKClient client = ZkClientCacheFactory.getZKClientIfNoneCreate(server);
		List<String> children = client.getChildren(path);
		List<ZKNodeInfo> nodes = new ArrayList<ZKNodeInfo>();
		int index = 1;
		for(String child : children) {
			ZKNodeInfo node = new ZKNodeInfo();
			node.setId(parentId+"."+(index++));
			node.setIsParent(true);
			try {
				node.setName(URLDecoder.decode(child,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			node.setParentId(parentId);
			node.setPath(path+"/"+child);
			if(path.equals("/")) {
				node.setPath("/"+child);
			}
			nodes.add(node);
		}
		return nodes;
	}
	
	//读取某个路径下的所有子节点数据
	public ZKNodeInfo getNodeInfo(String server,String path){
		InteligentZKClient client = ZkClientCacheFactory.getZKClientIfNoneCreate(server);
		String data = "";
		Stat stat = new Stat();
		try {
			String odata = client.readData(path,stat);
			if(odata != null) {
				data = new String(odata.getBytes(),"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ZKNodeInfo node = new ZKNodeInfo();
		node.setData(data);
		node.setChildNum(stat.getNumChildren());
		if(stat.getEphemeralOwner()==0) {
			node.setIsEphemeral(false);
		}else {
			node.setIsEphemeral(true);
		}
		return node;
	}
	
	
	public String deletePath(String server,String path) {
		InteligentZKClient client = ZkClientCacheFactory.getZKClientIfNoneCreate(server);
		boolean ret = false;
		try {
			ret= client.deletePath(path);
		} catch (Exception e) {
		}
		return ret?"删除成功":"删除失败";
	}
	
	public String deepDeletePath(String server,String path) {
		InteligentZKClient client = ZkClientCacheFactory.getZKClientIfNoneCreate(server);
		boolean ret = false;
		try {
			ret= client.deletePathRecursive(path);
		} catch (Exception e) {
		}
		return ret?"级联删除成功":"级联删除失败";
	}
}
