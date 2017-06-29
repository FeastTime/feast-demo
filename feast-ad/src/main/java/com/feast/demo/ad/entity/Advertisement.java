package com.feast.demo.ad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DateFormat;

/**
 * Created by ggke on 2017/6/21.
 */

@Entity
@Data
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "YYYY/MM/DD hh:mm:ss", timezone="GMT+8")
    @Column(updatable = false, nullable = false)
    protected Timestamp creation;

    private String type;

    private Integer width;

    private Integer height;
}