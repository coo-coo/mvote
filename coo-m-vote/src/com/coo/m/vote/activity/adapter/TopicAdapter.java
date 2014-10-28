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
public class TopicAdapter extends CommonAdapter<Topic> {

	/**
	 * 构造函数
	 */
	public TopicAdapter(Activity parent, List<Topic> items,
			ListView composite) {
		super(parent, items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.topic_activity_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		TopicRowHolder holder = new TopicRowHolder();
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.tv_topic_row_title);
		holder.tv_createtime = (TextView) convertView
				.findViewById(R.id.tv_topic_row_createtime);
		holder.tv_vote = (TextView) convertView
				.findViewById(R.id.tv_topic_row_vote);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Topic item) {
		TopicRowHolder holder = (TopicRowHolder) ciHolder;
		holder.tv_title.setText(item.getTitle());
		// 创建时间
		holder.tv_createtime
				.setText(VoteUtil.getTsText(item.get_tsi()));
		// 投票数
		holder.tv_vote.setText("" + item.getVote());
	}
}

class TopicRowHolder extends CommonItemHolder {
	// public TextView tv_icon;
	public TextView tv_title;
	public TextView tv_createtime;
	public TextView tv_vote;
}
