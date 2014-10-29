package com.coo.m.vote;

import java.util.List;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.model.CommonOption;

/**
 * Item的Option编辑框,列表控件实现
 * 
 * 
 */
public class CommonOptionDialog extends CommonItemDialog<CommonItem> {

	public CommonOptionDialog(Activity parent, CommonItem item) {
		super(parent, item);
	}
	
	
	
	@Override
	public void initControls(LinearLayout layout) {
		// 定义控件，控件New产生,没有从Context中寻找
		ListView lv_options = new ListView(parent);
		// 定义适配器
		CommonOptionAdapter adapter = new CommonOptionAdapter(
				parent, getItems(), lv_options);
		adapter.initParams(this, item);
		// 添加控件
		layout.addView(lv_options);
	}

	private List<CommonOption> getItems() {
		List<CommonOption> options = item.getOptions();
		for (CommonOption option : options) {
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
		return BTN_GROUP_NONE;
	}

	@Override
	protected String getTitle() {
		return "编辑:" + item.getLabel();
	}

}
