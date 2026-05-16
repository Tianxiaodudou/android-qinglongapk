# 数据模型层 (bean)

## 目录

### app/ — 应用层模型
- `Version` — 版本信息，用于更新检测
- `Config` — 应用配置（Gitee/GitHub 地址、QQ 群号等）
- `Account` — 本地存储的账号信息

### panel/ — 面板业务模型
- `PanelTask` — 定时任务
- `PanelEnvironment` — 环境变量
- `PanelFile` — 脚本/日志文件
- `PanelDependence` — 依赖项
- `PanelOpenApp` — OpenAPI 应用
- `PanelSystemConfig` — 系统配置
- `PanelSystemInfo` — 系统信息
- `PanelLoginLog` — 登录日志
- `PanelNotificationMode` — 通知渠道模式
- `PanelAccount` — 面板登录账号
- `PanelSubscription` — 订阅（规划中）

## 注意事项

面板 API 返回的 JSON 字段为蛇形命名（snake_case），如 `client_id`、`createdAt` 等。
Java 类使用驼峰命名，需在字段上添加 `@SerializedName` 注解映射：

```java
import com.google.gson.annotations.SerializedName;

@SerializedName("client_id")
private String clientId;
```

日期字段为 ISO 8601 字符串（如 `"2026-04-24T12:46:43.403Z"`），使用 `String` 类型。
