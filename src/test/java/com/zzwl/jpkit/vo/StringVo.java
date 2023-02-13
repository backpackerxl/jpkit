package com.zzwl.jpkit.vo;

import com.zzwl.jpkit.core.JSON;

public class StringVo {
    private String s;

    public StringVo(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return JSON.stringify(this).pretty();
    }
}
