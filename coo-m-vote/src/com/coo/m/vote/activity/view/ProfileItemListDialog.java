package com.coo.m.vote.activity.view;

import java.util.List;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coo.m.vote.activity.adapter.SysProfilePropertyOptionAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.model.CommonItemOption;

/**
 * 单行编辑框,列表控件实现
 * 
 * @since 0.5.2.0
 * @author boqing.shen
 * 
 */
public class ProfileItemListDialog extends CommonItemDialog<CommonItem> {

	public ProfileItemListDialog(Activity parent, CommonItem item) {
		super(parent, item);
	}

	@Override
	public void initControls(LinearLayout layout) {
		// 定义控件，控件New产生,没有从Context中寻找
		ListView lv_options = new ListView(parent);
		// 定义适配器
		SysProfilePropertyOptionAdapter adapter = new SysProfilePropertyOptionAdapter(
				parent, getItems(), lv_options);
		adapter.initParams(this, item);
		// 添加控件
		layout.addView(lv_options);
	}

	private List<CommonItemOption> getItems() {
		List<CommonItemOption> options = item.getOptions();
		for (CommonItemOption option : options) {
			if (option.getValue()
					.equals(item.getValue().toString())) {
				option.setSelected(true);
			} else {
				option.setSelected(false);
			}
		}
		return options;
	}

	@Override
	public int getBtnGroup() {
		// 没有底部按钮
		return BTN_GROUP_NONE;
	}

	@Override
	protected String getTitle() {
		return "编辑:" + item.getLabel();
	}

}
