package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.Account;
import com.kingstar.ngbf.ms.util.android.CommonItemAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemDialog;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * Account管理行Adapter
 * 
 */
public class AccountMgtListViewAdapter extends CommonItemAdapter<Account> {

	public AccountMgtListViewAdapter(List<Account> items, ListView composite) {
		super(items, composite);
	}

	/**
	 * 返回控件布局
	 */
	public int getItemConvertViewId() {
		return R.layout.account_mgt_activity_row;
	}

	@Override
	public CommonItemHolder initHolder(View convertView) {
		AccountMgtItemRowHolder holder = new AccountMgtItemRowHolder();
		holder.tv_mobile = (TextView) convertView
				.findViewById(R.id.tv_account_mgt_row_mobile);
		holder.tv_status = (TextView) convertView
				.findViewById(R.id.tv_account_mgt_row_status);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Account item) {
		AccountMgtItemRowHolder holder = (AccountMgtItemRowHolder) ciHolder;
		holder.tv_mobile.setText(item.getMobile());
		String statusLabel = "有效";
		if (item.getStatus() == 5) {
			statusLabel = "被锁定";
		}
		holder.tv_status.setText(statusLabel);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		// 弹出处理对话框
		Account item = this.getItem(position);
		new AccountMgtItemHandleDialog(this.getActivity(), item).show();
		return true;
	}

}

class AccountMgtItemRowHolder extends CommonItemHolder {
	public TextView tv_mobile;
	public TextView tv_status;
	// TODO 注册时间...
}

/**
 * 条目长恩处理
 * 
 * @author boqing.shen
 * 
 */
class AccountMgtItemHandleDialog extends CommonItemDialog<Account> {

	public AccountMgtItemHandleDialog(Activity activity, Account item) {
		super(activity, item);
	}

	@Override
	public String getTitle() {
		return "账号处理";
	}
	
	// 指明下一个状态  目前，只有【正常、锁定】两个状态的切换
	

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
		toast("执行.." + item.getMobile() + "  " + statusTarget);
		item.setStatus(statusTarget);
		// 交給Activity來進行RPC调用
		this.notitifyParentAbsViewItemChanged();
	}
}
