package com.netty.entity;

import java.util.Date;


public class User {
	private String user_pk;//用户主键
    private String user_number;//用户编号
    private String user_name;//用户姓名
    private String user_password;//用户密码
    private String user_sex;//用户性别
    private Date user_birthday;//用户生日
    private Date user_createtime;//创建时间
    private String user_phone;//电话
    private String user_email;//邮箱
    
	public String getUser_pk() {
		return user_pk;
	}
	public void setUser_pk(String user_pk) {
		this.user_pk = user_pk;
	}
	public String getUser_number() {
		return user_number;
	}
	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public Date getUser_birthday() {
		return user_birthday;
	}
	public void setUser_birthday(Date user_birthday) {
		this.user_birthday = user_birthday;
	}
	
	public Date getUser_createtime() {
		return user_createtime;
	}
	public void setUser_createtime(Date user_intime) {
		this.user_createtime = user_intime;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	  @Override
	    public String toString() {
	        return "User [user_pk=" + user_pk + ", user_number=" + user_number
	                + ", user_name=" + user_name + ", user_password="
	                + user_password + ", user_sex=" + user_sex + ", user_birthday=" + user_birthday
	                + ",user_intime=" + user_createtime + ", user_phone=" + user_phone + ", user_email="
	                + user_email + "]";
	    }

}
