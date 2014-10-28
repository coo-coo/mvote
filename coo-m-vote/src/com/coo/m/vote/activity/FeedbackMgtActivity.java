package com.coo.m.vote.activity;

import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.FeedbackMgtAdapter;
import com.coo.s.vote.model.Feedback;
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
		IHttpCallback<SimpleMessage<Feedback>> {

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
		adapter = new FeedbackMgtAdapter(this,
				resp.getRecords(), listView);
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.feedback_mgt_activity;
	}

	@Override
	public String getHeaderTitle() {
		return "意见管理";
	}
}
