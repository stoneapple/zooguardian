package com.stone.zookeeper.zooguardians.util;

import com.stone.zookeeper.zooguardians.exception.InvalidZKAddressException;

public class ZkUtils {

	public static String getFirstServerFromCluster(String cluster) 
		throws InvalidZKAddressException{
		//如果传入的是集群，则取第一个
		String server = cluster;
		if(cluster.indexOf(",")>0) {
			server = server.substring(0,cluster.indexOf(","));
		}
		if(server.indexOf(":")<0) {
			throw new RuntimeException("非法的ZK服务器地址："+cluster);
		}
		String[] addrStrs = server.split(":");
		if(addrStrs.length != 2) {
			throw new RuntimeException("非法的ZK服务器地址："+cluster);
		}
		return server;
	}
}
