package com.feast.demo.history.dao;

import com.feast.demo.history.entity.UserStore;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mxb on 2018/1/7.
 */
public interface UserStoreDao extends PagingAndSortingRepository<UserStore,Long> {
    @Query("select h from UserStore h where h.userId = ?1 and h.storeId = ?2")
    UserStore selectHistoryByUserIdAndStoreId(Long userId, Long storeId);

    @Query("select u from UserStore h,User u where h.storeId = ?1 and h.userId = u.id order by h.count desc,h.lastModified desc")
    List<User> selectVisitUser(Long storeId);

    UserStore findByUserIdAndStoreId(Long userId, Long storeId);

    List<UserStore> findByUserIdOrderByCount(Long userId);

    List<UserStore> findByUserIdOrderByCountDesc(Long userId);

    List<UserStore> findByUserIdOrderByLastModifiedDesc(Long userId);

    ArrayList<User> findByStoreId(Long storeId);
}