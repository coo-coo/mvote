package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coo.m.vote.CommonItemAdapter;
import com.coo.m.vote.activity.view.SysProfileItemListEditDialog;
import com.coo.m.vote.activity.view.SysProfileItemPasswordEditDialog;
import com.coo.m.vote.activity.view.SysProfileItemTextEditDialog;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * 话题属性ListView的填充器
 * 
 */
public class TopicPropertyAdapter extends CommonItemAdapter {

	/**
	 * 构造函数
	 */
	public TopicPropertyAdapter(Activity parent, List<CommonItem> items, ListView composite) {
		super(parent,items, composite);
	}

	/**
	 * 监听条目单击事件响应
	 */
	@Override
	public void onItemClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		// 根据选中的条目的UI类型进行不同的修改方式...
		CommonItem item = this.getItem(position);
		// toast("onItemClick111..." + item.getLabel() + "-" +
		// item.getUiType());
		int uiType = item.getUiType();
		switch (uiType) {
		case CommonItem.UIT_TEXT:
			// 显示文本，修改对话框
			new SysProfileItemTextEditDialog(parent,item).show();
			break;
		case CommonItem.UIT_PASSWORD:
			// 显示文本，修改对话框
			new SysProfileItemPasswordEditDialog(parent,item).show();
			break;
		case CommonItem.UIT_LIST:
			// 显示文本，修改对话框
			new SysProfileItemListEditDialog(parent,item).show();
			break;
		default:
			// 其它，包括Label/Boolean等,不处理
			break;
		}
	}
}
