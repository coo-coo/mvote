package com.coo.m.vote.activity;

import android.content.Intent;
import android.widget.ListView;

import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.adapter.TopicPropertyAdapter;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;

/**
 * admin 话题管理
 * 
 * @author boqing.shen
 * 
 */
public class TopicConfigActivity extends CommonBizActivity {
	
	/**
	 * 当前Topic,从 TopicActivity中传递过来
	 */
	private Topic current;
	
	@Override
	public int getResViewLayoutId() {
		return R.layout.topic_config_activity;
	}
	
	@Override
	public void loadContent() {
		ListView listView = (ListView) findViewById(R.id.lv_topic_config);
		// 获得从TopicActivity传递过来的某一个Topic，进行设置
		Intent intent = getIntent();
		current = ((Topic) intent.getSerializableExtra("ITEM"));
		// 初始化设置
		adapter = new TopicPropertyAdapter(
				this,VoteManager.getTopicSkeletonItems(current), listView);

	}

	@Override
	public String getHeaderTitle() {
		return "话题设置";
	}
}
