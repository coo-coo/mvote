package com.coo.m.vote;

import android.os.Environment;

import com.coo.m.vote.activity.SysMainActivity;

/**
 * 常量类
 * 
 * @author boqing.shen
 * 
 */
public final class Constants {

	public static boolean MOCK_ACCOUNT = true;

	public static boolean MOCK_DATA = true;

	public static String APP_NAME = "我投";
	public static String APP_URL = "http://lightapp.baidu.com/?appid=1568236";

	public static Class<?> LOGIN_CLASS = SysMainActivity.class;

	// public static Class<?> LOGIN_CLASS = SysLoginActivity.class;

	// TODO BIZ->RPC
	public final static int RPC_FEEDBACK_CREATE = 2000;
	public final static int RPC_FEEDBACK_LATEST = 2001;
	public final static int RPC_FEEDBACK_UPDATE_STATUS = 2002;

	public final static int RPC_ACCOUNT_ALL = 2010;
	public final static int RPC_ACCOUNT_UPDATE_STATUS = 2011;
	public final static int RPC_ACCOUNT_UPDATE_PARAM = 2012;
	public final static int RPC_ACCOUNT_INFO = 2013;
	public final static int RPC_ACCOUNT_LOGIN = 2014;
	public final static int RPC_ACCOUNT_SMS = 2015;
	public final static int RPC_ACCOUNT_REGISTER = 2016;

	public final static int RPC_TOPIC_LIST_MINE = 2020;
	public final static int RPC_TOPIC_LIST_CODE = 2021;
	public final static int RPC_TOPIC_LIST_ADMIN = 2022; // [管理]获取最新创建
	public final static int RPC_TOPIC_UPDATE_STATUS = 2023; // [管理]更新状态
	public final static int RPC_TOPIC_CREATE = 2024;
	public final static int RPC_TOPIC_VOTE = 2025;

	// TODO...
	public final static int BIZ_MCONTACT_SYNC_REMOTE = 2090;
	public final static int BIZ_MCHANNEL_SYNC_REMOTE = 2091;

	/**
	 * 后台服务器地址
	 */
	public final static String HOST_REST = "http://10.253.45.103:8080/vote/rest";
	public final static String HOST_SERVLET = "http://10.253.45.103:8080/vote/servlet";

	/**
	 * REST URI地址
	 */
	public static String rest(String relativeUrl) {
		return HOST_REST + relativeUrl;
	}

	/**
	 * 数据库存储文件?
	 */
	public final static String DB_SQLLITE = "coo_vote.db";

	/**
	 * 获取token接口
	 */
	public final static String GET_TOKEN_URL = "  ";
	/**
	 * 帮助页面
	 */
	public final static String HELP_URL = "";
	/**
	 * 缓存存储目录
	 */
	public final static String SAVE_DIR = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/Android/data/com.coo.m.vote/files/cache/";
	/**
	 * 数据库表名
	 */
	public final static String VOTE_M_PROFILE = "vote_m_profile";
	public final static String VOTE_M_CHANNEL = "vote_m_channel";
	public final static String VOTE_M_TOPICSHOT = "vote_m_topicshot";

}
