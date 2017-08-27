package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by matao on 2017/8/6.
 */
@Entity
@Data
@Table(name = "dishescategory")
public class DishesCategory implements Serializable {

    @Id
    private String categoryid;
    private String categoryname;
    private String storeid;
}