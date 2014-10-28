package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * Topic管理行Adapter
 * 
 */
public class TopicMgtAdapter extends CommonAdapter<Topic> {

	public TopicMgtAdapter(Activity parent, List<Topic> items, ListView composite) {
		super(parent,items, composite);
	}

	/**
	 * 返回控件布局
	 */
	public int getItemConvertViewId() {
		return R.layout.topic_mgt_activity_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		TopicMgtItemRowHolder holder = new TopicMgtItemRowHolder();
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.tv_topic_mgt_row_title);
		holder.tv_owner = (TextView) convertView
				.findViewById(R.id.tv_topic_mgt_row_owner);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Topic item) {
		TopicMgtItemRowHolder holder = (TopicMgtItemRowHolder) ciHolder;
		holder.tv_title.setText(item.getTitle());
		holder.tv_owner.setText(item.getOwner());
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		// 弹出处理对话框
		Topic item = this.getItem(position);
		new TopicMgtItemHandleDialog(parent,item).show();
		return true;
	}

}

class TopicMgtItemRowHolder extends CommonItemHolder {
	public TextView tv_title;
	public TextView tv_owner;
}

/**
 * 条目长恩处理
 * 
 * @author boqing.shen
 * 
 */
class TopicMgtItemHandleDialog extends CommonItemDialog<Topic> {

	public TopicMgtItemHandleDialog(Activity parent,Topic item) {
		super(parent,item);
	}

	@Override
	public String getTitle() {
		return "处理账号:" + item.getTitle();
	}

	@Override
	public void doOkAction() {
		// TODO 处理,发送请求，改状态0到1
		// item.setStatus(1);
//		this.notitifyParentAbsViewItemChanged();
	}
}
