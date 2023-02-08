package com.zzwl.jpkit.vo;


import com.zzwl.jpkit.anno.JFString;

public class Use {
    private float f;
    @JFString
    private long l;
    private int i;
    private short s;
    private byte b;
    private char c;
    private boolean bl;
    private double d;

    public Use() {
    }

    public Use(float f, long l, int i, short s, byte b, char c, boolean bl, double d) {
        this.f = f;
        this.l = l;
        this.i = i;
        this.s = s;
        this.b = b;
        this.c = c;
        this.bl = bl;
        this.d = d;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public boolean isBl() {
        return bl;
    }

    public void setBl(boolean bl) {
        this.bl = bl;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public float getF() {
        return f;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setF(float f) {
        this.f = f;
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

    public short getS() {
        return s;
    }

    public void setS(short s) {
        this.s = s;
    }
}
