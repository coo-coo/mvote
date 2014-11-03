package com.coo.m.vote.model;

import org.litepal.crud.DataSupport;

/**
 * 本地账号，从通讯薄中获取之后,不再具有修改权, 向服务器端同步请求之后获得服务器的最新数据进行本地的SQLite数据更新
 * 
 * @author boqing.shen
 * 
 */
public class MContact extends DataSupport {

	public MContact() {
	}

	public MContact(String mobile, String name) {
		this.mobile = mobile;
		this.name = name;
		this.tsi = System.currentTimeMillis();
	}

	/**
	 * SQLite ID
	 */
	private long id = 0l;
	/**
	 * 本机账号，即account
	 */
	private String host = "";
	/**
	 * 创建时间戳
	 */
	private long tsi = 0l;
	/**
	 * 更新时间戳
	 */
	private long tsu = 0l;
	/**
	 * 通讯薄名称:(通讯薄获得)
	 */
	private String name = "";
	/**
	 * 通讯薄号码 TODO 属于手机号码才进数据库 (通讯薄获得)
	 */
	private String mobile = "";

	public long getTsi() {
		return tsi;
	}

	public void setTsi(long tsi) {
		this.tsi = tsi;
	}

	public long getTsu() {
		return tsu;
	}

	public void setTsu(long tsu) {
		this.tsu = tsu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * UI操作,选中操作,应该不进入数据库
	 */
	private boolean selected = false;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
