package com.coo.m.vote;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Message;
import android.text.InputType;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.kingstar.ngbf.ms.util.DateUtil;
import com.kingstar.ngbf.s.ntp.NtpHead;
import com.kingstar.ngbf.s.ntp.NtpMessage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public final class VoteUtil {

	private static DisplayImageOptions imageLoadOptions = null;

	/**
	 * 生成UUID
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 加载Icon图片
	 * 
	 * @param iconUrl
	 * @param ivIcon
	 */
	public static void loadIcon(String iconUrl, ImageView ivIcon) {
		ImageLoader.getInstance().displayImage(iconUrl, ivIcon,
				VoteUtil.imageLoadOptions());
	}

	/**
	 * 获得图片加载策略,参见ImageLoader
	 * http://blog.csdn.net/juyo_ch/article/details/26493119
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
	public static boolean isRespOK(NtpMessage resp) {
		return resp.getHead().getRep_code().equals(NtpHead.REP_OK);
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

	/**
	 * 生成二维码
	 * 参见ms-util.QrcodeBuilder
	 */
	public static Bitmap createQrcode(String text) {
		try {
			// 定义参数
			Hashtable<EncodeHintType, Object> params = new Hashtable<EncodeHintType, Object>();
			params.put(EncodeHintType.ERROR_CORRECTION,
					ErrorCorrectionLevel.H);
			params.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
			BitMatrix matrix = new MultiFormatWriter()
					.encode(text, BarcodeFormat.QR_CODE,
							300, 300, params);
			int width = matrix.getWidth();
			int height = matrix.getHeight();

			// 二维矩阵转为一维像素数组,也就是一直横着排了
			int[] pixels = new int[width * height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (matrix.get(x, y)) {
						pixels[y * width + x] = 0xff000000;
					} else
						pixels[y * width + x] = -1;
				}
			}

			Bitmap bitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			// 通过像素数组生成bitmap,具体参考api
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 在已有的Qrcode上加载头像Icon
	 * 参见ms-util.QrcodeBuilder
	 */
	public void createQrcodeWithPortrait(Bitmap qrBitmap, Bitmap portrait) {
		// 头像图片的大小
		int portrait_W = portrait.getWidth();
		int portrait_H = portrait.getHeight();

		// 设置头像要显示的位置，即居中显示
		int left = (300 - portrait_W) / 2;
		int top = (300 - portrait_H) / 2;
		int right = left + portrait_W;
		int bottom = top + portrait_H;
		Rect rect1 = new Rect(left, top, right, bottom);

		// 取得qr二维码图片上的画笔，即要在二维码图片上绘制我们的头像
		Canvas canvas = new Canvas(qrBitmap);

		// 设置我们要绘制的范围大小，也就是头像的大小范围
		Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
		// 开始绘制
		canvas.drawBitmap(portrait, rect2, rect1, null);
	}
}
