package com.feast.demo.user.dao;

import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.HistoryStore;
import com.feast.demo.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.OneToMany;
import java.util.List;

public interface HistoryStoreDao extends PagingAndSortingRepository<HistoryStore,Long> {

    @OneToMany
    @Query("select s from Store s where s.id in (select h.storeId from HistoryStore h where h.userId = ?1) ")
    List<Store> selectVisitStore(Long userId);
}
