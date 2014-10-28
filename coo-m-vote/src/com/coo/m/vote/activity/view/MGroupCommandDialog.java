package com.coo.m.vote.activity.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coo.m.vote.CommonCommandAdapter;
import com.coo.m.vote.activity.MGroupEditorActivity;
import com.coo.m.vote.model.MGroup;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * Topic 命令对话框
 * 
 * @since 0.6.0.0
 * @author boqing.shen
 * 
 */
public class MGroupCommandDialog extends CommonItemDialog<MGroup> {

	public MGroupCommandDialog(Activity parent, MGroup item) {
		super(parent, item);
	}

	@Override
	public void initControls(LinearLayout layout) {
		// 定义控件，控件New产生,没有从Context中寻找
		ListView lv = new ListView(parent);
		// 定义适配器
		@SuppressWarnings("unused")
		CommonCommandAdapter adapter = new CommonCommandAdapter(parent,
				getCommands(), lv);
		// 添加控件
		layout.addView(lv);
	}

	@Override
	protected String getTitle() {
		return item.getName();
	}

	@Override
	public int getBtnGroup() {
		return BTN_GROUP_NONE;
	}

	/**
	 * 模拟产生Profile的属性条目对象,用于集中展现
	 * 
	 * @return
	 */
	private List<CommonItem> getCommands() {
		List<CommonItem> items = new ArrayList<CommonItem>();
		// 建立传递的item信息
		Intent intent = new Intent(parent,
				MGroupEditorActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("ITEM", item);
		intent.putExtras(bundle);
		items.add(new CommonItem("group.edit", "编辑", intent)
				.uiType(CommonItem.UIT_COMMAND_ACTIVITY));

		items.add(new CommonItem("group.delete", "删除",
				"GroupDeleteAction")
				.uiType(CommonItem.UIT_COMMAND_ACTION));
		// 关闭Dialog
		items.add(new CommonItem("dialog.cancel", "取消", this)
				.uiType(CommonItem.UIT_DIALOG_CANCEL));
		return items;
	}
}
