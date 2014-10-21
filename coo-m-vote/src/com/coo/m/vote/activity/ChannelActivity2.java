package com.coo.m.vote.activity;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.coo.m.vote.activity.adapter.ChannelGridItemAdapter;
import com.coo.m.vote.activity.adapter.CommonCallback;
import com.coo.s.vote.model.Channel;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.component.IShakeListener;
import com.kingstar.ngbf.ms.util.rpc.HttpAsynCaller;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

/**
 * 我的频道，登录账号可以查询、关注指定的频道
 * @deprecated 参见ChannelActivity 从MChannel中获得
 */
public class ChannelActivity2 extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<Channel>>, IShakeListener {

	public static final String TAG = ChannelActivity2.class.getSimpleName();

	// 加载所有的channel
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			response(Mock.channels());
		} else {
			// TODO 直接加载本地的MChannel(已经有Focus行为)
			// 异步加载获得的数据
			String uri = Constants.HOST_REST
					+ "/channel/focus/account/"
					+ VoteManager.getStrAccount();
			HttpAsynCaller.doGet(uri, Constants.TYPE_CHANNEL, this);
		}
		this.registerShakeListener(this);
	}

	private ChannelGridItemAdapter adapter;

	@Override
	public void onAbsViewItemChanged(Object item) {
		// 登录账号对指定的Channel做了关注|解除关注 操作
		adapter.notifyDataSetChanged();
		if (item instanceof Channel) {
			// TODO 更新本地的MChannel
			Channel c = (Channel) item;
			String account = VoteManager.getStrAccount();
			toast(account + "-" + c.getCode() + "-" + c.getStatus()
					+ "-" + c.get_id());
			String uri = Constants.HOST_REST + "/focus/channel/"
					+ account + "/" + c.getCode();
			toast(uri);
			// 异步请求
			HttpAsynCaller.doGet(uri, Constants.TYPE_NONE,
					new CommonCallback(this));
		}
	}

	@Override
	public void response(SimpleMessage<Channel> resp) {
		if (VoteUtil.isRespOK(resp)) {
			// 从本地初始化数据
			// GridView girdView = (GridView)
			// findViewById(R.id.gv_channel);
			// adapter = new
			// ChannelGridItemAdapter(resp.getRecords(),
			// girdView);
			adapter.initContext(this);
		} else {
			toast(Constants.MSG_RPC_ERROR);
		}
	}

	@Override
	public String getHeaderTitle() {
		return "我的频道";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.channel_activity;
	}

	@Override
	public void onShake() {
		toast("shake....");
	}

}
