package com.coo.m.vote.activity;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.coo.m.vote.activity.view.CommonOptionDialog;
import com.coo.m.vote.activity.view.ItemAdapter;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.CommonBizOptions;
import com.kingstar.ngbf.ms.util.android.view.CommonItemPasswordDialog;
import com.kingstar.ngbf.ms.util.android.view.CommonItemTextDialog;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.update.IconSettingManager;
import com.kingstar.ngbf.ms.util.update.IconSettingOptions;
import com.kingstar.ngbf.s.ntp.NtpMessage;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 【个人信息】，信息本地存储，网络备份
 * 
 * @since 1.0
 * @author boqing.shen
 * 
 */
public class ProfileActivity extends CommonBizActivity {

	private ImageView ivIcon = null;

	// 图片设置管理器
	private IconSettingManager iconSettingManager = null;

	@Override
	public void loadContent() {
		// 定义控件 & 定义适配器
		if (Constants.MOCK_DATA) {
			ListView listView = (ListView) findViewById(R.id.lv_sys_profile);
			// TODO 需要数据的服务器端Merge处理...
			adapter = new ItemAdapter(this,
					VoteManager.getProfileSkeletonItems(),
					listView);
		} else {
			// 异步调用数据
			String account = VoteManager.getStrAccount();
			String uri = "/account/info/mobile/" + account;
			toast(uri);
			httpCaller.doGet(Constants.RPC_ACCOUNT_INFO,
					Constants.rest(uri));
		}

		// 初始化Icon
		ivIcon = (ImageView) findViewById(R.id.iv_sys_profile_icon);
		String iconPath = VoteManager.getSdProfileIconPath();
		// 加载SDCard上的图片,需要加前缀
		String iconUrl = "file://" + iconPath;
		ImageLoader.getInstance().displayImage(iconUrl, ivIcon,
				VoteUtil.imageLoadOptions());
		ivIcon.setOnClickListener(this);

		
		// 图片设置管理器
		IconSettingOptions options = IconSettingOptions.blank()
				.icon(ivIcon).store(true).storePath(iconPath);
		iconSettingManager = new IconSettingManager(this, options);
	}

	
	@Override
	@Reference(override = CommonBizActivity.class)
	public CommonBizOptions getOptions() {
		return CommonBizOptions.blank().headerTitle("个人信息")
				.resViewLayoutId(R.layout.sys_profile_activity);
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
			switch (uiType) {
			case CommonItem.UIT_TEXT:
				new CommonItemTextDialog(this, item).show();
				break;
			case CommonItem.UIT_PASSWORD:
				// 显示文本，修改对话框
				new CommonItemPasswordDialog(this, item).show();

				break;
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
		if (item instanceof CommonItem) {
			CommonItem ci = (CommonItem) item;
			String id = VoteManager.getAccount().get_id();
			// toast(id + "-" + ci.getCode() + "-" + ci.getValue());
			NtpMessage nm = new NtpMessage();
			nm.set("_id", id);
			nm.set("key", ci.getCode());
			// TODO 暂定都是字符串
			nm.set("value", ci.getValue().toString());
			// 发送更新请求...
			String uri = "/account/update/param";
			// 修改信息，參見topicUpdate
			httpCaller.doPost(Constants.RPC_ACCOUNT_UPDATE_PARAM,
					Constants.rest(uri), nm);
		}
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.RPC_ACCOUNT_UPDATE_PARAM) {
			toast("更新属性成功....");
		} else if (what == Constants.RPC_ACCOUNT_INFO) {
			ListView listView = (ListView) findViewById(R.id.lv_sys_profile);
			// 获得服务器的值，有些Key是没有设定的，则为空，空表示服务器没有
			List<CommonItem> items = VoteManager
					.getProfileSkeletonItems();
			for (CommonItem ci : items) {
				// 通过code，获得Mongo条目信息的属性值
				Object value = resp.get(ci.getCode());
				if (value != null) {
					// TODO 暂定都是字符串
					ci.setValue(value.toString());
				}
			}
			adapter = new ItemAdapter(this, items, listView);
		} else {

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_sys_profile_icon:
			// 图片设置....
			iconSettingManager.doStart();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		// 针对iconSettingManager.doStart()的反馈结果
		iconSettingManager.onActivityResult(requestCode, resultCode,
				data);
		super.onActivityResult(requestCode, resultCode, data);
	}
}
