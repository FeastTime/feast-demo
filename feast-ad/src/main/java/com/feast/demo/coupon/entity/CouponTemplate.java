package com.feast.demo.coupon.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ggke on 2017/8/2
 * 优惠券.
 */

@Entity
@Data
@Table(name = "coupon_template")
public class CouponTemplate implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String couponTitle;

    private String couponPicture;

    private Long couponCount;

    private String permissionsDescribed;

    private Date createTime;

    private Integer couponValidity;

    private Date lastModified;

    private Integer couponType;

    private Long storeId;

    private Long userId;

}
