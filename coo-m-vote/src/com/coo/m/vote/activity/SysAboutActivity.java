package com.coo.m.vote.activity;

import android.annotation.SuppressLint;
import android.webkit.WebView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.component.InnerWebViewClient;

/**
 * 【关于应用】
 * 
 * @author boqing.shen
 * @since 1.0
 * 
 */
public class SysAboutActivity extends CommonBizActivity {

	@Override
	public String getHeaderTitle() {
		return "关于" + Constants.APP_NAME;
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.sys_about_activity;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void loadContent() {
		WebView webview = (WebView) findViewById(R.id.wv_about);

		webview.getSettings().setJavaScriptEnabled(true);
		// 触摸焦点起作用
		webview.requestFocus();
		webview.loadUrl(Constants.APP_URL);
		// 页面中链接，如果希望点击链接继续在当前browser中响应
		webview.setWebViewClient(new InnerWebViewClient());
		// 取消滚动条
		// webview.setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
	}
}
