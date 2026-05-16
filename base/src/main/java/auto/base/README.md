# 基础组件库 (base)

独立于业务的基础 UI 组件和工具。

## 包结构

```
auto/base/
├── ui/
│   ├── popup/             # 弹窗组件
│   │   ├── PopupWindowBuilder.java  # 弹窗构建器
│   │   └── ConfirmPopupWindow.java  # 确认弹窗
│   └── view/              # 自定义视图
│       └── ...            # 自定义控件
└── util/                  # 基础工具
    └── WindowUnit.java    # 窗口尺寸工具
```

## 与 app 模块的关系

`app` 模块依赖 `base` 模块。`base` 不依赖任何业务代码，可复用于其他项目。
