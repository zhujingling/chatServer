package com.netty.service;

import java.util.List;

import com.netty.entity.Friend;

public interface FriendService {
	List<Friend> friendList(String user_number);
}
