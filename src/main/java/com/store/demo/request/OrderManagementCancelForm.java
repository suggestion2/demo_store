package com.store.demo.request;

import javax.validation.constraints.NotEmpty;

public class OrderManagementCancelForm {
    @NotEmpty
    private Integer id;

    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
