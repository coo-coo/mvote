package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.TopicLeg;
import com.kingstar.ngbf.ms.util.android.CommonItemAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * 用于Topic投票的LEG适配器,Radio|Title|
 * @deprecated 参见CommonBarChart
 * @author boqing.shen
 * 
 */
public class TopicLegResultAdapter extends CommonItemAdapter<TopicLeg> {

	public TopicLegResultAdapter(List<TopicLeg> items, AbsListView composite) {
		super(items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.inc_topic_result_leg_row;
	}

	@Override
	protected CommonItemHolder initHolder(View convertView) {
		TopicLegResultHolder holder = new TopicLegResultHolder();
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.tv_topic_leg_result_row_title);
		holder.iv_vote = (ImageView) convertView
				.findViewById(R.id.iv_topic_leg_result_row_vote);
		return holder;
	}

	@Override
	protected void initHolderValue(CommonItemHolder ciHolder, TopicLeg item) {
		TopicLegResultHolder holder = (TopicLegResultHolder) ciHolder;
		// 设置标题
		holder.tv_title.setText(item.getTitle());

		// 設置宽度
		ImageView iv = holder.iv_vote;
		iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
		// 设置宽度
		LayoutParams lrs = iv.getLayoutParams();
		lrs.width = 10 + item.getVote() * 10;
		iv.setLayoutParams(lrs);
	}
}

class TopicLegResultHolder extends CommonItemHolder {
	public TextView tv_title;
	public ImageView iv_vote;
}
