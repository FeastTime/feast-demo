package com.feast.demo.history.dao;

import com.feast.demo.history.entity.UserStore;
import com.feast.demo.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by mxb on 2018/1/7.
 */
public interface UserStoreDao extends PagingAndSortingRepository<UserStore,Long> {
    @Query("select h from UserStore h where h.userId = ?1 and h.storeId = ?2")
    UserStore selectHistoryByUserIdAndStoreId(Long userId, Long storeId);

    @Query("select u from UserStore h,User u where h.storeId = ?1 and h.userId = u.id order by h.count desc,h.lastModified desc")
    ArrayList<User> selectVisitUser(Long storeId);

    UserStore findByUserIdAndStoreId(Long userId, Long storeId);

    ArrayList<UserStore> findByUserIdOrderByCount(Long userId);

    ArrayList<UserStore> findByUserIdOrderByCountDesc(Long userId);

    ArrayList<UserStore> findByUserIdOrderByLastModifiedDesc(Long userId);

    ArrayList<User> findByStoreId(Long storeId);

    @Query("select us.storeId from UserStore us where us.userId = ?1")
    Set<Long> findStoreIdByUserId(Long userId);
}
