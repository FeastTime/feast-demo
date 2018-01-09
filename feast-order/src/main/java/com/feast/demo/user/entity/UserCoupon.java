package com.feast.demo.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "user_coupon")
@Entity
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long coupon_Id;

    private String code;

    private Long storeId;

    private String discountMessage;

    private Date startTime;

    private Date endTime;

    private Integer isUse;
}
