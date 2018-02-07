package com.feast.demo.web.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CouponBean implements Serializable{

    private Long couponTemplateId;

    private Integer count;
}
