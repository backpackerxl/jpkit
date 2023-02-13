package com.zzwl.jpkit.vo;

public class StringVoSerializable {
    public String write(Object obj) {
        StringVo bean = (StringVo) obj;
        return "{\"s\":" + bean.getS() + "}";
    }
}


