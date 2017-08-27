package com.feast.demo.ad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by ggke on 2017/6/21.
 */

@Entity
@Data
public class Advertisement implements Serializable{

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

    private String path;

    public Advertisement(){
        Timestamp t = new Timestamp(System.currentTimeMillis());
        this.setCreation(t);
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

}
