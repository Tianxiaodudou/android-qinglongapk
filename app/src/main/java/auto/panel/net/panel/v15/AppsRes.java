package auto.panel.net.panel.v15;

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
        private String clientId;
        private String clientSecret;
        private List<String> scopes;
        private long createdAt;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getClientId() { return clientId; }
        public String getClientSecret() { return clientSecret; }
        public List<String> getScopes() { return scopes; }
        public long getCreatedAt() { return createdAt; }
    }
}
