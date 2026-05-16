package auto.panel.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import auto.panel.R;
import auto.panel.ui.adapter.PanelSettingPagerAdapter;
import auto.panel.utils.ToastUnit;

public class PanelSettingFragment extends BaseFragment {
    public static String TAG = "PanelSettingFragment";
    public static String NAME = "系统设置";

    private MenuClickListener menuClickListener;
    private PanelSettingPagerAdapter mPagerAdapter;

    private ImageView ui_menu;
    private TabLayout ui_tab;
    private ViewPager2 ui_page;
    private Button uiExportLog;

    private final ActivityResultLauncher<String> exportLogLauncher =
            registerForActivityResult(new ActivityResultContracts.CreateDocument("text/plain"), uri -> {
                if (uri != null) {
                    writeLogToUri(uri);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.panel_fragment_setting, null);

        ui_menu = view.findViewById(R.id.action_nav_bar_menu);
        ui_tab = view.findViewById(R.id.page_tab);
        ui_page = view.findViewById(R.id.view_page);
        uiExportLog = view.findViewById(R.id.export_log_btn);

        init();
        return view;
    }

    @Override
    public void init() {
        ui_menu.setOnClickListener(v -> menuClickListener.onMenuClick());

        mPagerAdapter = new PanelSettingPagerAdapter(requireActivity());
        ui_page.setAdapter(mPagerAdapter);
        ui_page.setUserInputEnabled(false);
        ui_page.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
            }
        });

        new TabLayoutMediator(ui_tab, ui_page, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("常规设置");
                    break;
                case 1:
                    tab.setText("通知设置");
                    break;
                case 2:
                    tab.setText("登录日志");
                    break;
                case 3:
                    tab.setText("应用设置");
                    break;
            }
        }).attach();

        // 导出日志按钮
        uiExportLog.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String fileName = "qinglong_log_" + sdf.format(new Date()) + ".txt";
            exportLogLauncher.launch(fileName);
        });
    }

    private void writeLogToUri(Uri uri) {
        try {
            OutputStream os = requireContext().getContentResolver().openOutputStream(uri);
            if (os == null) {
                ToastUnit.showShort("无法创建文件");
                return;
            }
            String log = buildLogContent();
            os.write(log.getBytes("UTF-8"));
            os.close();
            ToastUnit.showShort("日志已导出");
        } catch (Exception e) {
            ToastUnit.showShort("导出失败: " + e.getMessage());
        }
    }

    private String buildLogContent() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        sb.append("=== 青龙面板 App 日志 ===\n");
        sb.append("导出时间: ").append(sdf.format(new Date())).append("\n\n");

        // 设备信息
        sb.append("--- 设备信息 ---\n");
        sb.append("品牌: ").append(Build.BRAND).append("\n");
        sb.append("型号: ").append(Build.MODEL).append("\n");
        sb.append("Android 版本: ").append(Build.VERSION.RELEASE).append("\n");
        sb.append("SDK 版本: ").append(Build.VERSION.SDK_INT).append("\n\n");

        // 应用信息
        sb.append("--- 应用信息 ---\n");
        try {
            Activity activity = requireActivity();
            sb.append("应用包名: ").append(activity.getPackageName()).append("\n");
            sb.append("版本名: ").append(activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).versionName).append("\n");
            sb.append("版本号: ").append(activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).getLongVersionCode()).append("\n");
        } catch (Exception e) {
            sb.append("(无法获取应用信息)\n");
        }
        sb.append("\n");

        // 面板连接状态
        sb.append("--- 面板连接 ---\n");
        try {
            String baseUrl = auto.panel.database.sp.PanelPreference.getBaseUrl();
            String auth = auto.panel.database.sp.PanelPreference.getAuthorization();
            sb.append("面板地址: ").append(baseUrl).append("\n");
            sb.append("Token: ").append(auth != null && !auth.isEmpty() ? "已设置" : "未设置").append("\n");
        } catch (Exception e) {
            sb.append("(无法获取面板信息)\n");
        }
        sb.append("\n");

        // 错误日志（从全局异常收集器获取）
        sb.append("--- 异常日志 ---\n");
        sb.append(getStoredErrors());
        sb.append("\n");

        sb.append("=== 日志结束 ===\n");
        return sb.toString();
    }

    /**
     * 获取存储的错误信息
     */
    private String getStoredErrors() {
        try {
            StringBuilder sb = new StringBuilder();
            // 从 SharedPreferences 读取错误日志
            android.content.SharedPreferences sp = requireContext()
                    .getSharedPreferences("error_logs", android.content.Context.MODE_PRIVATE);
            String errors = sp.getString("logs", "");
            if (errors != null && !errors.isEmpty()) {
                sb.append(errors);
            } else {
                sb.append("(无错误日志)\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "(读取错误日志失败)\n";
        }
    }

    @Override
    public void setMenuClickListener(MenuClickListener mMenuClickListener) {
        this.menuClickListener = mMenuClickListener;
    }
}
