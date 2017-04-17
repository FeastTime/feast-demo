package com.feast.demo.order.service.impl;

import com.feast.demo.order.service.OrderListService;

/**
 * Created by ggke on 2017/3/31.
 */
public class OrderListServiceImpl implements OrderListService {

    public String status() {
        return "From OrderService.OK!";
    }
}
