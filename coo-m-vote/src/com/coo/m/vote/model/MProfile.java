package com.coo.m.vote.model;

/**
 * TODO 个人账号属性信息 M端存储...
 * @author boqing.shen
 *
 */
public class MProfile {

	public MProfile() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 数据库ID
	 */
	private long id = 0l;
	
	/**
	 * 本机账号，即account
	 */
	private String host = "";
	/**
	 * 名称: 昵称
	 */
	private String nickname = "";
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
