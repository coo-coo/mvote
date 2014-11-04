package com.coo.m.vote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.coo.s.vote.model.Account;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.RegexUtil;
import com.kingstar.ngbf.ms.util.android.GenericActivity;
import com.kingstar.ngbf.ms.util.rpc2.IRpcCallback;
import com.kingstar.ngbf.ms.util.rpc2.RpcCallHandler;
import com.kingstar.ngbf.ms.util.rpc2.RpcCaller;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 登陆界面activity
 * 
 * @author Bingjue.Sun
 */
public class SysLoginActivity extends GenericActivity implements
		OnClickListener, IRpcCallback {

	@SuppressWarnings("unused")
	private static String TAG = SysLoginActivity.class.getName();

	// 登陆按钮
	private Button btn_login;
	// 登录测试
	private Button btn_login_test;
	// 注册按钮
	private TextView tv_login_regist;
	// 用户名
	private EditText et_account;
	// 用户名密码
	private EditText et_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sys_login_activity);

		// 加载子内容
		loadContent();
	}

	@Override
	public void loadContent() {

		et_account = (EditText) findViewById(R.id.et_sys_login_account);
		et_pwd = (EditText) findViewById(R.id.et_sys_login_pwd);

		// 获得可能已登录存储的账号信息
		Account account = VoteManager.getAccount();
		if (account != null) {
			et_account.setText(account.getMobile());
		}
		// 测试用
		et_account.setText("13917081673");
		et_pwd.setText("222222");

		btn_login = (Button) findViewById(R.id.btn_sys_login_login);
		tv_login_regist = (TextView) findViewById(R.id.tv_sys_login_register);
		btn_login_test = (Button) findViewById(R.id.btn_sys_login_test);

		// 添加事件响应
		tv_login_regist.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		btn_login_test.setOnClickListener(this);

		httpCaller = new RpcCaller(new RpcCallHandler(this));
		// FancyButton fb =
		// (FancyButton)findViewById(R.id.fb_sys_login_icon);
		// fb.setIconResource(R.drawable.ic_launcher);
		// fb.setRadius(30);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sys_login_login:
			// 依据手机账号/密码登录
			doLogin();
			break;
		case R.id.tv_sys_login_register:
			// 跳转注册
			Intent intent = new Intent(SysLoginActivity.this,
					SysRegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_sys_login_test:
			// 模拟登录
			VoteManager.get().setAccount(Mock.getAccount());
			Intent intent3 = new Intent(SysLoginActivity.this,
					SysMainActivity.class);
			startActivity(intent3);
			break;
		}
	}

	/**
	 * 登录实现
	 */
	private void doLogin() {
		mobile = et_account.getText().toString();
		password = et_pwd.getText().toString();

		boolean mobileValid = RegexUtil.isMobile(mobile);
		if (!mobileValid) {
			this.toast("登录账号不是手机号码!");
			return;
		}
		// 参见AccountRestService.accountLogin
		String uri = "/account/login/mobile/" + mobile + "/password/"
				+ password;
		toast(uri);
		// 同步調用不可以,需要异步调用
		httpCaller.doGet(Constants.RPC_ACCOUNT_LOGIN,
				Constants.rest(uri));
	}

	// 异步调用
	private RpcCaller httpCaller;

	// 手机号
	private String mobile = "";
	// 密码
	private String password = "";

	@Override
	@Reference(override = IRpcCallback.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.RPC_ACCOUNT_LOGIN) {
			if (VoteUtil.isRespOK(resp)) {
				// 登录成功
				toast("登录成功");
				String id = (String) resp.get("id");
				String type = (String) resp.get("type");
				// 再次保存账号信息,可能前面因APP删除SR不存在用户信息
				VoteManager.get().saveAccount(id, mobile,
						password, type);
				// 跳转注册
				Intent intent = new Intent(
						SysLoginActivity.this,
						SysMainActivity.class);
				startActivity(intent);
			} else {
				toast("用户名或密码不正确!");
			}
		}
	}

}
