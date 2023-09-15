package com.zzwl.jpkit.vo;

import com.zzwl.jpkit.anno.JRename;
import com.zzwl.jpkit.core.JSON;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MySQL {
    @JRename("server")
    private String serverName;
    private String version;
    private BigDecimal bigDecimal;
    private BigDecimal[] bigs;
    private List<BigDecimal> bigDecimals;
    private Map<String, BigDecimal> map;
    private List<MySQL> mySQLList;
    private Type[] types;
    private Type type;

    public MySQL() {
    }

    public MySQL(String serverName, String version, BigDecimal bigDecimal) {
        this.serverName = serverName;
        this.version = version;
        this.bigDecimal = bigDecimal;
    }

    public MySQL(String serverName, String version, BigDecimal bigDecimal, BigDecimal[] bigs) {
        this.serverName = serverName;
        this.version = version;
        this.bigDecimal = bigDecimal;
        this.bigs = bigs;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    public List<BigDecimal> getBigDecimals() {
        return bigDecimals;
    }

    public List<MySQL> getMySQLList() {
        return mySQLList;
    }

    public Map<String, BigDecimal> getMap() {
        return map;
    }

    public void setMap(Map<String, BigDecimal> map) {
        this.map = map;
    }

    public BigDecimal[] getBigs() {
        return bigs;
    }

    public void setBigs(BigDecimal[] bigs) {
        this.bigs = bigs;
    }

    public void setMySQLList(List<MySQL> mySQLList) {
        this.mySQLList = mySQLList;
    }

    public void setBigDecimals(List<BigDecimal> bigDecimals) {
        this.bigDecimals = bigDecimals;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        return JSON.stringify(this).pretty();
    }
}
