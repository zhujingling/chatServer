package com.netty.process.UCProcess;

import javax.annotation.Resource;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.netty.pkg.Pkg;
import com.netty.process.utils.ApplicationContextUtil;
import com.netty.processor.IProcessor;
import com.netty.service.UserService;
import com.netty.session.ISession;

public class LoginProcess implements IProcessor {

	public String process(ISession session, Pkg pkg) {
		// TODO Auto-generated method stub
		String user_number=pkg.getStr(0);
		String user_password=pkg.getStr(1);
		return login(user_number,user_password);
	}
	
	@Resource
	private UserService userService = ApplicationContextUtil
			.getBean("userServiceImpl");
	
	public String login(String user_number, String user_password) {
		return userService.login(user_number, user_password);
	}

}
