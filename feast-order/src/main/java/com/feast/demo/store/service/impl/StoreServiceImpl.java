package com.feast.demo.store.service.impl;

import org.springframework.stereotype.Service;
import com.feast.demo.history.dao.UserStoreDao;
import com.feast.demo.history.entity.UserStore;
import com.feast.demo.store.dao.StoreDao;
import com.feast.demo.store.entity.Store;
import com.feast.demo.store.service.StoreService;
import com.feast.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggke on 2017/8/1.
 */
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private UserStoreDao userStoreDao;

    public Store getStoreInfo(Long storeId) {
        return storeDao.findOne(storeId);
    }

    public ArrayList<User> queryHadVisitUser(Long storeId) {
        return userStoreDao.findByStoreId(storeId);
    }

    public String findStoreName(Long storeId) {
        return storeDao.findStoreName(storeId);
    }

    public ArrayList<Store> getStoreInfoList(ArrayList<Long> storeIds) {
        return storeDao.findByStoreIdIn(storeIds);
    }


}
