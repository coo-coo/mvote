package com.coo.m.vote.activity;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.adapter.MyTopicAdapter;
import com.coo.m.vote.activity.view.TopicCommandDialog;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.CommonBizOptions;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 【我的话题】
 * 
 * @since 1.0
 * @author boqing.shen
 */
public class MyTopicActivity extends CommonBizActivity {

	@Override
	@Reference(override = CommonBizActivity.class)
	public CommonBizOptions getOptions() {
		return CommonBizOptions.blank().headerTitle("我的话题")
				.resViewLayoutId(R.layout.sys_blank_activity);
	}

	@Override
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			List<Topic> list = Mock.topicshots("my");
			ListView composite = new ListView(this, null,
					R.attr.ref_common_lv);
			this.setContentView(composite);
			adapter = new MyTopicAdapter(this, list, composite);
		} else {
			// 异步调用数据
			String account = VoteManager.getStrAccount();
			String uri = "/topic/list/mine?op=" + account;
			httpCaller.doGet(Constants.RPC_TOPIC_LIST_MINE,
					Constants.rest(uri));
		}
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.RPC_TOPIC_LIST_MINE) {
			List<Topic> list = resp.getItems(Topic.class);
			ListView composite = new ListView(this, null,
					R.attr.ref_common_lv);
			this.setContentView(composite);
			adapter = new MyTopicAdapter(this, list, composite);
		}
	}

	/**
	 * 监听Topic的长嗯响应
	 */
	@Override
	public void onAdapterItemClickedLong(Object item) {
		Topic topic = (Topic) item;
		new TopicCommandDialog(this, topic).show();
	}

	@Override
	public void onAdapterItemClicked(Object item) {
		if (item instanceof CommonItem) {
			CommonItem ci = (CommonItem) item;
			int uiType = ci.getUiType();
			switch (uiType) {
			case CommonItem.UIT_COMMAND_ACTIVITY:
				Intent intent = (Intent) ci.getValue();
				startActivity(intent);
				break;
			case CommonItem.UIT_COMMAND_ACTION:
				toast("未实现..");
				break;
			case CommonItem.UIT_DIALOG_CANCEL:
				Dialog dlg = (Dialog) ci.getValue();
				dlg.cancel();
				break;
			default:
				// 其它，包括Label/Boolean等,不处理
				break;
			}
		}
	}

}
