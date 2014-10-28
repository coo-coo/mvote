package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.m.vote.VoteUtil;
import com.coo.m.vote.model.MGroup;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * MGroup适配器
 * @author boqing.shen
 *
 */
public class MGroupAdapter extends CommonAdapter<MGroup> {

	public MGroupAdapter(Activity parent, List<MGroup> items,
			ListView composite) {
		super(parent, items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.group_activity_row;
	}

	@Override
	protected CommonItemHolder initHolder(View view) {
		MGroupItemHolder holder = new MGroupItemHolder();
		holder.tv_name = (TextView) view
				.findViewById(R.id.tv_group_row_name);
		holder.tv_tsi = (TextView) view
				.findViewById(R.id.tv_group_row_tsi);
		return holder;
	}

	@Override
	protected void initHolderValue(CommonItemHolder ciHolder, MGroup cb) {
		MGroupItemHolder holder = (MGroupItemHolder) ciHolder;
		holder.tv_name.setText(cb.getName());
		holder.tv_tsi.setText(VoteUtil.getTsText(cb.getTsi()));
	}
}

class MGroupItemHolder extends CommonItemHolder {
	public TextView tv_name;
	public TextView tv_tsi;
}
