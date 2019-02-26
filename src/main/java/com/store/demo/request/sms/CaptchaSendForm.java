package com.store.demo.request.sms;

import com.sug.core.util.RegexUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CaptchaSendForm {
    @NotEmpty
    @Pattern(regexp = RegexUtils.REGEX_MOBILE,message = "错误的手机格式")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
