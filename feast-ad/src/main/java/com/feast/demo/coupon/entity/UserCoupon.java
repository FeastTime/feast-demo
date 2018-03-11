package com.feast.demo.coupon.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "user_coupon")
@Entity
public class UserCoupon implements Serializable{

    public static final int ISUSE_UNUSED = 1;

    public static final int ISUSE_USED = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private Long userId;

    private String couponCode;

    private Long storeId;

    private String permissionsDescribed;

    private Date couponValidity;

    private Integer isUse;

    private String couponPicture;

    private String couponTitle;

    private Date startTime;

    private Date useTime;

    private Integer couponType;

    private Date takeTime;

    private String storeName;

}
