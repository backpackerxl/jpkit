package com.zzwl.jpkit.vo;

import com.zzwl.jpkit.anno.JRename;

public class MySQL {
    @JRename("server")
    private String serverName;
    private String version;
    private Integer count;
    private Double num;
    private boolean isActive;

    public MySQL(){}

    public MySQL(String version, Integer count, Double num, boolean isActive, String serverName) {
        this.version = version;
        this.count = count;
        this.num = num;
        this.isActive = isActive;
        this.serverName = serverName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        return "MySQL{" +
                "version='" + version + '\'' +
                ", count=" + count +
                ", num=" + num +
                ", isActive=" + isActive +
                ", serverName='" + serverName + '\'' +
                '}';
    }
}
