package com.feast.demo.store.service;

import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggke on 2017/8/1.
 */
public interface StoreService {

    public Store getStoreInfo(Long storeId);

    public ArrayList<User> queryHadVisitUser(Long storeId);

    public String findStoreName(Long storeId);

    public ArrayList<Store> getStoreInfoList(ArrayList<Long> storeIds);
}
