package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by matao on 2017/8/6.
 */
@Entity
@Data
@Table(name = "menu")
public class Menu implements Serializable {

    @Id
    private String dishid;

    private String dishno;

    private String dishname;

    private String dishimgurl;

    private String tvurl;

    private String materialflag;

    private String titleadimgurl;

    private String titleadurl;

    private String detail;

    private BigDecimal cost;

    private String waittime;

    private String pungencydegree;

    private String storeid;
}
