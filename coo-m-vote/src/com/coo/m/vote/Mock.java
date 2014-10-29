package com.coo.m.vote;

import java.util.ArrayList;
import java.util.List;

import com.coo.s.vote.model.Account;
import com.coo.s.vote.model.Channel;
import com.coo.s.vote.model.Contact;
import com.coo.s.vote.model.Feedback;
import com.coo.s.vote.model.Topic;
import com.coo.s.vote.model.TopicLeg;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.model.CommonItemOption;
import com.kingstar.ngbf.s.ntp.SimpleMessage;

/**
 * 提供测试数据，供界面调试用
 * 
 * @author boqing.shen
 * 
 */
public class Mock {

	public static List<Channel> CHANNNELS = new ArrayList<Channel>();

	public static List<Contact> CONTACTS = new ArrayList<Contact>();

	static {
		CHANNNELS.add(new Channel("yinyue", "音乐"));
		CHANNNELS.add(new Channel("tiyu", "体育"));
		CHANNNELS.add(new Channel("worldcup_2014", "2014世界杯"));
		CHANNNELS.add(new Channel("keji", "科技"));
		CHANNNELS.add(new Channel("xinwen", "新闻"));
		CHANNNELS.add(new Channel("guoji", "国际"));
		CHANNNELS.add(new Channel("minsheng", "民生"));
		CHANNNELS.add(new Channel("youxi", "游戏"));

		CONTACTS.add(new Contact("13917081671", "SBQ1"));
		CONTACTS.add(new Contact("13917081672", "SBQ2"));
		CONTACTS.add(new Contact("13917081673", "SBQ3"));
		CONTACTS.add(new Contact("13917081674", "SBQ4"));
		CONTACTS.add(new Contact("13917081675", "SBQ5"));
		CONTACTS.add(new Contact("13917081676", "SBQ6"));
		CONTACTS.add(new Contact("13917081677", "SBQ7"));
		CONTACTS.add(new Contact("13917081678", "SBQ8"));
		CONTACTS.add(new Contact("13917081679", "SBQ9"));
	}

	/**
	 * 模拟产生Profile的属性条目对象,用于集中展现
	 * 
	 * @deprecated
	 * @return
	 */
	public static List<CommonItem> getProfileItems() {
		// TODO 向服务端获得个人信息
		List<CommonItem> items = new ArrayList<CommonItem>();
		items.add(new CommonItem("profile.uuid", "UUID", "uuid.value"));
		items.add(new CommonItem("profile.mobile", "手机号", "13917081673"));
		items.add(new CommonItem("profile.nickname", "昵称", "zhangsan")
				.uiType(CommonItem.UIT_TEXT));
		List<CommonItemOption> genders = new ArrayList<CommonItemOption>();
		genders.add(new CommonItemOption("男", "男"));
		genders.add(new CommonItemOption("女", "女"));
		genders.add(new CommonItemOption("未知", "未知"));
		CommonItem ci04 = new CommonItem("profile.gender", "性别", "未知")
				.uiType(CommonItem.UIT_LIST).options(genders);
		items.add(ci04);

		items.add(new CommonItem("profile.address", "住址", "")
				.uiType(CommonItem.UIT_TEXT));

		return items;
	}

	/**
	 * 获得模拟账号
	 * 
	 * @return
	 */
	public static Account getAccount() {
		Account account = new Account();
		account.setMobile("13917081673");
		account.setPassword("111111");
		account.set_id("541155452170e0df13091431");
		account.setType(Account.TYPE_ADMIN);
		return account;
	}

	public static SimpleMessage<Feedback> feedbacks() {
		SimpleMessage<Feedback> resp = new SimpleMessage<Feedback>();
		for (int i = 0; i < 10; i++) {
			Feedback item = new Feedback();
			item.setAppVersion("0." + i);
			item.setNote("note-" + i);
			resp.addRecord(item);
		}
		return resp;
	}

	public static SimpleMessage<Account> accounts() {
		SimpleMessage<Account> resp = new SimpleMessage<Account>();
		for (int i = 0; i < 10; i++) {
			Account item = new Account();
			item.setMobile("139-00000-" + i);
			resp.addRecord(item);
		}
		return resp;
	}

	public static SimpleMessage<Topic> topicshots(String code) {
		SimpleMessage<Topic> resp = new SimpleMessage<Topic>();
		for (int i = 0; i < 30; i++) {
			Topic item = new Topic();
			item.setTitle(code + "-Topic Title-" + i);
			item.setVote(i);
			item.set_tsi(System.currentTimeMillis());
			item.set_id(System.currentTimeMillis() + "-ID");
			TopicLeg tl1 = new TopicLeg("0", "是");
			tl1.setVote(i + 1);
			item.add(tl1);
			TopicLeg tl2 = new TopicLeg("1", "不是");
			tl2.setVote(i + 2);
			item.add(tl2);
			if (i % 3 == 0) {
				TopicLeg tl3 = new TopicLeg("2", "LEG3");
				tl3.setVote(i + 3);
				item.add(tl3);
				TopicLeg tl4 = new TopicLeg("3", "LEG4");
				tl4.setVote(i + 4);
				item.add(tl4);
				// 设置投过票了....
				item.setVoted(true);
			}
			// 设置所有者...
			item.setOwner("13917081673");
			resp.addRecord(item);
		}
		return resp;
	}

	public static SimpleMessage<Channel> channels() {
		SimpleMessage<Channel> resp = new SimpleMessage<Channel>();
		for (Channel record : CHANNNELS) {
			resp.addRecord(record);
		}
		return resp;
	}

}
