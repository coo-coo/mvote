package com.coo.m.vote.activity;

import java.util.List;

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
import com.coo.m.vote.activity.adapter.SysMainChannelAdapter;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.rpc2.IRpcCallback;
import com.kingstar.ngbf.ms.util.rpc2.RpcCallHandler;
import com.kingstar.ngbf.ms.util.rpc2.RpcCaller;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 【主界面】頻道
 * 
 * @since 1.0
 * @author boqing.shen
 */
@SuppressLint({ "ValidFragment", "HandlerLeak" })
public class SysMainChannel extends Fragment implements IRpcCallback {

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

	/**
	 * 构造函数
	 */
	public SysMainChannel(FragmentActivity parent, String code, String label) {
		super();
		this.parent = parent;
		this.code = code;
		this.label = label;
	}

	private RpcCaller httpCaller;

	private SysMainChannelAdapter channelAdapter;

	/**
	 * 覆盖此函数，先通过inflater inflate函数得到view最后返回
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragementView = inflater.inflate(R.layout.sys_main_fragment,
				container, false);

		httpCaller = new RpcCaller(new RpcCallHandler(this));

		if (Constants.MOCK_DATA) {
			// 获得Fragement视图内的ListView,加载数据
			List<Topic> list = Mock.topicshots(this.code);
			ListView lv_topics = (ListView) fragementView
					.findViewById(R.id.lv_sys_main_fragment);
			channelAdapter = new SysMainChannelAdapter(this, list,
					lv_topics);
		} else {
			// 异步加载获得的数据
			String uri = "/topic/list/code/" + code + "?op="
					+ VoteManager.getStrAccount();
			httpCaller.doGet(Constants.RPC_TOPIC_LIST_CODE,
					Constants.rest(uri));
		}

		// TODO 根据频道代码来加载数据,应根据手势滑动来加载数据，效率为高
		return fragementView;
	}

	public RpcCaller getHttpCaller() {
		return httpCaller;
	}

	public FragmentActivity getParent() {
		return parent;
	}

	@Override
	@Reference(override = IRpcCallback.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.RPC_TOPIC_LIST_CODE) {
			// 获得Fragement视图内的ListView,加载数据
			List<Topic> list = resp.getItems(Topic.class);
			ListView lv_topics = (ListView) fragementView
					.findViewById(R.id.lv_sys_main_fragment);
			channelAdapter = new SysMainChannelAdapter(this, list,
					lv_topics);
		} else if (what == Constants.RPC_TOPIC_VOTE) {
			toast("投票成功");
			channelAdapter.getSelected().setVoted(Boolean.TRUE);
		} else {

		}
	}

	public String getLabel() {
		return label;
	}

	public void toast(String msg) {
		Toast.makeText(parent, msg, Toast.LENGTH_SHORT).show();
	}

}