package com.stone.zookeeper.zooguardians.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
	
	public static final String SESSION_KEY = "usersession";
	
	
	@Autowired
	public LoginInterceptor loginInterceptor;//用户登录拦截器
	
	// 自定义拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		InterceptorRegistration addInterceptor = registry.addInterceptor(loginInterceptor);
        // 拦截配置
        addInterceptor.addPathPatterns("/zkoperation/*");
	}
}
