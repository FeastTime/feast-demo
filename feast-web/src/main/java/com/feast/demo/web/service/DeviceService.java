package com.feast.demo.web.service;

import com.feast.demo.device.entity.Device;
import com.feast.demo.store.entity.Store;
import com.feast.demo.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ggke on 2017/8/2.
 */
@Service
@Transactional(readOnly = true)
public class DeviceService {

    @Autowired
    private com.feast.demo.device.service.DeviceService deviceRemoteService;

    @Autowired
    private StoreService storeRemoteService;

    public Device findDeviceInfoByImei(String imei){
        Device device = deviceRemoteService.fingByImei(imei);
//        device.getStore().getDevices();
        return device;
    }

    public Store findStoreById(Long id){
        return storeRemoteService.findById(id);
    }
}
