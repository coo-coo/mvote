package com.coo.m.vote.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.adapter.ChannelFragementTopicAdapter;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.rpc.HttpAsynCaller;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

/**
 * 主界面的每一个Channel的Fragment
 * 
 * @since 0.4.2.0
 * @author Bingjue.Sun
 */
@SuppressLint({ "ValidFragment", "HandlerLeak" })
public class SysMainChannel extends Fragment implements
		IHttpCallback<SimpleMessage<Topic>> {

	// parent
	private FragmentActivity parent;
	/**
	 * 频道代码
	 */
	private String code = "";
	/**
	 * 频道名称
	 */
	private String label = "";

	private View fragementView;

	// private ViewGroup container;

	/**
	 * 构造函数
	 */
	public SysMainChannel(FragmentActivity parent, String code,
			String label) {
		super();
		this.parent = parent;
		this.code = code;
		this.label = label;
	}

	/**
	 * 覆盖此函数，先通过inflater inflate函数得到view最后返回
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragementView = inflater.inflate(R.layout.sys_main_fragment,
				container, false);
		
		if (Constants.MOCK_DATA) {
			response(Mock.topicshots(this.code));
		} else {
			// 异步加载获得的数据
			String uri = Constants.SERVERHOST
					+ "/topic/code/" + code + "/account/"
					+ VoteManager.getStrAccount();
			HttpAsynCaller.doGet(uri, Constants.TYPE_TOPIC, this);
		}
		
		// TODO 根据频道代码来加载数据,应根据手势滑动来加载数据，效率为高
		return fragementView;
	}

	
	// @Override
	public void response(SimpleMessage<Topic> resp) {
		// 获得Fragement视图内的ListView,加载数据
		ListView lv_topics = (ListView) fragementView
				.findViewById(R.id.lv_sys_main_fragment);
		// toast("msg-" + code + "-" + label);
		ChannelFragementTopicAdapter adapter = new ChannelFragementTopicAdapter(
				resp.getRecords(), lv_topics);
		adapter.initContext(parent);
	}

	public String getLabel() {
		return label;
	}

	@SuppressWarnings("unused")
	private void toast(String msg) {
		Toast.makeText(parent, msg, Toast.LENGTH_SHORT).show();
	}

}
