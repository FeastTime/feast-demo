package com.feast.demo.redPackage.entity;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="red_package_detail")
@Entity
public class RedPackageDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String redPackageId;

    private Long userId;

    private Date unpackTime;

    private String redPackageTitle;

    private Integer isBestLuck;

    public static final int ISBESTLUCK = 1;

    public static final int NOTBESTLUCK = 2;

    @Transient
    private String nickName;

    @Transient
    private String userIcon;
}
