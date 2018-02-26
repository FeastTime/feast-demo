package com.feast.demo.ad.dao;

import com.feast.demo.ad.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * Created by ggke on 2017/6/25.
 */
public class AdvertisementDaoImpl implements AdvertisementDaoCustom{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public ArrayList<Advertisement> findAll2(){
        StringBuilder sb = new StringBuilder();
        sb.append("select a from Advertisement a");
        TypedQuery<Advertisement> query = em.createQuery( sb.toString(), Advertisement.class);
        ArrayList<Advertisement> list = (ArrayList<Advertisement>)query.getResultList();
        return list;
    }

    public Page<Advertisement> findByPage(Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" from Advertisement a");
        TypedQuery<Advertisement> query = em.createQuery( "select a " + sb.toString(), Advertisement.class);
        Query countQuery = em.createNativeQuery("select count(a) "+sb.toString());
        query.setFirstResult(pageable.getPageSize()*pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return new PageImpl<Advertisement>(query.getResultList(),pageable, (Long) countQuery.getSingleResult());
    }

    public ArrayList<Advertisement> findBySizeUseNativeSql(Integer width,Integer height,Integer num,boolean isRand){
        StringBuilder sql = new StringBuilder();
        sql.append("select * from advertisement a where a.width=:width and a.height=:height ");
        HashMap<String , Object> params = new HashMap<String , Object>();
        params.put("width", width);
        params.put("height", height);
        if(isRand){
            sql.append(" order by rand() ");
        }
        if(num !=null){
            sql.append(" limit :num");
            params.put("num", num);
        }
        Query query = em.createNativeQuery(sql.toString(),Advertisement.class);
        for(String key: params.keySet()) {
            query.setParameter(key, params.get(key));
        }
        ArrayList list = (ArrayList<Advertisement>)query.getResultList();
        if(list.size()==0){
            return null;
        }
        return  list;
    }
}
