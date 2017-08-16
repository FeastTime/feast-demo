package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by aries on 2017/8/6.
 */
@Entity
@Data
@Table(name = "ingredients")
public class Ingredients {
    /**
     * 食材ID
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientid;
    /**
     * 食材名称
     */
    private String ingredientname;
    /**
     * 个数
     */
    private String number;
    /**
     * 重量
     */
    private String weight;
    /**
     * 卡路里
     */
    private String calories;
    /**
     * 菜品ID
     */
    private String dishid;
}
