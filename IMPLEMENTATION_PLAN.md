# 青龙面板 Android App 设置功能增强计划

## 目标
在 `青龙APP_claw版` 分支上，增加青龙面板 Web UI 中已有的但 App 中缺失的设置功能。

## 现有代码结构
- 包名: `auto.panel`
- 网络层: `auto.panel.net.panel.Api` (Retrofit接口) + `ApiController` (封装调用)
- v15新版API: `auto.panel.net.panel.v15.Api` + `v15.ApiController`
- 设置页面: `PanelSettingFragment` (TabLayout + ViewPager2)
- 适配器: `PanelSettingPagerAdapter`
- 当前Tab: 常规设置(PanelSettingCommonFragment) | 登录日志(PanelSettingLoginLogFragment) | 应用设置(PanelSettingAppFragment空白)

## 需要新增的功能

### 1. Base API Config endpoints (补充青龙面板API)
从青龙面板后端分析发现以下API需要补充：
- `PUT api/system/config` - 更新系统配置 (已有v15版本)
- `GET api/system/config` - 获取系统配置 (已有)
- `GET /api/system/log` - 获取系统日志
- `DELETE /api/system/log` - 删除系统日志
- `PUT api/system/config/timezone` - 设置时区
- `PUT api/system/config/dependence-proxy` - 设置依赖代理
- `PUT api/system/config/node-mirror` - 设置Node镜像
- `PUT api/system/config/python-mirror` - 设置Python镜像
- `PUT api/system/config/linux-mirror` - 设置Linux镜像
- `PUT api/system/config/global-ssh-key` - 设置全局SSH密钥
- `PUT /api/system/notify` - 测试通知
- `GET /api/system/config` 应该返回完整配置对象

### 2. 通知设置 (Notification Settings)
- 创建 `PanelSettingNotificationFragment`
- 支持至少核心通知渠道: Server酱, 钉钉机器人, Telegram Bot, Bark, PushPlus, 飞书, 邮件, 企业微信, iGot, PushDeer 等
- 布局文件: `panel_fragment_setting_notification.xml`
- 通知渠道选择器 + 参数配置 + 测试按钮

### 3. 应用设置 (Open API Apps) - 填充当前空白
- 创建 `PanelSettingOpenAppsFragment` 替换当前空白的应用设置
- 显示Open API应用列表 (client_id, client_secret, scopes)
- 创建/编辑/删除应用
- 布局文件: `panel_fragment_setting_open_apps.xml`
- 适配器: `PanelOpenAppsAdapter`

### 4. 依赖设置 (Dependence Settings)
- 创建 `PanelSettingDependenceFragment`
- 显示依赖列表 (Node.js / Python3 / Linux)
- 清理依赖缓存
- 布局文件: `panel_fragment_setting_dependence.xml`  
- (依赖管理在主界面已经有，但设置中的依赖缓存清理功能缺失)

### 5. 扩展常规设置
- 添加: 时区设置、Node镜像、Python镜像、Linux镜像、代理设置、全局SSH Key

### 6. 系统日志
- 创建 `PanelSettingSystemLogFragment`
- 查看/清理系统日志
- 布局文件: `panel_fragment_setting_system_log.xml`

## 实现步骤

### Step 1: 扩展API层
新增/修改文件:
- `auto/panel/net/panel/Api.java` - 添加新API接口
- `auto/panel/net/panel/ApiController.java` - 添加新API调用封装
- `auto/panel/bean/panel/PanelSystemConfig.java` - 扩展系统配置Bean
- `auto/panel/net/panel/v15/SystemConfigRes.java` - 扩展配置响应

### Step 2: 添加通知设置
- `auto/panel/ui/fragment/PanelSettingNotificationFragment.java` (新)
- `res/layout/panel_fragment_setting_notification.xml` (新)

### Step 3: 填充应用设置
- `auto/panel/ui/fragment/PanelSettingOpenAppsFragment.java` (新)
- `auto/panel/bean/panel/PanelOpenApp.java` (新)
- `auto/panel/ui/adapter/PanelOpenAppsAdapter.java` (新)
- `res/layout/panel_fragment_setting_open_apps.xml` (新)

### Step 4: 添加依赖设置
- `auto/panel/ui/fragment/PanelSettingDependenceFragment.java` (新)
- `res/layout/panel_fragment_setting_dependence.xml` (新)

### Step 5: 添加系统日志
- `auto/panel/ui/fragment/PanelSettingSystemLogFragment.java` (新)
- `res/layout/panel_fragment_setting_system_log.xml` (新)

### Step 6: 更新现有代码
- `PanelSettingPagerAdapter.java` - 更新Tab列表、添加新Tab
- `PanelSettingFragment.java` - 确保能正确加载新Tab
- `strings.xml` - 添加新字符串资源

## 技术要点
- 所有新增代码放在 `青龙APP_claw版` 分支
- API调用复用现有的 `RetrofitFactory.buildWithAuthorization`
- 保持与现有UI风格一致（TabLayout切换）
- 通知设置需要一个滚动列表，每个渠道可展开配置
