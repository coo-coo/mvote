package com.coo.m.vote.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.adapter.TopicLegCreateAdapter;
import com.coo.s.cloud.model.Account;
import com.coo.s.vote.model.TopicLeg;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.StringUtil;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 【创建话题】
 * 
 * @since 1.0
 * @author boqing.shen
 */
public class TopicCreateActivity extends CommonBizActivity {

	protected static final String TAG = TopicCreateActivity.class.getName();
	/**
	 * Topic的文字主题输入框
	 */
	private EditText et_title;
	/**
	 * TopicLeg 的列表
	 */
	private ListView lv_legs;
	/**
	 * 定义数据适配器
	 */
	private TopicLegCreateAdapter adapter;

	@Override
	public String getHeaderTitle() {
		return "创建话题";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.topic_create_activity;
	}

	@Override
	public void loadContent() {
		// 设置Topic的标题
		et_title = (EditText) findViewById(R.id.et_topic_create_title);

		// 设置Leg ListView
		lv_legs = (ListView) findViewById(R.id.lv_topic_create_legs);
		// 初始化adapter
		adapter = new TopicLegCreateAdapter(this, getDefaultLegs());
		lv_legs.setAdapter(adapter);
	}

	/**
	 * 设置菜单操作按钮
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.topic_create, menu);
		return true;
	}

	/**
	 * 返回缺省的Leg条目
	 */
	private List<TopicLeg> getDefaultLegs() {
		List<TopicLeg> legs = new ArrayList<TopicLeg>();
		legs.add(new TopicLeg("0", "是"));
		legs.add(new TopicLeg("1", "不是"));
		return legs;
	}

	/**
	 * 各个菜单的实现
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_topic_leg_create:
			doCreateLeg();
			return true;
		case R.id.item_topic_publish:
			doPublishTopic();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 新增一个LEG输入框
	 */
	private void doCreateLeg() {
		adapter.add(new TopicLeg());
	}

	/**
	 * 执行Topic发布操作
	 */
	private void doPublishTopic() {
		Account account = VoteManager.getAccount();
		String title = et_title.getText().toString();
		if (StringUtil.isNullOrSpace(title)) {
			toast("请输入投票主题!");
			return;
		}

		// 创建NtpMessage消息
		NtpMessage nm = new NtpMessage();
		nm.set("title", title);
		nm.set("owner", account.getMobile());
		for (int i = 0; i < adapter.getCount(); i++) {
			TopicLeg leg = adapter.getItem(i);
			nm.add(leg);
		}
		String uri = "/topic/create/";
		httpCaller.doPost(Constants.RPC_TOPIC_CREATE,
				Constants.rest(uri), nm);
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.RPC_TOPIC_CREATE) {
			toast("话题创建成功....");
			Intent intent = new Intent();
			intent.setClass(this, MyTopicActivity.class);
			startActivity(intent);
		}
	}
}
