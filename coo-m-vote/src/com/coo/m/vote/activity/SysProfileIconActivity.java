package com.coo.m.vote.activity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.kingstar.ngbf.ms.cropper.CropImageView;
import com.kingstar.ngbf.ms.util.FileUtil;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.update.FileUploadManager;
import com.kingstar.ngbf.ms.util.update.FileUploadOptions;

/**
 * Profile
 * @deprecated 参见SysProfileActivity,调用系统的图片选取和剪裁
 * @author boqing.shen
 * 
 */
public class SysProfileIconActivity extends CommonBizActivity{

	private CropImageView civIcon;
	private Button btnMore;
	private Button btnOk;

	@Override
	public String getHeaderTitle() {
		return "图标设置";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.sys_profile_icon_activity;
	}

	@Override
	public void loadContent() {
		civIcon = (CropImageView) findViewById(R.id.civ_btn_sys_profile_icon);

		// civIcon.setAspectRatio(1,1);
		// civIcon.setFixedAspectRatio(true);
		// civIcon.setGuidelines(1);

		btnMore = (Button) findViewById(R.id.btn_sys_profile_icon_more);
		btnOk = (Button) findViewById(R.id.btn_sys_profile_icon_ok);

		btnOk.setOnClickListener(this);
		btnMore.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sys_profile_icon_more:
			doSelectMore();
			break;
		case R.id.btn_sys_profile_icon_ok:
			doCropOk();
			break;
		}
	}

	private void doCropOk() {
		Bitmap bitmap = civIcon.getCroppedImage();
		// TODO 本地存储,
		String filePath = VoteManager.getSdProfileIconPath();
		FileUtil.saveBitmap(bitmap, filePath);
		// 文件上传,网络存储
		uploadFile(filePath);
		
		// TODO 跳转
		
	}

	private static int REQUEST_ALBUM = 0;

	// private static int REQUEST_CAMERA = 1;

	private void doSelectMore() {
		// 调用系统相册
		Intent loadSysAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		loadSysAlbum.addCategory(Intent.CATEGORY_OPENABLE);
		loadSysAlbum.setType("image/jpeg");
		startActivityForResult(loadSysAlbum, REQUEST_ALBUM);

//		String filePath = VoteManager.getSdProfileIconPath();
//		uploadFile(filePath);
		
		// TODO 拍照....
		// Uri uri = Uri.fromFile(vFile);
		// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
		// startActivityForResult(intent,1);
	}

	/**
	 * startActivityForResult的回调函数
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		ContentResolver resolver = getContentResolver();
		// requestCode跟startActivityForResult里面第二个参数对应
		if (requestCode == REQUEST_ALBUM) {
			try {
				// 获得图片的uri
				Uri originalUri = data.getData();
				// Toast.makeText(this, originalUri + "",
				// Toast.LENGTH_LONG).show();
				InputStream is = resolver
						.openInputStream(originalUri);
				byte[] bytes = VoteUtil.readStream(is);
				// 将字节数组转换为ImageView可调用的Bitmap对象
				Bitmap bitmap = FileUtil.transform(bytes, null);
				// myBitmap=getBitMapFromPath(imgPath);
				civIcon.setImageBitmap(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void uploadFile(String filePath) {
		FileUploadManager fum = new FileUploadManager();
		String actionUrl = Constants.HOST_SERVLET + "/icon2";
		
		// TODO 文件上传参数
		Map<String, String> params = new HashMap<String, String>();
//		params.put("orderId", "11111");
//		params.put("orderId2", "22222");
		FileUploadOptions op = FileUploadOptions.blank().handler(handler);
		fum.upload(actionUrl, filePath, params, op);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//			toast(msg.what + "-" + msg.obj);
		}
	};
	
	

}
