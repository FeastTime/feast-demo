package com.feast.demo.menu.dao.impl;

import com.feast.demo.menu.dao.MenuDao;
import com.google.common.collect.Maps;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * Created by aries on 2017/8/6.
 */
public class MenuDaoImpl implements MenuDao {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public String getMenuCountByCategoryIdAndStoreId(String categoryId, String storeId){
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select count(1) from Menu m, CategoryMenu cm where cm.dishid=m.dishid ");
        sb.append("and cm.categoryid=:categoryId and m.storeid=:storeId");
        params.put("categoryId",categoryId);
        params.put("storeId",storeId);
        javax.persistence.Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return String.valueOf(query.getMaxResults());
    }

    public List<?> findMenuByCategoryIdAndStoreId(String categoryId, String storeId, int pageNo, int pageNum) {
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select m.dishid,m.dishno,m.dishname,m.dishimgurl,m.tvurl,m.materialflag,m.titleadimgurl,m.titleadurl,m.detail,m.cost,m.waittime,m.pungencydegree,ma.hotflag,ma.eattimes,ma.discountstime,ma.price,ma.sales,ma.starlevel,ma.tmpid,ma.pageid from menu m, menuauxiliary ma, categorymenu cm where cm.dishid=m.dishid and m.dishid=ma.dishid ");
        sb.append("and cm.categoryid=:categoryId");
        sb.append("and m.storeid=:storeId");
        sb.append("limit (:pageNo-1) * :pageNum,:pageNum");
        params.put("categoryId",categoryId);
        params.put("storeId",storeId);
        params.put("pageNo",pageNo);
        params.put("pageNum",pageNum);
        javax.persistence.Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return query.getResultList();
    }
}