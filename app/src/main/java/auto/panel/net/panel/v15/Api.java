package auto.panel.net.panel.v15;

import auto.panel.net.panel.BaseRes;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author wsfsp4
 * @version 2023.07.06
 */
public interface Api {
    @GET("api/crons")
    Call<TasksRes> getTasks(@Query("searchValue") String searchValue, @Query("page") int page, @Query("size") int size);

    @GET("api/envs")
    Call<EnvironmentsRes> getEnvironments(@Query("searchValue") String searchValue);

    @GET("api/scripts")
    Call<ScriptFilesRes> getScriptFiles();

    @POST("api/scripts")
    Call<BaseRes> addScript(@Body RequestBody body);

    @GET("api/logs")
    Call<LogFilesRes> getLogFiles();

    @GET("api/dependencies")
    Call<DependenciesRes> getDependencies(@Query("searchValue") String searchValue, @Query("type") String type);

    @HTTP(method = "DELETE", path = "api/dependencies/force", hasBody = true)
    Call<BaseRes> deleteDependencies(@Body RequestBody body);

    @PUT("api/system/config")
    Call<BaseRes> updateSystemConfig(@Body RequestBody body);

    // Open API (应用设置)
    @GET("api/apps")
    Call<AppsRes> getApps();

    @POST("api/apps")
    Call<BaseRes> createApp(@Body RequestBody body);

    @PUT("api/apps")
    Call<BaseRes> updateApp(@Body RequestBody body);

    @HTTP(method = "DELETE", path = "api/apps", hasBody = true)
    Call<BaseRes> deleteApps(@Body RequestBody body);

    @PUT("api/apps/{id}/reset-secret")
    Call<BaseRes> resetAppSecret(@Path("id") int id);

    // System log
    @GET("api/system/log")
    Call<SystemLogRes> getSystemLog(@Query("startTime") String startTime, @Query("endTime") String endTime);

    @DELETE("api/system/log")
    Call<BaseRes> deleteSystemLog();

    // Notification test
    @PUT("api/system/notify")
    Call<BaseRes> testNotification(@Body RequestBody body);

    // System config items (various)
    @PUT("api/system/config/timezone")
    Call<BaseRes> updateTimezone(@Body RequestBody body);

    @PUT("api/system/config/dependence-proxy")
    Call<BaseRes> updateDependenceProxy(@Body RequestBody body);

    @PUT("api/system/config/node-mirror")
    Call<BaseRes> updateNodeMirror(@Body RequestBody body);

    @PUT("api/system/config/python-mirror")
    Call<BaseRes> updatePythonMirror(@Body RequestBody body);

    @PUT("api/system/config/linux-mirror")
    Call<BaseRes> updateLinuxMirror(@Body RequestBody body);

    @PUT("api/system/config/global-ssh-key")
    Call<BaseRes> updateGlobalSshKey(@Body RequestBody body);

    @PUT("api/system/config/dependence-clean")
    Call<BaseRes> cleanDependenceCache(@Body RequestBody body);
}
