package com.feast.demo.coupon.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by ggke on 2017/8/2
 * 优惠券.
 */

@Entity
@Data
public class CouponTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String picture;

    private Long count;

    private String discountMessage;

    private Date createTime;

    private Date updateTime;

    private Date startTime;

    private Date endTime;

    private Integer type;

    private Long storeId;

    private Long userId;

}
