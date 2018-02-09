package com.feast.demo.redPackage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name="redpackage_coupontemplate")
public class RedPackageCouponTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long redPackageId;

    private Long couponTemplateId;

    private Integer couponCount;
}
