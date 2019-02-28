package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CommentCreateForm {

    @NotEmpty
    private String comment;
    @NotNull
    private Integer stars;
    @NotNull
    private Integer orderItemId;

    public String getComment() {
    return comment;
    }

    public void setComment(String comment) {
    this.comment = comment;
    }
    public Integer getStars() {
    return stars;
    }

    public void setStars(Integer stars) {
    this.stars = stars;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }
}