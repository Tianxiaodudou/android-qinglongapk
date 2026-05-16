# 数据持久化层 (database)

## 目录结构

```
database/
├── db/                    # SQLite 数据库
│   ├── DBHelper.java      # 数据库创建/升级
│   ├── AccountContract.java # 账号表契约
│   └── AccountDataSource.java # 账号数据操作
│
└── sp/                    # SharedPreferences
    ├── PanelPreference.java   # 面板相关偏好（地址、token）
    └── SettingPreference.java # APP 设置偏好（通知开关等）
```

## 存储说明

- **账号信息**：SQLite，存储多面板地址、用户名、token
- **面板连接信息**：SharedPreferences，当前面板地址、认证 token
- **APP 设置**：SharedPreferences，通知开关、更新检测结果缓存
- **文件备份**：外部存储 `Android/data/auto.panel/files/`
