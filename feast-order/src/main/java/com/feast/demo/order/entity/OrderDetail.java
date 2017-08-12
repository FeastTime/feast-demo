package com.feast.demo.order.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by ggke on 2017/8/9.
 */
@Entity
@Data
@Table(name = "orderdetail")
public class OrderDetail implements Serializable{

    private Long userid;// bigint(20) NOT NULL COMMENT '用户id',

    @Id
    private Long orderid;// bigint(20) NOT NULL COMMENT '订单号',

    @Id
    private Long dishid;// bigint(20) NOT NULL COMMENT '菜品id',

    private Long dishno;// varchar(20) DEFAULT NULL COMMENT '菜品编号',

    private String dishname;// varchar(30) DEFAULT NULL COMMENT '菜品名称',
    private BigDecimal originalprice;// decimal(10,2) DEFAULT NULL COMMENT '原价',
    private BigDecimal actualprice;// decimal(10,2) DEFAULT NULL COMMENT '实际价格',
    private String dishimgurl;// varchar(50) DEFAULT NULL COMMENT '菜品图片',
    private Long catagrayid;// bigint(20) DEFAULT NULL COMMENT '分类id',
    private String catagrayname;// varchar(20) DEFAULT NULL COMMENT '分类名称',
    private Integer amount;// int(11) DEFAULT NULL COMMENT '数量',
    private BigDecimal totalprice;// decimal(10,2) DEFAULT NULL COMMENT '总价',
    private Timestamp starttime;// time DEFAULT NULL COMMENT '开始时间',
    private Integer needtime;// int(11) DEFAULT NULL COMMENT '需要时间',
    private Integer restrictid;// int(11) DEFAULT NULL COMMENT '忌口id',
    private Integer status;// tinyint(1) DEFAULT NULL COMMENT '状态 0:未开始 1:下锅 2完成 3异常',
    private String reservedfst;// varchar(20) DEFAULT NULL COMMENT '预留字段1',
    private String reservedsnd;// varchar(20) DEFAULT NULL COMMENT '预留字段2',
}
