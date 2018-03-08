package com.feast.demo.table.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="table_info")
@Data
public class TableInfo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    private String suportSeatNumber;

    private Integer passType;

    private Date maketableTime;

    private Date taketableTime;

    private Integer recieveTime;

    private Long userId;

    private Long storeId;

    private String userPhone;

    private String userIcon;

    private String userNickname;

    private Double price;

    private Integer isCome;

    private String description;

    // 剩余 秒
    @Transient
    private Long  expirationTime;


    public void setTaketableTime(Date taketableTime) {

        this.taketableTime = taketableTime;

        setExpirationTimeValue();
    }

    public void setRecieveTime(Integer recieveTime) {

        this.recieveTime = recieveTime;

        setExpirationTimeValue();
    }

    /**
     * 设置剩余时间
     */
    private void setExpirationTimeValue(){

        if (null != taketableTime && null != recieveTime){

            long lastTime = taketableTime.getTime() + recieveTime * 60 * 1000;

            this.expirationTime = (lastTime - new Date().getTime()) / 1000;

            if (lastTime < 0)
                this.expirationTime = 0L;
        }
    }


}
