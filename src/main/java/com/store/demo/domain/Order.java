package com.store.demo.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private Integer id;
    private String number;
    private Integer customerId;
    private String customerName;
    private String goodsName;
    private String customerPhone;
    private String customerAddress;
    private Integer customerAddressId;
    private String dispatchCompany;
    private String dispatchNumber;
    private String remarks;
    private String cancelReason;
    private BigDecimal goodsAmount;
    private BigDecimal totalAmount;
    private Integer count;
    private BigDecimal shippingCostAmount;
    private Integer comment;
    private Date createTime;
    private Date updateTime;
    private Integer createBy;
    private Integer updateBy;
    private Integer status;
    private Integer valid;

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
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

}