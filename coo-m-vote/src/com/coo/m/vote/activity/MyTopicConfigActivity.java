package com.coo.m.vote.activity;

import java.util.List;

import android.content.Intent;
import android.widget.ListView;

import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.activity.view.ItemAdapter;
import com.coo.m.vote.activity.view.CommonOptionDialog;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * 【我的话题】话题设置
 * 
 * @since 1.0
 * @author boqing.shen
 * 
 */
public class MyTopicConfigActivity extends CommonBizActivity {

	/**
	 * 当前Topic,从 TopicActivity中传递过来
	 */
	private Topic current;

	@Override
	public void loadContent() {
		ListView lv = (ListView) findViewById(R.id.lv_topic_config);
		// 获得从TopicActivity传递过来的某一个Topic，进行设置
		Intent intent = getIntent();
		current = ((Topic) intent.getSerializableExtra("ITEM"));
		// 初始化设置
		List<CommonItem> items = VoteManager
				.getTopicSkeletonItems(current);
		adapter = new ItemAdapter(this, items, lv);
	}

	/**
	 * AdapterItem改变时调用，对应EVT_ITEM_CLICKED事件
	 */
	@Override
	@Reference(override = CommonBizActivity.class)
	public void onAdapterItemClicked(Object object) {
		if (object instanceof CommonItem) {
			CommonItem item = (CommonItem) object;
			int uiType = item.getUiType();
			toast("clicked ==" + uiType + "-" + item.getLabel());
			switch (uiType) {
			case CommonItem.UIT_LIST:
				// 显示文本，修改对话框
				new CommonOptionDialog(this, item).show();
				break;
			default:
				// 其它，包括Label/Boolean等,不处理
				break;
			}
		}
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onAdapterItemChanged(Object item) {
		// 交由子类实现
		adapter.notifyDataSetChanged();
		// TODO RPC更新
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.topic_config_activity;
	}

	@Override
	public String getHeaderTitle() {
		return "话题设置";
	}
}
