package auto.panel.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import auto.panel.R;
import auto.panel.bean.panel.PanelOpenApp;
import auto.panel.net.RetrofitFactory;
import auto.panel.net.panel.BaseRes;
import auto.panel.net.panel.v15.Api;
import auto.panel.net.panel.v15.AppsRes;
import auto.panel.ui.adapter.PanelOpenAppsAdapter;
import auto.panel.utils.TextUnit;
import auto.panel.utils.ToastUnit;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelSettingOpenAppsFragment extends BaseFragment {
    public static String TAG = "PanelSettingOpenAppsFragment";
    public static String NAME = "系统设置-应用设置";

    private PanelOpenAppsAdapter itemAdapter;
    private SmartRefreshLayout uiRefresh;
    private RecyclerView uiRecycler;
    private Button uiAddBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.panel_fragment_setting_open_apps, container, false);

        uiAddBtn = view.findViewById(R.id.setting_apps_add_btn);
        uiRefresh = view.findViewById(R.id.refresh_layout);
        uiRecycler = view.findViewById(R.id.recycler_view);

        init();
        return view;
    }

    @Override
    protected void init() {
        itemAdapter = new PanelOpenAppsAdapter();
        uiRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        uiRecycler.setAdapter(itemAdapter);

        uiRefresh.setOnRefreshListener(refreshLayout -> loadApps());

        uiAddBtn.setOnClickListener(v -> showAddDialog());

        itemAdapter.setOnItemClickListener(new PanelOpenAppsAdapter.OnItemClickListener() {
            @Override
            public void onDelete(PanelOpenApp app, int position) {
                confirmDelete(app);
            }

            @Override
            public void onResetSecret(PanelOpenApp app, int position) {
                resetAppSecret(app);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !init) {
            uiRefresh.autoRefreshAnimationOnly();
            loadApps();
        }
    }

    private void loadApps() {
        Call<AppsRes> call = RetrofitFactory.buildWithAuthorization(Api.class).getApps();

        call.enqueue(new Callback<AppsRes>() {
            @Override
            public void onResponse(Call<AppsRes> call, Response<AppsRes> response) {
                if (response.code() != 200 || response.body() == null || response.body().getCode() != 200) {
                    ToastUnit.showShort("加载失败: " + (response.body() != null ? response.body().getMessage() : "响应异常"));
                    finishRefresh(false);
                    return;
                }
                List<PanelOpenApp> apps = new ArrayList<>();
                AppsRes res = response.body();
                if (res.getData() != null) {
                    for (AppsRes.AppObject obj : res.getData()) {
                        PanelOpenApp app = new PanelOpenApp();
                        app.setId(obj.getId());
                        app.setName(obj.getName());
                        app.setClientId(obj.getClientId());
                        app.setClientSecret(obj.getClientSecret());
                        if (obj.getScopes() != null) {
                            app.setScopes(String.join(", ", obj.getScopes()));
                        }
                        app.setCreatedAt(obj.getCreatedAt());
                        apps.add(app);
                    }
                }
                itemAdapter.setData(apps);
                init = true;
                finishRefresh(true);
            }

            @Override
            public void onFailure(Call<AppsRes> call, Throwable t) {
                ToastUnit.showShort("网络错误: " + t.getLocalizedMessage());
                finishRefresh(false);
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("添加应用");

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_app, null);
        EditText nameInput = dialogView.findViewById(R.id.dialog_app_name);

        builder.setView(dialogView);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("添加", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            if (TextUnit.isEmpty(name)) {
                ToastUnit.showShort("应用名称不能为空");
                return;
            }
            createApp(name);
        });
        builder.show();
    }

    private void createApp(String name) {
        JsonArray scopesArray = new JsonArray();
        scopesArray.add("all");

        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.add("scopes", scopesArray);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());
        Call<BaseRes> call = RetrofitFactory.buildWithAuthorization(Api.class).createApp(body);

        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                if (response.code() == 200 && response.body() != null && response.body().getCode() == 200) {
                    ToastUnit.showShort("添加成功");
                    loadApps();
                } else {
                    ToastUnit.showShort("添加失败: " + (response.body() != null ? response.body().getMessage() : "响应异常"));
                }
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                ToastUnit.showShort("网络错误: " + t.getLocalizedMessage());
            }
        });
    }

    private void confirmDelete(PanelOpenApp app) {
        new AlertDialog.Builder(requireContext())
                .setTitle("删除应用")
                .setMessage("确定删除应用 \"" + app.getName() + "\" 吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("删除", (dialog, which) -> deleteApp(app))
                .show();
    }

    private void deleteApp(PanelOpenApp app) {
        JsonArray ids = new JsonArray();
        ids.add(app.getId());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), ids.toString());
        Call<BaseRes> call = RetrofitFactory.buildWithAuthorization(Api.class).deleteApps(body);

        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                if (response.code() == 200 && response.body() != null && response.body().getCode() == 200) {
                    ToastUnit.showShort("删除成功");
                    loadApps();
                } else {
                    ToastUnit.showShort("删除失败: " + (response.body() != null ? response.body().getMessage() : "响应异常"));
                }
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                ToastUnit.showShort("网络错误: " + t.getLocalizedMessage());
            }
        });
    }

    private void resetAppSecret(PanelOpenApp app) {
        Call<BaseRes> call = RetrofitFactory.buildWithAuthorization(Api.class).resetAppSecret(app.getId());

        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                if (response.code() == 200 && response.body() != null && response.body().getCode() == 200) {
                    ToastUnit.showShort("密钥已重置");
                    loadApps();
                } else {
                    ToastUnit.showShort("重置失败: " + (response.body() != null ? response.body().getMessage() : "响应异常"));
                }
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                ToastUnit.showShort("网络错误: " + t.getLocalizedMessage());
            }
        });
    }

    private void finishRefresh(boolean success) {
        if (uiRefresh.isRefreshing()) {
            uiRefresh.finishRefresh(success);
        }
    }
}
