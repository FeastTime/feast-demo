package com.feast.demo.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by ggke on 2017/4/11.
 */
@Entity
@Table(name="TOrder")
@Data
public class TOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 乐观锁
     */
    private Long version;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "YYYY/MM/DD hh:mm:ss", timezone="GMT+8")
    @Column(updatable = false, nullable = false)
    private Timestamp creation;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "YYYY/MM/DD hh:mm:ss", timezone="GMT+8")
    @Column(updatable = false, nullable = false)
    private Timestamp lastModified;

    /**
     * 创建者类型（用户、设备）
     */
    private String creatorType;

    /**
     * 创建者id
     */
    private String creatorId;

    /**
     * 订单状态
     */
    private String state;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 逻辑删除标识
     */
    private boolean removed;


}
