package com.coo.m.vote.model;

import org.litepal.crud.DataSupport;

/**
 * 本地账号建群,群来MContact
 * 
 */
public class MGroup extends DataSupport {

	public MGroup() {
		// TODO Auto-generated constructor stub
	}

	private long id = 0l;
	/**
	 * 本机账号，即account
	 */
	private String host = "";
	/**
	 * 名称: 组描述,备用字段
	 */
	private String name = "";
	/**
	 * 组描述：备用字段
	 */
	private String description = "";
	/**
	 * 账号组1,记录名称和手机号.... 参见 name|13512210211;name|13512210212;
	 */
	private String accounts0 = "";
	/**
	 * 账号组2，备用字段，受SQLite长度限制?
	 */
	// private String accounts1 = "";

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccounts0() {
		return accounts0;
	}

	public void setAccounts0(String accounts0) {
		this.accounts0 = accounts0;
	}
}
