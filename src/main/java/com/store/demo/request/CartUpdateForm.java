package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CartUpdateForm {

    @NotNull
    private Integer id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Integer count;

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
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

}