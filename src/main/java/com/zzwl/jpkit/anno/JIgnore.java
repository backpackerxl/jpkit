package com.zzwl.jpkit.anno;

import java.lang.annotation.*;


/**
 * 忽略属性值
 * <p>此注解可以作用于实体类上的<em>get方法</em>上，或者<em>属性</em>上</p>
 *
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JIgnore {
}
