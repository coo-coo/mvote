package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coo.m.vote.R;
import com.coo.s.vote.model.TopicLeg;
import com.kingstar.ngbf.ms.util.android.CommonTextCacheListener;

/**
 * Topic新建时后Leg的填充器 参见:http://blog.csdn.net/nairuohe/article/details/6457300
 * 
 */
public class TopicLegCreateAdapter extends ArrayAdapter<TopicLeg> {

	private final Context context;

	public TopicLegCreateAdapter(Context context, List<TopicLeg> legs) {
		super(context, R.layout.topic_create_leg_row, legs);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 当前绘画的TopicLeg对象
		TopicLeg leg = this.getItem(position);
		// 获取填充服务
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 填充一行
		View row = inflater.inflate(R.layout.topic_create_leg_row,
				parent, false);

		// 设置序号View
		TextView tv_index = (TextView) row
				.findViewById(R.id.tv_topic_create_leg_index);
		tv_index.setText(position + ". ");
		// 更新Leg中的seq值
		leg.setSeq(""+position);

		// 设置Leg输入框
		EditText et_title = (EditText) row
				.findViewById(R.id.et_topic_create_leg_content);
		et_title.setText(leg.getTitle());
		// 增加缓存,否则会重绘掉....
		et_title.addTextChangedListener(new LegCacheListener(leg));

		// 设置Leg删除按钮
		Button btn_delete = (Button) row
				.findViewById(R.id.btn_topic_create_leg_delete);
		btn_delete.setOnClickListener(new LegDeletionListener(this, leg));
		return row;
	}
}

/**
 * 删除一个Leg的监听者
 * 
 */
class LegDeletionListener implements View.OnClickListener {

	/**
	 * 要删除的Leg
	 */
	private TopicLeg leg;

	/**
	 * 该Adapter
	 */
	private TopicLegCreateAdapter adapter;

	public LegDeletionListener(TopicLegCreateAdapter adapter,
			TopicLeg leg) {
		this.leg = leg;
		this.adapter = adapter;
	}

	@Override
	public void onClick(View v) {
		// 删除执行
		adapter.remove(leg);
	}
}

class LegCacheListener extends CommonTextCacheListener {

	private TopicLeg leg;

	LegCacheListener(TopicLeg leg) {
		this.leg = leg;
	}

	@Override
	public void onTextChanged(String text) {
		// Leg的信息 以起到缓存的作用
		leg.setTitle(text);
	}

}