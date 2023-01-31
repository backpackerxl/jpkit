package com.zzwl.jpkit.anno;

import java.lang.annotation.*;

/**
 * 将字段json格式化为字符串
 * 此注解目前仅支持对Long或long类型处理
 * <p>此注解可以作用于实体类的<em>属性</em>上</p>
 *
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JFString {
    Class<?> type() default Object.class;
}
