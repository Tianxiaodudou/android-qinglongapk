# 界面层 (ui)

## 目录结构

```
ui/
├── activity/             # Activity
│   ├── SplashActivity    # 启动页
│   ├── LoginActivity     # 登录页
│   ├── HomeActivity      # 主页（侧边栏 + Fragment 容器）
│   ├── SettingActivity   # APP 自身设置
│   ├── AccountActivity   # 账号管理
│   ├── AboutActivity     # 关于
│   ├── TextEditorActivity # 代码编辑器
│   ├── MarkdownActivity  # Markdown 查看器
│   └── PluginWebActivity # Web 插件页
│
├── adapter/              # RecyclerView 适配器
│   ├── PanelTaskItemAdapter
│   ├── PanelEnvironmentItemAdapter
│   ├── PanelScriptItemAdapter
│   ├── PanelDependenceItemAdapter
│   ├── PanelDependencePagerAdapter
│   ├── PanelLogItemAdapter
│   ├── PanelLoginLogItemAdapter
│   ├── PanelOpenAppsAdapter
│   └── PanelSettingPagerAdapter
│
└── fragment/             # Fragment（主页内容区）
    ├── PanelTaskFragment          # 定时任务
    ├── PanelEnvironmentFragment   # 环境变量
    ├── PanelScriptFragment        # 脚本管理
    ├── PanelDependenceFragment    # 依赖管理（单页）
    ├── PanelDependencePagerFragment # 依赖管理（分页）
    ├── PanelLogFragment           # 任务日志
    ├── PanelSettingFragment       # 系统设置（容器）
    │   ├── PanelSettingCommonFragment    # 常规设置
    │   ├── PanelSettingNotificationFragment # 通知设置
    │   ├── PanelSettingLoginLogFragment     # 登录日志
    │   └── PanelSettingOpenAppsFragment     # 应用设置
    ├── PanelSubscriptionFragment  # 订阅管理（规划中）
    └── PanelDiffFragment          # 对比工具（规划中）
```

## 导航机制

`HomeActivity` 使用 DrawerLayout 侧边栏 + Fragment 切换：
- 侧边栏菜单项点击 → `showFragment(Class)` → 隐藏旧 Fragment，显示新 Fragment
- Fragment 懒加载，首次显示时创建并添加到 FragmentManager

## 设置页

`PanelSettingFragment` 使用 ViewPager2 + TabLayout 实现子页切换，当前 4 个子页。
