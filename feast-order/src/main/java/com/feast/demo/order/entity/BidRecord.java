package com.feast.demo.order.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by matao on 2017/11/5.
 */
@Entity
@Data
@Table(name = "bidrecord")
public class BidRecord implements Serializable {

    @Id
    private String bidrecordid;
    private String storeid;
    private String mobileno;
    private String bid;
    private String maxprice;
    private String recordtime;
    private String stt;
}
