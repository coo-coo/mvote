package com.coo.m.vote.model;

import java.util.TimerTask;

import android.content.Context;
import android.widget.Toast;

public class DemoTask extends TimerTask {

	private Context context;

	public DemoTask(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		toast("DemoTask-" + System.currentTimeMillis());
	}

	private void toast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
