package com.feast.demo.device.entity;

/**
 * Created by ggke on 2017/8/1.
 */

import com.feast.demo.store.entity.Store;
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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
    @Column(insertable = false, updatable = false, nullable = false)
    private Long storeId;

}
