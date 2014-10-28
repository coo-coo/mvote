package com.coo.m.vote.activity;

import java.util.List;

import android.widget.LinearLayout;
import android.widget.ListView;

import com.coo.m.vote.R;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.component.ContactBean;
import com.kingstar.ngbf.ms.util.android.component.ContactManager;
import com.kingstar.ngbf.ms.util.android.component.DataLoadProgressBar;
import com.kingstar.ngbf.ms.util.android.component.IProgressBarAble;
import com.kingstar.ngbf.ms.util.android.component.ProgressBarHandler;

/**
 * 我的电话薄Activity,讀取設備信息，進行顯示
 * 
 * @deprecated 参见ContactActivity，从MContact中获得
 * 
 */
public class ContactActivity2 extends CommonBizActivity implements
		IProgressBarAble {

	protected static final String TAG = ContactActivity2.class.getName();

	/**
	 * 所有电话薄
	 */
	@SuppressWarnings("unused")
	private List<ContactBean> contactBeans;

	@Override
	public String getHeaderTitle() {
		return "通讯录";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.mcontact_activity;
	}

	@Override
	public void loadContent() {
		// 加载进度条
		DataLoadProgressBar bar = new DataLoadProgressBar(this,
				R.drawable.progressbar_dataload);
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll_phone_activity);
		ll.addView(bar);

		// 添加一个Handler
		final ProgressBarHandler processBarHandler = new ProgressBarHandler(
				this, bar, 150);
		// 异步读取电话薄
		final ContactManager cm = new ContactManager(this);
		new Thread() {
			public void run() {
				contactBeans = cm.findAll(processBarHandler);
				processBarHandler.complete();
			}
		}.start();
	}

	@Override
	public void onDataLoadCompleted() {
		// 数据读取结束,加载电话薄
		@SuppressWarnings("unused")
		ListView lv_contact = (ListView) findViewById(R.id.lv_phone_activity);
		// TODO
		// ContactBeanAdapter adapter = new ContactBeanAdapter(
		// contactBeans, lv_contact);
		// adapter.initContext(this);
		toast("onDataLoadCompleted...");
	}
}
