# CI/CD 工作流

## build.yml

触发条件：推送到 `master`、`main`、`青龙APP_claw版` 分支，或手动触发。

流程：
1. 检出代码
2. 设置 JDK 17 + Android SDK
3. 自动递增版本号（versionCode = GITHUB_RUN_NUMBER，versionName = 2.1.{RUN_NUMBER}）
4. 解码签名密钥（从 Secret `KEYSTORE_BASE64`）
5. 构建 Release APK
6. 更新 `version.json`
7. 推送版本号变更
8. 创建 GitHub Release 并上传 APK

## 密钥管理

- `KEYSTORE_BASE64` — Base64 编码的签名密钥文件
- `GITHUB_TOKEN` — GitHub 自动提供，用于 Release 创建

## 版本号规则

每次构建 `versionCode` 自动递增（使用 GitHub Actions 运行编号）。
