package com.zzwl.jpkit.vo;

public class SVip extends UserInfo {
    private UserInfo user;
    private boolean isSVip;

    public SVip() {
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public boolean isSVip() {
        return isSVip;
    }

    public void setSVip(boolean SVip) {
        isSVip = SVip;
    }
}
