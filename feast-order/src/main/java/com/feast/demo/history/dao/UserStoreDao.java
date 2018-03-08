package com.feast.demo.history.dao;

import com.feast.demo.history.entity.UserStore;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by mxb on 2018/1/7.
 */
public interface UserStoreDao extends PagingAndSortingRepository<UserStore,Long> {

    UserStore findByUserIdAndStoreId(Long userId, Long storeId);

    ArrayList<Store> findByUserIdOrderByCount(Long userId);

    ArrayList<Store> findByUserIdOrderByCountDesc(Long userId);

    ArrayList<Store> findByUserIdOrderByLastModifiedDesc(Long userId);

    ArrayList<User> findByStoreId(Long storeId);

    @Query("select us.storeId from UserStore us where us.userId = ?1")
    Set<Long> findStoreIdByUserId(Long userId);
}
