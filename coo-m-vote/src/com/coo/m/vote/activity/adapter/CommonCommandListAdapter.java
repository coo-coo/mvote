package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.kingstar.ngbf.ms.util.android.CommonItemAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * 一般命令对话框，要么跳转,要么执行命令之后，关闭，需要更新adapter等
 * 
 * @since 0.6.0.0
 * @author boqing.shen
 * 
 */
public class CommonCommandListAdapter extends CommonItemAdapter<CommonItem> {

	/**
	 * 构造函数
	 */
	public CommonCommandListAdapter(List<CommonItem> commands,
			ListView composite) {
		super(commands, composite);
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

	/**
	 * 监听条目单击事件响应
	 */
	@Override
	public void onItemClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		// 根据选中的条目的UI类型进行不同的修改方式...
		CommonItem item = this.getItem(position);
		toast("onItemClick..." + item.getLabel() + "-"
				+ item.getUiType());
		int uiType = item.getUiType();
		switch (uiType) {
		case CommonItem.UIT_COMMAND_ACTIVITY:
			// 跳转到
			Intent intent = (Intent) item.getValue();
//			Bundle bundle = new Bundle();
//			bundle.putSerializable("item", item);
//			intent.putExtra("bundle", bundle);
			this.getActivity().startActivity(intent);
			break;
		case CommonItem.UIT_COMMAND_ACTION:
			this.toast("暂未实现...");
			// 显示文本，修改对话框
			// new SysProfileItemListEditDialog(this, item).show();
			break;
		default:
			// 其它，包括Label/Boolean等,不处理
			break;
		}
	}
}

class CommonCommandItemHolder extends CommonItemHolder {
	public TextView tv_label;
}
