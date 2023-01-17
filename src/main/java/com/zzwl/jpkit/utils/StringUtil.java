package com.zzwl.jpkit.utils;

import com.zzwl.jpkit.typeof.JBool;

import java.lang.reflect.Type;

public class StringUtil {

    private final static String boolPrefix = "is";
    private final static String basicPrefix = "get";


    private StringUtil() {
    }

    /**
     * 获取对应字段对应的get方法
     *
     * @param type 类型
     * @param name 字段名称
     * @return 方法名
     */
    public static String getMethodNameByFieldType(Type type, String name) {
        String typeName = type.getTypeName();
        if (typeName.equals(JBool.BOOLEAN)) {
            return String.format("%s%s%s", boolPrefix, name.substring(0, 1).toUpperCase(), name.substring(1));
        } else {
            return String.format("%s%s%s", basicPrefix, name.substring(0, 1).toUpperCase(), name.substring(1));
        }
    }

    /**
     * 截取字符串后几个字母
     *
     * @param target 目标字符串
     * @param num    截取数
     * @return 截取后的字符串
     */
    public static String substringByNumber(String target, int num) {
        return target.substring(0, target.length() - num);
    }

    /**
     * 填充相应的空白字符
     *
     * @param num 空白字符数量
     * @return 空白占位符字符串
     */
    public static String getWhiteByNumber(int num) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++) {
            s.append(" ");
        }
        return s.toString();
    }
}
