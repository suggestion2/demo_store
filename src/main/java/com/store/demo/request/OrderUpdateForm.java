package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderUpdateForm {

    @NotNull
    private Integer id;
    @NotEmpty
    private String number;
    @NotNull
    private Integer customerId;
    @NotEmpty
    private String customerName;
    @NotEmpty
    private String goodsName;
    @NotEmpty
    private String customerPhone;
    @NotEmpty
    private String customerAddress;
    @NotNull
    private Integer customerAddressId;
    @NotEmpty
    private String dispatchCompany;
    @NotEmpty
    private String dispatchNumber;
    @NotEmpty
    private String remarks;
    @NotEmpty
    private String cancelReason;
    @NotNull
    private BigDecimal goodsAmount;
    @NotNull
    private BigDecimal totalAmount;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Integer count;
    @NotNull
    private BigDecimal shippingCostAmount;
    @NotNull
    private Integer comment;

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }
    public String getNumber() {
    return number;
    }

    public void setNumber(String number) {
    this.number = number;
    }
    public Integer getCustomerId() {
    return customerId;
    }

    public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
    }
    public String getCustomerName() {
    return customerName;
    }

    public void setCustomerName(String customerName) {
    this.customerName = customerName;
    }
    public String getGoodsName() {
    return goodsName;
    }

    public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
    }
    public String getCustomerPhone() {
    return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
    }
    public String getCustomerAddress() {
    return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
    this.customerAddress = customerAddress;
    }
    public Integer getCustomerAddressId() {
    return customerAddressId;
    }

    public void setCustomerAddressId(Integer customerAddressId) {
    this.customerAddressId = customerAddressId;
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
    public String getRemarks() {
    return remarks;
    }

    public void setRemarks(String remarks) {
    this.remarks = remarks;
    }
    public String getCancelReason() {
    return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
    this.cancelReason = cancelReason;
    }
    public BigDecimal getGoodsAmount() {
    return goodsAmount;
    }

    public void setGoodsAmount(BigDecimal goodsAmount) {
    this.goodsAmount = goodsAmount;
    }
    public BigDecimal getTotalAmount() {
    return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
    }
    public BigDecimal getAmount() {
    return amount;
    }

    public void setAmount(BigDecimal amount) {
    this.amount = amount;
    }
    public Integer getCount() {
    return count;
    }

    public void setCount(Integer count) {
    this.count = count;
    }
    public BigDecimal getShippingCostAmount() {
    return shippingCostAmount;
    }

    public void setShippingCostAmount(BigDecimal shippingCostAmount) {
    this.shippingCostAmount = shippingCostAmount;
    }
    public Integer getComment() {
    return comment;
    }

    public void setComment(Integer comment) {
    this.comment = comment;
    }

}