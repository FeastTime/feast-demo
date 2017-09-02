package com.feast.demo.menu.dao;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created by matao on 2017/8/6.
 */
public class MenuDaoImpl implements MenuDaoCustom{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public String getMenuCountByCategoryIdAndStoreId(String categoryId, String storeId){
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select count(cm.dishid) from Menu m, CategoryMenu cm where cm.dishid=m.dishid ");
        sb.append("and cm.categoryid=:categoryId and m.storeid=:storeId");
        params.put("categoryId",categoryId);
        params.put("storeId",storeId);
        Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return query.getSingleResult().toString();
    }

    public List<?> findMenuByCategoryIdAndStoreId(String categoryId, String storeId, int pageNo, int pageNum) {
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select m.dishid,m.dishno,m.dishname,m.dishimgurl,m.tvurl,m.materialflag," +
                "m.titleadimgurl,m.titleadurl,m.detail,m.cost,m.waittime,m.pungencydegree,ma.hotflag," +
                "ma.eattimes,ma.discountstime,ma.price,ma.sales,ma.starlevel,ma.tmpid,ma.pageid ,cm.categoryid " +
                "from Menu m, MenuAuxiliary ma, CategoryMenu cm where cm.dishid=m.dishid and m.dishid=ma.dishid ");
        sb.append(" and cm.categoryid=:categoryId");
        sb.append(" and m.storeid=:storeId");
        sb.append(" order by m.dishid");
        params.put("categoryId",categoryId);
        params.put("storeId",storeId);

        Query query = em.createQuery(sb.toString());
        Pageable pageable = new PageRequest(pageNo, pageNum);
        if(pageable != null){
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return query.getResultList();
    }

    public String getCategoryIdStrByStoreId(String storeId){
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select categoryid from Recommend where storeid = :storeId");
        params.put("storeId",storeId);
        Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return query.getSingleResult().toString();
    }

    public List<?> findRecommendPrdByStoreIdAndHomeFlag(String storeId, String isHomePage, String categoryIdStr) {
        StringBuilder sb = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sb.append("select m.dishid,m.dishno,m.dishname,m.dishimgurl,m.tvurl,m.materialflag," +
                "m.titleadimgurl,m.titleadurl,m.detail,m.cost,m.waittime,m.pungencydegree,ma.hotflag," +
                "ma.eattimes,ma.discountstime,ma.price,ma.sales,ma.starlevel,ma.tmpid,ma.pageid,cm.categoryid " +
                "from Menu m, MenuAuxiliary ma, CategoryMenu cm where cm.dishid=m.dishid and m.dishid=ma.dishid ");
        if (StringUtils.isNotEmpty(categoryIdStr)){
            if("1".equals(isHomePage)) {
                sb.append(" and cm.categoryid in (:categoryId)");
            }else if("0".equals(isHomePage)) {
                sb.append(" and cm.categoryid not in(:categoryId)");
            }
        }
        sb.append(" and m.storeid=:storeId");
        sb.append(" order by m.dishid");
        if (StringUtils.isNotEmpty(categoryIdStr)) {
            params.put("categoryId", categoryIdStr);
        }
        params.put("storeId",storeId);
        Query query = em.createQuery(sb.toString());
        for(String key:params.keySet()){
            query.setParameter(key,params.get(key));
        }
        return query.getResultList();
    }
}