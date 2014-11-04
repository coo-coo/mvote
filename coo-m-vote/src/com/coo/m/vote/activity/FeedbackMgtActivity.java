package com.coo.m.vote.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.FeedbackMgtAdapter;
import com.coo.s.vote.model.Feedback;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 反馈管理
 * 
 * @author boqing.shen
 * @since 0.4.7.0
 */
public class FeedbackMgtActivity extends CommonBizActivity implements
		OnClickListener {

	protected static final String TAG = FeedbackMgtActivity.class.getName();

	@Override
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			List<Feedback> list = Mock.feedbacks();
			ListView composite = (ListView) findViewById(R.id.lv_feedback_mgt);
			adapter = new FeedbackMgtAdapter(this, list, composite);
		} else {
			String uri = "/feedback/latest/20";
			httpCaller.doGet(Constants.RPC_FEEDBACK_LATEST,
					Constants.rest(uri));
		}
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		// toast("" + what + "-" + resp.toJson());
		if (what == Constants.RPC_FEEDBACK_LATEST) {
			List<Feedback> list = resp.getItems(Feedback.class);
			ListView composite = (ListView) findViewById(R.id.lv_feedback_mgt);
			adapter = new FeedbackMgtAdapter(this, list, composite);
		}
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.feedback_mgt_activity;
	}

	@Override
	public String getHeaderTitle() {
		return "反馈管理";
	}

	private Feedback clicked = null;

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onAdapterItemClickedLong(Object object) {
		// 弹出处理对话框
		clicked = (Feedback) object;
		String msg = "";
		new AlertDialog.Builder(this).setCancelable(false)
				.setTitle("反馈处理").setIcon(R.drawable.ico_cirle)
				.setMessage(msg).setPositiveButton("确定", this)
				.setNegativeButton("取消", null).show();
	}

	@Override
	@Reference(override = OnClickListener.class)
	public void onClick(DialogInterface dialog, int whichButton) {
		if (whichButton == AlertDialog.BUTTON_POSITIVE) {
			// 改状态0到1
			clicked.setStatus(Feedback.STATUS_SOLVED);
			// 通知对象变更
			notifyAdapterEvent(CommonAdapter.EVT_ITEM_CHANGED,
					clicked);
		}
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onAdapterItemChanged(Object obj) {
		adapter.notifyDataSetChanged();
		// 进行RPC调用
		Feedback item = (Feedback) obj;
		String uri = "/feedback/update/_id/" + item.get_id()
				+ "/status/" + item.getStatus();
		httpCaller.doGet(Constants.RPC_FEEDBACK_UPDATE_STATUS,
				Constants.rest(uri));
	}
}
