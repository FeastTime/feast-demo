package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by matao on 2017/8/6.
 */
@Entity
@Data
@Table(name = "categorymenu")
public class CategoryMenu implements Serializable {

    @Id
    private String categoryid;

    @Id
    private String dishid;

    private int sort;
}