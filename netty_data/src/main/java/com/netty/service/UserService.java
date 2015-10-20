package com.netty.service;

import com.netty.entity.User;
import com.netty.pkg.Pkg;

public interface UserService {
	/**
	 * @param id
	 * @return
	 */
	User getUser(String user_number);
	
	boolean checkUser(String user_number);

	boolean checkPwd(String user_number, String user_password);

	String login(String user_number, String user_password);
}
