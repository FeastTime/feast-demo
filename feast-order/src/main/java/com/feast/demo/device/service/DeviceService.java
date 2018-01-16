package com.feast.demo.device.service;

import com.feast.demo.device.entity.Device;

/**
 * Created by ggke on 2017/8/1.
 */
public interface DeviceService {

    public Device fingByDeviceId(Long deviceId);
}
