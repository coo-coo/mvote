package com.coo.m.vote.model;

import org.litepal.crud.DataSupport;

/**
 * 本地账户关注和未关注的Channel，和服务器进行同步
 * 
 * @author boqing.shen
 * 
 */
public class MChannel extends DataSupport {

	public MChannel() {
		// TODO Auto-generated constructor stub
	}

	public MChannel(String code, String label) {
		this.code = code;
		this.label = label;
	}

	private int id = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 頻道代码
	 */
	private String code = "";
	/**
	 * 頻道名称
	 */
	private String label = "";
	/**
	 * 是否已关注:0 未关注,1 已关注
	 */
	private int status = STATUS_UNFOCUS;

	public static final int STATUS_FOCUSD = 1;
	public static final int STATUS_UNFOCUS = 0;
	/**
	 * 是否删除:0 未删除,1 已删除
	 */
	private int deleted = 0;
	/**
	 * 本机账号，即account
	 */
	private String host = "";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
