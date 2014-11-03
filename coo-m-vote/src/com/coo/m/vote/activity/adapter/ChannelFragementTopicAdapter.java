package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.coo.s.vote.model.Topic;
import com.coo.s.vote.model.TopicLeg;
import com.kingstar.ngbf.ms.mpchart.ChartFactory;
import com.kingstar.ngbf.ms.mpchart.ChartSeries;
import com.kingstar.ngbf.ms.mpchart.CommonBarChart;
import com.kingstar.ngbf.ms.mpchart.CommonPieChart;
import com.kingstar.ngbf.ms.util.android.CommonAdapter;
import com.kingstar.ngbf.ms.util.android.CommonItemHolder;

/**
 * 我的主题的ListView的填充器
 * 
 * @since 0.5.1.0
 * @author boqing.shen
 * 
 */
public class ChannelFragementTopicAdapter extends CommonAdapter<Topic>
		implements OnClickListener {

	/**
	 * 构造函数
	 */
	public ChannelFragementTopicAdapter(Activity parent, List<Topic> items,
			ListView composite) {
		super(parent, items, composite);
	}

	@Override
	public int getItemConvertViewId() {
		// TODO 返回Topic的布局
		return R.layout.sys_main_topic_row;
	}

	/**
	 * 定义线性布局对象,减少创建次数
	 */
	private LinearLayout linearLayoutRight = null;

	private LayoutInflater layoutInflater = null;

	@Override
	public void onItemClick(AdapterView<?> parentView, View view,
			int position, long rowId) {

		if (linearLayoutRight == null) {
			linearLayoutRight = (LinearLayout) getParent()
					.findViewById(R.id.ll_sys_main_right);
		}
		// 清除右侧滑动界面，动态加载布局...
		linearLayoutRight.removeAllViews();
		if (layoutInflater == null) {
			layoutInflater = LayoutInflater.from(parent);
		}

		Topic item = this.getItem(position);
		this.selected = item;
		// 模拟 根据Topic和Account之间是否有差异
		if (item.isVoted()) {
			initIncTopicResult(item);
		} else {
			initIncTopicVote(item);
		}

		// 显示右侧滑动的信息....
		drawerLayout = (DrawerLayout) getParent().findViewById(
				R.id.drawer_layout);
		drawerLayout.openDrawer(Gravity.RIGHT);
	}

	private DrawerLayout drawerLayout;

	/**
	 * 初始化 @layout/inc_topic_result 布局内信息，查看投票结果信息
	 */
	private void initIncTopicResult(Topic item) {
		View viewTopicResult = layoutInflater.inflate(
				R.layout.inc_topic_result, null);
		linearLayoutRight.addView(viewTopicResult);

		// 初始化标题
		TextView tv_title = (TextView) getParent().findViewById(
				R.id.tv_inc_topic_result_title);
		tv_title.setText(item.getTitle());

		// 初始化结果，以饼图的形式呈现
		CommonPieChart chart = (CommonPieChart) getParent()
				.findViewById(R.id.cpc_inc_topic_result);
		CommonBarChart chart2 = (CommonBarChart) getParent()
				.findViewById(R.id.cbc_inc_topic_result);
		ChartSeries cs = ChartSeries.instance("投票数");
		for (TopicLeg leg : item.getLegs()) {
			cs.add(leg.getTitle(), leg.getVote());
		}
		ChartFactory.init(cs, chart);
		ChartFactory.init(cs, chart2);

		// 初始化选项条目
		// ListView lv_legs = (ListView) getActivity().findViewById(
		// R.id.lv_inc_topic_result_legs);
		// TopicLegResultAdapter adapter = new TopicLegResultAdapter(
		// item.getLegs(), lv_legs);
		// adapter.initContext(getActivity());
	}

	/**
	 * 初始化 @layout/inc_topic_vote 布局内信息，准备进行投票
	 */
	private void initIncTopicVote(Topic item) {
		View viewTopicVote = layoutInflater.inflate(
				R.layout.inc_topic_vote, null);
		linearLayoutRight.addView(viewTopicVote);

		// 初始化标题
		TextView tv_title = (TextView) getParent().findViewById(
				R.id.tv_inc_topic_vote_title);
		tv_title.setText(item.getTitle());

		// 初始化选项条目
		ListView lv_legs = (ListView) getParent().findViewById(
				R.id.lv_inc_topic_vote_legs);

		// 初始化适配器...
		topicLegVoteAdapter = new TopicLegVoteAdapter(parent,
				item.getLegs(), lv_legs);

		Button btn_vote = (Button) getParent().findViewById(
				R.id.btn_topic_vote);
		btn_vote.setOnClickListener(this);
	}

	private TopicLegVoteAdapter topicLegVoteAdapter;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_topic_vote:
			// 依据手机账号/密码登录
			doTopicVote();
			break;
		}
	}

	/**
	 * 投票操作
	 */
	private void doTopicVote() {
		TopicLeg topicLeg = topicLegVoteAdapter.getSelected();
		if (topicLeg == null) {
			toast("请选择一项!");
			return;
		}

		// 进行投票请求
		String account = VoteManager.getStrAccount();
		String topicId = selected.get_id();

		String uri = Constants.HOST_REST + "/topic/vote/account/"
				+ account + "/topic/" + topicId + "/legSeq/"
				+ topicLeg.getSeq();
		toast("投票RPC未实现:" + uri);
		// 异步请求
//		 HttpAsynCaller.doGet(uri, Constants.TYPE_NONE, this);
		// 关闭界面
		drawerLayout.closeDrawer(Gravity.RIGHT);
	}

	// @Override
	// public void response(SimpleMessage<?> resp) {
	// this.toast(Constants.MSG_RPC_OK);
	//
	// }

	@Override
	public CommonItemHolder initHolder(View convertView) {
		ChannelFragementTopicRowHolder holder = new ChannelFragementTopicRowHolder();
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.tv_topic_row_title);
		holder.tv_createtime = (TextView) convertView
				.findViewById(R.id.tv_topic_row_createtime);
		holder.tv_vote = (TextView) convertView
				.findViewById(R.id.tv_topic_row_vote);
		holder.tv_author = (TextView) convertView
				.findViewById(R.id.tv_topic_row_author);
		holder.cpc_chart = (CommonPieChart) convertView
				.findViewById(R.id.cpc_topic_result);
		return holder;
	}

	@Override
	public void initHolderValue(CommonItemHolder ciHolder, Topic item) {
		ChannelFragementTopicRowHolder holder = (ChannelFragementTopicRowHolder) ciHolder;
		holder.tv_title.setText(item.getTitle());
		String BLANK = "    ";
		// TODO 多数据多屏幕，被遮盖之后有问题 参见M-20
		holder.tv_vote.setText("投票数:" + item.getVote());
		holder.tv_createtime.setText(BLANK + "创建日期:"
				+ VoteUtil.getTsDateText(item.get_tsi()));
		// TODO 作者账号|昵称
		holder.tv_author.setText(BLANK + "作者:" + item.getOwner());

		// 初始化数据饼图...
		ChartSeries cs = ChartSeries.instance(item.getTitle());
		for (TopicLeg leg : item.getLegs()) {
			cs.add(leg.getTitle(), leg.getVote());
		}
		ChartFactory.init(cs, holder.cpc_chart);

	}

}

class ChannelFragementTopicRowHolder extends CommonItemHolder {
	// public TextView tv_icon;
	public TextView tv_title;
	public TextView tv_createtime;
	public TextView tv_vote;
	public TextView tv_author;

	public CommonPieChart cpc_chart;
}
