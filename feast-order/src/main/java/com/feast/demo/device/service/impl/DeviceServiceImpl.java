package com.feast.demo.device.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.device.dao.DeviceDao;
import com.feast.demo.device.entity.Device;
import com.feast.demo.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ggke on 2017/8/1.
 */
@Service
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    public Device fingByImei(String imei) {
        if(StringUtils.isNotEmpty(imei)){
            return deviceDao.fingByImei(imei);
        }
        return null;
    }
}
