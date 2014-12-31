package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.m.vote.VoteUtil;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * 我的主题的ListView的填充器
 * 
 * @since 0.5.1.0
 * @author boqing.shen
 * 
 */
public class MyTopicAdapter extends CommonAdapter<Topic> {

	/**
	 * 构造函数
	 */
	public MyTopicAdapter(Activity parent, List<Topic> items,
			ListView composite) {
		super(parent, items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.my_topic_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		TopicRowHolder holder = new TopicRowHolder();
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.tv_my_topic_title);
		holder.tv_createtime = (TextView) convertView
				.findViewById(R.id.tv_my_topic_createtime);
		holder.tv_vote = (TextView) convertView
				.findViewById(R.id.tv_my_topic_vote);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Topic item) {
		TopicRowHolder holder = (TopicRowHolder) ciHolder;
		holder.tv_title.setText(item.getTitle());
		// 创建时间
		holder.tv_createtime.setText("创建日期:"
				+ VoteUtil.getTsDateText(item.get_tsi()));
		// 投票数
		holder.tv_vote.setText("投票数:" + item.getVote());
	}
}

class TopicRowHolder extends CommonItemHolder {
	public TextView tv_title;
	public TextView tv_createtime;
	public TextView tv_vote;
}
