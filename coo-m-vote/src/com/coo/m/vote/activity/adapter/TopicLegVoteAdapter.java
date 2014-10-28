package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.TopicLeg;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * 用于Topic投票的LEG适配器,Radio|Title|
 * 
 * @author boqing.shen
 * 
 */
public class TopicLegVoteAdapter extends CommonAdapter<TopicLeg> {

	public TopicLegVoteAdapter(Activity parent, List<TopicLeg> items, AbsListView composite) {
		super(parent,items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.inc_topic_vote_leg_row;
	}

	@Override
	protected CommonItemHolder initHolder(View convertView) {
		TopicLegVoteHolder holder = new TopicLegVoteHolder();
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.tv_topic_leg_vote_row_title);
		// holder.tv_seq = (TextView) convertView
		// .findViewById(R.id.tv_topic_leg_vote_row_seq);
		holder.rb_seq = (RadioButton) convertView
				.findViewById(R.id.rb_topic_leg_vote_row_seq);
		return holder;
	}

	@Override
	protected void initHolderValue(CommonItemHolder ciHolder, TopicLeg item) {
		TopicLegVoteHolder holder = (TopicLegVoteHolder) ciHolder;
		// 设置标题
		holder.tv_title.setText(item.getTitle());
		// holder.tv_seq.setText("" + item.getSeq());
		holder.rb_seq.setChecked(item.isSelected());
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		TopicLeg current = this.getItem(position);
		// 设置所有未选，设置当前已选
		for (TopicLeg item : this.getItems()) {
			item.setSelected(Boolean.FALSE);
		}
		current.setSelected(Boolean.TRUE);
		// 设置选中的TopicLeg
		this.setSelected(current);
		// 通知模型更新
		this.notifyDataSetChanged();
	}
}

class TopicLegVoteHolder extends CommonItemHolder {
	// public TextView tv_seq;
	public RadioButton rb_seq;
	public TextView tv_title;
}
