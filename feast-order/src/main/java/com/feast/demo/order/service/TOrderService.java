package com.feast.demo.order.service;

import com.feast.demo.order.entity.TOrder;

import java.util.List;

/**
 * Created by ggke on 2017/3/31.
 */
public interface TOrderService {

    public String status();

    public List<TOrder> findAll();
}
