package com.zzwl.jpkit.vo;

public class LG {
    private String lgName;
    private double price;

    public LG() {
    }

    public String getLgName() {
        return lgName;
    }

    public void setLgName(String lgName) {
        this.lgName = lgName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "LG{" +
                "lgName='" + lgName + '\'' +
                ", price=" + price +
                '}';
    }
}
