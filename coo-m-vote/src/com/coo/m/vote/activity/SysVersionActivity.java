package com.coo.m.vote.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coo.m.vote.Constants;
import com.coo.m.vote.R;
import com.coo.m.vote.activity.adapter.CommonItemListAdapter;
import com.kingstar.ngbf.ms.util.android.CommonBizActivity;
import com.kingstar.ngbf.ms.util.android.res.ServiceFactory;
import com.kingstar.ngbf.ms.util.android.res.ServiceProvider;
import com.kingstar.ngbf.ms.util.model.CommonItem;
import com.kingstar.ngbf.ms.util.update.UpdateBean;
import com.kingstar.ngbf.ms.util.update.UpdateManager;

/**
 * 【版本信息】关于Vote、产品、团队
 * 
 * @author Qiaoqiao.li
 */
public class SysVersionActivity extends CommonBizActivity {
	/**
	 * 更新按钮
	 */
	private Button btn_update;
	/**
	 * 更新版本管理器
	 * 
	 * @since 0.4.6.0
	 */
	private UpdateManager updateManager = new UpdateManager();

	// 当前应用版本信息
	private UpdateBean updateBean;
	// 下载中
	private static final int DOWNLOAD = 1;
	// 下载结束
	private static final int DOWNLOAD_FINISH = 2;
	
	@Override
	public String getHeaderTitle() {
		return "版本信息";
	}

	@Override
	public int getResViewLayoutId() {
		return R.layout.sys_version_activity;
	}

	// @Override
	public void loadContent() {
		ListView listView = (ListView) findViewById(R.id.lv_sys_version);
		// 定义适配器
		CommonItemListAdapter adapter = new CommonItemListAdapter(
				getItems(), listView);
		adapter.initContext(this);

		btn_update = (Button) findViewById(R.id.btn_sys_version_update);
		btn_update.setOnClickListener(this);
	}

	/**
	 * 模拟产生Profile的属性条目对象,用于集中展现
	 * 
	 * @return
	 */
	private List<CommonItem> getItems() {
		String version_current = updateManager
				.getAppVersion(this.getPackageManager(),
						this.getPackageName());
		boolean network = isNetworkAble();
		toast("网络连接可用?=" + network);

		// TODO 从服务器获得最新版本, 非标准的SimpleMessage的形式,需要改进
		String version_latest = "1.0.0";
		if (network) {
			String params = "/account/version/update/";
			String uri = Constants.HOST_REST + params;
			toast("网络连接可用=" + uri);
			// 获取最新版本，进行显示
			// updateBean = updateManager.request(uri);
			// version_latest = updateBean.getVersionName());
		} else {
			toast("当前网络不可用");
		}
		List<CommonItem> items = new ArrayList<CommonItem>();
		items.add(new CommonItem("version.app", "应用名称",
				getPackageName()));
		items.add(new CommonItem("version.current", "当前版本",
				version_current));
		items.add(new CommonItem("version.latest", "最新版本",
				version_latest));
		return items;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sys_version_update:
			// 执行更新下载操作
			showNoticeDialog(updateBean);
			break;
		}
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * 最新版本更新到SD卡地址
	 * 
	 * @since 0.4.6.0
	 */
	private String path = "";
	
	/**
	 * 显示软件更新对话框
	 * 
	 * @since 0.4.6.0
	 */
	private void showNoticeDialog(final UpdateBean info) {
		// 构造对话框
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("软件更新");
		StringBuffer sb = new StringBuffer();
		// 是否強制更新
		if (info.getForce().equals("1")) {
			sb.append("检测到新版本" + info.getVersionName() + "，必須更新！\n");
			builder.setMessage(sb.toString());
		} else {
			sb.append("检测到新版本" + info.getVersionName()
					+ "，立即更新吗?\n");
			builder.setMessage(sb.toString());
		}
		// 更新
		builder.setPositiveButton("更新",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						dialog.dismiss();
						// 显示下载对话框
						// showDownloadDialog();
						showProgressDialog();
					}
				});
		// 稍后更新
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						dialog.dismiss();
						// 強制更新直接退出应用
						if (info.getForce().equals("1")) {
							Toast.makeText(SysVersionActivity.this,
									"必须更新",
									Toast.LENGTH_LONG)
									.show();
							android.os.Process
									.killProcess(android.os.Process
											.myPid());
						}
					}
				});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 * 
	 * @since 0.4.6.0
	 */
	@SuppressWarnings("unused")
	private void showDownloadDialog() {
		// 构造软件下载对话框
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("正在更新");

		// 给下载对话框增加进度条
		ProgressBar progress = new ProgressBar(this);
		progress.setMax(100);
		progress.setProgress(0);
		progress.setVerticalScrollBarEnabled(false);
		// 取消更新
		// builder.setNegativeButton("取消", new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// // 设置取消状态
		// cancelUpdate = true;
		// }
		// });
		builder.setView(progress);
		Dialog downloadDialog = builder.create();
		downloadDialog.show();
		path = Environment.getExternalStorageDirectory() + "/";
		path = path + "download";
		// 下载文件
		updateManager.downloadApk(handler, path);
	}

	private ProgressDialog progressDialog = null;

	/**
	 * 显示软件下载对话框
	 * 
	 * @since 0.4.6.0
	 */
	private void showProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setTitle("正在更新");
		progressDialog.setProgress(0);
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		path = Environment.getExternalStorageDirectory() + "/";
		path = path + "download";
		// 下载文件
		updateManager.downloadApk(handler, path);
	}

	/**
	 * 更新progress(进度条)
	 * 
	 * @since 0.4.6.0
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				progressDialog.setProgress(data
						.getInt("progress"));
				break;
			case DOWNLOAD_FINISH:
				progressDialog.dismiss();
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 安装APK文件
	 * 
	 * @since 0.4.6.0
	 */
	private void installApk() {
		// String newVersion = tv_version_new.getText().toString();
		String newVersion = "TODO";
		File apkfile = new File(path, newVersion);
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		this.startActivity(i);
	}

	/**
	 * 检测当前网络是否可用
	 */
	private boolean isNetworkAble() {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		return ServiceProvider.isNetworkConnected(ServiceFactory
				.connectivityManager(this));
	}

	// /**
	// * 卸载APK文件
	// */
	// public void uninstallApk() {
	// Uri packageURI = Uri.parse("package:com.kingstar.ngbf.m.ngbf");
	// Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
	// packageURI);
	// this.startActivity(uninstallIntent);
	// }

	// /**
	// * 检查版本是否需要更新,匹配服务器最新版本和当前版本
	// */
	// @SuppressWarnings("unused")
	// private boolean checkVersionUpdate(String newVersionName,
	// String currentVersionName) {
	// char[] updateVersion = newVersionName.toCharArray();
	// char[] currentVersion = currentVersionName.toCharArray();
	// if (currentVersionName.equals(newVersionName)) {
	// return false;
	// }
	// for (int i = 0; i < currentVersion.length; i++) {
	// if (updateVersion[i] < currentVersion[i]) {
	// return false;
	// }
	// }
	// return true;
	// }

}
