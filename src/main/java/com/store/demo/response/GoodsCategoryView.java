package com.store.demo.response;

import java.util.List;

public class GoodsCategoryView {
    private Integer id;

    private String name;

    private Integer status;

    private List<GoodsCategoryView> list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<GoodsCategoryView> getList() {
        return list;
    }

    public void setList(List<GoodsCategoryView> list) {
        this.list = list;
    }
}
