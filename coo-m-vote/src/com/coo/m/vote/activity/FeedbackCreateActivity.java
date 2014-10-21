package com.coo.m.vote.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.s.vote.model.Account;
import com.kingstar.ngbf.ms.util.StringUtil;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.rpc.HttpAsynCaller;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;
import com.kingstar.ngbf.s.ntp.SimpleMessageHead;

/**
 * 【意见反馈】创建
 * 
 * @since 0.4.3.0
 * @author boqing.shen
 */
public class FeedbackCreateActivity extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<?>> {
	private static final String TAG = FeedbackCreateActivity.class
			.getSimpleName();

	/**
	 * note文本编辑框
	 */
	private EditText et_note = null;
	/**
	 * 保存按钮
	 */
	private Button btn_save = null;

	@Override
	public int getResViewLayoutId() {
		return R.layout.feedback_create_activity;
	}

	@Override
	public void loadContent() {
		et_note = (EditText) findViewById(R.id.et_feedback_create_note);
		btn_save = (Button) findViewById(R.id.btn_feedback_create_save);
		btn_save.setOnClickListener(this);
	}

	@Override
	public String getHeaderTitle() {
		return "意见反馈";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_feedback_create_save:
			doSave();
			break;
		}
	}

	/**
	 * 保存Feedback操作
	 */
	private void doSave() {
		String note = et_note.getText().toString();
		// 判定Note空等
		if (StringUtil.isNullOrSpace(note)) {
			toast("反馈信息不能为空");
		} else {
			// 以SimpleMessage封装意见反馈消息体
			SimpleMessage<?> sm = new SimpleMessage<Object>();
			Account account = VoteManager.getAccount();
			
			String app_version = VoteManager.get().getAppVersion(
					this);
			sm.set("owner", account.getMobile());
			sm.set("owner_id", account.get_id());
			sm.set("note", note);
			sm.set("app_version", app_version);

			String uri = Constants.HOST_REST + "/feedback/create/";
			Log.i(TAG, uri);
			// 异步调用
			HttpAsynCaller.doPost(uri, sm, this);
		}
	}

	@Override
	public void response(SimpleMessage<?> resp) {
		// 获得反馈消息,处理业务
		if (resp.getHead().getRep_code()
				.equals(SimpleMessageHead.REP_OK)) {
			toast("感谢您的反馈,我们会尽快对您的反馈做出响应!");
			Intent intent = new Intent(FeedbackCreateActivity.this,
					SysMainActivity.class);
			startActivity(intent);
		} else {
			toast(resp.toJson());
		}
	}
}
