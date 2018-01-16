package com.feast.demo.store.dao;

import com.feast.demo.store.entity.Store;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggke on 2017/8/1.
 */
public interface StoreDao extends PagingAndSortingRepository<Store,Long> {

    ArrayList<Store> findByStoreIdIn(List<Long> storeIdList);
}
