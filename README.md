# `jpkit`

[![Security Status](https://www.oscs1024.com/platform/badge/murphysecurity/murphysec.svg?t=1)](https://www.murphysec.com/accept?code=a111c7f25ae06b96daa00627832e6b68&type=1&from=2&t=2)

> 这是一个用于Java和JSON之间相互转换的工具。目前，它只针对JSON实现。在未来，将实现其他文本解析。

- `jpkit` 可以使用插件支持更多的Java类型的解析，在需要时可以快速添加，在不用时可以快速抛弃，实现了动态增强其解析能力

#### 快速开始

```java

import com.zzwl.jpkit.typeof.JBase;

public class Test {
    /**
     * jpkit 基础用法
     */
    @Test
    public void useJPKit() {
        // json 字符串
        String json = "...";
        // 将json解析为JBase
        JBase parse = (JBase) JSON.parse(json);
        // 将json解析为Java Bean
        B user = JSON.parse(json, B.class);
        // java bean
        B user = new B();
        // 常规转化
        String s = JSON.stringify(user).terse();
        // 格式化转化
        String s = JSON.stringify(user).pretty();
        // 保存在本地, 默认格式化保存, 无返回值
        JSON.stringify(user).save();
        // 本地json
        String user_path = "src\\main\\resources\\db.json";
        // 网络json
        String url = "https://xxxx.json";
        // 加载网络json
        JBase net_local = (JBase) JSON.load(url);
        // 加载为java对象
        B net_local = JSON.load(user_path, B.class);
    }
}
```

#### 更多用法 [`Doc.md`](docs/Doc.md)
