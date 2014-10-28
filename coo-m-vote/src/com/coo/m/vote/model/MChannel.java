package com.coo.m.vote.model;

import org.litepal.crud.DataSupport;

/**
 * 本地账户关注和未关注的Channel，和服务器进行同步
 * 
 * @author boqing.shen
 * 
 */
public class MChannel extends DataSupport implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6148210912329774733L;

	public MChannel() {
		// TODO Auto-generated constructor stub
	}

	public MChannel(String code, String label) {
		this.code = code;
		this.label = label;
		this.tsi = System.currentTimeMillis();
	}

	private long id = 0l;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	
	/**
	 * 创建时间戳
	 */
	private long tsi = 0l;
	/**
	 * 更新时间戳
	 */
	private long tsu = 0l;
	
	
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
