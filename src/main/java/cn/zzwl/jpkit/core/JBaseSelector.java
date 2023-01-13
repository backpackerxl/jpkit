package cn.zzwl.jpkit.core;

import cn.zzwl.jpkit.typeof.JBase;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JBaseSelector {
    private final static Map<String, JBase> jBaseMap = new HashMap<>();

        static {
//        Method[] declaredMethods = JBase.class.getDeclaredMethods();
//        jBaseMap.put(Integer.class.getName(), new JInteger(value));
//        jBaseMap.put(Long.class.getName(), new JLong(value));
//        jBaseMap.put(String.class.getName(), new JString(value));
    }

    private JBaseSelector() {
    }

    /**
     * 通过类型查找对应处理类方法
     *
     * @param className 类型
     * @return 对应处理类方法
     */
    public static JBase getJBaseByClassName(String className) {
        JBase jBase = jBaseMap.get(className);
        if (Objects.isNull(jBase)) {
            throw new RuntimeException(String.format("NoSuchMethodException: %s is not found.", className));
        }
        return jBase;
    }
}
