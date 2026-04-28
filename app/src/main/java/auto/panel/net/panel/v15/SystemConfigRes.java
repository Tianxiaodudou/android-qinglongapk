package auto.panel.net.panel.v15;

import auto.panel.net.panel.BaseRes;

/**
 * @author wsfsp4
 * @version 2023.07.10
 */
public class SystemConfigRes extends BaseRes {
    private DataObject data;

    public DataObject getData() {
        return data;
    }

    public void setData(DataObject data) {
        this.data = data;
    }

    public static class DataObject {
        private SystemConfigObject info;

        public SystemConfigObject getInfo() {
            return info;
        }
    }

    public static class SystemConfigObject {
        private int logRemoveFrequency;
        private int cronConcurrency;
        private String timezone;
        private String nodeMirror;
        private String pythonMirror;
        private String linuxMirror;
        private String dependenceProxy;
        private String globalSshKey;

        public int getLogRemoveFrequency() {
            return logRemoveFrequency;
        }

        public int getCronConcurrency() {
            return cronConcurrency;
        }

        public String getTimezone() {
            return timezone;
        }

        public String getNodeMirror() {
            return nodeMirror;
        }

        public String getPythonMirror() {
            return pythonMirror;
        }

        public String getLinuxMirror() {
            return linuxMirror;
        }

        public String getDependenceProxy() {
            return dependenceProxy;
        }

        public String getGlobalSshKey() {
            return globalSshKey;
        }
    }
}
