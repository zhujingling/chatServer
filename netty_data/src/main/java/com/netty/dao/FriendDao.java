package com.netty.dao;

import java.util.List;

import com.netty.entity.Friend;

public interface FriendDao {
	List<Friend> friendList(String user_number);
}
