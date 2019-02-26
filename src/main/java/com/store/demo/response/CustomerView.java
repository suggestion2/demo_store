package com.store.demo.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sug.core.util.jsonFormat.SimpleDateSerializer;

import java.util.Date;

public class CustomerView {
    private String name;
    private String phone;
    private String uuid;
    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date createTime;
    private Integer hasOpenId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public Integer getHasOpenId() {
        return hasOpenId;
    }

    public void setHasOpenId(Integer hasOpenId) {
        this.hasOpenId = hasOpenId;
    }
}
