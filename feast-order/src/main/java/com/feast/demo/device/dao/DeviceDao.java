package com.feast.demo.device.dao;

import com.feast.demo.device.entity.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ggke on 2017/8/1.
 */
public interface DeviceDao extends PagingAndSortingRepository<Device,Long> {

}
