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
import java.util.List;

import auto.panel.R;
import auto.panel.bean.panel.PanelNotificationMode;
import auto.panel.net.RetrofitFactory;
import auto.panel.net.panel.BaseRes;
import auto.panel.net.panel.NetHandler;
import auto.panel.net.panel.v15.Api;
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
    private final List<EditText> fieldEditTexts = new ArrayList<>();

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
        // Set up spinner with notification modes
        PanelNotificationMode[] modes = PanelNotificationMode.values();
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

            // Set input type based on field key
            if (field.getKey().contains("Pass") || field.getKey().contains("Secret")
                    || field.getKey().contains("Token") || field.getKey().contains("Key")) {
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
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
                ToastUnit.showShort("通知发送失败: " + t.getLocalizedMessage());
            }
        });
    }
}
