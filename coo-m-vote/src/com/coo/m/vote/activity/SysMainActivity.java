package com.coo.m.vote.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;

import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.adapter.ChannelPagerAdapter;
import com.coo.s.vote.model.Account;
import com.coo.s.vote.model.Channel;

/**
 * 【主界面】 TODO Shake 加载最新的条目......
 * 
 * @since 1.0
 * @author boqing.shen
 */
public class SysMainActivity extends FragmentActivity {

	// 各个ChannelFragment合起来加载到ViewPager中
	private ViewPager viewPager;
	// 各Tab的滑动
	private ChannelPagerAdapter pagerAdapter;
	// 频道Fragment在
	List<SysMainChannel> fragments = new ArrayList<SysMainChannel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐去，显示系统的
		setContentView(R.layout.sys_main_activity2);
		// 加载子内容
		loadContent();
	}

	/**
	 * 初始化
	 */
	public void loadContent() {
		// 无需设置ActionBar

		// 初始化 Fragments & Adapter
		initChannelFragments();
		pagerAdapter = new ChannelPagerAdapter(this,
				getSupportFragmentManager(), fragments);

		// 定义ViewPager资源
		viewPager = (ViewPager) findViewById(R.id.vp_sys_main);
		viewPager.setAdapter(pagerAdapter);
		// TODO 设置??
		viewPager.setOffscreenPageLimit(fragments.size());
		// 设置当前第一个
		viewPager.setCurrentItem(0);
		// 设置监听器
		viewPager.setOnPageChangeListener(new ChannelPagerListener(this));

		// viewPager.setPageMargin(300);
		// 加载左滑动屏....

	}

	/**
	 * 初始化ChannelFragments
	 */
	private void initChannelFragments() {
		// TODO 优化放到子线程里面
		fragments.clear();

		// 加载静态的系统缺省的频道
		List<Channel> channels = VoteManager.getStaticChannels();
		for (Channel c : channels) {
			fragments.add(new SysMainChannel(this, c.getCode(), c
					.getLabel()));
		}

		// 加载我关注的type类型的channel
		// List<MChannel> mChannels = MManager.findChannelFocus();
		// for (MChannel c : mChannels) {
		// fragments.add(new SysMainChannel(this, c.getCode(), c
		// .getLabel()));
		// }
	}

	/**
	 * 初始化菜单,分登陆账号的角色，初始化不同的菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Account account = VoteManager.getAccount();
		String type = account.getType();
		// 一般账号
		if (type.equals(Account.TYPE_ADMIN)) {
			getMenuInflater().inflate(R.menu.role_admin, menu);
		} else {
			// 其他账号
			getMenuInflater().inflate(R.menu.role_common, menu);
		}
		return true;
	}

	/**
	 * 各个菜单的实现
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_topic_create:
			handleNext(this, TopicCreateActivity.class);
			break;
		case R.id.item_profile:
			handleNext(this, ProfileActivity.class);
			break;
		case R.id.item_feedback_create:
			handleNext(this, FeedbackCreateActivity.class);
			break;
		case R.id.item_my_channel:
			handleNext(this, MyChannelActivity.class);
			break;
		case R.id.item_my_topic:
			handleNext(this, MyTopicActivity.class);
			break;
		case R.id.item_contact:
			handleNext(this, MContactActivity.class);
			break;
		case R.id.item_group:
			handleNext(this, MGroupActivity.class);
			break;
		case R.id.item_feedback_mgt:
			handleNext(this, FeedbackMgtActivity.class);
			break;
		case R.id.item_account_mgt:
			handleNext(this, AccountMgtActivity.class);
			break;
		case R.id.item_topic_mgt:
			handleNext(this, TopicMgtActivity.class);
			break;
		case R.id.item_sys_config:
			handleNext(this, SysConfigActivity.class);
			break;
		case R.id.item_sys_version:
			handleNext(this, SysVersionActivity.class);
			break;
		case R.id.item_sys_about:
			handleNext(this, SysAboutActivity.class);
			break;
		case R.id.item_sys_quit:
			doSysQuit();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void doSysQuit() {
		VoteManager.get().clearAccount();
		// 跳转到登录界面....
		Intent intent = new Intent(this, SysLoginActivity.class);
		startActivity(intent);
	}

	/**
	 * 任务结束,跳转到下一个Activity,指定动画效果
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private void handleNext(Context context, Class cl) {
		Intent intent = new Intent();
		intent.setClass(context, cl);
		startActivity(intent);
	}

	// private void toast(String message) {
	// Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	// }
}

/**
 * 手势滑动监听器 参见：http://blog.csdn.net/xipiaoyouzi/article/details/12121131
 * 
 * @author boqing.shen
 * 
 */
class ChannelPagerListener implements OnPageChangeListener {

	@SuppressWarnings("unused")
	private SysMainActivity parent;

	public ChannelPagerListener(SysMainActivity parent) {
		this.parent = parent;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// arg0 ==1的时辰默示正在滑动
		// arg0==2的时辰默示滑动完毕了
		// arg0==0的时辰默示什么都没做
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		// arg0 :当前页面，及你点击滑动的页面
		// arg1:当前页面偏移的百分比
		// arg2:当前页面偏移的像素位置
	}

	@Override
	public void onPageSelected(int position) {
		// position：表示Page的次序
		// parent.toast("onPageSelected-" + position);
	}

}
