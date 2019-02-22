package com.store.demo.request;

public class SpecEditForm {

    private Integer id;
    private Integer parentId;
    private String tempId;
    private String title;
    private String tempParentId;
    private Integer primary;
    private Integer goodsId;
    private Integer updateBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTempParentId() {
        return tempParentId;
    }

    public void setTempParentId(String tempParentId) {
        this.tempParentId = tempParentId;
    }

    public Integer getPrimary() {
        return primary;
    }

    public void setPrimary(Integer primary) {
        this.primary = primary;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
}