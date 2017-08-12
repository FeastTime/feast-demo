package com.feast.demo.web.entity;

import lombok.Data;

/**
 * Created by aries on 2017/5/14.
 */
@Data
public class DishesCategoryList {

    private String categoryName;
    private String categoryId;
    private String classType;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
