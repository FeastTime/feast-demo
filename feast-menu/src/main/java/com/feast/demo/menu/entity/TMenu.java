package com.feast.demo.menu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by ggke on 2017/4/11.
 */

@Entity
@Table(name="TMenu")
@Data
public class TMenu implements Serializable {


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
     * 修改时间
     */
    @JsonFormat(pattern = "YYYY/MM/DD hh:mm:ss", timezone="GMT+8")
    @Column(updatable = false, nullable = false)
    private Timestamp lastModified;

    /**
     * imei
     */
    private String imei;

    /**
     *android id
     */
    private String androidId;

    /**
     *ipv4
     */
    private String ipv4;

    /**
     *mac
     */
    private String mac;

    /**
     *电话号码
     */
    private String mobileNo;

}
