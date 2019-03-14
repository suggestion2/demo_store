package com.store.demo.request;

import javax.validation.constraints.NotNull;

public class CustomerAddressPrimaryForm {
    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
