package com.feast.demo.user.entity;

import com.feast.demo.hibernate.TimedEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by ggke on 2017/7/23.
 */

@Entity
@Table(name="user")
@Data
public class User extends TimedEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 乐观锁
     */
    private Long version=-1l;

    /**
     * imei
     */
    private String imei;

    /**
     *android设备id
     */
    private String androidId;

    /**
     *ipv4地址
     */
    private String ipv4;

    /**
     *MAC地址
     */
    private String mac;


    /**
     *手机号
     */
    private Long mobileNo;

    /**
     * 用户名
     */
    private String name;

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
     * 用户电话
     */
    private String phone;

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
