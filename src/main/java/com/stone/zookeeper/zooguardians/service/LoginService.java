package com.stone.zookeeper.zooguardians.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	@Value("${admin.user}")
	private String user;
	
	@Value("${admin.pwd}")
	private String pwd;
	
	public boolean isValidUser(String username,String passwd) {
		return user.equals(username) && pwd.equals(passwd);
	}
}
