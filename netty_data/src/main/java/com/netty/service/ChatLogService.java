package com.netty.service;

import java.util.List;

import com.netty.entity.ChatLog;


public interface ChatLogService {

	List<ChatLog> chatLogByFriend(String userId);
}
