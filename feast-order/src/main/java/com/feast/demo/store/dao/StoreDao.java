package com.feast.demo.store.dao;

import com.feast.demo.store.entity.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

/**
 * Created by ggke on 2017/8/1.
 */
public interface StoreDao extends PagingAndSortingRepository<Store,Long> {

    ArrayList<Store> findByStoreIdIn(ArrayList<Long> storeIdList);

    @Query("select s.storeName from Store s where s.storeId = ?1")
    String findStoreName(Long storeId);
}
