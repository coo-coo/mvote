package com.coo.m.vote.activity;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.MGroupAdapter;
import com.coo.m.vote.activity.view.MGroupCommandDialog;
import com.coo.m.vote.model.MGroup;
import com.coo.m.vote.model.MManager;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.model.CommonItem;

/**
 * 【群組管理】,暂不发布
 * 
 * @since 1.0
 * @author boqing.shen
 */
public class MGroupActivity extends CommonBizActivity {

	@Override
	public String getHeaderTitle() {
		return "群组管理";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.mgroup_activity;
	}

	@Override
	public int getResMenuId() {
		return R.menu.group;
	}

	
	private MGroupAdapter adapter;
	
	private Dialog commandDlg;
	
	@Override
	public void loadContent() {
		// TODO 列表显示: 组1 (15) 组2 (26) 点击，进入GroupEditActivity
		// 从SQLLite中获取
		List<MGroup> list = MManager.findGroupAll();
		ListView lv_group = (ListView) findViewById(R.id.lv_group_activity);
		adapter = new MGroupAdapter(this, list, lv_group);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_group_create:
			Intent intent = new Intent(this,
					MGroupEditorActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("ITEM", null);
			intent.putExtras(bundle);

			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 监听Topic的长嗯响应
	 */
	@Override
	public void onAdapterItemClickedLong(Object item) {
		MGroup group = (MGroup) item;
		commandDlg = new MGroupCommandDialog(this, group);
		commandDlg.show();
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
				onCommandAction(ci);
				break;
			case CommonItem.UIT_DIALOG_CANCEL:
				closeCommandDialog();
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * 执行命令
	 */
	private void onCommandAction(CommonItem ci){
		String code = ci.getCode();
		// 业务参见MGroupCommandDialog
		if(code.equals("group.delete")){
			MGroup group = (MGroup)ci.getValue();
			// 删除数据库模型
			group.delete();
			// 删除Adapter中模型
			adapter.remove(group);
			adapter.notifyDataSetChanged();
			closeCommandDialog();
		}
	}
	
	private void closeCommandDialog(){
		if(commandDlg!=null){
			commandDlg.cancel();
		}
	}
}
