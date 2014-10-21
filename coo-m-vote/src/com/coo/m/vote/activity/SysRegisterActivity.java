package com.coo.m.vote.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.kingstar.ngbf.ms.util.RegexUtil;
import com.kingstar.ngbf.ms.util.StringUtil;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.model.NgbfRuntimeException;
import com.kingstar.ngbf.ms.util.rpc.HttpAsynCaller;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

/**
 * 【注册界面】
 * 
 * @since0.1.0.0
 * @author Bingjue.Sun
 */
public class SysRegisterActivity extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<?>> {

	protected static String TAG = SysRegisterActivity.class.getName();

	// 手机号码
	private EditText et_mobile;
	// 验证码
	private EditText et_verify;
	// 设置密码
	private EditText et_pwd;
	// 设置密码
	private EditText et_pwd2;
	// 同意协议
	private CheckBox cb_agree;
	// 提交按钮
	private Button btn_submit;
	// 发送验证码按钮,验证码将通过【短信】进行发送
	private Button btn_sendcode;

	private TextView tv_clause;
	private ScrollView sv_clause;

	public int getResViewLayoutId() {
		return R.layout.sys_register_activity;
	}

	/**
	 * 初始化view
	 */
	@Override
	public void loadContent() {
		// 注册按钮
		btn_submit = (Button) findViewById(R.id.btn_sys_register_submit);
		btn_submit.setOnClickListener(this);

		// 发送验证码按钮
		btn_sendcode = (Button) findViewById(R.id.btn_sys_register_sendcode);
		btn_sendcode.setOnClickListener(this);

		// 输入框
		et_mobile = (EditText) findViewById(R.id.et_sys_register_mobile);
		et_verify = (EditText) findViewById(R.id.et_sys_register_verify);
		et_pwd = (EditText) findViewById(R.id.et_sys_register_pwd);
		et_pwd2 = (EditText) findViewById(R.id.et_sys_register_pwd2);

		// TODO 读取本机的手机号码，加载到et_mobile中

		// TODO 服务条款:内容
		tv_clause = (TextView) findViewById(R.id.tv_sys_register_clause);
		tv_clause.setOnClickListener(this);
		sv_clause = (ScrollView) findViewById(R.id.sv_sys_register);
		sv_clause.setVisibility(View.INVISIBLE);

		// 是否同意
		cb_agree = (CheckBox) findViewById(R.id.cb_sys_register_agree);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sys_register_sendcode:
			doSendVerifyNo();
			break;
		case R.id.btn_sys_register_submit:
			doRegister();
			break;
		case R.id.tv_sys_register_clause:
			doClause();
			break;
		}
	}

	/**
	 * 显示和隐藏法律条款...
	 */
	private void doClause() {
		showClause = !showClause;
		if (showClause) {
			sv_clause.setVisibility(View.VISIBLE);
		} else {
			sv_clause.setVisibility(View.INVISIBLE);
		}
	}

	private boolean showClause = false;

	/**
	 * 标题
	 */
	public String getHeaderTitle() {
		return "用户注册";
	}

	/**
	 * 发送验证码实现
	 */
	private void doSendVerifyNo() {
		String mobile = et_mobile.getText().toString();
		boolean mobileValid = RegexUtil.isMobile(mobile);
		if (!mobileValid) {
			// 对手机号码严格验证，参见工具类中的正则表达式
			toast("请正确填写手机号，我们将向您发送一条验证码短信");
		} else {
			// 参见AccountRestService.accountVerifyCode
			String params = "/account/sms/mobile/" + mobile;
			String uri = Constants.HOST_REST + params;
			HttpAsynCaller.doGet(uri, null, this);
		}
	}

	private String mobile = "";
	private String password = "";

	/**
	 * 注册提交
	 */
	private void doRegister() {
		try {
			if (!cb_agree.isChecked()) {
				throw new NgbfRuntimeException(
						"请确认是否已经阅读《Vote 服务条款》");
			}

			String sms = et_verify.getText().toString();
			if (StringUtil.isNullOrSpace(sms)) {
				throw new NgbfRuntimeException("请输入验证码!");
			}

			mobile = et_mobile.getText().toString();
			password = et_pwd.getText().toString();
			String password2 = et_pwd2.getText().toString();

			if (StringUtil.isNullOrSpace(password)) {
				throw new NgbfRuntimeException("请设置登录密码!");
			}
			if (!password.equals(password2)) {
				throw new NgbfRuntimeException("您设置的登录密码不正确!");
			}

			// 验证结束,提交请求...
			// 参见AccountRestService.accountRegister
			String params = "/account/register/mobile/" + mobile
					+ "/sms/" + sms + "/password/"
					+ password;
			String uri = Constants.HOST_REST + params;
			// toast(uri);
			HttpAsynCaller.doGet(uri, null, this);

		} catch (NgbfRuntimeException e) {
			toast(e.getMessage());
		}
	}

	@Override
	public void response(SimpleMessage<?> resp) {
		if (resp.getHead().getRep_code().equals(Constants.REP_OK)) {
			// 此Activity会向服务器发送两次请求,返回不同的信息,需要服务器进行api_code的设定
			// M客户端依据api_code分别进行响应
			String apiCode = resp.getHead().getApi_code();
			if (apiCode.equals("account_sms")) {
				toast("已经向您手机发送验证码,请注意查收");
				String sms = resp.getData("sms");
				toast("调试用:验证码是:" + sms);
			} else if (apiCode.equals("account_register")) {
				// 获得服务器端生成的id,作為uuid
				String id = resp.getData("id");
				String type = resp.getData("type");
				// 保存账号信息
				VoteManager.get().saveAccount(id, mobile,
						password, type);
				toast("注册成功");
				// 跳转到主界面
				Intent intent = new Intent(
						SysRegisterActivity.this,
						SysMainActivity.class);
				startActivity(intent);
			} else {
				// do nothing...
			}
		} else {
			toast(resp.getHead().getRep_message());
		}
	}

}
