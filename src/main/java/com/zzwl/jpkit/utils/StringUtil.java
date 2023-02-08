package com.zzwl.jpkit.utils;

import com.zzwl.jpkit.conversion.BToJSON;
import com.zzwl.jpkit.typeof.JBool;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param s 字符
     * @return 浏览器中文编码
     */
    public static String getEncodeString(String s) {
        return replaceAll("[一-龥]+", s, StringUtil::getEncode);
    }

    /**
     * 替换匹配值
     *
     * @param regex  正则
     * @param target 字符串
     * @param func   处理函数
     * @return 结果
     */
    public static String replace(String regex, String target, Function<String, String> func) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(target);
        if (matcher.find()) {
            String group = matcher.group();
            target = target.replace(group, func.apply(group));
        }
        return target;
    }

    /**
     * 替换匹配值
     *
     * @param regex  正则
     * @param target 字符串
     * @param func   处理函数
     * @return 结果
     */
    public static String replaceAll(String regex, String target, Function<String, String> func) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(target);
        while (matcher.find()) {
            String group = matcher.group();
            target = target.replace(group, func.apply(group));
        }
        return target;
    }

    /**
     * 获取浏览器中文编码
     *
     * @param s 字符 默认UTF-8
     * @return 浏览器中文编码
     */
    public static String getEncode(String s) {
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
