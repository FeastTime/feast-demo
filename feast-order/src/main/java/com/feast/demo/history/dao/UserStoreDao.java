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

    @Query(value = "select s.*,us.last_modified,us.create_time,us.count,us.status from store_info s join user_store us on s.store_id = us.store_id where us.user_id = ?1 order by us.count asc",nativeQuery = true)
    ArrayList<Object[]> findByUserIdOrderByCount(Long userId);

    @Query(value = "select s.*,us.last_modified,us.create_time,us.count,us.status from store_info s join user_store us on s.store_id = us.store_id where us.user_id = ?1 order by us.count desc",nativeQuery = true)
    ArrayList<Object[]> findByUserIdOrderByCountDesc(Long userId);

    @Query(value = "select s.*,us.last_modified,us.create_time,us.count,us.status from store_info s join user_store us on s.store_id = us.store_id where us.user_id = ?1 order by us.last_modified desc",nativeQuery = true)
    ArrayList<Object[]> findByUserIdOrderByLastModifiedDesc(Long userId);

    ArrayList<User> findByStoreId(Long storeId);

    @Query("select us.storeId from UserStore us where us.userId = ?1")
    Set<Long> findStoreIdByUserId(Long userId);
}
