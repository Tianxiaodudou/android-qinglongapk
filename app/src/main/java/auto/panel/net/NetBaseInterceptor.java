package auto.panel.net;

import androidx.annotation.NonNull;

import java.io.IOException;

import auto.panel.utils.CrashLogUtil;
import auto.panel.utils.LogUnit;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author: ASman
 * @date: 2023/12/20
 * @description:
 */
public class NetBaseInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        LogUnit.log("originalRequest:" + originalRequest.url());

        Response response = chain.proceed(originalRequest);

        LogUnit.log("response:" + response.code());

        // 记录非 200 响应用于错误排查
        if (response.code() != 200) {
            CrashLogUtil.log("Net", "HTTP " + response.code()
                    + " " + response.message()
                    + " -> " + originalRequest.url().encodedPath());
        }

        return response;
    }

}
