package com.feast.demo.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feast.demo.device.entity.Device;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

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

//    @OneToMany(mappedBy = "store",fetch = FetchType.EAGER)
//    private Set<Device> devices;

}