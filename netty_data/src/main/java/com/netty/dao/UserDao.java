package com.netty.dao;

import org.apache.ibatis.annotations.Param;

import com.netty.entity.User;


public interface UserDao {
	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return User
	 */
	User getUser(String user_number);
	
	User checkUser(String user_number);
	
	User checkPwd(@Param(value="user_number")String user_number,@Param(value="user_password")String user_password);
	
	User login(@Param(value="user_number")String user_number,@Param(value="user_password")String user_password);

}
