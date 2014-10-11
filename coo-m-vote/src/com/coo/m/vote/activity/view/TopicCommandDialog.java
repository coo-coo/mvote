package com.coo.m.vote.activity.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coo.m.vote.activity.TopicConfigActivity;
import com.coo.m.vote.activity.adapter.CommonCommandListAdapter;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * Topic 命令对话框
 * 
 * @since 0.6.0.0
 * @author boqing.shen
 * 
 */
public class TopicCommandDialog extends CommonItemDialog<Topic> {

	public static final String TAG = TopicCommandDialog.class
			.getSimpleName();

	public TopicCommandDialog(Activity activity, Topic item) {
		super(activity, item);
	}

	@Override
	public void initControls(LinearLayout layout) {
		Log.i(TAG, "initControls...");
		// 定义控件，控件New产生,没有从Context中寻找
		ListView lv_commands = new ListView(parent);
		// 定义适配器
		CommonCommandListAdapter adapter = new CommonCommandListAdapter(
				getCommands(), lv_commands);
		// 初始化上下文
		adapter.initContext(parent);
		// 添加控件
		layout.addView(lv_commands);
	}

	@Override
	protected String getTitle() {
		return this.item.getTitle();
	}

	@Override
	public int getBtnGroup() {
		// 底层按钮不显示
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
		Intent intent = new Intent(this.parent,
				TopicConfigActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("item", item);
		intent.putExtras(bundle);
		items.add(new CommonItem("topic.config", "设置", intent)
				.uiType(CommonItem.UIT_COMMAND_ACTIVITY));
		
		items.add(new CommonItem("topic.share", "分享",
				"TopicShareAction")
				.uiType(CommonItem.UIT_COMMAND_ACTION));
		return items;
	}
}
