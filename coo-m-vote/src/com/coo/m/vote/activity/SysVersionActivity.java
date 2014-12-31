package com.coo.m.vote.activity;

import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.activity.view.ItemAdapter;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.CommonBizOptions;
import com.kingstar.ngbf.ms.util.android.res.ServiceProvider;
import com.kingstar.ngbf.ms.util.model.CommonItem;


/**
 * [框架] 版本信息
 * 
 * @author boqing.shen
 * @since 1.3
 */
public class SysVersionActivity extends CommonBizActivity {

	@Override
	@Reference(override = CommonBizActivity.class)
	public CommonBizOptions getOptions() {
		return CommonBizOptions.blank().headerTitle("版本信息")
				.resViewLayoutId(R.layout.sys_blank_activity);
	}

	@Override
	@Reference(override = CommonBizActivity.class)
	public void loadContent() {
		// 代码构建ListView,不进行Find,需指定attr,参见styes.xml
		ListView listView = new ListView(this, null,
				R.attr.ref_common_lv);
		this.setContentView(listView);

		// 定义适配器
		adapter = new ItemAdapter(this, getItems(), listView);
	}

	/**
	 * 属性条目对象
	 */
	private List<CommonItem> getItems() {
		String vcurrent = ServiceProvider.getAppVersionName(this);
		List<CommonItem> items = new ArrayList<CommonItem>();
		items.add(new CommonItem("version.app", "应用名称",
				Constants.APP_NAME));
		items.add(new CommonItem("version.current", "当前版本", vcurrent));
		items.add(new CommonItem("version.author", "开发作者",
				"shenboqing@163.com"));
		return items;
	}

}
