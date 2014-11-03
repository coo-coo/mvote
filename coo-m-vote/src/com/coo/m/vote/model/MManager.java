package com.coo.m.vote.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.litepal.crud.DataSupport;

import com.coo.s.vote.model.Account;
import com.coo.s.vote.model.Channel;
import com.kingstar.ngbf.ms.util.RegexUtil;
import com.kingstar.ngbf.ms.util.android.component.ContactBean;

/**
 * M端数据需要[原封不动]的同步到服务端,以防止应用APP删除之后的重装,进行数据恢复 M端数据只和host(Account)有关
 * 
 * @author boqing.shen
 * 
 */
public final class MManager {

	/**
	 * 获得所有关注的频道,如遇到新增加的频道（交由同步来实现）
	 */
	public static List<MGroup> findGroupAll() {
		return DataSupport.findAll(MGroup.class);
	}

	public static MGroup findGroup(long id) {
		return DataSupport.find(MGroup.class, id);
	}

	/**
	 * 获得所有关注的频道,如遇到新增加的频道（交由同步来实现）
	 */
	public static List<MChannel> findChannelAll() {
		return DataSupport.findAll(MChannel.class);
	}

	public static void clearGroups() {
		DataSupport.deleteAll(MGroup.class, "");
	}

	public static void clearChannels() {
		DataSupport.deleteAll(MChannel.class, "");
	}

	public static void clearContacts() {
		DataSupport.deleteAll(MContact.class, "");
	}

	/**
	 * 更新focus
	 */
	public static int updateChannelFocus(int id, int status) {
		MChannel mc = DataSupport.find(MChannel.class, id);
		mc.setStatus(status);
		return mc.update(id);
	}

	/**
	 * 获得服务器端的所有系统Channel,进行M端SQLite同步 TODO Focus状态同步??
	 */
	public static void syncChannel(List<Channel> channels, String account) {
		// 获得本地的全部MChannel列表，转化为Map对象
		List<MChannel> mChannels = findChannelAll();
		Map<String, MChannel> map = new HashMap<String, MChannel>();
		for (MChannel mc : mChannels) {
			map.put(mc.getCode(), mc);
		}
		// 进行服务器端的channel和本地channel的同步
		for (Channel channel : channels) {
			if (!map.containsKey(channel.getCode())) {
				// 如果M不存在，则为服务器新增频道，进行本地添加
				MChannel mc = new MChannel();
				mc.setCode(channel.getCode());
				mc.setLabel(channel.getLabel());
				mc.setHost(account);
				// 本地保存
				mc.save();
			} else {
				// TODO 暂时不处理
			}
		}
	}

	/**
	 * 获得M端所有的Contact信息
	 * 
	 * @return
	 */
	public static List<MContact> findContactAll() {
		return DataSupport.findAll(MContact.class);
	}

	/**
	 * 获得手机通讯录的信息,进行M端SQLite同步 TODO Focus状态同步??
	 */
	public static void syncDeviceContact(List<ContactBean> contacts,
			String account) {
		// 获得本地的全部MChannel列表，转化为Map对象
		List<MContact> mContacts = findContactAll();
		Map<String, MContact> map = new HashMap<String, MContact>();
		for (MContact mc : mContacts) {
			map.put(mc.getMobile(), mc);
		}

		// 手机通讯录 & SQLite之间 同步
		for (ContactBean cb : contacts) {
			String phone = cb.getFirstPhone();
			boolean isMobile = RegexUtil.isMobile(phone);
			// 只有手机才能参与本地通讯录的存储
			if (isMobile && !map.containsKey(phone)) {
				// 如果M不存在，则为服务器新增，进行本地添加
				MContact mc = new MContact();
				mc.setName(cb.getName());
				mc.setMobile(phone);
				mc.setHost(account);
				// 本地保存
				mc.save();
			} else {
				// TODO 暂时不处理
			}
		}
	}

	/**
	 * 实现由服务器端获得的Account账号,来同步本地MContact是有账号,微信号等
	 */
	public static void syncRemoteAccount(List<Account> accounts, String host) {
		// 获得本地的全部MChannel列表，转化为Map对象
		List<MContact> mContacts = findContactAll();
		Map<String, MContact> map = new HashMap<String, MContact>();
		for (MContact mc : mContacts) {
			map.put(mc.getMobile(), mc);
		}

		// 手机通讯录 & SQLite之间 同步
		for (Account account : accounts) {
			String mobile = account.getAccount();
			// 只有手机才能参与本地通讯录的存储
			if (map.containsKey(mobile)) {
				// 如果M存在，此Mobile已经注册了账号
				MContact mc = map.get(mobile);
				// 更新执行....
				mc.update(mc.getId());
			} else {
				// TODO 暂时不处理
			}
		}
	}
}
