package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.m.vote.model.MContact;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

public class MContactAdapter extends CommonAdapter<MContact> {

	public MContactAdapter(Activity parent, List<MContact> items,
			ListView composite) {
		super(parent, items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.mcontact_activity_row;
	}

	@Override
	protected CommonItemHolder initHolder(View view) {
		MContactRowHolder holder = new MContactRowHolder();
		holder.tv_name = (TextView) view
				.findViewById(R.id.tv_phone_row_name);
		holder.tv_mobile = (TextView) view
				.findViewById(R.id.tv_phone_row_mobile);
		return holder;
	}

	@Override
	protected void initHolderValue(CommonItemHolder ciHolder, MContact cb) {
		MContactRowHolder holder = (MContactRowHolder) ciHolder;
		holder.tv_name.setText(cb.getName());
		holder.tv_mobile.setText(cb.getMobile());
	}
}

class MContactRowHolder extends CommonItemHolder {
	public TextView tv_name;
	public TextView tv_mobile;
}
