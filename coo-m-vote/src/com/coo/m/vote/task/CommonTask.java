package com.coo.m.vote.task;

import java.util.TimerTask;

import com.coo.m.vote.VoteService;

public abstract class CommonTask extends TimerTask {

	protected VoteService service;

	public CommonTask(VoteService service) {
		this.service = service;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		execute();
		// TODO 记录日志
	}

	public abstract void execute();

	protected void toast(String text) {
		service.sendCommonText(text);
	}
}
