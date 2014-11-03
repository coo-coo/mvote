package com.coo.m.vote.model;

/**
 * TODO 个人账号属性信息(即Account的部分信息)不在M端存储，可能会有网页端修改的可能....
 * 通过本地的MContact账号列表获取服务器端的Account信息,加工成为MProfile信息
 * MProfile仅仅是服务器端Account信息的子集,M端暂时不能修改，备注名什么的..
 * 
 * @author boqing.shen
 * 
 */
public class MProfile {

	public MProfile() {
		// TODO Auto-generated constructor stub
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
	 * 通讯薄号码 TODO 属于手机号码才进数据库 (通讯薄获得)
	 */
	private String mobile = "";

	/**
	 * [服务器端获得]:绑定的应用账号, 目前和该手机号一致 和服务器端同步，如有表示已注册 (服务器端获得)
	 */
	private String account = "";
	/**
	 * [服务器端获得]:备用字段:手机所属地区
	 */
	private String area = "";
	/**
	 * [服务器端获得]:备用字段:ICON
	 */
	private String icon = "";
	/**
	 * [服务器端获得]:备用字段:微信ID
	 */
	private String wxId = "";
	/**
	 * [服务器端获得]:备用字段:个性化签名 (服务器端获得,即Account的签名)
	 */
	private String signature = "";
	/**
	 * [服务器端获得]:备用字段:昵称，别名 (服务器端获得,即Account的昵称)
	 */
	private String alias = "";
	/**
	 * [服务器端获得]:是否已关注:0 未关注,1 已关注 (服务器端获得)
	 */
	private int focus = 0;

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

	public int getFocus() {
		return focus;
	}

	public void setFocus(int focus) {
		this.focus = focus;
	}
}
