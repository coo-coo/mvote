package com.coo.m.vote.activity.view;

import android.app.Activity;

import com.coo.m.vote.model.MChannel;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;

/**
 * 频道关注对话框,可以关注,也可以取消,操作都通过REST进行服务器更新
 * 
 * @since 0.5.2.0
 * @author boqing.shen
 * 
 */
public class ChannelFocusDialog extends CommonItemDialog<MChannel> {

	public ChannelFocusDialog(Activity activity, MChannel item) {
		super(activity, item);
	}

	protected void doOkAction() {
		if (item.getStatus() == 0) {
			item.setStatus(1);
		} else {
			item.setStatus(0);
		}
		// toast("操作結果:" + item.getLabel() + "-" + item.getStatus());
		// 通知父类Activity对象进行数据的更新
		this.notitifyParentAbsViewItemChanged();
	}

	@Override
	protected String getTitle() {
		String title = "关注:" + item.getLabel();
		if (item.getStatus() == 1) {
			title = "取消关注:" + item.getLabel();
		}
		return title;
	}

}
