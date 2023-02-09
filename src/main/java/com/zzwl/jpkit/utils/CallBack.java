package com.zzwl.jpkit.utils;

/**
 * @since 1.0
 */
@FunctionalInterface
public interface CallBack {
    /**
     * 为对象操作属性值提供回调
     *
     * @param name 字段名称
     * @param obj  字段值
     * @return 回调实现的字符串
     */
    String apply(String name, Object obj);
}
