package com.zzwl.jpkit.vo;


import com.zzwl.jpkit.anno.JFString;

public class UsePlus {
    private Float f;
    @JFString
    private Long l;
    private Integer i;
    private Short s;
    private Byte b;
    private Character c;
    private Boolean bl;
    private Double d;

    public UsePlus() {
    }

    public UsePlus(Float f, Long l, Integer i, Short s, Byte b, Character c, Boolean bl, Double d) {
        this.f = f;
        this.l = l;
        this.i = i;
        this.s = s;
        this.b = b;
        this.c = c;
        this.bl = bl;
        this.d = d;
    }

    public Float getF() {
        return f;
    }

    public void setF(Float f) {
        this.f = f;
    }

    public Long getL() {
        return l;
    }

    public void setL(Long l) {
        this.l = l;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Short getS() {
        return s;
    }

    public void setS(Short s) {
        this.s = s;
    }

    public Byte getB() {
        return b;
    }

    public void setB(Byte b) {
        this.b = b;
    }

    public Character getC() {
        return c;
    }

    public void setC(Character c) {
        this.c = c;
    }

    public Boolean getBl() {
        return bl;
    }

    public void setBl(Boolean bl) {
        this.bl = bl;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }
}
