package com.coo.m.vote.activity;

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
import com.kingstar.ngbf.ms.util.rpc.HttpAsynCaller;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

/**
 * 反馈管理
 * 
 * @author boqing.shen
 * @since 0.4.7.0
 */
public class FeedbackMgtActivity extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<Feedback>>, OnClickListener {

	@Override
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			response(Mock.feedbacks());
		} else {
			// 异步调用数据
			String URL = Constants.HOST_REST
					+ "/feedback/latest/20";
			HttpAsynCaller.doGet(URL, Constants.TYPE_FEEDBACK, this);
		}
	}

	@Override
	public void response(SimpleMessage<Feedback> resp) {
		ListView listView = (ListView) findViewById(R.id.lv_feedback_mgt);
		adapter = new FeedbackMgtAdapter(this, resp.getRecords(),
				listView);
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
			clicked.setStatus(Feedback.STATUS_READED);
			// 通知对象变更
			notifyAdapterEvent(CommonAdapter.EVT_ITEM_CHANGED,
					clicked);
		}
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onAdapterItemChanged(Object item) {
		adapter.notifyDataSetChanged();
		// TODO 进行RPC调用
	}
}
