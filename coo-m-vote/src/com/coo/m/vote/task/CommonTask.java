package com.coo.m.vote.task;

import java.util.TimerTask;

import com.coo.m.vote.VoteService;
import com.kingstar.ngbf.ms.util.rpc2.IRpcCallback;
import com.kingstar.ngbf.ms.util.rpc2.RpcCallHandler;
import com.kingstar.ngbf.ms.util.rpc2.RpcCaller;
import com.kingstar.ngbf.s.ntp.NtpMessage;

public abstract class CommonTask extends TimerTask implements IRpcCallback{
	
	protected VoteService service;
	
	protected RpcCaller rpcCaller;
	
	public CommonTask(VoteService service) {
		this.service = service;
		rpcCaller = new RpcCaller(new RpcCallHandler(this));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub	
		execute();
		// TODO 记录日志
	}
	
	public abstract void execute();
	
	/**
	 * 异步调用请求反馈
	 */
	public void onHttpCallback(int what, NtpMessage sm) {
		
	}
}
