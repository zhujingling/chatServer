package com.netty.entity;

import java.util.Date;

public class ChatLog {
	private String chatlog_pk;
	private String user_number;
	private String friend_number;
	private String chatcontent;
	private Date chattime;
	private boolean isRead;
	public String getChatlog_pk() {
		return chatlog_pk;
	}
	public void setChatlog_pk(String chatlog_pk) {
		this.chatlog_pk = chatlog_pk;
	}
	public String getUser_number() {
		return user_number;
	}
	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}
	public String getFriend_number() {
		return friend_number;
	}
	public void setFriend_number(String friend_number) {
		this.friend_number = friend_number;
	}
	public String getChatcontent() {
		return chatcontent;
	}
	public void setChatcontent(String chatcontent) {
		this.chatcontent = chatcontent;
	}
	public Date getChattime() {
		return chattime;
	}
	public void setChattime(Date chattime) {
		this.chattime = chattime;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	
}
