package com.stone.zookeeper.zooguardians.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stone.zookeeper.zooguardians.entity.MNTR;
import com.stone.zookeeper.zooguardians.entity.TestEnv;
import com.stone.zookeeper.zooguardians.exception.InvalidZKAddressException;
import com.stone.zookeeper.zooguardians.interceptor.WebSecurityConfig;
import com.stone.zookeeper.zooguardians.service.EnvLoadService;
import com.stone.zookeeper.zooguardians.service.LoginService;
import com.stone.zookeeper.zooguardians.service.ZookeeperService;
import com.stone.zookeeper.zooguardians.util.ZkUtils;

@Controller
public class PageController {

	@Autowired
	ZookeeperService zookeeperService;

	@Autowired
	LoginService loginService;
	
	@Autowired
	EnvLoadService envService;
	
	
	@GetMapping("/login")
    public String login() {
        return "login";
    }
	
    @PostMapping("/login")
    @ResponseBody
    public  Map<String, Object> loginPost(String account, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(!loginService.isValidUser(account, password)) {
            map.put("success", false);
            map.put("message", "密码错误");
            return map;
        }
        // 设置session
        session.setAttribute(WebSecurityConfig.SESSION_KEY, account);
        session.setMaxInactiveInterval(3600);//会话保持1个小时

        map.put("success", true);
        map.put("message", "登录成功");
        return map;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes attr,HttpServletRequest request) {  
        //50004是自己定义的作物代码！  
        attr.addAttribute("zkaddr", request.getParameter("zkaddr"));
        // 移除session
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/zookeeper/tree";
    }
    
	@RequestMapping("/")
	public String index(Model model) {
		List<TestEnv> envlist = envService.loadConfigEnvs();
		model.addAttribute("envs", envlist);
		return "index";
	}
	
	@RequestMapping("zookeeper/tree")
	public String zktree(@RequestParam("zkaddr") String zkaddr,Model model) {
		model.addAttribute("zkaddr", zkaddr);
		//如果传入的是集群，则取第一个
		String server = null;
		try {
			server = ZkUtils.getFirstServerFromCluster(zkaddr);
		} catch (InvalidZKAddressException e) {
			e.printStackTrace();
		}
		String[] addrStrs = server.split(":");
		MNTR mntr = zookeeperService.getMNTRInfo(addrStrs[0], Integer.valueOf(addrStrs[1]));
		model.addAttribute("mntr", mntr);
		return "zktree";
	}
}
