package cn.zzwl.jpkit.utils;

import java.lang.reflect.Type;

@FunctionalInterface
public interface CallBack {
    String apply(Type type, String name, Object obj);
}
