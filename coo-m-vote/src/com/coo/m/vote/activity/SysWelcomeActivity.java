package com.coo.m.vote.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coo.m.vote.Constants;
import com.coo.m.vote.Mock;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.coo.s.vote.model.Account;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.rpc2.IRpcCallback;
import com.kingstar.ngbf.ms.util.rpc2.RpcCallHandler;
import com.kingstar.ngbf.ms.util.rpc2.RpcCaller;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 欢迎界面,如果没有登录,则进行登录
 * 
 * @since 0.5.0.0
 * 
 */
public class SysWelcomeActivity extends Activity implements AnimationListener,
		IRpcCallback {

	private static String TAG = SysWelcomeActivity.class.getName();

	// 底部封面
	private ImageView iv_cover;
	// 版本
	private TextView tv_copyright;
	// 设置动画
	private Animation animation = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 全屏
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 设置Title不显示
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置图层
		setContentView(R.layout.sys_welcome_activity);

		// 加载子内容
		loadContent();
	}

	protected void loadContent() {
		Display display = this.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		iv_cover = (ImageView) findViewById(R.id.iv_sys_welcome_cover);
		iv_cover.setAdjustViewBounds(true);
		iv_cover.setScaleType(ImageView.ScaleType.FIT_XY);
		iv_cover.setMaxHeight(size.x);
		iv_cover.setMaxWidth(size.y);
		// 创建一个动画
		animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		animation.setFillEnabled(true);
		animation.setFillAfter(true);
		iv_cover.setAnimation(animation);

		tv_copyright = (TextView) findViewById(R.id.tv_sys_welcome_copyright);
		tv_copyright.setAnimation(animation);

		httpCaller = new RpcCaller(new RpcCallHandler(this));
		// 设置动画监听
		animation.setAnimationListener(this);
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		Log.i(TAG, "onAnimationEnd...");

		Account account = VoteManager.getAccount();
		if (account == null) {
			toast("account IS NULL");
			// 跳转到登录界面
			if (Constants.MOCK_ACCOUNT) {
				VoteManager.get().setAccount(Mock.getAccount());
			}
			// 跳转到登录界面
			Intent intent = new Intent(SysWelcomeActivity.this,
					Constants.LOGIN_CLASS);
			// 调试用，跳转到指定界面
			startActivity(intent);
			// this.overridePendingTransition(R.anim.fadeout,
			// R.anim.fadein);

			this.finish();
		} else {
			// TODO 登录验证，验证成功之后,转向主界面
			// TODO 如果登录失败,跳转到登录界面
			doLogin(account);
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	/**
	 * 用SR存储的信息进行登录，
	 */
	private void doLogin(Account account) {
		String mobile = account.getMobile();
		String password = account.getPassword();
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

	@Override
	@Reference(override = IRpcCallback.class)
	public void onHttpCallback(int what, NtpMessage resp) {
		if (what == Constants.RPC_ACCOUNT_LOGIN) {
			if (VoteUtil.isRespOK(resp)) {
				// 登录成功，证明用户名和密码正确, 不再进行信息的保存
				// 跳转主界面
				Intent intent = new Intent(
						SysWelcomeActivity.this,
						SysMainActivity.class);
				startActivity(intent);
				this.finish();
			} else {
				// 登录不成功，证明用户名和密码不正确, 需要重新登录
				Intent intent = new Intent(
						SysWelcomeActivity.this,
						SysLoginActivity.class);
				startActivity(intent);
				this.finish();
			}
		}
	}

	public void toast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}