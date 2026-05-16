package auto.panel.net.panel.v15;

import auto.panel.net.panel.BaseRes;

import java.util.List;

public class AppsRes extends BaseRes {
    private AppsData data;

    public AppsData getData() {
        return data;
    }

    public void setData(AppsData data) {
        this.data = data;
    }

    public static class AppsData {
        private List<AppObject> apps;
        private int total;

        public List<AppObject> getApps() { return apps; }
        public void setApps(List<AppObject> apps) { this.apps = apps; }
        public int getTotal() { return total; }
        public void setTotal(int total) { this.total = total; }
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
