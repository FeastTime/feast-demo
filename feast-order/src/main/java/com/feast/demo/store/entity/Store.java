package com.feast.demo.store.entity;

import com.sun.org.glassfish.gmbal.ManagedAttribute;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by ggke on 2017/8/2.
 */

@Entity
@Data
@Table(name = "store_info")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *店铺名称
     */
    private String name;

}
