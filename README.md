# 青龙面板 Android 客户端

基于青龙面板 v2.20.2 API 开发的安卓管理客户端，支持面板全部原生功能。

## 分支说明

- `master` —— 原作者原始代码（不可修改）
- `青龙APP_claw版` —— 当前维护分支，持续迭代中

## 运行环境

- 安卓 8.0+
- 青龙面板 v2.15+

## 功能列表

### 已实现

| 模块 | 功能 |
|------|------|
| 定时任务 | 增删改查、批量操作、运行/停止、日志查看、脚本跳转、标签分类、任务备份导入 |
| 环境变量 | 增删改查、批量操作、快捷导入、备份导入 |
| 配置文件 | 查看编辑 config.sh |
| 脚本管理 | 树形浏览、查看、编辑、删除、更新、下载 |
| 依赖管理 | 新建、删除、批量操作、日志查看 |
| 任务日志 | 按订阅/脚本树形浏览运行日志 |
| 系统设置 | 改密码、通知配置、登录日志、应用管理、日志导出 |

### 开发中

| 模块 | 进度 |
|------|------|
| 订阅管理 | 规划中 |
| 对比工具 | 规划中 |
| 系统日志 | 规划中 |
| 依赖设置 | 规划中 |
| 关于页面 | 规划中 |
| 多文件配置 | 规划中 |

## 代码结构

```
android-qinglongapk/
├── app/                          # 主应用模块
│   └── src/main/java/auto/panel/
│       ├── bean/                 # 数据模型
│       │   ├── app/              # 应用层模型（版本、配置）
│       │   └── panel/            # 面板业务模型（任务、环境变量等）
│       ├── database/             # 数据持久化
│       │   ├── db/               # SQLite 数据库
│       │   └── sp/               # SharedPreferences
│       ├── net/                  # 网络层
│       │   ├── app/              # 应用 API（版本检查、配置拉取）
│       │   ├── panel/            # 面板 API（Retrofit 接口）
│       │   │   └── v15/          # v2.15+ 新版 API
│       │   └── web/              # WebView JS 桥接
│       ├── ui/                   # 界面层
│       │   ├── activity/         # Activity
│       │   ├── adapter/          # RecyclerView 适配器
│       │   └── fragment/         # Fragment
│       └── utils/                # 工具类
├── base/                         # 基础 UI 组件
│   └── src/main/java/auto/base/
│       ├── ui/popup/             # 弹窗组件
│       ├── ui/view/              # 自定义视图
│       └── util/                 # 基础工具
└── gradle/                       # 构建配置
```

## 构建方式

推送代码到 `青龙APP_claw版` 分支，GitHub Actions 自动编译签名 APK。

手动构建：
```bash
./gradlew assembleRelease
```

签名通过环境变量配置：`KEYSTORE_FILE`、`KEYSTORE_PASSWORD`、`KEY_ALIAS`、`KEY_PASSWORD`

## 更新通道

App 启动时自动检查本分支的 `version.json`，有新版本时应用内下载安装。

## 相关项目

- [qinglong](https://github.com/whyour/qinglong) —— 青龙面板

## 交流反馈

有问题请提 Issue。
