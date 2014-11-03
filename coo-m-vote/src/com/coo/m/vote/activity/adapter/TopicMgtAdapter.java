package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * Topic管理行Adapter
 * 
 */
public class TopicMgtAdapter extends CommonAdapter<Topic> {

	public TopicMgtAdapter(Activity parent, List<Topic> items,
			ListView composite) {
		super(parent, items, composite);
	}

	/**
	 * 返回控件布局
	 */
	@Override
	public int getItemConvertViewId() {
		return R.layout.topic_mgt_activity_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		TopicMgtRowHolder holder = new TopicMgtRowHolder();
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.tv_topic_mgt_row_title);
		holder.tv_owner = (TextView) convertView
				.findViewById(R.id.tv_topic_mgt_row_owner);
		holder.iv_status_icon = (ImageView) convertView
				.findViewById(R.id.iv_topic_mgt_row_status_icon);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Topic item) {
		TopicMgtRowHolder holder = (TopicMgtRowHolder) ciHolder;
		holder.tv_title.setText(item.getTitle());
		holder.tv_owner.setText(item.getOwner());
		ImageView icon = holder.iv_status_icon;
		int resIcon = R.drawable.status_gray;
		if (item.getStatus() == Topic.STATUS_VALID.code) {
			resIcon = R.drawable.status_green;
		}
		icon.setAdjustViewBounds(false);
		icon.setPadding(0, 0, 1, 1);
		icon.setImageResource(resIcon);
	}
}

class TopicMgtRowHolder extends CommonItemHolder {
	public TextView tv_title;
	public TextView tv_owner;
	public ImageView iv_status_icon;
}
