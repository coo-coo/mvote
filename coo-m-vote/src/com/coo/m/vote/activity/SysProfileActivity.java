package com.coo.m.vote.activity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.VoteManager;
import com.coo.m.vote.VoteUtil;
import com.coo.m.vote.activity.adapter.SysProfilePropertyAdapter;
import com.kingstar.ngbf.ms.util.FileUtil;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.rpc.HttpAsynCaller;
import com.kingstar.ngbf.ms.util.rpc.IHttpCallback;
import com.kingstar.ngbf.ms.util.update.FileUploadManager;
import com.kingstar.ngbf.ms.util.update.FileUploadOptions;
import com.kingstar.ngbf.s.ntp.SimpleMessage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SysProfileActivity extends CommonBizActivity implements
		IHttpCallback<SimpleMessage<?>> {

	@Override
	public String getHeaderTitle() {
		return "个人信息";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.sys_profile_activity;
	}

	/**
	 * 条目适配器
	 */
	private SysProfilePropertyAdapter adapter;

	private ImageView ivIcon = null;

	private TextView tvAccount = null;

	@Override
	public void onAbsViewItemChanged(Object item) {
		// 交由子类实现
		adapter.notifyDataSetChanged();
		if (item instanceof CommonItem) {
			CommonItem ci = (CommonItem) item;
			String id = VoteManager.getAccount().get_id();
			// toast(id + "-" + ci.getCode() + "-" + ci.getValue());

			// 发送更新请求...
			String uri = Constants.HOST_REST
					+ "/profile/update/id/" + id
					+ "/param/" + ci.getCode() + "/value/"
					+ ci.getValue() + "/type/0";
			toast("uri=" + uri);
			// TODO 中文？GET?
			// 修改信息，參見topicUpdate
			HttpAsynCaller.doGet(uri, null, this);
		}
	}

	@Override
	public void loadContent() {
		// 定义控件 & 定义适配器
		if (Constants.MOCK_DATA) {
			ListView listView = (ListView) findViewById(R.id.lv_sys_profile);

			// TODO 需要数据的服务器端Merge处理...
			adapter = new SysProfilePropertyAdapter(
					VoteManager.getProfileSkeletonItems(),
					listView);
			adapter.initContext(this);
		} else {
			// 异步调用数据
			String account = VoteManager.getStrAccount();
			String uri = Constants.HOST_REST
					+ "/profile/get/account/" + account;
			toast("uri=" + uri);
			HttpAsynCaller.doGet(uri, null, this);
		}
		ivIcon = (ImageView) findViewById(R.id.iv_sys_profile_icon);

		// 参见: http://blog.csdn.net/juyo_ch/article/details/26493119
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.status_orange)
				.showImageForEmptyUri(R.drawable.status_gray)
				.showImageOnFail(R.drawable.status_red)
				.cacheInMemory(false).cacheOnDisk(false)
				.considerExifParams(true)
				.resetViewBeforeLoading(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		// 加载SDCard上的图片
		String iconUrl = VoteManager.getSdProfileIconPath2();
		ImageLoader.getInstance()
				.displayImage(iconUrl, ivIcon, options);
		ivIcon.setOnClickListener(this);

		// TODO 位置
		tvAccount = (TextView) findViewById(R.id.iv_sys_profile_account);
		tvAccount.setText(VoteManager.getStrAccount());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_sys_profile_icon:
			doSettingIcon();
			break;
		}
	}

	private final static int REQUEST_ALBUM = 0;
	private final static int REQUEST_CAMERA = 1;
	private final static int REQUEST_CROP = 2;

	private void doSettingIcon() {
		// 从相册中获取
//		Intent intent = new Intent(Intent.ACTION_PICK, null);
//		intent.setDataAndType(
//				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//				"image/*");
//		startActivityForResult(intent, REQUEST_ALBUM);
		
		// TODO 参见系统参数配置....
		
		// 从拍照中获取
		Intent intent2 = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri
				.fromFile(new File(Environment
						.getExternalStorageDirectory(),
						"test11.jpg")));
		startActivityForResult(intent2, REQUEST_CAMERA);
		
		// toast("图片设置");
		// Intent intent = new Intent();
		// intent.setClass(SysProfileActivity.this,
		// SysProfileIconActivity.class);
		// startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		switch (requestCode) {
		case REQUEST_ALBUM:
			startPhotoZoom(data.getData());
			break;
		case REQUEST_CAMERA:
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/test11.jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case REQUEST_CROP:
			resetIcon(data);
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 50);
		intent.putExtra("outputY", 50);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUEST_CROP);
	}
	
	/**
	 * 获得返回结果,重新设置Icon,存储/上传的
	 * @param picdata
	 */
	private void resetIcon(Intent picdata) {
		if (picdata != null) {
			Bundle extras = picdata.getExtras();
			if (extras != null) {
				Bitmap bitmap = extras.getParcelable("data");
				ivIcon.setImageBitmap(bitmap);

				// 本地存储
				String filePath = VoteManager
						.getSdProfileIconPath();
				FileUtil.saveBitmap(bitmap, filePath);
				// 网络存储
				uploadFile(filePath);
			}
		}
	}
	
	/**
	 * 上传图片到网络
	 * @param filePath
	 */
	private void uploadFile(String filePath) {
		FileUploadManager fum = new FileUploadManager();
		String actionUrl = Constants.HOST_SERVLET + "/icon2";

		// TODO 文件上传参数
		Map<String, String> params = new HashMap<String, String>();
		// params.put("orderId", "11111");
		// params.put("orderId2", "22222");
		FileUploadOptions op = FileUploadOptions.blank().handler(
				handler);
		fum.upload(actionUrl, filePath, params, op);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// toast(msg.what + "-" + msg.obj);
		}
	};

	@Override
	public void response(SimpleMessage<?> resp) {
		if (VoteUtil.isRespOK(resp)) {
			// 获得业务请求代码
			String api_code = resp.getHead().getApi_code();
			if (api_code.equals("profile_get")) {
				responseProfileGet(resp);
			} else if (api_code.equals("profile_update")) {
				toast("更新属性成功....");
			} else {

			}
		} else {
			toast(Constants.MSG_RPC_ERROR);
		}
	}

	/**
	 * 
	 * @param resp
	 */
	private void responseProfileGet(SimpleMessage<?> resp) {
		ListView listView = (ListView) findViewById(R.id.lv_sys_profile);
		// 获得服务器的值，有些Key是没有设定的，则为空，空表示服务器没有
		List<CommonItem> items = VoteManager.getProfileSkeletonItems();
		for (CommonItem ci : items) {
			// 通过code，获得Mongo条目信息的属性值
			String value = resp.getData(ci.getCode());
			if (value != null) {
				// TODO 暂定都是字符串
				ci.setValue(value);
			}
		}
		adapter = new SysProfilePropertyAdapter(items, listView);
		adapter.initContext(this);
	}

}
