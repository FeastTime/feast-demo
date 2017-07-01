package com.feast.demo.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.order.dao.TOrderDao;
import com.feast.demo.order.entity.TOrder;
import com.feast.demo.order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ggke on 2017/3/31.
 */
@Service()
public class TOrderServiceImpl implements TOrderService {

    @Autowired
    private TOrderDao tOrderDao;

    public String status() {
        return "From OrderService.OK!";
    }

    public List<TOrder> findAll() {
        return (List<TOrder>) tOrderDao.findAll();
    }
}
