package auto.panel.bean.panel;

/**
 * Notification mode types matching Qinglong panel's NotificationMode enum
 */
public enum PanelNotificationMode {
    serverChan("Server酱", new PanelNotificationField[]{
            new PanelNotificationField("serverChanKey", "Server酱 Key", "", true)
    }),
    pushDeer("PushDeer", new PanelNotificationField[]{
            new PanelNotificationField("pushDeerKey", "PushDeer Key", "", true),
            new PanelNotificationField("pushDeerUrl", "PushDeer URL", "https://api2.pushdeer.com", false)
    }),
    bark("Bark", new PanelNotificationField[]{
            new PanelNotificationField("barkPush", "Bark Push Key", "", true),
            new PanelNotificationField("barkIcon", "图标 URL", "https://qn.whyour.cn/logo.png", false),
            new PanelNotificationField("barkSound", "提示音", "", false),
            new PanelNotificationField("barkGroup", "分组", "qinglong", false)
    }),
    telegramBot("Telegram Bot", new PanelNotificationField[]{
            new PanelNotificationField("telegramBotToken", "Bot Token", "", true),
            new PanelNotificationField("telegramBotUserId", "用户 ID", "", true),
            new PanelNotificationField("telegramBotApiHost", "API 域名", "https://api.telegram.org", false),
            new PanelNotificationField("telegramBotProxyHost", "代理地址", "", false),
            new PanelNotificationField("telegramBotProxyPort", "代理端口", "", false),
            new PanelNotificationField("telegramBotProxyAuth", "代理认证", "", false)
    }),
    dingtalkBot("钉钉机器人", new PanelNotificationField[]{
            new PanelNotificationField("dingtalkBotToken", "Webhook Token", "", true),
            new PanelNotificationField("dingtalkBotSecret", "加签密钥", "", false)
    }),
    weWorkBot("企业微信机器人", new PanelNotificationField[]{
            new PanelNotificationField("weWorkBotKey", "Webhook Key", "", true),
            new PanelNotificationField("weWorkOrigin", "自定义域名", "https://qyapi.weixin.qq.com", false)
    }),
    weWorkApp("企业微信应用消息", new PanelNotificationField[]{
            new PanelNotificationField("weWorkAppKey", "应用 Key (corpid,corpsecret,...)", "", true),
            new PanelNotificationField("weWorkOrigin", "自定义域名", "https://qyapi.weixin.qq.com", false)
    }),
    email("邮件", new PanelNotificationField[]{
            new PanelNotificationField("emailService", "邮件服务 (如QQ)", "", true),
            new PanelNotificationField("emailUser", "邮箱账号", "", true),
            new PanelNotificationField("emailPass", "邮箱密码/授权码", "", true),
            new PanelNotificationField("emailTo", "接收邮箱（多地址用;分隔）", "", false)
    }),
    pushPlus("PushPlus", new PanelNotificationField[]{
            new PanelNotificationField("pushPlusToken", "Token", "", true),
            new PanelNotificationField("pushPlusUser", "群组编码", "", false),
            new PanelNotificationField("pushPlusTemplate", "模板", "html", false),
            new PanelNotificationField("pushplusChannel", "推送渠道", "wechat", false)
    }),
    lark("飞书", new PanelNotificationField[]{
            new PanelNotificationField("larkKey", "Webhook Key/URL", "", true),
            new PanelNotificationField("larkSecret", "签名密钥", "", false)
    }),
    iGot("iGot", new PanelNotificationField[]{
            new PanelNotificationField("iGotPushKey", "Push Key", "", true)
    }),
    goCqHttpBot("Go-CQHTTP", new PanelNotificationField[]{
            new PanelNotificationField("goCqHttpBotUrl", "URL", "", true),
            new PanelNotificationField("goCqHttpBotToken", "Token", "", true),
            new PanelNotificationField("goCqHttpBotQq", "QQ号", "", true)
    }),
    gotify("Gotify", new PanelNotificationField[]{
            new PanelNotificationField("gotifyUrl", "服务器 URL", "", true),
            new PanelNotificationField("gotifyToken", "Token", "", true),
            new PanelNotificationField("gotifyPriority", "优先级 (0-10)", "5", false)
    }),
    aibotk("AI BotK", new PanelNotificationField[]{
            new PanelNotificationField("aibotkKey", "API Key", "", true),
            new PanelNotificationField("aibotkType", "类型 (room/contact)", "room", true),
            new PanelNotificationField("aibotkName", "群名/联系人名", "", true)
    }),
    pushMe("PushMe", new PanelNotificationField[]{
            new PanelNotificationField("pushMeKey", "Push Key", "", true),
            new PanelNotificationField("pushMeUrl", "Push URL", "", false)
    }),
    webhook("Webhook", new PanelNotificationField[]{
            new PanelNotificationField("webhookUrl", "Webhook URL", "", true),
            new PanelNotificationField("webhookMethod", "请求方式 (GET/POST/PUT)", "POST", false),
            new PanelNotificationField("webhookContentType", "Content-Type", "application/json", false),
            new PanelNotificationField("webhookHeaders", "自定义 Headers (JSON)", "", false),
            new PanelNotificationField("webhookBody", "自定义 Body", "", false)
    }),
    wxPusherBot("WxPusher", new PanelNotificationField[]{
            new PanelNotificationField("wxPusherBotAppToken", "App Token", "", true),
            new PanelNotificationField("wxPusherBotTopicIds", "Topic IDs (分号分隔)", "", false),
            new PanelNotificationField("wxPusherBotUids", "UIDs (分号分隔)", "", false)
    }),
    ntfy("ntfy", new PanelNotificationField[]{
            new PanelNotificationField("ntfyUrl", "服务器 URL", "https://ntfy.sh", false),
            new PanelNotificationField("ntfyTopic", "Topic", "", true),
            new PanelNotificationField("ntfyPriority", "优先级 (1-5)", "3", false),
            new PanelNotificationField("ntfyToken", "Token", "", false),
            new PanelNotificationField("ntfyUsername", "用户名", "", false),
            new PanelNotificationField("ntfyPassword", "密码", "", false)
    }),
    chronocat("Chronocat", new PanelNotificationField[]{
            new PanelNotificationField("chronocatURL", "URL", "", true),
            new PanelNotificationField("chronocatQQ", "QQ (user_id=xxx&group_id=xxx)", "", true),
            new PanelNotificationField("chronocatToken", "Token", "", false)
    }),
    wePlusBot("WePlus Bot", new PanelNotificationField[]{
            new PanelNotificationField("wePlusBotToken", "Token", "", true),
            new PanelNotificationField("wePlusBotReceiver", "接收者", "", false),
            new PanelNotificationField("wePlusBotVersion", "版本 (pro/basic)", "pro", false)
    }),
    openiLink("OpenI Link", new PanelNotificationField[]{
            new PanelNotificationField("openiLinkAppToken", "App Token", "", true),
            new PanelNotificationField("openiLinkHubUrl", "Hub URL", "", false),
            new PanelNotificationField("openiLinkContextToken", "Context Token", "", false)
    });

    public static class PanelNotificationField {
        private String key;
        private String label;
        private String defaultValue;
        private boolean required;

        public PanelNotificationField(String key, String label, String defaultValue, boolean required) {
            this.key = key;
            this.label = label;
            this.defaultValue = defaultValue;
            this.required = required;
        }

        public String getKey() { return key; }
        public String getLabel() { return label; }
        public String getDefaultValue() { return defaultValue; }
        public boolean isRequired() { return required; }
    }

    private final String displayName;
    private final PanelNotificationField[] fields;

    PanelNotificationMode(String displayName, PanelNotificationField[] fields) {
        this.displayName = displayName;
        this.fields = fields;
    }

    public String getDisplayName() { return displayName; }
    public PanelNotificationField[] getFields() { return fields; }
}
