package com.zzwl.jpkit.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0
 */
public final class Options {
    private final Map<String, String> pram;
    private final Map<String, Object> data;

    private Options() {
        pram = new HashMap<>();
        data = new HashMap<>();
    }


    /**
     * 获取实例
     *
     * @return 实例
     */
    public static Options getInstance() {
        return new Options();
    }

    /**
     * 获取请求头参数
     *
     * @return 请求头参数
     */
    public Map<String, String> getPram() {
        return pram;
    }

    /**
     * 获取请求参数
     *
     * @return 请求参数
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * 设置请求头参数
     *
     * @param key   请求头参数
     * @param value 请求头参数值
     * @return 链式调用
     */
    public Options setPram(String key, String value) {
        pram.put(key, value);
        return this;
    }

    /**
     * 设置请求参数
     *
     * @param key   请求参数
     * @param value 请求参数值
     * @return 链式调用
     */
    public Options setData(String key, Object value) {
        data.put(key, value);
        return this;
    }
}
