package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;
import com.zzwl.jpkit.utils.ArrayUtil;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * @return Object
     */
    public static Object getArr(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Date[] res = new Date[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JDate(value.get(i)).getValue();
            }
            return res;
        });
    }

    /**
     * JArray to Date[]
     *
     * @param jBase 数据源
     * @param style 样式
     * @return Object
     */
    public static Object getArr(JBase jBase, String style) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            Date[] res = new Date[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = new JDate(value.get(i), style).getValue();
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Date>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getList(JBase jBase) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Date> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(new JDate(base).getValue());
            }
            return res;
        });
    }

    /**
     * List<JBase> 转 List<Date>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getList(JBase jBase, String style) {
        return ArrayUtil.doArrayByJArray(jBase, (value) -> {
            List<Date> res = new ArrayList<>(value.size());
            for (JBase base : value) {
                res.add(new JDate(base, style).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Date>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getMap(JBase jBase) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Date> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, new JDate(value.get(base)).getValue());
            }
            return res;
        });
    }

    /**
     * Map<String, JBase> 转 Map<String,Date>
     *
     * @param jBase 数据源
     * @return Object
     */
    public static Object getMap(JBase jBase, String style) {
        return ArrayUtil.doMapByJObject(jBase, (value) -> {
            Map<String, Date> res = new HashMap<>(value.size());
            for (String base : value.keySet()) {
                res.put(base, new JDate(value.get(base), style).getValue());
            }
            return res;
        });
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
