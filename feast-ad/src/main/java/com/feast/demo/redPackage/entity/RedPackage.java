package com.feast.demo.redPackage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="red_package")
public class RedPackage implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long redPackageId;

    private String redPackageName;

    private Long storeId;

    private Integer isUse;

    private Date createTime;

    @Transient
    private Integer isCouponEnough;

    // 红包正在使用
    public static final Integer IS_USE = 2;

    // 红包不使用
    public static final Integer IS_NOT_USE = 1;
}
