package com.feast.demo.order.entity;

import com.feast.demo.hibernate.TimedEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by wangpp on 2017/7/30.
 */
@Entity
@Table(name="orderinfo")
@Data
public class OrderInfo extends TimedEntity {
    /**
     * 订单号
     */
    @Id
    private long orderid;
    /**
     * 用户id
     */
    private long userid;
    /**
     * 店铺id
     */
    private long storeid;
    /**
     * 桌号
     */
    private long tableid;
//    /**
//     * 订单日期
//     */
//    private Date orderdate;
//    /**
//     * 订单时间
//     */
//    private Time ordertime;
//    /**
//     * 最后修改时间
//     */
//    private Timestamp modifytime;
    /**
     * 订单金额
     */
    private BigDecimal totalprice;
    /**
     * 优惠明细
     */
    private String discountdetail;
    /**
     * 付款金额
     */
    private BigDecimal payprice;
    /**
     * 状态 0:未下单 1:已下单 2:已完成
     */
    private Integer status;
    /**
     * 预留字段1
     */
    private String reservedfst;
    /**
     * 预留字段2
     */
    private String reservedsnd;
}
