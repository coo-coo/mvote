package com.coo.m.vote;

import android.os.Handler;
import android.os.Message;

public class VoteServiceHandler extends Handler {

	private VoteService service;

	public VoteServiceHandler(VoteService service) {
		this.service = service;
	}

	@Override
	public void handleMessage(Message msg) {
		int what = msg.what;
//		service.toast(what);
		switch (what) {
		case 1000:
			service.toast(""+msg.obj);
			break;
		}
	}
}
