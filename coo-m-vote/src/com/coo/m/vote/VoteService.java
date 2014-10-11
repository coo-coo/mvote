package com.coo.m.vote;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

@SuppressWarnings("unused")
public class VoteService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private Timer timer = new Timer();
	/**
	 * 实现SQLite和服务器端的MChannel同步，如遇到有新的静态Channel,需要新增行为
	 */
	private TimerTask syncRemoteChannelTask;
	/**
	 * 实现设备通讯录到SQLite的同步
	 */
	private TimerTask syncDeviceContactTask;
	/**
	 * 实现SQLite和服务器端的MContact同步,主要是获得远端指定手机是否有Vote账号等
	 */
	private TimerTask syncRemoteContactTask;
	/**
	 * 实现SQLite和服务器端的MGroup同步
	 */
	private TimerTask syncRemoteGroupTask;
	
	
	
	private TimerTask demoTask;

	@Override
	public void onCreate() {
		// if (demoTask == null) {
		// demoTask = new DemoTask(this);
		// // 5秒
		// timer.schedule(demoTask, 5*1000);
		// }
	}

	/**
	 * TODO 比onCreate好?
	 */
	@Override
	public int onStartCommand(Intent intent, int i, int j) {
		return super.onStartCommand(intent, i, j);
	}
}
