package auto.panel.bean.panel;

/**
 * @author wsfsp4
 * @version 2023.07.03
 */
public class PanelSystemConfig {
    private int logRemoveFrequency;
    private int cronConcurrency;
    private String timezone;
    private String nodeMirror;
    private String pythonMirror;
    private String linuxMirror;
    private String dependenceProxy;
    private String globalSshKey;

    public int getCronConcurrency() {
        return cronConcurrency;
    }

    public void setCronConcurrency(int cronConcurrency) {
        this.cronConcurrency = cronConcurrency;
    }

    public int getLogRemoveFrequency() {
        return logRemoveFrequency;
    }

    public void setLogRemoveFrequency(int logRemoveFrequency) {
        this.logRemoveFrequency = logRemoveFrequency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getNodeMirror() {
        return nodeMirror;
    }

    public void setNodeMirror(String nodeMirror) {
        this.nodeMirror = nodeMirror;
    }

    public String getPythonMirror() {
        return pythonMirror;
    }

    public void setPythonMirror(String pythonMirror) {
        this.pythonMirror = pythonMirror;
    }

    public String getLinuxMirror() {
        return linuxMirror;
    }

    public void setLinuxMirror(String linuxMirror) {
        this.linuxMirror = linuxMirror;
    }

    public String getDependenceProxy() {
        return dependenceProxy;
    }

    public void setDependenceProxy(String dependenceProxy) {
        this.dependenceProxy = dependenceProxy;
    }

    public String getGlobalSshKey() {
        return globalSshKey;
    }

    public void setGlobalSshKey(String globalSshKey) {
        this.globalSshKey = globalSshKey;
    }
}
