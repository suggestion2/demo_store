package com.store.demo.request;

import javax.validation.constraints.NotEmpty;

public class OrderConfirmForm {
    @NotEmpty
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
