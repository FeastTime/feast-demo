package com.feast.demo.store.dao;

import com.feast.demo.device.entity.Device;
import com.feast.demo.store.entity.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ggke on 2017/8/1.
 */
public interface StoreDao extends PagingAndSortingRepository<Store,Long> {

}
