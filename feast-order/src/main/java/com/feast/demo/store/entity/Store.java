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
    private Long id;

    /**
     *店铺名称
     */
    private String name;


    private String locate;

    private String phone;

    private BigDecimal longitude;

    private BigDecimal latitude;


//    @OneToMany(mappedBy = "store",cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    @Lazy(value = false)
//    private List<Device> devices;



}
