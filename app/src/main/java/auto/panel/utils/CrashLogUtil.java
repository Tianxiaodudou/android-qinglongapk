package auto.panel.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 错误日志收集工具，将错误写入 SharedPreferences，供导出使用
 */
public class CrashLogUtil {
    private static final String PREF_NAME = "error_logs";
    private static final String KEY_LOGS = "logs";
    private static final int MAX_LOG_LENGTH = 50000; // 最多保留 50KB

    private static Context appContext;

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static void log(String tag, String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault());
        String timestamp = sdf.format(new Date());
        String entry = "[" + timestamp + "] [" + tag + "] " + message + "\n";
        append(entry);
    }

    public static void log(String tag, Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        log(tag, throwable.getMessage() + "\n" + sw.toString());
    }

    public static void ensureInit(Context context) {
        if (appContext == null && context != null) {
            appContext = context.getApplicationContext();
        }
    }

    private static synchronized void append(String entry) {
        if (appContext == null) return;
        try {
            SharedPreferences sp = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String existing = sp.getString(KEY_LOGS, "");
            String updated = existing + entry;
            // 超过最大长度则截断
            if (updated.length() > MAX_LOG_LENGTH) {
                updated = updated.substring(updated.length() - MAX_LOG_LENGTH);
            }
            sp.edit().putString(KEY_LOGS, updated).apply();
        } catch (Exception ignored) {
        }
    }

    public static void clear() {
        if (appContext == null) return;
        appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit().remove(KEY_LOGS).apply();
    }
}
