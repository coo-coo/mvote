package com.coo.m.vote.activity;

import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.AccountMgtAdapter;
import com.coo.s.vote.model.Account;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

/**
 * 【账号管理】
 * 
 * @since 0.4.3.0
 * @author boqing.shen
 */
public class AccountMgtActivity extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<Account>> {

	@Override
	public int getResViewLayoutId() {
		return R.layout.account_mgt_activity;
	}

	@Override
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			response(Mock.accounts());
		} else {
			toast("暂未实现");
		}
	}

	@Override
	public String getHeaderTitle() {
		return "账号管理";
	}

	@Override
	public void response(SimpleMessage<Account> resp) {
		ListView listView = (ListView) findViewById(R.id.lv_account_mgt);
		adapter = new AccountMgtAdapter(this, resp.getRecords(),
				listView);

	}

	@Override
	public void onAdapterItemClicked(Object item) {
		// 更新Adapter
		adapter.notifyDataSetChanged();
		// TODO 进行RPC调用
	}

}
