package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.Account;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * Account管理行Adapter
 * 
 */
public class AccountMgtAdapter extends CommonAdapter<Account> {

	public AccountMgtAdapter(Activity parent, List<Account> items,
			ListView composite) {
		super(parent, items, composite);
	}

	/**
	 * 返回控件布局
	 */
	public int getItemConvertViewId() {
		return R.layout.account_mgt_activity_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		AccountMgtRowHolder holder = new AccountMgtRowHolder();
		holder.tv_mobile = (TextView) convertView
				.findViewById(R.id.tv_account_mgt_row_mobile);
		holder.tv_status = (TextView) convertView
				.findViewById(R.id.tv_account_mgt_row_status);
		holder.iv_status_icon = (ImageView) convertView
				.findViewById(R.id.tv_account_mgt_row_status_icon);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Account item) {
		AccountMgtRowHolder holder = (AccountMgtRowHolder) ciHolder;
		holder.tv_mobile.setText(item.getMobile());
		String statusLabel = "有效";
		int resIcon = R.drawable.status_green;
		if (item.getStatus() == 5) {
			statusLabel = "被锁定";
			resIcon = R.drawable.status_gray;
		}
		holder.tv_status.setText(statusLabel);
		ImageView icon = holder.iv_status_icon;
		icon.setAdjustViewBounds(false);
//		icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		icon.setPadding(0, 0, 1, 1);
		icon.setImageResource(resIcon);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		// 弹出处理对话框
		Account item = this.getItem(position);
		new AccountMgtItemDialog(parent, item).show();
		return true;
	}

}

class AccountMgtRowHolder extends CommonItemHolder {
	public TextView tv_mobile;
	public TextView tv_status;
	public ImageView iv_status_icon;
	// TODO 注册时间...
}

/**
 * 条目长恩处理
 * 
 * @author boqing.shen
 * 
 */
class AccountMgtItemDialog extends CommonItemDialog<Account> {

	public AccountMgtItemDialog(Activity parent, Account item) {
		super(parent, item);
	}

	@Override
	public String getTitle() {
		return "账号处理";
	}

	// 指明下一个状态 目前，只有【正常、锁定】两个状态的切换

	protected void initControls(LinearLayout layout) {
		TextView tv = new EditText(parent);
		int status = item.getStatus();
		String op = "";
		if (status == 0) {
			op = "确定要[锁定]" + item.getMobile() + "么?";
		} else {
			op = "确定要[解锁]" + item.getMobile() + "么?";
		}
		tv.setText(op);
		layout.addView(tv);
	}

	@Override
	public void doOkAction() {
		int status = item.getStatus();
		int statusTarget = 0;
		if (status == 0) {
			statusTarget = 5;
		} else {
			statusTarget = 0;
		}
		item.setStatus(statusTarget);
		// 通知对象变更
		this.notifyAdapterEvent(CommonAdapter.EVT_ITEM_CHANGED, item);
	}
}
