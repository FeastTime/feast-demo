package com.feast.demo.redPackage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="red_package_auto_send_time")
public class RedPackageAutoSendTime implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private Integer autoSendTime;
}

