package com.coo.m.vote.activity;

import java.util.List;

import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.coo.m.vote.activity.adapter.SysProfilePropertyAdapter;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.rpc.HttpAsynCaller;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

public class SysProfileActivity extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<?>> {

	@Override
	public String getHeaderTitle() {
		return "个人信息";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.sys_profile_activity;
	}

	/**
	 * 条目适配器
	 */
	private SysProfilePropertyAdapter adapter;

	@Override
	public void onAbsViewItemChanged(Object item) {
		// 交由子类实现
		adapter.notifyDataSetChanged();
		if (item instanceof CommonItem) {
			CommonItem ci = (CommonItem) item;
			String id = VoteManager.getAccount().get_id();
			// toast(id + "-" + ci.getCode() + "-" + ci.getValue());

			// 发送更新请求...
			String uri = Constants.SERVERHOST
					+ "/profile/update/id/" + id
					+ "/param/" + ci.getCode() + "/value/"
					+ ci.getValue() + "/type/0";
			toast("uri=" + uri);
			// TODO 中文？GET?
			// 修改信息，參見topicUpdate
			HttpAsynCaller.doGet(uri, null, this);
		}
	}

	@Override
	public void loadContent() {
		// 定义控件 & 定义适配器
		if (Constants.MOCK_DATA) {
			ListView listView = (ListView) findViewById(R.id.lv_sys_profile);
			
			// TODO 需要数据的服务器端Merge处理...
			adapter = new SysProfilePropertyAdapter(
					VoteManager.getProfileSkeletonItems(), listView);
			adapter.initContext(this);
		} else {
			// 异步调用数据
			String account = VoteManager.getStrAccount();
			String uri = Constants.SERVERHOST
					+ "/profile/get/account/" + account;
			toast("uri=" + uri);
			HttpAsynCaller.doGet(uri, null, this);
		}
	}

	@Override
	public void response(SimpleMessage<?> resp) {
		if (VoteUtil.isRespOK(resp)) {
			// 获得业务请求代码
			String api_code = resp.getHead().getApi_code();
			if (api_code.equals("profile_get")) {
				responseProfileGet(resp);
			} else if (api_code.equals("profile_update")) {
				toast("更新属性成功....");
			} else {

			}
		} else {
			toast(Constants.MSG_RPC_ERROR);
		}
	}

	/**
	 * 
	 * @param resp
	 */
	private void responseProfileGet(SimpleMessage<?> resp) {
		ListView listView = (ListView) findViewById(R.id.lv_sys_profile);
		// 获得服务器的值，有些Key是没有设定的，则为空，空表示服务器没有
		List<CommonItem> items = VoteManager.getProfileSkeletonItems();
		for (CommonItem ci : items) {
			// 通过code，获得Mongo条目信息的属性值
			String value = resp.getData(ci.getCode());
			if (value != null) {
				// TODO 暂定都是字符串
				ci.setValue(value);
			}
		}
		adapter = new SysProfilePropertyAdapter(items, listView);
		adapter.initContext(this);
	}

}
