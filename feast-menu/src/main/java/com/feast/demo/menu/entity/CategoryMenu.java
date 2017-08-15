package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by aries on 2017/8/6.
 */
@Entity
@Data
@Table(name = "categorymenu")
public class CategoryMenu implements Serializable {
    private String categoryid;
    private String dishid;
    private int sort;
}