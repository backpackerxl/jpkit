# `jpkit`

[![Security Status](https://www.oscs1024.com/platform/badge/murphysecurity/murphysec.svg?t=1)](https://www.murphysec.com/accept?code=a111c7f25ae06b96daa00627832e6b68&type=1&from=2&t=2)
[![](https://badgen.net/badge/releases/v1.0.0/purple?icon=github)](https://github.com/Backpackerxl/jpkit/releases/tag/v1.0.0)
[![](https://badgen.net/badge/maven/v1.0.0/cyan?icon=maven)](https://github.com/Backpackerxl/jpkit/packages/1773341)
[![](https://badgen.net/badge/license/Apache%20License%202.0/blue?icon=gitlab)](https://github.com/Backpackerxl/jpkit/blob/main/LICENSE)

> 这是一个用于Java和JSON之间相互转换的工具。目前，它只针对JSON实现。在未来，将实现其他文本解析。

- `jpkit` 可以使用插件支持更多的Java类型的解析，在需要时可以快速添加，在不用时可以快速抛弃，实现了动态增强其解析能力

#### 快速开始

- 下载导入`jpkit`的jar包

```java
public class Test {
    /**
     * jpkit 基础用法
     */
    @Test
    public void useJPKit() {
        // java bean
        B user = new B();
        // ...设置属性值
        // 常规转化
        String s = JSON.stringify(user).terse();
        // 格式化转化
        String s = JSON.stringify(user).pretty();
        // 将中文以Unicode形式转化
        String s = JSON.stringify(user).ucpJSON();
        // 保存在本地, 默认格式化保存, 无返回值
        JSON.stringify(user).save();
        // json 字符串
        String json = "...";
        // 将json解析为JBase
        JBase parse = (JBase) JSON.parse(json);
        // 将json解析为Java Bean
        B user = JSON.parse(json, B.class);
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

#### Maven install

**依次执行一下命令**
```
git clone https://github.com/Backpackerxl/jpkit.git
cd jpkit
mvn install
```

**在需要使用的项目中加入依赖**
```xml
<dependency>
    <groupId>com.zzwl</groupId>
    <artifactId>jpkit</artifactId>
    <version>${version}</version>
</dependency>
```

#### 更多用法 [`Doc.md`](docs/Doc.md)

#### `jpkit` [`官方API`](https://backpackerxl.github.io/jpkit/)

#### `jpkit` 应用示例代码 [`JCurlApp`](src/test/java/com/zzwl/jpkit/plug/JCurlApp.java)
