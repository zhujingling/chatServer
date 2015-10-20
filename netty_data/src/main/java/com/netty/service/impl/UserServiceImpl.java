package com.netty.service.impl;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netty.dao.FriendDao;
import com.netty.dao.UserDao;
import com.netty.entity.Friend;
import com.netty.entity.User;
import com.netty.pkg.Pkg;
import com.netty.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	@Autowired
	public UserDao userDao;
	@Autowired
	public FriendDao friendDao;
	public User  getUser(String user_number) {
		// TODO Auto-generated method stub
		User  userList = userDao.getUser(user_number);
		return userList;
	}
	
	public boolean checkUser(String user_number) {
		// TODO Auto-generated method stub
		User user = userDao.checkUser(user_number);
		if (user != null) {
			return true;
		}
		return false;
	}

	public boolean checkPwd(String user_number, String user_password) {
		// TODO Auto-generated method stub
		User user = userDao.checkPwd(user_number, user_password);
		if (user != null) {
			return true;
		}
		return false;
	}

	public String login(String user_number, String user_password) {
		// TODO Auto-generated method stub
		JSONObject jsonRoot = new JSONObject();
		if (checkUser(user_number)) {
			if (checkPwd(user_number, user_password)) {
				User user = userDao.login(user_number, user_password);
				jsonRoot.put("code", 200);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("username", user.getUser_name());
				jsonObject.put("userbirthday", user.getUser_birthday());
				jsonObject.put("email", user.getUser_email());
				jsonObject.put("createtime", user.getUser_createtime());
				jsonObject.put("usernumber", user.getUser_number());
				jsonObject.put("phone", user.getUser_phone());
				jsonRoot.put("user", jsonObject);
				
				
				List<Friend> friendList=friendDao.friendList(user_number);
				JSONArray jAFriend=new JSONArray();
				for (Friend friend : friendList) {
					User friendInfo=getUser(friend.getFriend_number());
					JSONObject jsonFriend = new JSONObject();
					jsonFriend.put("username", friendInfo.getUser_name());
					jsonFriend.put("userbirthday", friendInfo.getUser_birthday());
					jsonFriend.put("email", friendInfo.getUser_email());
					jsonFriend.put("createtime", friendInfo.getUser_createtime());
					jsonFriend.put("usernumber", friendInfo.getUser_number());
					jsonFriend.put("phone", friendInfo.getUser_phone());
					jAFriend.put(jsonFriend);
				}
				
				jsonRoot.put("friendList", jAFriend);
			} else {
				jsonRoot.put("code", 100);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("error", "密码错误");
				jsonRoot.put("msg", jsonObject);
			}
		} else {
			jsonRoot.put("code", 101);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", "用户不存在");
			jsonRoot.put("msg", jsonObject);
		}
		
		return jsonRoot.toString();
	}

	

}
