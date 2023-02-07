package com.zzwl.jpkit.anno;

import java.lang.annotation.*;

/**
 * 指定插件实现自定义解析
 * <p>此注解可以作用于实体类的<em>属性</em>上</p>
 *
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JParse {
    String method();

    int pos() default 0;
}
