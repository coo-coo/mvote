package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.Feedback;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * 意见管理行Adapter
 * 
 * @since 0.4.7.0
 * @author boqing.shen
 * 
 */
public class FeedbackMgtAdapter extends CommonAdapter<Feedback> {

	public FeedbackMgtAdapter(Activity parent, List<Feedback> items,
			ListView composite) {
		super(parent, items, composite);
	}

	/**
	 * 返回控件布局
	 */
	public int getItemConvertViewId() {
		return R.layout.feedback_mgt_activity_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		FeedbackMgtRowHolder holder = new FeedbackMgtRowHolder();
		holder.tv_version = (TextView) convertView
				.findViewById(R.id.tv_feedback_mgt_row_version);
		holder.iv_status_icon = (ImageView) convertView
				.findViewById(R.id.tv_feedback_mgt_row_status_icon);
		holder.tv_note = (TextView) convertView
				.findViewById(R.id.tv_feedback_mgt_row_note);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Feedback item) {
		FeedbackMgtRowHolder holder = (FeedbackMgtRowHolder) ciHolder;
		holder.tv_version.setText(item.getAppVersion());
		holder.tv_note.setText(item.getNote());
		// holder.tv_status.setText(item.getStatus());
		ImageView icon = holder.iv_status_icon;
		int resIcon = R.drawable.status_green;
		if (item.getStatus() == 0) {
			resIcon = R.drawable.status_gray;
		}
		icon.setAdjustViewBounds(false);
		icon.setPadding(0, 0, 1, 1);
		icon.setImageResource(resIcon);
	}
}

class FeedbackMgtRowHolder extends CommonItemHolder {
	public TextView tv_note;
	public TextView tv_version;
	// public TextView tv_status;
	public ImageView iv_status_icon;
}
