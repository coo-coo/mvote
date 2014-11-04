package com.coo.m.vote;

import java.util.List;

import org.litepal.LitePalApplication;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.coo.m.vote.activity.SysMainActivity;
import com.coo.m.vote.model.MChannel;
import com.coo.m.vote.model.MContact;
import com.coo.m.vote.model.MManager;
import com.coo.s.vote.model.Channel;
import com.kingstar.ngbf.ms.util.android.CommonConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 主函数
 * 
 * @author boqing.shen
 * 
 */
public class VoteApplication extends LitePalApplication {

	private static final String TAG = VoteApplication.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "App on created..");
		// 初始化
		VoteManager.onAppCreate(this);

		// 初始化Commom模型
		initCommonModel();

		// 初始化M模型信息
		initMChannels();
		initMContacts();

		// 初始化ImageLoader
		initImageLoader();

		
//		MManager.clearGroups();
		// 启动后台程序
	}
	
	
	
	/**
	 * 初始化ImageLoader,用于图片显示
	 */
	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).build();
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 初始化MChannel,如果数据库没有,则增加若干Channel，参见Mock.channels TODO 经过Service进行同步?
	 */
	private void initMChannels() {
		List<MChannel> list = MManager.findChannelAll();
		if (list.size() == 0) {
			List<Channel> channels = Mock.CHANNNELS;
			toast("初始化MChannel:" + channels.size());
			for (Channel c : channels) {
				// 添加静态的频道信息
				MChannel mc = new MChannel(c.getCode(),
						c.getLabel());
				mc.save();
			}
		}
	}

	/**
	 * 初始化MContact信息,应从设备信息中初始化获取，参见ContactActivity2
	 */
	private void initMContacts() {
		List<MContact> list = MManager.findContactAll();
		if (list.size() == 0) {
			for (int i = 10; i < 20; i++) {
				// 添加静态的频道信息
				MContact mc = new MContact("139170816" + i,
						"MOBILE-" + i);
				mc.save();
			}
		}
	}

	/**
	 * 初始化Commom模型,参见CommonItemConfig
	 */
	private void initCommonModel() {
		CommonConfig.clearParams();
		// 初始化上下文....
		CommonConfig.setContext(this);
		CommonConfig.initParam(
				CommonConfig.KEY_CLASS_HOME_ACTIVITY,
				SysMainActivity.class);
		CommonConfig.initParam(
				CommonConfig.KEY_INT_DIALOG_VIEW_ID,
				R.layout.common_dialog);
		CommonConfig.initParam(
				CommonConfig.KEY_INT_DIALOG_LAYOUT_ID,
				R.id.layout_dialog_common);
	}

	@SuppressWarnings("unused")
	private void testLitePal() {
		// MChannel c1 = new MChannel("code22", "中文");
		// boolean tof = c1.save();
		// toast("MChannel save " + tof);

		// MChannel cc = DataSupport.find(MChannel.class, 1);
		// cc.setFocus(1);
		// cc.update(1); // 更新ID=1的数据
		//
		// List<MChannel> list = DataSupport.findAll(MChannel.class);
		// // List<MChannel> list = DataSupport.where("focus=?",
		// "1").find(
		// // MChannel.class);
		// for (MChannel c : list) {
		// toast(c.getId() + "-" + c.getCode() + "-"
		// + c.getLabel() + "-" + c.getFocus());
		// }

		// MChannel c3 = new MChannel();
		// c3.setFocus(1);
		// 将所有foucs=0的对象改为1
		// int rowsAffected = c3.updateAll("focus=?", "0");
		// toast("VoteApplication update " + rowsAffected);
		//
		// int count = DataSupport.delete(MChannel.class, 4);
		// toast("VoteApplication delete " + count);
		// int rowsAffected2 = DataSupport.deleteAll(MChannel.class,
		// "name=? and age=?", "ming.wang", 22 + "");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Intent intent = new Intent(this, VoteService.class);
		this.stopService(intent);
	}

	private void toast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
