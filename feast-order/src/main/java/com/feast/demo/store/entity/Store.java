package com.feast.demo.store.entity;

import com.feast.demo.history.entity.UserStore;
import com.google.common.collect.Sets;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ggke on 2017/8/2.
 */

@Entity
@Data
@Table(name = "store_info")
//@ToString(exclude = {"devices"})
//@JsonIgnoreProperties(value = { "devices"})
//@EqualsAndHashCode(exclude = {"devices"})
public class Store implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    /**
     *店铺名称
     */
    private String storeName;

    private String locate;

    private String phone;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String storeIcon;

    private String storePicture;

    @Transient
    private Integer status;

    @Transient
    private Date lastModified;

    @Transient
    private Date createTime;

    @Transient
    private Long count;


//    @OneToMany(mappedBy = "store",cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    @Lazy(value = false)
//    private List<Device> devices;



}
