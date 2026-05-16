# 网络层 (net)

## 目录结构

```
net/
├── app/                  # 应用自身 API（版本检查、配置拉取）
│   ├── Api.java          # Retrofit 接口定义
│   ├── ApiController.java # 调用封装
│   └── BaseRes.java      # 基础响应
├── panel/                # 面板 API 旧版（兼容）
│   ├── Api.java
│   ├── ApiController.java
│   ├── NetHandler.java   # 通用网络处理
│   └── *Res.java         # 各接口响应模型
├── panel/v15/            # 面板 API v2.15+ 新版
│   ├── Api.java          # 当前主力接口
│   ├── ApiController.java
│   ├── Converter.java    # 数据转换
│   └── *Res.java         # 响应模型（TasksRes, AppsRes 等）
└── web/                  # WebView JS 桥接
    ├── WebViewBuilder.java
    ├── PanelWebJsManager.java
    └── CommonJSInterface.java
```

## 核心类

### NetManager
全局网络管理器，处理 Token 注入和请求拦截。

### RetrofitFactory
Retrofit 实例工厂，配置基础 URL、Gson 转换器、拦截器。

### NetAuthInterceptor
认证拦截器，在请求头注入 `Authorization: Bearer xxx`。

### NetBaseInterceptor
基础拦截器，处理通用请求/响应逻辑。

## API 版本

- `panel/Api` — 旧版 API（v2.12 以下），逐步废弃
- `panel/v15/Api` — 新版 API（v2.15+），当前主力，支持蛇形命名字段

## 更新通道

`app/Api` 的 `URL_BASE` 指向本仓库 `青龙APP_claw版` 分支的 `version.json`。
