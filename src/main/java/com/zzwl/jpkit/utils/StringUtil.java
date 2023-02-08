package com.zzwl.jpkit.utils;

import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.typeof.JBool;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;

public class StringUtil {

    private final static String boolPrefix = "is";
    public final static String basicGetPrefix = "get";
    public final static String basicSetPrefix = "set";


    private StringUtil() {
    }

    /**
     * 获取对应字段对应的get方法
     *
     * @param type 类型
     * @param name 字段名称
     * @return 方法名
     */
    public static String getMethodNameByFieldType(String prefix, Type type, String name) {
        String typeName = type.getTypeName();
        String format = String.format("%s%s%s", prefix, name.substring(0, 1).toUpperCase(), name.substring(1));
        if (typeName.equals(JBool.BOOLEAN)) {
            if (prefix.equals(basicSetPrefix)) {
                return format;
            }
            return String.format("%s%s%s", boolPrefix, name.substring(0, 1).toUpperCase(), name.substring(1));
        } else {
            return format;
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
            s.append(BToJSON.getTabCharacter());
        }
        return s.toString();
    }

    /**
     * 获取浏览器中文编码
     *
     * @param s  字符
     * @param ec 编码方式
     * @return 浏览器中文编码
     */
    public static String getEncodeString(String s, String ec) {
        try {
            if (!s.matches("[一-龥]")) {
                return URLEncoder.encode(s, ec);
            }
            return s;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 获取浏览器中文编码
     *
     * @param s 字符 默认UTF-8
     * @return 浏览器中文编码
     */
    public static String getEncodeString(String s) {
        return getEncodeString(s, "utf-8");
    }
}
