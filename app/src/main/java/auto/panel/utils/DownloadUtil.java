package auto.panel.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;

import java.io.File;

public class DownloadUtil {
    private static DownloadManager dm;
    private static long downloadId;

    /**
     * 应用内下载 APK 并安装（使用系统 DownloadManager）
     */
    public static void downloadApk(Activity activity, String url, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("正在下载更新");
        request.setDescription("青龙 APP 新版本下载中...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setMimeType("application/vnd.android.package-archive");

        dm = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = dm.enqueue(request);

        // 监听下载完成
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == downloadId) {
                    installApk(activity, id);
                    activity.unregisterReceiver(this);
                }
            }
        }, filter);
    }

    /**
     * 安装 APK
     */
    private static void installApk(Activity activity, long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = dm.query(query);
        if (cursor != null && cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                cursor.close();
                if (uriString != null) {
                    File apkFile = new File(Uri.parse(uriString).getPath());
                    if (apkFile.exists()) {
                        installFile(activity, apkFile);
                    } else {
                        ToastUnit.showShort("下载文件不存在");
                    }
                }
            } else {
                cursor.close();
                ToastUnit.showShort("下载失败，请重试");
            }
        } else {
            if (cursor != null) cursor.close();
        }
    }

    /**
     * 调用系统安装器
     */
    private static void installFile(Activity activity, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        activity.startActivity(intent);
    }
}
