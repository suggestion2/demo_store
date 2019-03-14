package com.store.demo.service.oss;

import java.util.List;

/**
 * Created by user on 2016/4/22.
 */
public class PolicyText {
    private String expiration;
    private List<Object> conditions;

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public List<Object> getConditions() {
        return conditions;
    }

    public void setConditions(List<Object> conditions) {
        this.conditions = conditions;
    }
}
