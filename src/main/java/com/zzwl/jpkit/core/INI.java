package com.zzwl.jpkit.core;


import com.zzwl.jpkit.typeof.JBase;
import com.zzwl.jpkit.utils.ReflectUtil;
import com.zzwl.jpkit.utils.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class INI {
    private final Map<String, JBase> iniMap = new LinkedHashMap<>();
    private String iniFileName;
    public static String NOTE = "@{ini_note}$";

    public INI() {
    }

    public INI(String iniFileName) {
        this.iniFileName = iniFileName;
    }

    public String getIniFileName() {
        return iniFileName;
    }

    public Map<String, JBase> getINIPool() {
        return iniMap;
    }

    public String stringify() {
        return this.toString();
    }

    public <B> String stringify(B bean) {
        return StringUtil.substringByNumber(ReflectUtil.doBeanByField(bean, (name, obj) -> {
            if (obj.toString().equals(NOTE)) {
                return String.format("[%s]\n", name);
            } else {
                return String.format("%s=%s\n", name, obj);
            }
        }), 2);
    }

    public INI parse(String ini) {
        return this;
    }

    public INI load(String ini) {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder bs = new StringBuilder();
        for (String s : iniMap.keySet()) {
            if (Objects.equals(iniMap.get(s).toString(), String.format("\"%s\"", NOTE))) {
                bs.append("[").append(s).append("]\n");
            } else {
                bs.append(s).append("=").append(iniMap.get(s).getValue()).append("\n");
            }
        }
        return bs.toString();
    }
}
