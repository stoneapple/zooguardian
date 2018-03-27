package com.stone.zookeeper.zooguardians.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.stone.zookeeper.zooguardians.entity.InteligentZKClient;

public class ZkClientCacheFactory {

	static ConcurrentHashMap<String, InteligentZKClient> cache = new ConcurrentHashMap<String, InteligentZKClient>();
	
	public static InteligentZKClient getZKClient(String server) {
		return cache.get(server);
	}
	
	public static InteligentZKClient getZKClientIfNoneCreate(String server) {
		InteligentZKClient zk = cache.get(server);
		if(zk==null) {
			synchronized (cache) {
				zk = cache.get(server);
				if(zk==null) {
					zk = new InteligentZKClient(server);
					cache.put(server, zk);
				}
			}
		}
		if(!zk.isAvaliable()) {
			zk.reCreateClient();
		}
		return zk;
	}
	
	
}
