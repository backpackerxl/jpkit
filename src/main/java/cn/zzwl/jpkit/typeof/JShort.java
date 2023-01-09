package cn.zzwl.jpkit.typeof;

import cn.zzwl.jpkit.exception.JTypeofException;

public class JShort extends JBase {

    private final Short value;

    public JShort(JBase jBase) {
        try {
            JInteger jInteger = (JInteger) jBase;
            this.value = Short.valueOf(jInteger.getValue().toString());
        } catch (Exception e) {
            throw new JTypeofException(String.format("the %s can't to %s, because of %s", jBase.getValue(), Short.class.getName(), e.getMessage()));
        }
    }

    @Override
    public Short getValue() {
        return value;
    }

    @Override
    public String apply(String name) {
        return null;
    }
}
