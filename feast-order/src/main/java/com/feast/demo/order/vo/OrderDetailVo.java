package com.feast.demo.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ggke on 2017/8/12.
 */
@Data
public class OrderDetailVo {

    private String storeName;

    private String dishName;

    private BigDecimal originalprice;

    private Integer status;
}
