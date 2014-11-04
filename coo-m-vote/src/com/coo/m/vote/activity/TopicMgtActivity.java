package com.coo.m.vote.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.adapter.TopicMgtAdapter;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * admin 话题管理
 * 
 * @author boqing.shen
 * 
 */
public class TopicMgtActivity extends CommonBizActivity implements
		OnClickListener {

	@Override
	public int getResViewLayoutId() {
		return R.layout.topic_mgt_activity;
	}

	@Override
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			List<Topic> list = Mock.topicshots("latest");
			ListView composite = (ListView) findViewById(R.id.lv_topic_mgt);
			adapter = new TopicMgtAdapter(this, list, composite);
		} else {
			// TODO 获得全部最新创建的...
			String uri = "/topic/list/admin?op="
					+ VoteManager.getStrAccount();
			httpCaller.doGet(Constants.RPC_TOPIC_LIST_ADMIN,
					Constants.rest(uri));
		}
	}

	@Override
	public String getHeaderTitle() {
		return "话题管理";
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.RPC_TOPIC_LIST_ADMIN) {
			List<Topic> list = resp.getItems(Topic.class);
			ListView composite = (ListView) findViewById(R.id.lv_topic_mgt);
			adapter = new TopicMgtAdapter(this, list, composite);
		}
		else if (what == Constants.RPC_TOPIC_UPDATE_STATUS) {
			toast("操作成功!");
		}
		else{
			
		}
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
	public void onAdapterItemChanged(Object object) {
		adapter.notifyDataSetChanged();
		// 进行RPC调用
		Topic item = (Topic) object;
		String uri = "/topic/update/_id/" + item.get_id()
				+ "/status/" + item.getStatus();
		httpCaller.doGet(Constants.RPC_TOPIC_UPDATE_STATUS,
				Constants.rest(uri));
	}
}
