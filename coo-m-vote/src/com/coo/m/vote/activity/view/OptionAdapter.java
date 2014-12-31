package com.coo.m.vote.activity.view;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.model.CommonOption;

/**
 * 
 * CommonItem的Option填充器,主要是完成针对CommonItem的value值的设定
 * 
 */
public class OptionAdapter extends CommonAdapter<CommonOption> {

	/**
	 * 构造函数
	 */
	public OptionAdapter(Activity parent, List<CommonOption> items,
			ListView composite) {
		super(parent, items, composite);
	}

	/**
	 * TODO 再考虑 初始化对象,用于对象的选中,值的回写
	 */
	public void initParams(CommonItemDialog<?> dialog, CommonItem item) {
		this.dialog = dialog;
		this.item = item;
	}

	private CommonItemDialog<?> dialog;
	private CommonItem item;

	@Override
	public int getItemConvertViewId() {
		return R.layout.common_option_row;
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		// 更改指定的CommonItem对象的value值
		CommonOption option = getItem(position);
		// 设置原有的Item的值
		item.setValue(option.getValue());

		// 通知父类Activity对象进行数据的更新
		dialog.notifyAdapterEvent(CommonAdapter.EVT_ITEM_CHANGED, item);
		// 关闭对话框
		dialog.dismiss();
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		CommonItemOptionRowHolder holder = new CommonItemOptionRowHolder();
		holder.tv_value = (TextView) convertView
				.findViewById(R.id.common_item_option_row_value);
		holder.iv_selected = (ImageView) convertView
				.findViewById(R.id.common_item_option_row_selected);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, CommonOption item) {
		CommonItemOptionRowHolder holder = (CommonItemOptionRowHolder) ciHolder;
		holder.tv_value.setText(item.getValue());
		ImageView icon = holder.iv_selected;
		if (item.isSelected()) {
			// 當前選中
			icon.setImageResource(R.drawable.status_green_36);
		} else {
			icon.setImageResource(R.drawable.status_gray_36);
		}
	}
}

class CommonItemOptionRowHolder extends CommonItemHolder {
	public TextView tv_value;
	public ImageView iv_selected;
}
