package com.coo.m.vote;

import java.util.Date;

import android.text.InputType;

import com.kingstar.ngbf.ms.util.DateUtil;
import com.kingstar.ngbf.s.ntp.SimpleMessage;
import com.kingstar.ngbf.s.ntp.SimpleMessageHead;

public final class VoteUtil {

	/**
	 * 返回TS的日期表达式
	 */
	public static String getTsDateText(long ts) {
		return DateUtil.format(new Date(ts), "yyyy-MM-dd");
	}

	/**
	 * 返回TS的时间表达式
	 */
	public static String getTsText(long ts) {
		return DateUtil.format(new Date(ts), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 判断返回信息是否OK
	 */
	public static boolean isRespOK(SimpleMessage<?> resp) {
		boolean tof = resp.getHead().getRep_code()
				.equals(SimpleMessageHead.REP_OK);
		return tof;
	}
	
	public static int getPwdInputType(){
		return (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
}
