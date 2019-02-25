package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GoodsUpdateForm {

    @NotNull
    private Integer id;
    @NotNull
    private Integer categoryId1;
    @NotNull
    private Integer categoryId2;
    @NotEmpty
    private String category1;
    @NotEmpty
    private String category2;
    @NotEmpty
    private String name;
    @NotEmpty
    private String bannerUrl;
    @NotEmpty
    private String imagesUrl;
    @NotEmpty
    private String remarks;

    private List<SpecEditListForm> specList;

    private List<SpecUnitEditForm> unitList;

    public List<SpecEditListForm> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SpecEditListForm> specList) {
        this.specList = specList;
    }

    public List<SpecUnitEditForm> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<SpecUnitEditForm> unitList) {
        this.unitList = unitList;
    }

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }
    public Integer getCategoryId1() {
    return categoryId1;
    }

    public void setCategoryId1(Integer categoryId1) {
    this.categoryId1 = categoryId1;
    }
    public Integer getCategoryId2() {
    return categoryId2;
    }

    public void setCategoryId2(Integer categoryId2) {
    this.categoryId2 = categoryId2;
    }
    public String getCategory1() {
    return category1;
    }

    public void setCategory1(String category1) {
    this.category1 = category1;
    }
    public String getCategory2() {
    return category2;
    }

    public void setCategory2(String category2) {
    this.category2 = category2;
    }
    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }
    public String getBannerUrl() {
    return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
    this.bannerUrl = bannerUrl;
    }
    public String getImagesUrl() {
    return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
    this.imagesUrl = imagesUrl;
    }
    public String getRemarks() {
    return remarks;
    }

    public void setRemarks(String remarks) {
    this.remarks = remarks;
    }

}