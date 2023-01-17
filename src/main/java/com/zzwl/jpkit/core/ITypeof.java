package com.zzwl.jpkit.core;

public interface ITypeof<T> {
    /**
     * 返回对应的值
     *
     * @return 对应值
     */
    T getValue();

    String apply(String name);
}
