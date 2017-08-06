package com.feast.demo.store.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.device.dao.DeviceDao;
import com.feast.demo.device.entity.Device;
import com.feast.demo.device.service.DeviceService;
import com.feast.demo.store.dao.StoreDao;
import com.feast.demo.store.entity.Store;
import com.feast.demo.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ggke on 2017/8/1.
 */
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;

    public Store findById(Long id) {
        return storeDao.findOne(id);
    }
}
