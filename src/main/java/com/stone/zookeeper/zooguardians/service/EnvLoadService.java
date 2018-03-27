package com.stone.zookeeper.zooguardians.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stone.zookeeper.zooguardians.entity.TestEnv;

@Service
public class EnvLoadService {

	@Value("${zookeeper.env}")
	private String envStr;
	
	private List<TestEnv> envs;
	
	public List<TestEnv> loadConfigEnvs(){
		if(envs == null) {
			try {
				//为了防止乱码，临时处理一把
				envStr = new String(envStr.getBytes("iso-8859-1"),"utf-8");
				envs = JSON.parseArray(envStr,TestEnv.class);
			}catch(Exception e) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>Invalid zk environment config");
				envs = new ArrayList<TestEnv>();
			}
		}
		return envs;
	}
}
