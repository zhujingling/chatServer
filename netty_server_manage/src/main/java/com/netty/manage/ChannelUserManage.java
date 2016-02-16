package com.netty.manage;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户与通道关系
 * @author Administrator
 *
 */
public class ChannelUserManage {
	private static Map<String,Channel> channelUserMap=new HashMap<String,Channel>();
	
	/**
	 * 添加到集合里面去
	 * @param userId
	 * @param channel
	 */
	public static void addConn(String userId,Channel channel) {
		channelUserMap.put(userId, channel);
	}
	
	/**
	 * 移除通道和用户
	 * @param userId
	 */
	public static void removeConn(String userId) {
		if (channelUserMap.containsKey(userId)) {
			channelUserMap.remove(userId);
		}
	}
	
	/**
	 * 得到通道
	 * @param userId
	 * @return
	 */
	public static Channel getConn(String userId) {
		return channelUserMap.get(userId);
	}
	

}
