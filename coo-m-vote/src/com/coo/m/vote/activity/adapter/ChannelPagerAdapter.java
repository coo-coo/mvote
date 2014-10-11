package com.coo.m.vote.activity.adapter;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;

import com.coo.m.vote.R;
import com.coo.m.vote.activity.SysMainChannel;

/**
 * 主界面
 * 
 * @since0.4.2.0
 * @author Bingjue.Sun
 */
public class ChannelPagerAdapter extends FragmentPagerAdapter {

	private List<SysMainChannel> fragments;
	private FragmentActivity parent;

	/**
	 * 构造函数，指定所有的ChannelPagerFragment
	 * 
	 * @param fm
	 * @param fragments
	 */
	public ChannelPagerAdapter(FragmentActivity parent, FragmentManager fm,
			List<SysMainChannel> fragments) {
		super(fm);
		this.parent = parent;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return (fragments == null || fragments.size() == 0) ? null
				: fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments == null ? 0 : fragments.size();
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// http://www.myexception.cn/android/1672250.html
		String title = ((SysMainChannel) getItem(position))
				.getLabel();
		// return title;
		// 前后补空格,增加下坠的宽度
		SpannableStringBuilder ssb = new SpannableStringBuilder("  "
				+ title + "        ");
		// 重载更改标题颜色 Channel的Icon
		Drawable logo = parent.getResources().getDrawable(
				R.drawable.ico_heart);
		// logo.setBounds(0, 0, logo.getIntrinsicWidth(),
		// logo.getIntrinsicHeight());
		logo.setBounds(0, 0, 16, 16);
		// 设置图片Span等...
		ImageSpan span = new ImageSpan(logo, ImageSpan.ALIGN_BASELINE);
		ssb.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new RelativeSizeSpan(1.2f), 1, ssb.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ssb;
	}
}
