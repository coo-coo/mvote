package com.coo.m.vote;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

import com.coo.m.vote.model.MGroup;
import com.coo.m.vote.model.MManager;
import com.coo.s.vote.model.Account;
import com.coo.s.vote.model.Channel;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.StringUtil;
import com.kingstar.ngbf.ms.util.android.res.ServiceProvider;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.model.CommonOption;
import com.kingstar.ngbf.ms.util.storage.SharedManager;

/**
 * 单例模式, 应用管理者
 */
public class VoteManager {

	private String TAG = VoteManager.class.getName();

	private static SharedManager sharedManager = null;

	public static String SD_Path = Environment
			.getExternalStorageDirectory().getPath();
	public static String PACKAGE_Path = SD_Path + "/com.coo.vote";

	private static Application appContext;

	/**
	 * 管理器初始化，在VoteApplication中进行
	 */
	public static void onAppCreate(Application app) {
		// TODO 短信推送、百度推送

		// 初始化数据库,参见LitePay架构
		// https://github.com/suzlwang/LitePal/
		appContext = app;
		sharedManager = new SharedManager(app, "vote");

		// 启动后台程序
		// startVoteService(app);
	}

	/**
	 * 启动后台服务
	 * 
	 * @param context
	 */
	public static void startVoteService(Context context) {
		// 启动后台程序
		Intent intent = new Intent(context, VoteService.class);
		context.startService(intent);
	}

	/**
	 * 获得SD下的Profile的IconPath: /storage/sdcard0/PACKAGE_NAME/
	 */
	public static String getSdProfileIconPath() {
		return PACKAGE_Path + "/" + VoteManager.getStrAccount()
				+ "_icon.png";
	}

	public static String getSdTopicIconPath(Topic topic) {
		return PACKAGE_Path + "/" + topic.get_id() + "_icon.png";
	}

	/**
	 * 获得静态类型的频道
	 */
	public static List<Channel> getStaticChannels() {
		List<Channel> list = new ArrayList<Channel>();
		list.add(new Channel("channel_top", "最热")); // 投票最多
		list.add(new Channel("channel_latest", "最新")); // 最新创建
		String focus = "channel_focus_" + VoteManager.getStrAccount();
		list.add(new Channel(focus, "关注")); // 我的关注
		return list;
	}

	/**
	 * 返回SharedManager对象
	 */
	public static SharedManager getSharedManager() {
		return sharedManager;
	}

	/**
	 * 当前账号的对象，由登录之后设置
	 */
	private static Account account = null;

	/**
	 * 返回Profile的属性骨架，随着版本会增加，值都为空，从服务器获取来填充 参见Mock.getProfileItems()
	 * 
	 * @return
	 */
	public static List<CommonItem> getProfileSkeletonItems() {
		List<CommonItem> items = new ArrayList<CommonItem>();
		// 基础属性
		String account = getStrAccount();
		// items.add(new CommonItem("account", "用户名", account));
		items.add(new CommonItem("mobile", "手机号", account));
		items.add(new CommonItem("password", "登录密码", "")
				.uiType(CommonItem.UIT_PASSWORD));
		items.add(new CommonItem("mail", "邮箱", "")
				.uiType(CommonItem.UIT_TEXT));
		items.add(new CommonItem("weixin", "微信号", "")
				.uiType(CommonItem.UIT_TEXT));

		// 其它属性
		items.add(new CommonItem("p_nickname", "昵称", "")
				.uiType(CommonItem.UIT_TEXT));
		List<CommonOption> genders = new ArrayList<CommonOption>();
		genders.add(new CommonOption("男", "男"));
		genders.add(new CommonOption("女", "女"));
		genders.add(new CommonOption("保密", "保密"));
		items.add(new CommonItem("p_gender", "性别", "保密").uiType(
				CommonItem.UIT_LIST).options(genders));
		return items;
	}

