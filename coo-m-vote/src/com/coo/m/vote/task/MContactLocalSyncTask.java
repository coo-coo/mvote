package com.coo.m.vote.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coo.m.vote.VoteService;
import com.coo.m.vote.model.MContact;
import com.coo.m.vote.model.MManager;
import com.kingstar.ngbf.ms.util.android.component.ContactBean;
import com.kingstar.ngbf.ms.util.android.component.ContactManager;

/**
 * 同步本地通信录信息到MContact数据库
 * 
 * @author boqing.shen
 * 
 */
public class MContactLocalSyncTask extends CommonTask {

	public MContactLocalSyncTask(VoteService service) {
		super(service);
	}

	@Override
	public void execute() {
		// 获取所有数据库信息
		List<MContact> list = MManager.findContactAll();
		Map<String, MContact> map = new HashMap<String, MContact>();
		for (MContact mc : list) {
			map.put(mc.getMobile(), mc);
		}

		// 获取所有通信录信息
		ContactManager cm = new ContactManager(service);
		List<ContactBean> items = cm.findAll(null);
		for (ContactBean cb : items) {
			String mobile = cb.getFirstPhone();
			if (!map.containsKey(mobile)) {
				// 找到新增的行(通讯录新增)，本地数据库存储
				MContact mc = new MContact(cb.getFirstPhone(),
						cb.getName());
				mc.save();
			}
		}
	}

}
