package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.m.vote.activity.view.ChannelFocusDialog;
import com.coo.m.vote.model.MChannel;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * 频道管理界面GridView的adapter
 * 
 * @since0.4.2.0
 */
public class ChannelGridItemAdapter extends CommonAdapter<MChannel> {

	/**
	 * 构造函数
	 */
	public ChannelGridItemAdapter(Activity parent, List<MChannel> items, GridView composite) {
		super(parent,items, composite);
	}

	/**
	 * 返回布局...
	 */
	@Override
	public int getItemConvertViewId() {
		return R.layout.channel_activity_grid;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		ChannelGridHolder holder = new ChannelGridHolder();
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.tv_channel_grid_title);
		holder.iv_icon = (ImageView) convertView
				.findViewById(R.id.iv_channel_grid_icon);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, MChannel item) {
		ChannelGridHolder holder = (ChannelGridHolder) ciHolder;
		// 设置标题
		holder.tv_title.setText(item.getLabel());
		// 设置图片....
		ImageView icon = holder.iv_icon;
		icon.setAdjustViewBounds(true);
//		icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		icon.setPadding(0, 0, 1, 1);
		// TODO 根据Channel的状态进行设置图片资源, 0:未关注,1:已关注
		if (item.getStatus()==0) {
			icon.setImageResource(R.drawable.status_gray);
		} else {
			icon.setImageResource(R.drawable.status_green);
		}
	}

	/**
	 * 长按View的Click事件响应
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		MChannel item = getItem(position);
		new ChannelFocusDialog(parent,item).show();
		return true;
	}
}

class ChannelGridHolder extends CommonItemHolder {
	public TextView tv_title;
	public ImageView iv_icon;
}
