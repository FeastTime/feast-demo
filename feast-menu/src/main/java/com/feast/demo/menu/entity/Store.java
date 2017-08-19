package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by matao on 2017/8/2.
 */

@Entity
@Data
@Table(name = "store_info")
public class Store {
    @Id
    private String id;
    private String name;
}
