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
	}

	private long id = 0l;
	/**
	 * 通讯薄名称:(通讯薄获得)
	 */
	private String name = "";
	/**
	 * 通讯薄号码 TODO 属于手机号码才进数据库 (通讯薄获得)
	 */
	private String mobile = "";
	/**
	 * 绑定的应用账号, 目前和该手机号一致 和服务器端同步，如有表示已注册 (服务器端获得)
	 */
	private String account = "";
	/**
	 * 备用字段:手机所属地区 (服务器端获得)
	 */
	private String area = "";
	/**
	 * 备用字段:ICON
	 */
	private String icon = "";
	/**
	 * 备用字段:微信ID
	 */
	private String wxId = "";
	/**
	 * 备用字段:个性化签名 (服务器端获得,即Account的签名)
	 */
	private String signature = "";
	/**
	 * 备用字段:昵称，别名 (服务器端获得,即Account的昵称)
	 */
	private String alias = "";
	/**
	 * 本机账号，即account
	 */
	private String host = "";
	/**
	 * 是否已关注:0 未关注,1 已关注 (服务器端获得)
	 */
	private int focus = 0;

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getFocus() {
		return focus;
	}

	public void setFocus(int focus) {
		this.focus = focus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
