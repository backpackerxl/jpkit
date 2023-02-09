package com.zzwl.jpkit.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0
 */
public final class JPConfigAnnoContext {
    private final Map<String, JPConfigAnno> context = new HashMap<>();

    private JPConfigAnnoContext() {
    }

    private volatile static JPConfigAnnoContext annoConfigContext;

    /**
     * 获取配置注解的容器
     *
     * @return 配置注解的容器
     */
    public static JPConfigAnnoContext getAnnoConfigContext() {
        if (annoConfigContext == null) {
            synchronized (JPConfigAnnoContext.class) {
                if (annoConfigContext == null) {
                    annoConfigContext = new JPConfigAnnoContext();
                }
            }
        }
        return annoConfigContext;
    }

    public Map<String, JPConfigAnno> getContext() {
        return context;
    }
}
