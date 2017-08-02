package com.feast.demo.device.entity;

/**
 * Created by ggke on 2017/8/1.
 */

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "device_info")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *imei
     */
    private String imei;

    private Long storeId;

}
