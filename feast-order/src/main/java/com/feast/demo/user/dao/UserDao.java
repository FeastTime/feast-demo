package com.feast.demo.user.dao;

import com.feast.demo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

/**
 *
 * Created by ggke on 2017/7/23.
 */
public interface UserDao  extends PagingAndSortingRepository<User,Long>,UserDaoCustom {

    @Query(value = "select u from User u where u.mobileNo=?1")
    User findByMobileNo(String mobileNo);

    @Query(countQuery = "select count(u) from User u",value = "select u from User u order by u.userId")
    Page<User> findByPage2(Pageable pageable);

    @Query("select u from User u where u.mobileNo=?1 and u.password=?2")
    User findByMobileAndPwd(String mobileNo, String pwd);

    User findByUsernameAndPassword(String username, String password);

    ArrayList<User> findByUsername(String username);

    User findByOpenId(String openId);

    @Query("select u.userId from User u where u.openId =?1")
    Long findUserId(String openId);

    @Query("select u.userId from User u where u.storeId = ?1")
    ArrayList<Long> findUserIdByStoreId(Long storeId);

    @Query("select u.mobileNo from User u where u.userId = ?1")
    String findMobileNoByUserId(Long userId);

    @Query("select u.userId from User u where u.storeId = ?1 and u.userType = ?2")
    ArrayList<Long> findWaitersIdByStoreIdAndUserType(Long storeId, int userType);


    /*@Query("select s from Store s where s.id in ?1")
    List<Store> findByIdIn(List<Long> storeIds);*/
}

