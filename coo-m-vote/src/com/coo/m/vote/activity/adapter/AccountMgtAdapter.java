package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.Account;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
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
		// icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		icon.setPadding(0, 0, 1, 1);
		icon.setImageResource(resIcon);
	}
}

class AccountMgtRowHolder extends CommonItemHolder {
	public TextView tv_mobile;
	public TextView tv_status;
	public ImageView iv_status_icon;
	// TODO 注册时间...
}
