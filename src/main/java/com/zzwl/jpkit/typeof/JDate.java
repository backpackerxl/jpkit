package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    @Override
    public Date getValue() {
        return value;
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value.toString());
    }
}
