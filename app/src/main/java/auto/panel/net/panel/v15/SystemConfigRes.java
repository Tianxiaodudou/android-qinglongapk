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

        // 通知设置字段
        // serverChan
        private String serverChanKey;
        // pushDeer
        private String pushDeerKey;
        private String pushDeerUrl;
        // bark
        private String barkPush;
        private String barkIcon;
        private String barkSound;
        private String barkGroup;
        // telegramBot
        private String telegramBotToken;
        private String telegramBotUserId;
        private String telegramBotApiHost;
        private String telegramBotProxyHost;
        private String telegramBotProxyPort;
        private String telegramBotProxyAuth;
        // dingtalkBot
        private String dingtalkBotToken;
        private String dingtalkBotSecret;
        // weWorkBot
        private String weWorkBotKey;
        // weWorkApp
        private String weWorkAppKey;
        // 通用 weWork 域名
        private String weWorkOrigin;
        // email
        private String emailService;
        private String emailUser;
        private String emailPass;
        private String emailTo;
        // pushPlus
        private String pushPlusToken;
        private String pushPlusUser;

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

        // 通知设置 getters
        public String getServerChanKey() { return serverChanKey; }
        public String getPushDeerKey() { return pushDeerKey; }
        public String getPushDeerUrl() { return pushDeerUrl; }
        public String getBarkPush() { return barkPush; }
        public String getBarkIcon() { return barkIcon; }
        public String getBarkSound() { return barkSound; }
        public String getBarkGroup() { return barkGroup; }
        public String getTelegramBotToken() { return telegramBotToken; }
        public String getTelegramBotUserId() { return telegramBotUserId; }
        public String getTelegramBotApiHost() { return telegramBotApiHost; }
        public String getTelegramBotProxyHost() { return telegramBotProxyHost; }
        public String getTelegramBotProxyPort() { return telegramBotProxyPort; }
        public String getTelegramBotProxyAuth() { return telegramBotProxyAuth; }
        public String getDingtalkBotToken() { return dingtalkBotToken; }
        public String getDingtalkBotSecret() { return dingtalkBotSecret; }
        public String getWeWorkBotKey() { return weWorkBotKey; }
        public String getWeWorkAppKey() { return weWorkAppKey; }
        public String getWeWorkOrigin() { return weWorkOrigin; }
        public String getEmailService() { return emailService; }
        public String getEmailUser() { return emailUser; }
        public String getEmailPass() { return emailPass; }
        public String getEmailTo() { return emailTo; }
        public String getPushPlusToken() { return pushPlusToken; }
        public String getPushPlusUser() { return pushPlusUser; }
    }
}
