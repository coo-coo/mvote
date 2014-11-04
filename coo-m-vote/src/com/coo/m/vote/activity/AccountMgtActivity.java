package com.coo.m.vote.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.AccountMgtAdapter;
import com.coo.s.vote.model.Account;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 【账号管理】
 * 
 * @since 0.4.3.0
 * @author boqing.shen
 */
public class AccountMgtActivity extends CommonBizActivity implements
		OnClickListener {

	@Override
	public int getResViewLayoutId() {
		return R.layout.account_mgt_activity;
	}

	@Override
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			ListView composite = (ListView) findViewById(R.id.lv_account_mgt);
			adapter = new AccountMgtAdapter(this, Mock.accounts(),
					composite);
			// onHttpCallback(Constants.BIZ_ACCOUNT_ALL,Mock.accounts());
		} else {
			String uri = "/account/list/all";
			httpCaller.doGet(Constants.RPC_ACCOUNT_ALL,
					Constants.rest(uri));
		}
	}

	@Override
	public String getHeaderTitle() {
		return "账号管理";
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.RPC_ACCOUNT_ALL) {
			List<Account> list = resp.getItems(Account.class);
			ListView composite = (ListView) findViewById(R.id.lv_account_mgt);
			adapter = new AccountMgtAdapter(this, list, composite);
		}
	}

	private Account clicked = null;

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onAdapterItemClickedLong(Object object) {
		// 弹出处理对话框
		clicked = (Account) object;
		// new AccountMgtItemDialog(this, item).show();
		int status = clicked.getStatus();
		String msg = "";
		if (status == 0) {
			msg = "确定要[锁定]" + clicked.getMobile() + "么?";
		} else {
			msg = "确定要[解锁]" + clicked.getMobile() + "么?";
		}

		new AlertDialog.Builder(this).setCancelable(false)
				.setTitle("账号处理").setIcon(R.drawable.ico_cirle)
				.setMessage(msg).setPositiveButton("确定", this)
				.setNegativeButton("取消", null).show();
	}

	@Override
	@Reference(override = OnClickListener.class)
	public void onClick(DialogInterface dialog, int whichButton) {
		if (whichButton == AlertDialog.BUTTON_POSITIVE) {
			int status = clicked.getStatus();
			int statusTarget = 0;
			if (status == 0) {
				statusTarget = 5;
			} else {
				statusTarget = 0;
			}
			clicked.setStatus(statusTarget);

			// 通知对象变更
			notifyAdapterEvent(CommonAdapter.EVT_ITEM_CHANGED,
					clicked);
		}
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onAdapterItemChanged(Object object) {
		adapter.notifyDataSetChanged();
		// 进行RPC调用
		Account item = (Account) object;
		String uri = "/account/update/_id/" + item.get_id()
				+ "/status/" + item.getStatus();
		httpCaller.doGet(Constants.RPC_ACCOUNT_UPDATE_STATUS,
				Constants.rest(uri));
	}
}
