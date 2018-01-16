package com.feast.demo.history.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="user_store")
@Data
public class UserStore implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long storeId;
    private Integer status;
    private Date lastModified;
    private Date createTime;
    private Long count;

}
