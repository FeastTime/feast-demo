package com.feast.demo.order.dao;

import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.entity.TOrder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by ggke on 2017/6/25.
 * Update by wangpp on 2017/8/3.
 */
public class TOrderDaoImpl implements TOrderDaoCustom{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public List<OrderInfo> findAll2(){
        StringBuilder sb = new StringBuilder();
        sb.append("select a from OrderInfo a");
        TypedQuery<OrderInfo> query = em.createQuery( sb.toString(), OrderInfo.class);
        List<OrderInfo> list = query.getResultList();
        return list;
    }
}
