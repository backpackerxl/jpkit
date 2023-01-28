package com.zzwl.jpkit.typeof;

import com.zzwl.jpkit.exception.JTypeofException;

import java.lang.reflect.Field;
import java.util.List;

public class JString extends JBase {
    private final String value;

    public JString(String value) {
        this.value = value;
    }

    /**
     * JArray to String[]
     *
     * @param jBase 数据源
     * @return String[]
     */
    public static String[] getArr(JBase jBase) {
        try {
            JArray jArray = (JArray) jBase;
            List<JBase> value = jArray.getValue();
            String[] res = new String[value.size()];
            for (int i = 0; i < value.size(); i++) {
                res[i] = ((JString) value.get(i)).getValue();
            }
            return res;
        } catch (Exception e) {
            // log: error the source not cast array
            throw new JTypeofException("error the source not cast array, because " + e.getMessage());
        }
    }

    @Override
    public void apply(Object obj, Field field, JBase jBase) {

    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value);
    }
}
