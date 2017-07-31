package com.feast.demo.store.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by pinyou on 17-7-31.
 */
@Entity
@Data
@Table(name = "store_info")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 店铺名称
     */
    private String name;
}
