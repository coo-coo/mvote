package com.coo.m.vote.activity.view;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.coo.m.vote.VoteUtil;
import com.kingstar.ngbf.ms.util.StringUtil;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * 单行文本编辑对话框
 * 
 * @since 0.5.4.0
 * @author boqing.shen
 * 
 */
public class SysProfileItemPasswordEditDialog extends
		CommonItemDialog<CommonItem> {

	public SysProfileItemPasswordEditDialog(Activity parent,CommonItem item) {
		super(parent,item);
	}

	protected EditText et_pwd1;
	protected EditText et_pwd2;

	@Override
	public void initControls(LinearLayout layout) {
		// 定义子控件
		et_pwd1 = new EditText(parent);
		et_pwd1.setText("");
		et_pwd1.setSingleLine();
		et_pwd1.setInputType(VoteUtil.getPwdInputType());
		et_pwd1.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 80));
		// 添加子控件
		layout.addView(et_pwd1);

		// 定义子控件
		et_pwd2 = new EditText(parent);
		et_pwd2.setText("");
		et_pwd2.setSingleLine();
		et_pwd2.setInputType(VoteUtil.getPwdInputType());
		et_pwd2.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 80));
		// 添加子控件
		layout.addView(et_pwd2);
	}

	@Override
	protected void doOkAction() {
		String pwd1 = et_pwd1.getText().toString();
		String pwd2 = et_pwd1.getText().toString();
		if (StringUtil.isNullOrSpace(pwd1)) {
			this.toast("密码不能为空!");
			// TODO 不能关闭对话框....
			return;
		}
		if (!pwd1.equals(pwd2)) {
			this.toast("密码输入不一致!");
			// TODO 不能关闭对话框....
			return;
		}
		item.setValue(pwd1);

		// 通知对象条目变更
		this.notifyAdapterEvent(CommonAdapter.EVT_ITEM_CHANGED, item);
	}

	@Override
	protected String getTitle() {
		return "密码设置";
	}
}
