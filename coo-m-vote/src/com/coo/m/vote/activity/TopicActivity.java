package com.coo.m.vote.activity;

import android.content.Intent;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.adapter.TopicAdapter;
import com.coo.m.vote.activity.view.TopicCommandDialog;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.rpc.HttpAsynCaller;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

/**
 * 我(创建)的Topic管理，列表展示?
 * 
 * @since 0.4.0
 */
public class TopicActivity extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<Topic>> {

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
			response(Mock.topicshots("my"));
		} else {
			// 异步调用数据
			String account = VoteManager.getStrAccount();
			toast("account=" + account);
			String URL = Constants.HOST_REST
					+ "/topic/mine/account/" + account;
			HttpAsynCaller.doGet(URL, Constants.TYPE_TOPIC, this);
		}
	}

	@Override
	public void response(SimpleMessage<Topic> resp) {
		ListView listView = (ListView) findViewById(R.id.lv_topic);
		adapter = new TopicAdapter(this, resp.getRecords(), listView);
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
			default:
				// 其它，包括Label/Boolean等,不处理
				break;
			}
		}
	}

}
