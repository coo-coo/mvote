package com.coo.m.vote;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.coo.m.vote.task.MChannelRemoteSyncTask;
import com.coo.m.vote.task.MContactLocalSyncTask;
import com.coo.m.vote.task.MContactRemoteSyncTask;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.rpc2.IRpcCallback;
import com.kingstar.ngbf.ms.util.rpc2.RpcCallHandler;
import com.kingstar.ngbf.ms.util.rpc2.RpcCaller;
import com.kingstar.ngbf.s.ntp.NtpMessage;

@SuppressWarnings("unused")
public class VoteService extends Service implements IRpcCallback {

	private Timer timer = new Timer();
	/**
	 * 实现SQLite和服务器端的MChannel同步，如遇到有新的静态Channel,需要新增行为
	 */
	private TimerTask mChannelRemoteSyncTask;
	/**
	 * 实现设备通讯录到SQLite的同步
	 */
	private TimerTask mContactLocalSyncTask;
	/**
	 * 实现SQLite和服务器端的MContact同步,主要是获得远端指定手机是否有Vote账号等
	 */
	private TimerTask mContactRemoteSyncTask;
	/**
	 * 实现SQLite和服务器端的MGroup同步
	 */
	private TimerTask mGroupRemoteSyncTask;

	/**
	 * 内置Handler
	 */
	private CommonHandler commonHandler = null;

	protected RpcCaller rpcCaller;

	@Override
	public void onCreate() {
		toast("vote Service onCreate....");
		// 初始化所有的Task以及相关参数..
		initTasks();
		// 立即执行,每隔5秒
		timer.schedule(mChannelRemoteSyncTask, 0, 5 * 1000);
	}

	private void initTasks() {
		commonHandler = new CommonHandler(this);
		rpcCaller = new RpcCaller(new RpcCallHandler(this));

		if (mChannelRemoteSyncTask == null) {
			mChannelRemoteSyncTask = new MChannelRemoteSyncTask(
					this);
		}
		if (mContactLocalSyncTask == null) {
			mContactLocalSyncTask = new MContactLocalSyncTask(this);
		}
		if (mContactRemoteSyncTask == null) {
			mContactRemoteSyncTask = new MContactRemoteSyncTask(
					this);
		}
	}
	
	public RpcCaller getRpcCaller() {
		return rpcCaller;
	}

	/**
	 * 发送简单消息,供Toast调试用
	 */
	public void sendCommonText(String text){
		Message msg = VoteUtil.buildMessage(1000, text);
		commonHandler.sendMessage(msg);
	}
	
	/**
	 * 显示消息....
	 */
	public void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 异步调用请求反馈
	 */
	@Override
	@Reference(override = IRpcCallback.class)
	public void onHttpCallback(int what, NtpMessage nm) {
		String text = "what=" + what + "-" + nm.toJson();
		toast(text);
	}

	/**
	 * TODO 比onCreate好?
	 */
	@Override
	public int onStartCommand(Intent intent, int i, int j) {
		return super.onStartCommand(intent, i, j);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}


/**
 * 采用Handler进行简单的消息发送和Toast
 * @author boqing.shen
 *
 */
class CommonHandler extends Handler {

	private VoteService service;

	public CommonHandler(VoteService service) {
		this.service = service;
	}

	@Override
	public void handleMessage(Message msg) {
		service.toast(msg.obj.toString());
	}
}
