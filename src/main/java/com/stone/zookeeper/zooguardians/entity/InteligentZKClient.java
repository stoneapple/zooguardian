package com.stone.zookeeper.zooguardians.entity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;

import com.stone.zookeeper.zooguardians.util.MyZkSerializer;

public class InteligentZKClient {

	//真实的zkclient
	private ZkClient client;
	
	//zookeeper地址
	private String serverAddr;
	
	//最后一次使用的时间，超过固定时间未使用，则放弃连接，释放资源
	private long lastUsed;
	
	private boolean isAvaliable;
	
	public InteligentZKClient(String server) {
		this.serverAddr = server;
		try {
			client = new ZkClient(server, 60000, 10000,new MyZkSerializer());
			isAvaliable = true;
			lastUsed = System.currentTimeMillis();
			final int checkPeriod = 10;//默认检查周期是10分钟
			//定期10分钟查看是否有人使用，没人使用就关闭连接
			Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
				public void run() {
					long current = System.currentTimeMillis();
					if(current-lastUsed>1000*60*checkPeriod) {
						if(client!=null) {
							client.close();
							client = null;
							isAvaliable = false;
						}
					}
				}
			}, 1000, checkPeriod, TimeUnit.MINUTES);
		} catch (Exception e) {
			isAvaliable = false;
		}
	}
	
	public void reCreateClient() {
		try {
			client = new ZkClient(serverAddr, 60000, 10000);
			isAvaliable = true;
			lastUsed = System.currentTimeMillis();
		}catch (Exception e) {
			isAvaliable = false;
		}
	}
	
	
	public List<String> getChildren(String path){
		updateLastUsed();
		return client.getChildren(path);
	}
	
	public boolean deletePathRecursive(String parent) {
		updateLastUsed();
		return client.deleteRecursive(parent);
	}
	
	public boolean deletePath(String path) {
		updateLastUsed();
		return client.delete(path);
	}
	
	public String readData(String parent) {
		updateLastUsed();
		return client.readData(parent);
	}	

	public String readData(String parent,Stat stat) {
		updateLastUsed();
		return client.readData(parent,stat);
	}
	
	private void updateLastUsed() {
		lastUsed = System.currentTimeMillis();
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public long getLastUsed() {
		return lastUsed;
	}

	public boolean isAvaliable() {
		return isAvaliable;
	}
}
