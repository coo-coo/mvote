package com.coo.m.vote.activity;

import java.util.List;

import android.widget.GridView;

import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.ChannelGridItemAdapter;
import com.coo.m.vote.model.MChannel;
import com.coo.m.vote.model.MManager;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.component.IShakeListener;

/**
 * 我的频道，登录账号可以查询、关注指定的频道
 * 从MChannel中获得...
 */
public class ChannelActivity extends CommonBizActivity implements
		IShakeListener {

	public static final String TAG = ChannelActivity.class.getSimpleName();

	// 加载所有的channel
	public void loadContent() {
		// 从SQLLite中获取
		List<MChannel> list = MManager.findChannelAll();
		GridView girdView = (GridView) findViewById(R.id.gv_channel);
		adapter = new ChannelGridItemAdapter(list, girdView);
		adapter.initContext(this);

		// this.registerShakeListener(this);
	}

	private ChannelGridItemAdapter adapter;

	@Override
	public void onAbsViewItemChanged(Object item) {
		// 登录账号对指定的Channel做了关注|解除关注 操作
		adapter.notifyDataSetChanged();
		if (item instanceof MChannel) {
			// 更新本地SQLite
			MChannel c = (MChannel) item;
			toast(c.getCode() + "-" + c.getLabel() + "-"
					+ c.getStatus());
			c.update(c.getId());
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