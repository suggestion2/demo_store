package com.store.demo.request;

import com.sug.core.platform.web.pagination.PaginationForm;

public class OrderListForm extends PaginationForm{
    private String content;

    private Integer status;

    private Integer refundStatus;

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

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }
}
