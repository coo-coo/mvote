package com.coo.m.vote.task;

import java.util.List;

import com.coo.m.vote.VoteService;
import com.coo.m.vote.model.MChannel;
import com.coo.m.vote.model.MManager;
import com.coo.s.vote.model.Channel;
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

		// 组织消息
		NtpMessage sm = NtpMessage.ok();
		// String host = VoteManager.getStrAccount();
		String host = "13917081673";
		sm.set("host", host);
		for (MChannel item : list) {
			Channel c = new Channel();
			c.setCode(item.getCode());
			// c.setHost(host);
			c.setLabel(item.getLabel());
			// c.setId("" + item.getId());
			sm.add(c);
		}

		String uri = "/mchannel/sync";
		toast(uri + "-" + sm.toJson());
		// 提交到服务器端....
		// service.getRpcCaller().doPost(
		// Constants.BIZ_MCHANNEL_SYNC_REMOTE,
		// Constants.rest(uri), sm);
	}

}
