package com.coo.m.vote.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.TopicMgtAdapter;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

/**
 * admin 话题管理
 * 
 * @author boqing.shen
 * 
 */
public class TopicMgtActivity extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<Topic>>, OnClickListener {

	@Override
	public int getResViewLayoutId() {
		return R.layout.topic_mgt_activity;
	}

	@Override
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			response(Mock.topicshots("all"));
		} else {
			toast("暂未实现");
		}
	}

	@Override
	public String getHeaderTitle() {
		return "话题管理";
	}

	@Override
	public void response(SimpleMessage<Topic> resp) {
		ListView composite = (ListView) findViewById(R.id.lv_topic_mgt);
		adapter = new TopicMgtAdapter(this, resp.getRecords(),
				composite);
	}

	private Topic clicked = null;

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onAdapterItemClickedLong(Object object) {
		// 弹出处理对话框
		clicked = (Topic) object;
		// new AccountMgtItemDialog(this, item).show();
		int status = clicked.getStatus();
		String msg = "";
		if (status == Topic.STATUS_VALID) {
			msg = "确定要[锁定]" + clicked.getTitle() + "么?";
		} else {
			msg = "确定要[解锁]" + clicked.getTitle() + "么?";
		}

		new AlertDialog.Builder(this).setCancelable(false)
				.setTitle("话题管理").setIcon(R.drawable.ico_cirle)
				.setMessage(msg).setPositiveButton("确定", this)
				.setNegativeButton("取消", null).show();
	}

	@Override
	@Reference(override = OnClickListener.class)
	public void onClick(DialogInterface dialog, int whichButton) {
		if (whichButton == AlertDialog.BUTTON_POSITIVE) {
			int status = clicked.getStatus();
			if (status == Topic.STATUS_VALID) {
				status = Topic.STATUS_LOCKED;
			} else {
				status = Topic.STATUS_VALID;
			}
			clicked.setStatus(status);
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
