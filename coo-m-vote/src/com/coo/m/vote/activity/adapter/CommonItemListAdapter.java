package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.text.InputType;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.m.vote.VoteUtil;
import com.kingstar.ngbf.ms.util.android.CommonItemAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * CommonItem对象的普通适配器
 * 
 * @since 0.4.7.0
 * @author boqing.shen
 * 
 * @param <T>
 */
public class CommonItemListAdapter extends CommonItemAdapter<CommonItem> {

	/**
	 * 构造函数
	 */
	public CommonItemListAdapter(List<CommonItem> items, ListView composite) {
		super(items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.common_item_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		CommonItemRowHolder holder = new CommonItemRowHolder();
		holder.tv_label = (TextView) convertView
				.findViewById(R.id.common_item_row_label);
		holder.tv_value = (TextView) convertView
				.findViewById(R.id.common_item_row_value);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, CommonItem item) {
		CommonItemRowHolder holder = (CommonItemRowHolder) ciHolder;
		holder.tv_label.setText(item.getLabel());

		int uiType = item.getUiType();
		if (uiType == CommonItem.UIT_PASSWORD) {
			// 按密码显示处理
			holder.tv_value.setInputType(VoteUtil.getPwdInputType());
		} 
		else {
			holder.tv_value.setInputType(InputType.TYPE_CLASS_TEXT);
		}
		// TODO 图片设置....
		
		holder.tv_value.setText("" + item.getValue());
	}
}

class CommonItemRowHolder extends CommonItemHolder {
	public TextView tv_label;
	public TextView tv_value;
}
