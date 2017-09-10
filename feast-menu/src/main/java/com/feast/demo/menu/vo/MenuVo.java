package com.feast.demo.menu.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by matao on 2017/8/12.
 */
@Data
public class MenuVo {
    private String dishId;
    private String dishNo;
    private String dishImgUrl;
    private String tvUrl;
    private String hotFlag;
    private String materialFlag;
    private String titleAdImgUrl;
    private String titleAdUrl;
    private String eatTimes;
    private String dishName;
    private String detail;
    private String discountsTime;
    private BigDecimal cost;
    private BigDecimal price;
    private String sales;
    private String waitTime;
    private String exponent;
    private String starLevel;
    private String pungencyDegree;
    private String tmpId;
    private String pageId;
    private String categoryId;
    private String categoryName;
}

