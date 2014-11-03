package com.coo.m.vote.activity;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.adapter.TopicAdapter;
import com.coo.m.vote.activity.view.TopicCommandDialog;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 我(创建)的Topic管理，列表展示?
 * 
 * @since 0.4.0
 */
public class TopicActivity extends CommonBizActivity {

	@Override
	public String getHeaderTitle() {
		return "我的话题";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.topic_activity;
	}

	@Override
	public void loadContent() {
		if (Constants.MOCK_DATA) {
			List<Topic> list = Mock.topicshots("my");
			ListView listView = (ListView) findViewById(R.id.lv_topic);
			adapter = new TopicAdapter(this, list, listView);
		} else {
			// 异步调用数据
			String account = VoteManager.getStrAccount();
			String uri = "/topic/list/mine?op=" + account;
			toast(uri);
			httpCaller.doGet(Constants.BIZ_TOPIC_LIST_MINE,
					Constants.rest(uri));
		}
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.BIZ_TOPIC_LIST_MINE) {
			List<Topic> list = resp.getItems(Topic.class);
			ListView listView = (ListView) findViewById(R.id.lv_topic);
			adapter = new TopicAdapter(this, list, listView);
		}
	}

	// @Override
	// public void response(SimpleMessage<Topic> resp) {
	// ListView listView = (ListView) findViewById(R.id.lv_topic);
	// adapter = new TopicAdapter(this, resp.getRecords(), listView);
	// }

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
