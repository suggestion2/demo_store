package com.store.demo.request;

import com.sug.core.platform.web.pagination.PaginationForm;

public class GoodsListForm extends PaginationForm{
    private String content;

    private Integer status;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
