package com.feast.demo.store.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by ggke on 2017/8/2.
 */

@Entity
@Data
@Table(name = "store_info")
//@ToString(exclude = {"devices"})
//@JsonIgnoreProperties(value = { "devices"})
//@EqualsAndHashCode(exclude = {"devices"})
public class Store implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    /**
     *店铺名称
     */
    private String storeName;


    private String locate;

    private String phone;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String storeIcon;

    private String storePicture;

//    @OneToMany(mappedBy = "store",cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    @Lazy(value = false)
//    private List<Device> devices;



}
