package com.coo.m.vote.activity;

import com.coo.m.vote.R;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;

/**
 * 群组管理
 */
public class GroupActivity extends CommonBizActivity {

	@Override
	public String getHeaderTitle() {
		return "群组管理";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.topic_activity;
	}

	@Override
	public void loadContent() {
		// TODO 列表显示: 组1 (15) 组2 (26) 点击，进入GroupEditActivity
	}
}
