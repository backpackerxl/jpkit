package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.plugs.impl.JBaseEntryImpl;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 1.0
 */
public class JDate extends JBase {
    private final Date value;
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String MM_DD = "MM-dd";
    public final static String YYYY_MM = "yyyy-MM";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String HH_MM_SS = "HH:mm:ss";
    public final static String HH_MM = "HH:mm";

    public JDate(JBase jBase) {
        try {
            JLong jLong = (JLong) jBase;
            this.value = new Date(jLong.getValue());
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Date.class.getName(), e.getMessage()));
        }
    }

    public JDate(JBase jBase, String style) {
        try {
            JString jString = (JString) jBase;
            SimpleDateFormat sld = new SimpleDateFormat(style);
            this.value = sld.parse(jString.getValue());
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Date.class.getName(), e.getMessage()));
        }
    }

    /**
     * JArray to Date[]
     *
     * @param jBase 数据源
     * @return Date[]
     */
    public static Date[] getArr(JBase jBase) {
        return ArrayUtil.doArrayByArray(jBase, new Date[]{}, (jb) -> new JDate(jb).getValue());
    }

    /**
     * JArray to Date[]
     *
     * @param jBase 数据源
     * @param style 样式
     * @return Date[]
     */
    public static Date[] getArr(JBase jBase, String style) {
        return ArrayUtil.doArrayByArray(jBase, new Date[]{}, (jb) -> new JDate(jb, style).getValue());
    }

    /**
     * List<JBase> 转 List<Date>
     *
     * @param jBase 数据源
     * @return List<Date>
     */
    public static List<Date> getList(JBase jBase) {
        return Arrays.stream(getArr(jBase)).collect(Collectors.toList());
    }

    /**
     * List<JBase> 转 List<Date>
     *
     * @param jBase 数据源
     * @return List<Date>
     */
    public static List<Date> getList(JBase jBase, String style) {
        return Arrays.stream(getArr(jBase, style)).collect(Collectors.toList());
    }

    /**
     * Map<String, JBase> 转 Map<String,Date>
     *
     * @param jBase 数据源
     * @return Map<String, Date>
     */
    public static Map<String, Date> getMap(JBase jBase) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new JDate(jb.getValue()).getValue()));
    }

    /**
     * Map<String, JBase> 转 Map<String,Date>
     *
     * @param jBase 数据源
     * @return Map<String, Date>
     */
    public static Map<String, Date> getMap(JBase jBase, String style) {
        return ArrayUtil.doArrayByMap(jBase, (jb) -> new JBaseEntryImpl<>(jb.getKey(), new JDate(jb.getValue(), style).getValue()));
    }

    @Override
    public Date getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value.toString());
    }
}
