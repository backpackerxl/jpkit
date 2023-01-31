package com.zzwl.jpkit.anno;

import java.lang.annotation.*;

/**
 * List或Map做类型标记
 * 此注解仅支持对List或Map做类型标记
 * <p>此注解可以作用于实体类的<em>属性</em>上</p>
 *
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JFieldType {
    Class<?>[] type();
}
