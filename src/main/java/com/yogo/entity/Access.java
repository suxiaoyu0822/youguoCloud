package com.yogo.entity;

public class Access {
    private Integer accessId;

    private String name;

    public Integer getAccessId() {
        return accessId;
    }

    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "Access{" +
                "accessId=" + accessId +
                ", name='" + name + '\'' +
                '}';
    }
}