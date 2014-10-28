package com.coo.m.vote;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * 一般命令对话框，要么跳转,要么执行命令之后，关闭，需要更新adapter等
 * 
 * @since 0.6.0.0
 * @author boqing.shen
 * 
 */
public class CommonCommandAdapter extends CommonAdapter<CommonItem> {

	/**
	 * 构造函数
	 */
	public CommonCommandAdapter(Activity parent,
			List<CommonItem> commands, ListView composite) {
		super(parent, commands, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.common_command_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		CommonCommandItemHolder holder = new CommonCommandItemHolder();
		holder.tv_label = (TextView) convertView
				.findViewById(R.id.common_command_row_label);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, CommonItem item) {
		CommonCommandItemHolder holder = (CommonCommandItemHolder) ciHolder;
		holder.tv_label.setText(item.getLabel());
	}
}

class CommonCommandItemHolder extends CommonItemHolder {
	public TextView tv_label;
}
