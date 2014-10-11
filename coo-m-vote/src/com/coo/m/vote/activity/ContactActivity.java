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
		ContactBeanAdapter adapter = new ContactBeanAdapter(list,
				lv_contact);
		adapter.initContext(this);

		// // 加载进度条
		// DataLoadProgressBar bar = new DataLoadProgressBar(this,
		// R.drawable.progressbar_dataload);
		// LinearLayout ll = (LinearLayout)
		// findViewById(R.id.ll_phone_activity);
		// ll.addView(bar);
		//
		// // 添加一个Handler
		// final ProgressBarHandler processBarHandler = new
		// ProgressBarHandler(
		// this, bar, 150);
		// // 异步读取电话薄
		// final ContactManager cm = new ContactManager(this);
		// new Thread() {
		// public void run() {
		// contactBeans = cm.findAll(processBarHandler);
		// processBarHandler.complete();
		// }
		// }.start();
	}

	// @Override
	// public void onDataLoadCompleted() {
	// // 数据读取结束,加载电话薄
	// ListView lv_contact = (ListView)
	// findViewById(R.id.lv_phone_activity);
	// // toast(String.valueOf(list.size()));
	// ContactBeanAdapter adapter = new ContactBeanAdapter(
	// contactBeans, lv_contact);
	// adapter.initContext(this);
	// toast("onDataLoadCompleted...");
	// }
}
