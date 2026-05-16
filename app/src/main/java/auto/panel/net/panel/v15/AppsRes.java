package auto.panel.net.panel.v15;

import com.google.gson.annotations.SerializedName;

import auto.panel.net.panel.BaseRes;

import java.util.List;

public class AppsRes extends BaseRes {
    private List<AppObject> data;

    public List<AppObject> getData() {
        return data;
    }

    public void setData(List<AppObject> data) {
        this.data = data;
    }

    public static class AppObject {
        private int id;
        private String name;

        @SerializedName("client_id")
        private String clientId;

        @SerializedName("client_secret")
        private String clientSecret;

        private List<String> scopes;
        private List<Object> tokens;

        private String createdAt;
        private String updatedAt;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getClientId() { return clientId; }
        public String getClientSecret() { return clientSecret; }
        public List<String> getScopes() { return scopes; }
        public List<Object> getTokens() { return tokens; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }
    }
}
