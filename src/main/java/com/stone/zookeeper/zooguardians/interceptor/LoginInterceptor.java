package com.stone.zookeeper.zooguardians.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Service
public class LoginInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) {
		
		HttpSession session = request.getSession();
        if (session.getAttribute(WebSecurityConfig.SESSION_KEY) != null) {
            return true;
        }
		return false;
	}
}