package com.zzwl.jpkit.anno;

import java.lang.annotation.*;

/**
 * 配置自定义解析
 *
 * @since 1.0
 */
@Repeatable(JConfig.Group.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JConfig {

    Class<?> value();

    Class<?>[] typeof();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Group {
        JConfig[] value();
    }
}
