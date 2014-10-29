package com.coo.m.vote.activity.view;

import android.app.Activity;
import android.text.InputType;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

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
public class ProfileItemTextDialog extends CommonItemDialog<CommonItem> {

	/**
	 * 定义单文本编辑框
	 */
	private EditText et_item;

	public ProfileItemTextDialog(Activity parent, CommonItem item) {
		super(parent, item);
	}

	@Override
	public void initControls(LinearLayout layout) {
		// 定义子控件
		et_item = new EditText(parent);
		et_item.setText("" + item.getValue());
		et_item.setSingleLine();
		et_item.setInputType(getInputType());
		// 设置Layout参数
		et_item.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 80));
		// 添加子控件
		layout.addView(et_item);
	}

	/**
	 * 返回文本框类型
	 * 
	 * @return
	 */
	protected int getInputType() {
		return InputType.TYPE_CLASS_TEXT;
	}

	@Override
	protected void doOkAction() {
		// TODO 检验值的合法性...
		// 原Item重新设值
		item.setValue(et_item.getText());
		
		// 通知对象条目变更
		this.notifyAdapterEvent(CommonAdapter.EVT_ITEM_CHANGED, item);
	}

	@Override
	protected String getTitle() {
		return "编辑:" + item.getLabel();
	}

}
