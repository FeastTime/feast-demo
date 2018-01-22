package com.feast.demo.device.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.stereotype.Service;
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


    public Device fingByDeviceId(Long deviceId) {
        return deviceDao.findOne(deviceId);
    }
}
