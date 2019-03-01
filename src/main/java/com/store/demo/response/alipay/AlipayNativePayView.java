package com.store.demo.response.alipay;

public class AlipayNativePayView {
    private String formString;

    public AlipayNativePayView(String formString) {
        this.formString = formString;
    }

    public String getFormString() {
        return formString;
    }

    public void setFormString(String formString) {
        this.formString = formString;
    }
}
