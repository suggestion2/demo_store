package com.store.demo.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GoodsStatusForm {

    @NotNull
    private Integer id;

    @NotNull
    private Integer status;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}