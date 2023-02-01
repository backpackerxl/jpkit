package com.zzwl.jpkit.anno;

import java.lang.annotation.*;

/**
 * 配置自定义解析
 *
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JSingleConfig {
    Class<?>[] type();

    Class<?> target();
}
