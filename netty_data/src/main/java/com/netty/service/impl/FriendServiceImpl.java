package com.netty.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netty.dao.FriendDao;
import com.netty.entity.Friend;
import com.netty.entity.User;
import com.netty.service.FriendService;
@Service("friendServiceImpl")
public class FriendServiceImpl implements FriendService{
	@Autowired
	public FriendDao friendDao;	

	public List<Friend> friendList(String user_number) {
		// TODO Auto-generated method stub
		List<Friend> friendList= friendDao.friendList(user_number);
		return friendList;
	}
}
