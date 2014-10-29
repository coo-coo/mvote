package com.coo.m.vote;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Message;
import android.text.InputType;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.kingstar.ngbf.ms.util.DateUtil;
import com.kingstar.ngbf.s.ntp.SimpleMessage;
import com.kingstar.ngbf.s.ntp.SimpleMessageHead;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public final class VoteUtil {

	private static DisplayImageOptions imageLoadOptions = null;

	/**
	 * 获得图片加载策略,参见ImageLoader
	 * http://blog.csdn.net/juyo_ch/article/details/26493119
	 * 
	 * @return
	 */
	public static DisplayImageOptions imageLoadOptions() {
		if (imageLoadOptions == null) {
			imageLoadOptions = new DisplayImageOptions.Builder()
					.showImageOnLoading(
							R.drawable.status_orange)
					.showImageForEmptyUri(
							R.drawable.status_gray)
					.showImageOnFail(R.drawable.status_red)
					.cacheInMemory(false)
					.cacheOnDisk(false)
					.considerExifParams(false)
					.resetViewBeforeLoading(false)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.build();
		}
		return imageLoadOptions;
	}

	/**
	 * 创建一个简单的消息
	 */
	public static Message buildMessage(int what, Object message) {
		Message msg = new Message();
		msg.what = what;
		msg.obj = message;
		return msg;
	}

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

	public static int getPwdInputType() {
		return (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}

	/**
	 * 读输入流到字节数组中
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private Bitmap getBitMapFromPath(Display currentDisplay,
			String imageFilePath) {
		// Display currentDisplay =
		// getWindowManager().getDefaultDisplay();
		int dw = currentDisplay.getWidth();
		int dh = currentDisplay.getHeight();
		// Load up the image's dimensions not the image itself
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(imageFilePath,
				bmpFactoryOptions);
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) dh);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) dw);

		// If both of the ratios are greater than 1,
		// one of the sides of the image is greater than the screen
		if (heightRatio > 1 && widthRatio > 1) {
			if (heightRatio > widthRatio) {
				// Height ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				// Width ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		// Decode it for real
		bmpFactoryOptions.inJustDecodeBounds = false;
		bmp = BitmapFactory
				.decodeFile(imageFilePath, bmpFactoryOptions);

		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.setRotate(90);
		bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
				bmp.getHeight(), matrix, true);
		return bmp;
	}

	public static LayoutParams matchLayout() {
		return new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}
}
