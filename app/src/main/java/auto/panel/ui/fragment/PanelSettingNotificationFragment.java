package auto.panel.ui.fragment;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auto.panel.R;
import auto.panel.bean.panel.PanelNotificationMode;
import auto.panel.net.RetrofitFactory;
import auto.panel.net.panel.BaseRes;
import auto.panel.net.panel.v15.Api;
import auto.panel.net.panel.v15.SystemConfigRes;
import auto.panel.utils.CrashLogUtil;
import auto.panel.utils.TextUnit;
import auto.panel.utils.ToastUnit;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelSettingNotificationFragment extends BaseFragment {
    public static String TAG = "PanelSettingNotificationFragment";
    public static String NAME = "系统设置-通知设置";

    private Spinner uiSpinner;
    private LinearLayout uiConfigLayout;
    private LinearLayout uiFieldsContainer;
    private Button uiTestBtn;
    private Button uiSaveBtn;

    private PanelNotificationMode selectedMode;
    private PanelNotificationMode[] modes;
    private final List<EditText> fieldEditTexts = new ArrayList<>();
    // 从面板加载的原始值，用于预填（在 showConfigFields 之后应用）
    private Map<String, String> pendingValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.panel_fragment_setting_notification, container, false);

        uiSpinner = view.findViewById(R.id.setting_notification_spinner);
        uiConfigLayout = view.findViewById(R.id.setting_notification_config_layout);
        uiFieldsContainer = view.findViewById(R.id.setting_notification_fields_container);
        uiTestBtn = view.findViewById(R.id.setting_notification_test_btn);
        uiSaveBtn = view.findViewById(R.id.setting_notification_save_btn);

        init();
        return view;
    }

    @Override
    protected void init() {
        modes = PanelNotificationMode.values();
        String[] displayNames = new String[modes.length];
        for (int i = 0; i < modes.length; i++) {
            displayNames[i] = modes[i].getDisplayName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, displayNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uiSpinner.setAdapter(adapter);

        uiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMode = modes[position];
                showConfigFields(selectedMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        uiTestBtn.setOnClickListener(v -> testNotification());
        uiSaveBtn.setOnClickListener(v -> saveNotification());

        // 启动时加载面板现有配置
        loadSystemConfig();
    }

    /**
     * 从面板 API 加载当前系统配置
     */
    private void loadSystemConfig() {
        Call<SystemConfigRes> call = RetrofitFactory.buildWithAuthorization(Api.class).getSystemConfig();
        call.enqueue(new Callback<SystemConfigRes>() {
            @Override
            public void onResponse(Call<SystemConfigRes> call, Response<SystemConfigRes> response) {
                if (response.code() != 200 || response.body() == null || response.body().getCode() != 200) {
                    CrashLogUtil.log("Notify", "加载系统配置失败: code=" + response.code());
                    return;
                }
                SystemConfigRes res = response.body();
                if (res.getData() == null || res.getData().getInfo() == null) {
                    return;
                }
                SystemConfigRes.SystemConfigObject info = res.getData().getInfo();
                applyConfig(info);
                init = true;
            }

            @Override
            public void onFailure(Call<SystemConfigRes> call, Throwable t) {
                CrashLogUtil.log("Notify", "加载系统配置网络错误: " + t.toString());
            }
        });
    }

    /**
     * 根据系统配置判断当前通知模式并预填字段
     */
    private void applyConfig(SystemConfigRes.SystemConfigObject info) {
        PanelNotificationMode detectedMode = null;
        Map<String, String> values = new HashMap<>();

        // 按枚举顺序检测：找第一个必填字段有值的通知模式
        for (PanelNotificationMode mode : modes) {
            PanelNotificationMode.PanelNotificationField[] fields = mode.getFields();
            for (PanelNotificationMode.PanelNotificationField field : fields) {
                if (field.isRequired()) {
                    String value = getConfigField(info, field.getKey());
                    if (!TextUnit.isEmpty(value)) {
                        detectedMode = mode;
                    }
                    break;
                }
            }
            if (detectedMode != null) {
                // 读取该模式所有字段的值
                for (PanelNotificationMode.PanelNotificationField field : fields) {
                    String value = getConfigField(info, field.getKey());
                    if (!TextUnit.isEmpty(value)) {
                        values.put(field.getKey(), value);
                    }
                }
                break;
            }
        }

        if (detectedMode != null) {
            // 先保存待填充的值，然后选中对应的 spinner 项
            // spinner 选中会触发 onItemSelected -> showConfigFields
            pendingValues = values;
            for (int i = 0; i < modes.length; i++) {
                if (modes[i] == detectedMode) {
                    uiSpinner.setSelection(i);
                    break;
                }
            }
            pendingValues = null;
        }
    }

    /**
     * 通过反射获取 SystemConfigObject 中的字段值
     */
    private String getConfigField(SystemConfigRes.SystemConfigObject info, String key) {
        try {
            String methodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
            java.lang.reflect.Method getter = info.getClass().getMethod(methodName);
            Object result = getter.invoke(info);
            return result != null ? result.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private void showConfigFields(PanelNotificationMode mode) {
        uiFieldsContainer.removeAllViews();
        fieldEditTexts.clear();
        uiConfigLayout.setVisibility(View.VISIBLE);

        PanelNotificationMode.PanelNotificationField[] fields = mode.getFields();
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (PanelNotificationMode.PanelNotificationField field : fields) {
            View fieldView = inflater.inflate(R.layout.panel_setting_field_item, uiFieldsContainer, false);

            TextView label = fieldView.findViewById(R.id.field_label);
            EditText input = fieldView.findViewById(R.id.field_input);

            String labelText = field.getLabel();
            if (field.isRequired()) {
                labelText += " *";
            }
            label.setText(labelText);
            input.setHint(field.getDefaultValue());

            if (field.getKey().contains("Pass") || field.getKey().contains("Secret")
                    || field.getKey().contains("Token") || field.getKey().contains("Key")) {
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }

            // 如果是从面板加载的值，预填
            if (pendingValues != null && pendingValues.containsKey(field.getKey())) {
                input.setText(pendingValues.get(field.getKey()));
            }

            fieldEditTexts.add(input);
            uiFieldsContainer.addView(fieldView);
        }
    }

    private JsonObject buildNotificationJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", selectedMode.name());

        PanelNotificationMode.PanelNotificationField[] fields = selectedMode.getFields();
        for (int i = 0; i < fields.length; i++) {
            String value = fieldEditTexts.get(i).getText().toString();
            if (TextUnit.isEmpty(value)) {
                value = fields[i].getDefaultValue();
            }
            json.addProperty(fields[i].getKey(), value);
        }

        return json;
    }

    private boolean validateFields() {
        PanelNotificationMode.PanelNotificationField[] fields = selectedMode.getFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isRequired()) {
                String value = fieldEditTexts.get(i).getText().toString();
                if (TextUnit.isEmpty(value)) {
                    ToastUnit.showShort("请填写必填字段: " + fields[i].getLabel());
                    return false;
                }
            }
        }
        return true;
    }

    private void saveNotification() {
        if (selectedMode == null) return;
        if (!validateFields()) return;

        JsonObject body = buildNotificationJson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body.toString());

        Call<BaseRes> call = RetrofitFactory.buildWithAuthorization(Api.class).updateSystemConfig(requestBody);

        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                BaseRes res = response.body();
                if (res != null && res.getCode() == 200) {
                    ToastUnit.showShort("保存成功");
                } else if (response.code() == 401) {
                    ToastUnit.showShort("登录信息失效");
                } else if (res == null) {
                    ToastUnit.showShort("响应异常: " + response.code());
                } else {
                    ToastUnit.showShort(res.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                CrashLogUtil.log("Notify", "保存通知设置失败: " + t.toString());
                ToastUnit.showShort("保存失败: " + t.getLocalizedMessage());
            }
        });
    }

    private void testNotification() {
        if (selectedMode == null) return;
        if (!validateFields()) return;

        JsonObject body = buildNotificationJson();
        body.addProperty("title", "青龙面板测试通知");
        body.addProperty("content", "这是一条来自青龙面板 Android App 的测试通知");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body.toString());

        Call<BaseRes> call = RetrofitFactory.buildWithAuthorization(Api.class).testNotification(requestBody);

        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                BaseRes res = response.body();
                if (res != null && res.getCode() == 200) {
                    ToastUnit.showShort("通知发送成功");
                } else if (response.code() == 401) {
                    ToastUnit.showShort("登录信息失效");
                } else if (res == null) {
                    ToastUnit.showShort("响应异常: " + response.code());
                } else {
                    ToastUnit.showShort(res.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                CrashLogUtil.log("Notify", "测试通知发送失败: " + t.toString());
                ToastUnit.showShort("通知发送失败: " + t.getLocalizedMessage());
            }
        });
    }
}
