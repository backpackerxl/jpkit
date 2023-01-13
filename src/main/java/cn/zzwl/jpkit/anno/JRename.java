package cn.zzwl.jpkit.anno;

import java.lang.annotation.*;

/**
 * 更改属性名
 * <p>此注解可以作用于实体类上的<em>get方法</em>上，或者<em>属性</em>上</p>
 * <p>此注解需要传入一个要修改的属性名的值</p>
 *
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JRename {
    String value();
}
