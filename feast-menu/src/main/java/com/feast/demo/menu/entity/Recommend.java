package com.feast.demo.menu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by matao on 2017/9/2.
 */
@Entity
@Data
@Table(name = "recommend")
public class Recommend implements Serializable {

    @Id
    private String categoryid;

    @Id
    private String storeid;
}