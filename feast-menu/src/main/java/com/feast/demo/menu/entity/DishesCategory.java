package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by aries on 2017/8/6.
 */
@Entity
@Data
@Table(name = "dishescategory")
public class DishesCategory {
    /**
     * 分类ID
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryid;

    /**
     * 分类名称
     */
    private String categoryname;

    /**
     * 店铺ID
     */
//    private String storeid;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "storeid", nullable = false)
    private Store store;
    @Column(insertable = false, updatable = false, nullable = false)
    private String storeid;
}