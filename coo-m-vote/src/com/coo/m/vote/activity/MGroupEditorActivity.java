package com.coo.m.vote.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.MContactSelectAdapter;
import com.coo.m.vote.model.MContact;
import com.coo.m.vote.model.MGroup;
import com.coo.m.vote.model.MManager;
import com.kingstar.ngbf.ms.util.StringUtil;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;

/**
 * 群组管理
 */
public class MGroupEditorActivity extends CommonBizActivity {

	private MContactSelectAdapter adapter;

	private EditText et_name = null;
	
	@Override
	public String getHeaderTitle() {
		return "编辑群组";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.group_editor_activity;
	}

	@Override
	public void onAdapterItemClicked(Object item) {
		adapter.notifyDataSetChanged();
	}

	// 从前面传过来,如果是null,则表示是新增,否则表示为修改
	private MGroup group;
	
	@Override
	public void loadContent() {
		// TODO 列表显示: 组1 (15) 组2 (26) 点击，进入GroupEditorActivity
		Intent intent = this.getIntent();

		// 获得传递过来的MGroup对象,有可能是null，则为新增模式
		group = ((MGroup) intent.getSerializableExtra("ITEM"));

		// 获取全部|组内的MContact信息
		List<MContact> list = MManager.findContactAll();
		if (group != null) {
			// 根据组获得组对象
			Map<String, String> map = stringToMap(group
					.getAccounts0());
			for (MContact mc : list) {
				if (map.containsKey(mc.getMobile())) {
					mc.setSelected(true);
				}
			}
		}
		ListView lv_contacts = (ListView) findViewById(R.id.lv_group_create);
		adapter = new MContactSelectAdapter(this,list, lv_contacts);

		
		et_name = (EditText) findViewById(R.id.et_group_create_name);
		if(group!=null){
			et_name.setEnabled(false);
			et_name.setText(group.getName());
		}
		else{
			et_name.setEnabled(true);
			et_name.setText("");
		}
	}
	
	/**
	 * 将a,b,c转化为Map
	 */
	private Map<String,String> stringToMap(String source) {
		StringTokenizer strcStr = new StringTokenizer(source, ",");
		int num = strcStr.countTokens();
		Map<String,String> map = new HashMap<String,String>();
		for (int i = 0; i < num; i++) {
			String s = strcStr.nextToken();
			map.put(s, s);
		}
		return map;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.group_create, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_group_save:
			doSave();
			break;
		case R.id.item_group_back:
			doBack();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 返回
	 */
	private void doBack() {
		this.handleNext(MGroupActivity.class);
	}

	/**
	 * 保存
	 */
	private void doSave() {
		String name = et_name.getText().toString();
		if (StringUtil.isNullOrSpace(name)) {
			toast("群组名称不能为空...");
			return;
		}

		List<MContact> items = adapter.getItems();
		StringBuffer sb = new StringBuffer();
		for (MContact mc : items) {
			if (mc.isSelected()) {
				sb.append(mc.getMobile() + ",");
			}
		}
		if(group!=null){
			toast("更新模式: " + group.getId());
			group.setAccounts0(sb.toString());
			group.update(group.getId());
		}
		else{
			MGroup groupNew = new MGroup(name, sb.toString());
			groupNew.save();
		}
		// 保存成功返回
		doBack();
	}
}
