package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.m.vote.model.MContact;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * 用于MContact的选取,以实现Group的添加
 * 
 * @author boqing.shen
 * 
 */
public class MGroupContactSelectAdapter extends CommonAdapter<MContact> {

	public MGroupContactSelectAdapter(Activity parent, List<MContact> items, ListView composite) {
		super(parent,items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		return R.layout.mgroup_editor_activity_row;
	}

	@Override
	protected CommonItemHolder initHolder(View view) {
		MContactSelectItemHolder holder = new MContactSelectItemHolder();
		holder.tv_name = (TextView) view
				.findViewById(R.id.tv_group_create_row_name);
		holder.tv_mobile = (TextView) view
				.findViewById(R.id.tv_group_create_row_mobile);
		holder.cb_check = (CheckBox) view
				.findViewById(R.id.cb_group_create_row_check);
		return holder;
	}

	@Override
	protected void initHolderValue(CommonItemHolder ciHolder, MContact cb) {
		MContactSelectItemHolder holder = (MContactSelectItemHolder) ciHolder;
		holder.tv_name.setText(cb.getName());
		holder.tv_mobile.setText(cb.getMobile());
		holder.cb_check.setChecked(cb.isSelected());
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View view,
			int position, long rowId) {
		MContact item = getItem(position);
		item.setSelected(!item.isSelected());
		this.notifyDataSetChanged();
	}
}

class MContactSelectItemHolder extends CommonItemHolder {
	public TextView tv_name;
	public TextView tv_mobile;
	public CheckBox cb_check;
}
