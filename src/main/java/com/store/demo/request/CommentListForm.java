package com.store.demo.request;

import com.sug.core.platform.web.pagination.PaginationForm;

import javax.validation.constraints.NotNull;

public class CommentListForm extends PaginationForm{
    @NotNull
    private Integer goodsId;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
}
