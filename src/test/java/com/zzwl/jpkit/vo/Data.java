package com.zzwl.jpkit.vo;

public class Data {
    private int code;
    private long id;
    private short age;
    private byte set;
    private boolean ig;
    private char sex;
    private float price;
    private double ag;

    public Data(int code, long id, short age, byte set, boolean ig, char sex, float price, double ag) {
        this.code = code;
        this.id = id;
        this.age = age;
        this.set = set;
        this.ig = ig;
        this.sex = sex;
        this.price = price;
        this.ag = ag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public byte getSet() {
        return set;
    }

    public void setSet(byte set) {
        this.set = set;
    }

    public boolean isIg() {
        return ig;
    }

    public void setIg(boolean ig) {
        this.ig = ig;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getAg() {
        return ag;
    }

    public void setAg(double ag) {
        this.ag = ag;
    }

    @Override
    public String toString() {
        return "Data{" +
                "code=" + code +
                ", id=" + id +
                ", age=" + age +
                ", set=" + set +
                ", ig=" + ig +
                ", sex=" + sex +
                ", price=" + price +
                ", ag=" + ag +
                '}';
    }
}
