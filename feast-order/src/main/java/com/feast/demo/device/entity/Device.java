package com.feast.demo.device.entity;

/**
 * Created by ggke on 2017/8/1.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feast.demo.store.entity.Store;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "device_info")
@ToString(exclude = {"store"})
@JsonIgnoreProperties(value = { "store"})
@EqualsAndHashCode(exclude = {"store"})
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

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
