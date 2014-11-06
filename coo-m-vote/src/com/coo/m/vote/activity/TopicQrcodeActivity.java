package com.coo.m.vote.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.MenuItem;
import android.widget.ImageView;

import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.coo.s.vote.model.Topic;
import com.kingstar.ngbf.ms.util.FileUtil;
import com.kingstar.ngbf.ms.util.Reference;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.GenericActivity;

/**
 * 话题二维码
 * 
 * @author boqing.shen
 * 
 */
public class TopicQrcodeActivity extends CommonBizActivity {

	/**
	 * 当前Topic,从 TopicActivity中传递过来
	 */
	private Topic current;

	private ImageView ivIcon;

	@Override
	public void loadContent() {
		// 获得从TopicActivity传递过来的某一个Topic，进行设置
		Intent intent = getIntent();
		current = ((Topic) intent.getSerializableExtra("ITEM"));
		// 根据传递的Topic产生二维码
		// 初始化Icon
		ivIcon = (ImageView) findViewById(R.id.img_topic_qrcode_icon);

		// 加载SDCard上的图片,需要加前缀
		String iconUrl = "file://"
				+ VoteManager.getSdTopicIconPath(current);
		VoteUtil.loadIcon(iconUrl, ivIcon);
	}

	/**
	 * 产生二维码
	 */
	private void generate() {
		// 如果图片存在,则显示,不存在，则生成
		String topicLinkUrl = "http://vote.coo.com/topic/"
				+ current.get_id();
		Bitmap bitmap = VoteUtil.createQrcode(topicLinkUrl);
		if (bitmap != null) {
			// 设置BitMap
			ivIcon.setImageBitmap(bitmap);
			// 存储
			String iconPath = VoteManager
					.getSdTopicIconPath(current);
			FileUtil.saveBitmap(bitmap, iconPath);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_topic_qrcode:
			generate();
			return true;
		case R.id.item_topic_share:
			toast("分享暂未实现");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	@Reference(override = GenericActivity.class)
	public int getResMenuId() {
		return R.menu.topic_qrcode;
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.topic_qrcode_activity;
	}

	@Override
	public String getHeaderTitle() {
		return "二维码";
	}
}
