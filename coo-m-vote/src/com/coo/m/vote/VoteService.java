package com.coo.m.vote;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.coo.m.vote.task.MChannelRemoteSyncTask;
import com.coo.m.vote.task.MContactLocalSyncTask;
import com.coo.m.vote.task.MContactRemoteSyncTask;

@SuppressWarnings("unused")
public class VoteService extends Service {

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
	private VoteServiceHandler handler = null;

	@Override
	public void onCreate() {
		toast("vote Service onCreate....");
		handler = new VoteServiceHandler(this);
		// 初始化所有的Task
		initTasks();
		// 立即执行,每隔5秒
		timer.schedule(mChannelRemoteSyncTask, 0, 10 * 1000);
	}

	private void initTasks() {
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

	public VoteServiceHandler getHandler() {
		return handler;
	}

	public void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
