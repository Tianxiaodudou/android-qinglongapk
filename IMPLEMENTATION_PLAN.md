# 青龙 APP 完整开发计划

> 分支：`青龙APP_claw版`  
> 目标：APP 功能与网页端完全对齐 + 应用内更新通道

---

## 当前状态 vs 目标

### 侧边栏

| # | 网页端 | APP 现状 |
|---|--------|---------|
| 1 | 定时任务 `/crontab` | ✅ 有，缺标签分类/批量/运停 |
| 2 | **订阅管理** `/subscription` | ❌ 缺失 |
| 3 | 环境变量 `/env` | ✅ |
| 4 | 配置文件 `/config` | ⚠️ 只支持 config.sh |
| 5 | 脚本管理 `/script` | ✅ |
| 6 | 依赖管理 `/dependence` | ✅ |
| 7 | 日志管理 `/log` | ✅ |
| 8 | **对比工具** `/diff` | ❌ 缺失 |
| 9 | 系统设置 `/setting` | ⚠️ 4/8 子标签 |

### 系统设置子标签

| 子标签 | 状态 |
|--------|------|
| 安全设置 | ✅ 改密，缺头像 |
| 应用设置 | ✅ |
| 通知设置 | ✅ |
| **系统日志** | ❌ 缺失 |
| 登录日志 | ✅ |
| **依赖设置** | ❌ 缺失 |
| 其他设置 | ⚠️ 缺主题/时区/SSH/备份/重启 |
| **关于** | ❌ 缺失 |

---

## 执行阶段

### 阶段 1：更新通道重写 ✅ 已完成

| # | 任务 | 状态 |
|---|------|------|
| 1.1 | 根目录 `version.json` | ✅ |
| 1.2 | `Api.java` URL_BASE 改指向 | ✅ |
| 1.3 | `build.yml` 自动更新 + Release | ✅ |
| 1.4 | `HomeActivity` 应用内下载安装 | ✅ |
| 1.5 | `AndroidManifest.xml` FileProvider | ✅ |
| 1.6 | `DownloadUtil.java` | ✅ |
| 1.7 | 全项目 .md 文档 | ✅ |

### 阶段 2：API 层补齐

| # | 任务 |
|---|------|
| 2.1 | `v15/Api.java` 补订阅/系统日志/配置列表接口 |
| 2.2 | 对应 `*Res.java` 数据模型 |

### 阶段 3：新增页面

| # | 页面 | 文件 |
|---|------|------|
| 3.1 | 订阅管理 | Fragment + Adapter + XML |
| 3.2 | 对比工具 | Fragment + XML |
| 3.3 | 系统日志 | Fragment + XML（设置子页） |
| 3.4 | 依赖设置 | Fragment + XML（设置子页） |
| 3.5 | 关于 | Fragment + XML（设置子页） |

### 阶段 4：完善现有功能

| # | 功能 | 说明 |
|---|------|------|
| 4.1 | 定时任务增强 | 标签分类 + 批量 + 运行/停止 |
| 4.2 | 配置文件多文件 | 下拉选择文件列表 |
| 4.3 | 其他设置补全 | 主题/时区/SSH/备份/重启 |

### 阶段 5：导航重组

| # | 任务 |
|---|------|
| 5.1 | 侧边栏加新菜单项 |
| 5.2 | 设置 ViewPager 4→8 |

---

## 文件清单

### 新增文件
- `version.json`
- `app/src/main/res/xml/file_paths.xml`
- `app/src/main/java/auto/panel/utils/DownloadUtil.java`
- `app/src/main/java/auto/panel/ui/fragment/PanelSubscriptionFragment.java`
- `app/src/main/java/auto/panel/ui/fragment/PanelDiffFragment.java`
- `app/src/main/java/auto/panel/ui/fragment/PanelSettingSystemLogFragment.java`
- `app/src/main/java/auto/panel/ui/fragment/PanelSettingDependenceFragment.java`
- `app/src/main/java/auto/panel/ui/fragment/PanelSettingAboutFragment.java`
- `app/src/main/java/auto/panel/ui/adapter/PanelSubscriptionItemAdapter.java`
- `app/src/main/java/auto/panel/bean/panel/PanelSubscription.java`
- `app/src/main/java/auto/panel/net/panel/v15/SubscriptionsRes.java`
- `res/layout/panel_fragment_subscription*.xml`
- `res/layout/panel_fragment_diff.xml`
- `res/layout/panel_fragment_setting_system_log.xml`
- `res/layout/panel_fragment_setting_dependence.xml`
- `res/layout/panel_fragment_setting_about.xml`

### 修改文件
- `app/src/main/java/auto/panel/net/app/Api.java`
- `.github/workflows/build.yml`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/auto/panel/ui/activity/HomeActivity.java`
- `app/src/main/java/auto/panel/ui/adapter/PanelSettingPagerAdapter.java`
- `app/src/main/java/auto/panel/ui/fragment/PanelSettingFragment.java`
- `app/src/main/java/auto/panel/net/panel/v15/Api.java`
- `app/src/main/res/layout/activity_home.xml`
- `app/src/main/res/values/strings.xml`
