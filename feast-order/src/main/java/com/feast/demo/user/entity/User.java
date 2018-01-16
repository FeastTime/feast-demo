package com.feast.demo.user.entity;

import com.feast.demo.hibernate.TimedEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ggke on 2017/7/23.
 */

@Entity
@Table(name="user")
@Data
public class User extends TimedEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;



    /**
     * 密码
     */
    private String password;


    /**
     *android设备id
     */
    private Long deviceId;


    /**
     *手机号
     */
    private String mobileNo;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型，默认是客户
     */
    @Enumerated(EnumType.STRING)
    private UserType userType= UserType.customer;

    /**
     * 微信用户唯一标识
     */
    private String openId;


    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String userIcon;

    public enum UserType{
        customer,//客户
        store//店铺
    }
}
