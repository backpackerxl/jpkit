package cn.zzwl.jpkit.vo;

import cn.zzwl.jpkit.anno.JIgnore;

public class User {
    @JIgnore
    private Long id;
    private String name;
    private Integer code;
    private Boolean admin;

    public User() {
    }

    public User(Long id, String name, Integer code, boolean admin) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.admin = admin;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code=" + code +
                ", admin=" + admin +
                '}';
    }
}
