package com.store.demo.request;

import javax.validation.constraints.NotNull;

public class OrderDispatchForm {
    @NotNull
    private Integer id;

    private String dispatchCompany;

    private String dispatchNumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDispatchCompany() {
        return dispatchCompany;
    }

    public void setDispatchCompany(String dispatchCompany) {
        this.dispatchCompany = dispatchCompany;
    }

    public String getDispatchNumber() {
        return dispatchNumber;
    }

    public void setDispatchNumber(String dispatchNumber) {
        this.dispatchNumber = dispatchNumber;
    }
}
