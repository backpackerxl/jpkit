package com.zzwl.jpkit.vo;

import com.zzwl.jpkit.anno.JCollectType;
import com.zzwl.jpkit.anno.JConfig;
import com.zzwl.jpkit.anno.JRename;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.plugs.BigDecimalPlug;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@JConfig(value = BigDecimalPlug.class, typeof = {Map.class, List.class, BigDecimal.class, BigDecimal[].class,})
@JConfig(value = BigInteger.class, typeof = {BigInteger.class})
@JConfig(value = List.class, typeof = {})
public class MySQL {
    @JRename("server")
    private String serverName;
    private String version;
    private BigDecimal bigDecimal;
    private BigDecimal[] bigs;
    @JCollectType(type = BigDecimal.class)
    private List<BigDecimal> bigDecimals;
    @JCollectType(type = BigDecimal.class)
    private Map<String, BigDecimal> map;

    private List<MySQL> mySQLList;

    private Type[] types;

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
