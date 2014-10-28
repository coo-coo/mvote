package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.Feedback;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * 意见管理行Adapter
 * 
 * @since 0.4.7.0
 * @author boqing.shen
 * 
 */
public class FeedbackMgtListViewAdapter extends CommonAdapter<Feedback> {

	public FeedbackMgtListViewAdapter(Activity parent, List<Feedback> items,
			ListView composite) {
		super(parent,items, composite);
	}

	/**
	 * 返回控件布局
	 */
	public int getItemConvertViewId() {
		return R.layout.feedback_mgt_activity_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		FeedbackMgtItemRowHolder holder = new FeedbackMgtItemRowHolder();
		holder.tv_version = (TextView) convertView
				.findViewById(R.id.tv_feedback_mgt_row_version);
		holder.tv_status = (TextView) convertView
				.findViewById(R.id.tv_feedback_mgt_row_status);
		holder.tv_note = (TextView) convertView
				.findViewById(R.id.tv_feedback_mgt_row_note);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Feedback item) {
		FeedbackMgtItemRowHolder holder = (FeedbackMgtItemRowHolder) ciHolder;
		holder.tv_version.setText(item.getAppVersion());
		holder.tv_note.setText(item.getNote());
		// holder.tv_status.setText(item.getStatus());
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		// 弹出处理对话框
		Feedback item = this.getItem(position);
		new FeedbackMgtItemHandleDialog(parent,item).show();
		return true;
	}

}

class FeedbackMgtItemRowHolder extends CommonItemHolder {
	public TextView tv_note;
	public TextView tv_version;
	public TextView tv_status;
}

/**
 * 条目长恩处理
 * 
 * @author boqing.shen
 * 
 */
class FeedbackMgtItemHandleDialog extends CommonItemDialog<Feedback> {

	public FeedbackMgtItemHandleDialog(Activity parent,Feedback item) {
		super(parent,item);
	}

	@Override
	public String getTitle() {
		return "处理反馈:" + item.getNote();
	}

	@Override
	public void doOkAction() {
		// TODO 处理,发送请求，改状态0到1
		item.setStatus(1);
//		this.notitifyParentAbsViewItemChanged();
	}
}
