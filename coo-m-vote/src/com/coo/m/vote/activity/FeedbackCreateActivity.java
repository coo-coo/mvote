package com.coo.m.vote.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.StringUtil;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 【意见反馈】创建
 * 
 * @since 0.4.3.0
 * @author boqing.shen
 */
public class FeedbackCreateActivity extends CommonBizActivity {

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

	/**
	 * 参见HttpCallHandler的消息处理...
	 * 
	 * @since 2.4.9.0
	 */
	@Override
	@Reference(override = CommonBizActivity.class)
	public void onHttpCallback(int what, NtpMessage sm) {
		if (what == Constants.RPC_FEEDBACK_CREATE) {
			Intent intent = new Intent(FeedbackCreateActivity.this,
					SysMainActivity.class);
			startActivity(intent);
		}
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
			// 以NtpMessage封装意见反馈消息体
			NtpMessage sm = new NtpMessage();
			sm.set("owner", VoteManager.getStrAccount());
			sm.set("note", note);
			sm.set("app_version", VoteManager.getVersionName());

			// 异步调用
			String uri = "/feedback/create/";
			httpCaller.doPost(Constants.RPC_FEEDBACK_CREATE,
					Constants.rest(uri), sm);
		}
	}
}
