package com.feast.demo.user.dao;

import com.feast.demo.history.entity.History;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.entity.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ggke on 2017/7/23.
 */
public interface UserDao  extends PagingAndSortingRepository<User,Long>,UserDaoCustom {

    @Query(value = "select u from User u where u.mobileNo=?1")
    User findByMobileNo(Long mobileNo);

    @Query(countQuery = "select count(u) from User u",value = "select u from User u order by u.id")
    Page<User> findByPage2(Pageable pageable);

    @Query("select u from User u where u.name=?1")
    List<User> findByName(String name);

    @Query("select u from User u where u.mobileNo=?1 and u.pwd=?2")
    User findByMobileAndPwd(Long mobileNo, String pwd);

    @Query("select u from User u where u.openId=?1")
    User checkWeChatUserBindStatus(String openId, String imei, String androidId, String ipv4, String mac);

    @Query("select u from User u where u.imei=?1 and u.androidId=?2 and u.ipv4=?3 and u.mac=?4 and u.openId=?5 and u.id=?6")
    User findUserByImeiAndAndroidIdAndIpv4AndMacAndOpenIdAndUserId(String imei, String androidId, String ipv4, String mac, String openId, Long id);

    @Query("select u from User u where u.name =?1 and u.pwd=?2")
    User findByNameAndPwd(String name, String pwd);

    @Query("select uc from UserCoupon uc where uc.userId = ?1 and uc.storeId = ?2 and uc.code = ?3")
    UserCoupon selectCouponByUserIdAndStoreIdAndCouponCode(Long userId,Long storeId,String code);



    /*@Query("select s from Store s where s.id in ?1")
    List<Store> findByIdIn(List<Long> storeIds);*/
}

