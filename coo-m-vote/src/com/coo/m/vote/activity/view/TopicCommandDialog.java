package com.coo.m.vote.activity.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coo.m.vote.CommonCommandAdapter;
import com.coo.m.vote.activity.TopicConfigActivity;
import com.coo.m.vote.activity.TopicQrcodeActivity;
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

	public TopicCommandDialog(Activity parent, Topic item) {
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
		return item.getTitle();
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
		Intent intent = new Intent(getParent(),
				TopicConfigActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("ITEM", item);
		intent.putExtras(bundle);
		items.add(new CommonItem("topic.config", "设置", intent)
				.uiType(CommonItem.UIT_COMMAND_ACTIVITY));

		Intent intentQrcode = new Intent(getParent(),
				TopicQrcodeActivity.class);
		intentQrcode.putExtras(bundle);
		items.add(new CommonItem("topic.qrcode", "二维码", intentQrcode)
				.uiType(CommonItem.UIT_COMMAND_ACTIVITY));

		// items.add(new CommonItem("topic.share", "分享",
		// "TopicShareAction")
		// .uiType(CommonItem.UIT_COMMAND_ACTION));
		// 关闭Dialog
		items.add(new CommonItem("dialog.cancel", "取消", this)
				.uiType(CommonItem.UIT_DIALOG_CANCEL));
		return items;
	}
}