	/**
	 * 获得Topic的骨架
	 * 
	 * @return
	 */
	public static List<CommonItem> getTopicSkeletonItems(Topic topic) {
		List<CommonItem> items = new ArrayList<CommonItem>();
		// 基础属性
		items.add(new CommonItem("title", "标题", topic.getTitle()));
		items.add(new CommonItem("_tsi", "创建时间", VoteUtil
				.getTsDateText(topic.get_tsi())));
		items.add(new CommonItem("_expired", "截止时间", VoteUtil
				.getTsDateText(topic.get_tsi())));

		// TODO 可以分享之... 根据图标生成二维码
		items.add(new CommonItem("icon", "图标", "暂未实现"));
		// items.add(new CommonItem("qrcode", "二维码", "暂未实现"));

		// 本地动态获得,即朋友群:MGroup
		List<MGroup> mgroups = MManager.findGroupAll();
		List<CommonOption> groups = new ArrayList<CommonOption>();
		groups.add(new CommonOption("全部", "ALL"));
		for (MGroup g : mgroups) {
			groups.add(new CommonOption(g.getName(), g.getName()));
		}
		// 支持单选
		items.add(new CommonItem("public", "公开范围", "").uiType(
				CommonItem.UIT_LIST).options(groups));
		return items;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 保存账号信息到sharedManager中
	 * 
	 * @since 0.5.0.0
	 */
	public void saveAccount(String id, String mobile, String password,
			String type) {
		sharedManager.put(SR_ACCOUNT_ID, id);
		sharedManager.put(SR_ACCOUNT_PWD, password);
		sharedManager.put(SR_ACCOUNT_MOBILE, mobile);
		sharedManager.put(SR_ACCOUNT_TYPE, type);
	}

	public void clearAccount() {
		// 暂时设置为空,remove实现
		// TODO 参见ms-util.sharedManager.remove方法@since 2.4.2.0
		sharedManager.put(SR_ACCOUNT_ID, "");
		sharedManager.put(SR_ACCOUNT_PWD, "");
		sharedManager.put(SR_ACCOUNT_MOBILE, "");
		sharedManager.put(SR_ACCOUNT_TYPE, "");
	}

	private static String SR_ACCOUNT_ID = "account.id";
	private static String SR_ACCOUNT_PWD = "account.pwd";
	private static String SR_ACCOUNT_MOBILE = "account.mobile";
	private static String SR_ACCOUNT_TYPE = "account.type";

	/**
	 * 返回Mobile作为账号
	 * 
	 * @return
	 */
	public static String getStrAccount() {
		return getAccount().getMobile();
	}

	/**
	 * 获得当前登录账号,可能是模拟
	 * 
	 * @return
	 */
	public static Account getAccount() {
		if (account == null) {
			String uuid = getSharedManager().getString(
					SR_ACCOUNT_ID);
			String password = getSharedManager().getString(
					SR_ACCOUNT_PWD);
			String mobile = getSharedManager().getString(
					SR_ACCOUNT_MOBILE);
			String type = getSharedManager().getString(
					SR_ACCOUNT_TYPE);
			if (StringUtil.isNullOrSpace(uuid)) {
				// 如果没有存储,则返回Null,表示第一次登录?
				return null;
			}
			Account a = new Account();
			a.set_id(uuid);
			a.setMobile(mobile);
			a.setPassword(password);
			a.setType(type);
			account = a;
		}
		return account;
	}

	public void setAccount(Account account) {
		VoteManager.account = account;
	}

	/**
	 * 
	 * 
	 * @since 0.4.5.0
	 */
	public String getAppVersion(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
			return "";
		}
	}

	/**
	 * 获得当前应用的版本 TODO 参见 ms-util.resourceProvider
	 * 
	 * @return
	 */
	public static String getVersionName() {
		try {
			return ServiceProvider.getAppVersionName(appContext);
			// return appContext.getPackageManager().getPackageInfo(
			// appContext.getPackageName(), 0).versionName;
		} catch (Exception e) {
			return "";
		}
	}

	// /////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////

	/**
	 * 获得管理者实例
	 */
	public static VoteManager get() {
		if (instance == null) {
			instance = new VoteManager();
		}
		return instance;
	}

	private static VoteManager instance = null;

	/**
	 * 私有构造函数
	 */
	private VoteManager() {

	}

}
