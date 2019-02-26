package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerAddressCreateForm {

    @NotEmpty
    private String contact;
    @NotEmpty
    private String contactPhone;
    @NotEmpty
    private String province;
    @NotEmpty
    private String city;
    @NotEmpty
    private String county;
    @NotEmpty
    private String address;
    @NotNull
    private Integer primary;

    public String getContact() {
    return contact;
    }

    public void setContact(String contact) {
    this.contact = contact;
    }
    public String getContactPhone() {
    return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
    }
    public String getProvince() {
    return province;
    }

    public void setProvince(String province) {
    this.province = province;
    }
    public String getCity() {
    return city;
    }

    public void setCity(String city) {
    this.city = city;
    }
    public String getCounty() {
    return county;
    }

    public void setCounty(String county) {
    this.county = county;
    }
    public String getAddress() {
    return address;
    }

    public void setAddress(String address) {
    this.address = address;
    }
    public Integer getPrimary() {
    return primary;
    }

    public void setPrimary(Integer primary) {
    this.primary = primary;
    }

}