package com.feast.demo.redPackage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

    private Integer autoSendTime;

}
