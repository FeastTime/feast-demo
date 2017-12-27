package com.feast.demo.store.dao;

import com.feast.demo.store.entity.HistoryPerson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HistoryPersonDao extends PagingAndSortingRepository<HistoryPerson,Long> {
    @Query("select h from HistoryPerson h where h.userId = ?1 and h.storeId = ?2")
    HistoryPerson findByUserIdAndStoreId(Long userId,Long storeId);
}
