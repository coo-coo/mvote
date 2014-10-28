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

public class ContactBeanAdapter extends CommonAdapter<MContact> {

	public ContactBeanAdapter(Activity parent, List<MContact> items, ListView composite) {
		super(parent,items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.contact_activity_row;
	}

	@Override
	protected CommonItemHolder initHolder(View view) {
		ContactBeanItemHolder holder = new ContactBeanItemHolder();
		holder.tv_name = (TextView) view
				.findViewById(R.id.tv_phone_row_name);
		holder.tv_mobile = (TextView) view
				.findViewById(R.id.tv_phone_row_mobile);
		return holder;
	}

	@Override
	protected void initHolderValue(CommonItemHolder ciHolder, MContact cb) {
		ContactBeanItemHolder holder = (ContactBeanItemHolder) ciHolder;
		holder.tv_name.setText(cb.getName());
		holder.tv_mobile.setText(cb.getMobile());
	}
}

class ContactBeanItemHolder extends CommonItemHolder {
	public TextView tv_name;
	public TextView tv_mobile;
}
