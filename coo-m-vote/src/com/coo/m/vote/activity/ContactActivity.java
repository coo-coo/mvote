package com.coo.m.vote.activity;

import java.util.List;

import android.widget.ListView;

import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.ContactBeanAdapter;
import com.coo.m.vote.model.MContact;
import com.coo.m.vote.model.MManager;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;

/**
 * 我的电话薄Activity,讀取設備信息，進行顯示
 * 
 */
public class ContactActivity extends CommonBizActivity {

	protected static final String TAG = ContactActivity.class.getName();

	@Override
	public String getHeaderTitle() {
		return "通讯录";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.contact_activity;
	}

	@Override
	public void loadContent() {
		// 从SQLLite中获取
		List<MContact> list = MManager.findContactAll();
		ListView lv_contact = (ListView) findViewById(R.id.lv_phone_activity);
		adapter = new ContactBeanAdapter(this, list, lv_contact);
	}
}
