package cn.zzwl.jpkit.anno;

import cn.zzwl.jpkit.typeof.JDate;

import java.lang.annotation.*;

/**
 * Date格式化
 * <p>此注解可以作用于实体类的<em>属性</em>上</p>
 * <p>此注解需要传入一个符合格式日期样式的值</p>
 *
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JDateFormat {
    /**
     * 可以传递符合格式日期样式
     * 默认为时间戳
     *
     * @return 传递值
     */
    String value() default "#";
}
