package com.coo.m.vote.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;

/**
 * 【系统设置】
 * 
 * @author Qiaoqiao.Li
 */
public class SysConfigActivity extends CommonBizActivity {

	// 退出监听
	private Button btn_exit = null;
	private Button btn_private = null;
	private Button btn_version = null;

	public int getResViewLayoutId() {
		return R.layout.sys_config_activity;
	}

	/**
	 * 初始化组件
	 */
	public void loadContent() {
		btn_exit = (Button) findViewById(R.id.btn_sys_config_exit);
		btn_exit.setOnClickListener(this);
		btn_version = (Button) findViewById(R.id.btn_sys_config_version);
		btn_version.setOnClickListener(this);
		btn_private = (Button) findViewById(R.id.btn_sys_config_private);
		btn_private.setOnClickListener(this);
	}

	@Override
	public String getHeaderTitle() {
		return "系统设置";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sys_config_exit:
			doExit();
			break;
		case R.id.btn_sys_config_version:
			doJump(SysVersionActivity.class);
			break;
		case R.id.btn_sys_config_private:
			toast("暂未实现...");
			break;
		}
	}

	/**
	 * 系统退出
	 * 
	 * @since 0.4.2.0
	 */
	private void doExit() {
		VoteManager.get().clearAccount();
		// 跳转到登录界面....
		Intent intent = new Intent(SysConfigActivity.this,
				SysLoginActivity.class);
		startActivity(intent);
	}

	private void doJump(Class<?> next) {
		// 跳转到登录界面....
		Intent intent = new Intent(SysConfigActivity.this, next);
		startActivity(intent);
	}

}
