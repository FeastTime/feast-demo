package com.feast.demo.web.service;

import com.feast.demo.device.entity.Device;
import com.feast.demo.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ggke on 2017/8/2.
 */
@Service
public class DeviceService {

    @Autowired
    private com.feast.demo.device.service.DeviceService deviceRemoteService;

    @Autowired
    private StoreService storeRemoteService;

    public Device findDeviceInfoByImei(String imei){
        return deviceRemoteService.fingByImei(imei);
    }
}
