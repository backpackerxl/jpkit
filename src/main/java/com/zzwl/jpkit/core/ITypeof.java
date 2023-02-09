package com.zzwl.jpkit.core;

/**
 * 解析返回类型接口
 *
 * @param <T> 解析类型
 * @since 1.0
 */
public interface ITypeof<T> {
    /**
     * 返回具体对象的值
     *
     * @return 具体对象的值
     */
    T getValue();
}
