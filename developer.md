# 开发者文档

## 版本

- 文档版本：2.0
- 应用版本：2.1.x
- 面板 API 版本：v2.20.2

---

## 面板 API 结构

### 基础信息

| 项目 | 值 |
|------|-----|
| 基础路径 | `/api/` |
| 认证方式 | Token（Header: `Authorization: Bearer xxx`） |
| 响应格式 | `{"code":200,"data":...}` |

### 接口列表

#### 认证
- `POST /api/user/login` — 登录，返回 token

#### 定时任务
- `GET /api/crons` — 列表，支持 `?searchText=` 搜索
- `POST /api/crons` — 新建
- `PUT /api/crons` — 编辑
- `DELETE /api/crons` — 删除（body: `["id1","id2"]`）
- `PUT /api/crons/run` — 运行
- `PUT /api/crons/stop` — 停止
- `PUT /api/crons/disable` — 禁用
- `PUT /api/crons/enable` — 启用

请求/响应格式见 `v15/TasksRes.java`

#### 环境变量
- `GET /api/envs` — 列表，支持 `?searchText=`
- `POST /api/envs` — 新建（body: `[{"name":"","value":"","remarks":""}]`）
- `PUT /api/envs` — 编辑
- `DELETE /api/envs` — 删除（body: `["id1"]`）

#### 脚本管理
- `GET /api/scripts` — 文件树
- `GET /api/scripts?path=xxx` — 文件内容
- `PUT /api/scripts` — 保存文件（body: `{"filename":"","path":"","content":""}`）

#### 依赖管理
- `GET /api/dependencies` — 列表，支持 `?searchText=&type=`
- `POST /api/dependencies` — 新建/重装
- `DELETE /api/dependencies` — 删除

#### 订阅管理
- `GET /api/subscriptions` — 列表
- `POST /api/subscriptions` — 新建
- `PUT /api/subscriptions` — 编辑
- `DELETE /api/subscriptions` — 删除

#### 系统配置
- `GET /api/system` — 系统信息（版本号等）
- `GET /api/system/config` — 系统配置
- `PUT /api/system/config` — 更新配置
- `GET /api/system/log` — 系统日志
- `PUT /api/system/notify` — 测试通知

#### 应用管理
- `GET /api/apps` — 应用列表
- `POST /api/apps` — 新建
- `PUT /api/apps` — 编辑
- `DELETE /api/apps` — 删除

#### 其他
- `GET /api/crons/log` — 任务运行日志文件树
- `GET /api/crons/log?path=xxx` — 日志内容
- `GET /api/configs` — 配置文件列表
- `GET /api/configs/:name` — 配置文件内容

---

## 自定义接口

### 变量远程导入

url：自定义  
method：GET  
body：

```json
[
  {"name": "test", "value": "test", "remarks": "test"}
]
```

### Web 助手规则导入

url：自定义  
method：GET  
body：

```json
[
  {
    "name": "baidu",
    "url": "www.baidu.com",
    "envName": "baiduck",
    "target": "*",
    "main": "BAIDUID",
    "joinChar": ";"
  }
]
```

---

## 数据模型注意事项

面板 API 使用**蛇形命名**（snake_case），Java 模型需用 `@SerializedName` 注解映射：

```java
@SerializedName("client_id")
private String clientId;
```

日期字段为 ISO 字符串（如 `"2026-04-24T12:46:43.403Z"`），非时间戳。
