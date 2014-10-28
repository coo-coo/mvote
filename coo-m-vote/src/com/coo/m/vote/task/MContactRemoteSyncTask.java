package com.coo.m.vote.task;

import java.util.List;
import java.util.Map;

import android.os.Message;

import com.coo.m.vote.Constants;
import com.coo.m.vote.VoteService;
import com.coo.m.vote.model.MContact;
import com.coo.m.vote.model.MManager;
import com.kingstar.ngbf.ms.util.GenericsUtil;
import com.kingstar.ngbf.ms.util.rpc.HttpUtils2;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

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
		SimpleMessage<?> sm = new SimpleMessage<Object>();
		for (MContact item : list) {
			Map<String, Object> map = GenericsUtil.change2map(item);
			sm.add(map);
		}
		// String host = VoteManager.getStrAccount();
		String host = "13917081673";
		String restUrl = Constants.HOST_REST + "/mcontact/all/host/"
				+ host;
		// 提交到服务器端....
		HttpUtils2.doPost(restUrl, sm.toJson());

		Message msg = new Message();
		msg.what = 1001;
		msg.obj = sm.toJson();
		service.getHandler().sendMessage(msg);
	}

}
