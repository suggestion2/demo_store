package com.store.demo.request;


import com.sug.core.util.RegexUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {
    @NotEmpty
    @Pattern(regexp = RegexUtils.REGEX_MOBILE,message = "错误的手机格式")
    private String phone;

    @NotEmpty
    @Size(min = 6,max = 15)
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
