package cn.zzwl.jpkit.typeof;

import cn.zzwl.jpkit.exception.JTypeofException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JDate extends JBase {
    private Date value;

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
    public String apply(String name) {
        return null;
    }
}
