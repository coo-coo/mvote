package com.coo.m.vote.activity;

import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.TopicMgtListViewAdapter;
import com.coo.s.vote.model.Topic;
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
		IHttpCallback<SimpleMessage<Topic>> {

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
		ListView listView = (ListView) findViewById(R.id.lv_topic_mgt);
		TopicMgtListViewAdapter adapter = new TopicMgtListViewAdapter(
				resp.getRecords(), listView);
		adapter.initContext(this);
	}
}
