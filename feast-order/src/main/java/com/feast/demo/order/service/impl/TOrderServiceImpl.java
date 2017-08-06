package com.feast.demo.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.order.dao.TOrderDao;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ggke on 2017/3/31.
 * Update by wangpp on 2017/8/3.
 */
@Service()
public class TOrderServiceImpl implements TOrderService {

    @Autowired
    private TOrderDao tOrderDao;

    public List<OrderInfo> findAll() {
        return (List<OrderInfo>) tOrderDao.findAll();
    }


    /**
     * 加入购物车
     * @param order
     */
    @Transactional(readOnly = false)
    public void create(OrderInfo order) {
        tOrderDao.save(order);
    }

    /**
     * 修改购物车
     * @param order
     */
    @Transactional(readOnly = false)
    public void update(OrderInfo order) {
        tOrderDao.save(order);
    }

    /**
     * 删除购物车
     * @param order
     */
    @Transactional(readOnly = false)
    public void delete(OrderInfo order) {
        tOrderDao.delete(order);
    }

    public String status() {
        return "From OrderService.OK!";
    }

    public List<OrderInfo> findByOrderId(Long orderID) {
        if(orderID == null){
            return null;
        }
        return tOrderDao.findByOrderId(orderID);
    }
}
