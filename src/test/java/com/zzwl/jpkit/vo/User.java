package com.zzwl.jpkit.vo;

import com.zzwl.jpkit.anno.JDateFormat;
import com.zzwl.jpkit.anno.JIgnore;
import com.zzwl.jpkit.anno.JRename;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.typeof.JDate;

import java.util.Date;

public class User {
    @JIgnore
    private Long id;
    @JRename("username")
    private String name;
    private Integer code;
    private Boolean admin;
    //    @JIgnore
    @JRename("create_time")
    @JDateFormat(JDate.YYYY_MM_DD)
    private Date date;
//    @JIgnore
    private Integer[] nums;
//    @JIgnore
    private String[] strings;
    private int[] ints;

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public User() {
    }

    public User(Long id, String name, Integer code, boolean admin, Date date, Integer[] nums, String[] strings) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.admin = admin;
        this.date = date;
        this.nums = nums;
        this.strings = strings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JRename("user_code")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer[] getNums() {
        return nums;
    }

    public void setNums(Integer[] nums) {
        this.nums = nums;
    }

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    @Override
    public String toString() {
        return JSON.stringify(this).pretty();
    }
}
