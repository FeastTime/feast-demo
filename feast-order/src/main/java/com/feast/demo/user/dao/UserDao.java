package com.feast.demo.user.dao;

import com.feast.demo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by ggke on 2017/7/23.
 */
public interface UserDao  extends PagingAndSortingRepository<User,Long>,UserDaoCustom {

    @Query("select u from User u where u.mobileNo=?1")
    User findByMobileNo(Long mobileNo);

    @Query(countQuery = "select count(u) from User u",value = "select u from User u order by u.id")
    Page<User> findByPage2(Pageable pageable);

    @Query("select u from User u where u.name=?1")
    List<User> findByName(String name);

    @Query("select u from User u where u.mobileNo=?1 and u.pwd=?2")
    User findByMobileAndPwd(Long mobileNo, String pwd);

    @Query("select u from User u where u.openId=?1 and u.imei=?2 and u.androidId=?3 and u.ipv4=?4 and u.mac=?5")
    User checkWeChatUserBindStatus(String openId, String imei, String androidId, String ipv4, String mac);
}

