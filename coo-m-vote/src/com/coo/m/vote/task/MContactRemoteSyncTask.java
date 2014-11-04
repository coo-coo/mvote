package com.coo.m.vote.task;

import java.util.List;
import java.util.Map;

import com.coo.m.vote.VoteService;
import com.coo.m.vote.model.MContact;
import com.coo.m.vote.model.MManager;
import com.kingstar.ngbf.ms.util.GenericsUtil;
import com.kingstar.ngbf.s.ntp.NtpMessage;

/**
 * 同步本地MChannel数据到服务器端
 * 
 * @author boqing.shen
 * 
 */
public class MContactRemoteSyncTask extends CommonTask {

	public MContactRemoteSyncTask(VoteService service) {
		super(service);
	}

	@Override
	public void execute() {
		// 获取所有的MChannel同步到服务器端
		List<MContact> list = MManager.findContactAll();
		NtpMessage sm = new NtpMessage();
		for (MContact item : list) {
			Map<String, Object> map = GenericsUtil.change2map(item);
			sm.add(map);
		}
		// String host = VoteManager.getStrAccount();
		String host = "13917081673";
		sm.set("host", host);
//		String uri = "/mcontact/sync";
		// 提交到服务器端....
//		rpcCaller.doPost(Constants.BIZ_MCONTACT_SYNC_REMOTE,
//				Constants.rest(uri), sm);
	}
}
