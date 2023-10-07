package com.zzwl.jpkit.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 1.0
 */
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
        if (typeName.equals(boolean.class.getTypeName()) || typeName.equals(Boolean.class.getTypeName())) {
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
    public static String getWhiteByNumber(int num, char c) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++) {
            s.append(c);
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
        if (s == null) {
            return null;
        }
        Pattern compile = Pattern.compile("[一-龥]+");
        Matcher matcher = compile.matcher(s);
        if (!matcher.find()) {
            return s;
        }
        String res = s;
        String temp = matcher.group();
        res = res.replace(temp, getEncode(temp));
        while (matcher.find()) {
            String t = matcher.group();
            res = res.replace(t, getEncode(t));
        }
        return res;
    }

    /**
     * 替换匹配值
     *
     * @param regex  正则
     * @param target 字符串
     * @param func   处理函数
     * @return 替换结果
     */
    public static String replace(String regex, String target, Function<String, String> func) {
        if (target == null) {
            return null;
        }
        if (regex == null) {
            return target;
        }
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(target);
        if (matcher.find()) {
            String group = matcher.group();
            if (func != null) {
                target = target.replace(group, func.apply(group));
            }
        }
        return target;
    }

    /**
     * 替换匹配值
     *
     * @param regex  正则
     * @param target 字符串
     * @param func   处理函数
     * @return 替换结果
     */
    public static String replaceAll(String regex, String target, Function<String, String> func) {
        if (target == null) {
            return null;
        }
        if (regex == null) {
            return target;
        }
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(target);
        if (!matcher.find()) {
            return target;
        }
        String temp = matcher.group();
        if (func != null) {
            target = target.replace(temp, func.apply(temp));
        }
        while (matcher.find()) {
            String group = matcher.group();
            if (func != null) {
                target = target.replace(group, func.apply(group));
            }
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

    /**
     * 将Unicode转化为正常字符
     *
     * @param s Unicode字符串
     * @return 正常字符串
     */
    public static String unicodeToString(String s) {
        if (s == null) {
            return null;
        }
        Pattern compile = Pattern.compile("\\\\u([\\w]{2,4})");
        Matcher matcher = compile.matcher(s);
        if (!matcher.find()) {
            return s;
        }
        String temp = matcher.group(1);
        s = s.replace("\\\\u" + temp, String.valueOf((char) Integer.parseInt(temp, 16)));
        while (matcher.find()) {
            String t = matcher.group(1);
            s = s.replace("\\\\u" + t, String.valueOf((char) Integer.parseInt(t, 16)));
        }
        return s;
    }

    /**
     * 将正常字符转化为Unicode
     *
     * @param s 正常字符串
     * @return Unicode字符串
     */
    public static String stringToUnicode(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            sb.append('\\').append('\\').append('u').append(Integer.toHexString(c));
        }
        return sb.toString();
    }

    /**
     * 是否以Unicode的形式存储中文
     *
     * @param s       字符串
     * @param unicode 是否转化
     * @return unicode形式的中文字符串
     */
    public static String doChinese(String s, boolean unicode) {
        if (!unicode) {
            return s;
        }
        Pattern compile = Pattern.compile("\"(.*?)\"");
        Matcher matcher = compile.matcher(s);
        while (matcher.find()) {
            String s1 = matcher.group(1);
            if (isChinese(s1)) {
                s = s.replace(s1, stringToUnicode(s1));
            }
        }
        return s;
    }

    /**
     * 判断是否为中文包含中文标点
     *
     * @param s 字符串
     * @return 是否为中文
     */
    public static boolean isChinese(String s) {
        try {
            return s.length() != s.getBytes("GBK").length;
        } catch (Exception e) {
            return false;
        }
    }
}
