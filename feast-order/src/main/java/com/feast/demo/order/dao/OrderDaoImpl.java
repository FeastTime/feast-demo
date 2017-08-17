package com.feast.demo.order.dao;

import com.feast.demo.order.entity.OrderInfo;
import com.google.common.collect.Maps;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * Created by ggke on 2017/6/25.
 * Update by wangpp on 2017/8/3.
 */
public class OrderDaoImpl implements OrderDaoCustom{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public List<OrderInfo> findAll2(){
        StringBuilder sb = new StringBuilder();
        sb.append("select a from OrderInfo a");
        TypedQuery<OrderInfo> query = em.createQuery( sb.toString(), OrderInfo.class);
        List<OrderInfo> list = query.getResultList();
        return list;
    }

    public List<?> findOrderDetailVoByOrderId(Long orderId) {
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select s.name,detail.dishname,detail.originalprice,orderInfo.status from OrderInfo orderInfo,OrderDetail detail,Store s where detail.orderid=orderInfo.id and s.id=orderInfo.storeid ");
        sb.append(" and  orderInfo.id=:orderId");
        params.put("orderId",orderId);
        Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }

        return query.getResultList();
    }
}
