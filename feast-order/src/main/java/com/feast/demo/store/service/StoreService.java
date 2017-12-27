package com.feast.demo.store.service;

import com.feast.demo.device.entity.Device;
import com.feast.demo.store.entity.HistoryPerson;
import com.feast.demo.store.entity.Store;

/**
 * Created by ggke on 2017/8/1.
 */
public interface StoreService {

    public Store findById(Long id);

    public void save(HistoryPerson history);

    public HistoryPerson findByUserIdAndStoreId(Long userId,Long storeId);

}
