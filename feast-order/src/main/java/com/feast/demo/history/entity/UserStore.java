package com.feast.demo.history.entity;

import com.feast.demo.store.entity.Store;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="user_store")
@Data
public class UserStore implements Serializable{

    // 不再关注
    public static final int STATUS_RELATION_BREAK = 0;

    // 免打扰
    public static final int STATUS_DO_NOT_DISTURB = 1;

    // 聊天
    public static final int STATUS_CHATTING = 3;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long storeId;
    //    用户与商家关系状态
    private Integer status;
    private Date lastModified;
    //    创建时间
    private Date createTime;
    //    进店次数
    private Long count;
    // 每桌人数
    private Integer numberPerTable;

}
