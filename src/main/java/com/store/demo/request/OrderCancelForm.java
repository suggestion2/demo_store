package com.store.demo.request;

import javax.validation.constraints.NotEmpty;

public class OrderCancelForm {
    @NotEmpty
    private String number;

    private String reason;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
