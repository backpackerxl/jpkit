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

import com.zzwl.jpkit.typeof.JBase;

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
    
    /**
     * 将Unicode转化为正常字符
     *
     * @param s Unicode字符串
     * @return 正常字符串
     */
    private static String unicodeToString(String s) {
        if (s == null) {
            return null;
        }
        Pattern compile = Pattern.compile("\\\\u([\\w]{2,4})");
        Matcher matcher = compile.matcher(s);
        if (!matcher.find()) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        sb.append((char) Integer.parseInt(matcher.group(1), 16));
        while (matcher.find()) {
            sb.append((char) Integer.parseInt(matcher.group(1), 16));
        }
        return sb.toString();
    }

    /**
     * 将正常字符转化为Unicode
     *
     * @param s 正常字符串
     * @return Unicode字符串
     */
    private static String stringToUnicode(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            sb.append('\\').append('u').append(Integer.toHexString(c));
        }
        return sb.toString();
    }
}
```

#### Maven install

- 需要修改maven的setting文件中的<servers>标签中加入如下内容

```xml

<server>
    <id>github</id>
    <username>Backpackerxl</username>
    <password>token</password>
</server>
```

- 在项目的pom文件中加入依赖库
[`jpkit依赖`](https://github.com/Backpackerxl/jpkit/packages/)
- 加入依赖库仓库
```xml
<repositories>
<repository>
    <id>github</id>
    <name>GitHub Backpackerxl Apache Maven Packages</name>
    <url>https://maven.pkg.github.com/backpackerxl/jpkit</url>
</repository>
</repositories>
```

> 现在即可愉快的使用jpkit, token可以在issues中问我要或者发邮件，
> 网易邮箱: backpackerxh@163.com
> QQ邮箱: 946115360@qq.com

#### 更多用法 [`Doc.md`](docs/Doc.md)

#### `jpkit` [`官方API`](https://backpackerxl.github.io/jpkit/)
