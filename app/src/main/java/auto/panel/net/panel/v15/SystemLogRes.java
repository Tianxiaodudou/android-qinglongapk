package auto.panel.net.panel.v15;

import auto.panel.net.panel.BaseRes;

import java.util.List;

public class SystemLogRes extends BaseRes {
    private List<LogFileObject> data;

    public List<LogFileObject> getData() {
        return data;
    }

    public static class LogFileObject {
        private String title;
        private boolean isDir;
        private long size;
        private long createTime;
        private List<LogFileObject> children;

        public String getTitle() { return title; }
        public boolean isDir() { return isDir; }
        public long getSize() { return size; }
        public long getCreateTime() { return createTime; }
        public List<LogFileObject> getChildren() { return children; }
    }
}
