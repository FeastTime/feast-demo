package com.feast.demo.store.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by ggke on 2017/8/2.
 */

@Entity
@Data
@Table(name = "store_info")
//@ToString(exclude = {"devices"})
//@JsonIgnoreProperties(value = { "devices"})
//@EqualsAndHashCode(exclude = {"devices"})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *店铺名称
     */
    private String name;

//    @OneToMany(mappedBy = "store",cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    @Lazy(value = false)
//    private List<Device> devices;

}
