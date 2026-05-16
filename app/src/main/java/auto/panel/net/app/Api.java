package auto.panel.net.app;

import auto.panel.bean.app.Config;
import auto.panel.bean.app.Version;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 应用接口.
 */
public interface Api {
    String URL_BASE = "https://raw.githubusercontent.com/Tianxiaodudou/android-qinglongapk/青龙APP_claw版/";

    @GET("version.json")
    Call<Version> getVersion();

    @GET("config.json")
    Call<Config> getConfig();
}
