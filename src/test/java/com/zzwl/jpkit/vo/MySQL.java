package com.zzwl.jpkit.vo;

import com.zzwl.jpkit.anno.JFieldConfig;
import com.zzwl.jpkit.anno.JRename;
import com.zzwl.jpkit.core.JSON;
import com.zzwl.jpkit.plugs.BigDecimalPlug;

import java.math.BigDecimal;
import java.util.List;

@JFieldConfig(type = {BigDecimal.class}, target = BigDecimalPlug.class)
public class MySQL {
    @JRename("server")
    private String serverName;
    private String version;
    private BigDecimal bigDecimal;
    private List<BigDecimal> bigDecimals;

    public MySQL() {
    }

    public MySQL(String serverName, String version, BigDecimal bigDecimal) {
        this.serverName = serverName;
        this.version = version;
        this.bigDecimal = bigDecimal;
    }

    public List<BigDecimal> getBigDecimals() {
        return bigDecimals;
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
