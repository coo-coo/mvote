package com.coo.m.vote;

import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

public class CommonCallback implements IHttpCallback<SimpleMessage<?>> {

	private CommonBizActivity activity;

	public CommonCallback(CommonBizActivity activity) {
		this.activity = activity;
	}

	@Override
	public void response(SimpleMessage<?> resp) {
		activity.toast(Constants.MSG_RPC_OK);
	}

}
