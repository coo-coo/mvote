package com.coo.m.vote.task;

import java.util.List;
import java.util.Map;

import com.coo.m.vote.Constants;
import com.coo.m.vote.VoteService;
import com.coo.m.vote.model.MChannel;
import com.coo.m.vote.model.MManager;
import com.kingstar.ngbf.ms.util.GenericsUtil;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 同步本地MChannel数据到服务器端
 * 
 * @author boqing.shen
 * 
 */
public class MChannelRemoteSyncTask extends CommonTask {

	public MChannelRemoteSyncTask(VoteService service) {
		super(service);
	}

	@Override
	public void execute() {
		// 获取所有的MChannel同步到服务器端
		List<MChannel> list = MManager.findChannelAll();
		NtpMessage sm = new NtpMessage();
		for (MChannel item : list) {
			Map<String, Object> map = GenericsUtil.change2map(item);
			sm.add(map);
		}
		// String host = VoteManager.getStrAccount();
		String host = "13917081673";
		String uri = "/mchannel/sync/";
		sm.set("host", host);
		// 提交到服务器端....
		rpcCaller.doPost(Constants.BIZ_MCHANNEL_SYNC_REMOTE,
				Constants.rest(uri), sm);
	}

}
