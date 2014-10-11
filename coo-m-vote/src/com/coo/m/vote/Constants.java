package com.coo.m.vote;

import java.lang.reflect.Type;

import android.os.Environment;

import com.coo.s.vote.model.Channel;
import com.coo.s.vote.model.Feedback;
import com.coo.s.vote.model.Topic;
import com.google.gson.reflect.TypeToken;
import com.kingstar.ngbf.s.ntp.SimpleMessage;
import com.kingstar.ngbf.s.ntp.SimpleMessageHead;

/**
 * 常量类
 * 
 * @author boqing.shen
 * 
 */
public final class Constants {

	/**
	 * 后台服务器地址
	 */
	public final static String SERVERHOST = "http://10.253.45.103:8080/vote/rest";
	
	/**
	 * 模拟账号
	 */
	public static boolean MOCK_ACCOUNT = true;
	/**
	 * 模拟数据
	 */
	public static boolean MOCK_DATA = true;
	
	

	public final static Type TYPE_TOPIC = new TypeToken<SimpleMessage<Topic>>() {
	}.getType();
	public final static Type TYPE_CHANNEL = new TypeToken<SimpleMessage<Channel>>() {
	}.getType();
	public final static Type TYPE_FEEDBACK = new TypeToken<SimpleMessage<Feedback>>() {
	}.getType();
	public final static Type TYPE_NONE = null;

	public final static String REP_OK = SimpleMessageHead.REP_OK;
	
	public final static String MSG_RPC_ERROR = "RPC请求失败";
	public final static String MSG_RPC_OK = "RPC请求成功";
	
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
