package com.feast.demo.history.dao;

import com.feast.demo.history.entity.History;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by mxb on 2018/1/7.
 */
public interface  HistoryDao extends PagingAndSortingRepository<History,Long> {
    @Query("select h from History h where h.userId = ?1 and h.storeId = ?2")
    History selectHistoryByUserIdAndStoreId(Long userId, Long storeId);

    @Query("select s from History h,Store s where h.userId = ?1 and h.storeId = s.id order by h.count desc,h.visitTime desc")
    List<Store> selectVisitStore(Long userId);

    @Query("select u from History h,User u where h.storeId = ?1 and h.userId = u.id order by h.count desc,h.visitTime desc")
    List<User> selectVisitUser(Long storeId);
}
