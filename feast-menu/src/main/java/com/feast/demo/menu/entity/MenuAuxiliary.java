package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by aries on 2017/8/15.
 */@Entity
@Data
@Table(name = "menuauxiliary")
public class MenuAuxiliary implements Serializable {

    @Id
    private String dishid;
    private String hotflag;
    private String eattimes;
    private String discountstime;
    private BigDecimal price;
    private String sales;
    private String starlevel;
    private String tmpid;
    private String pageid;
}
