package com.feast.demo.store.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.store.dao.HistoryPersonDao;
import com.feast.demo.store.dao.StoreDao;
import com.feast.demo.store.entity.HistoryPerson;
import com.feast.demo.store.entity.Store;
import com.feast.demo.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ggke on 2017/8/1.
 */
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private HistoryPersonDao historyDao;

    public Store findById(Long id) {
        return storeDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(HistoryPerson history) {
        historyDao.save(history);
    }

    public HistoryPerson findByUserIdAndStoreId(Long userId,Long storeId) {
        return historyDao.findByUserIdAndStoreId(userId,storeId);
    }

}
