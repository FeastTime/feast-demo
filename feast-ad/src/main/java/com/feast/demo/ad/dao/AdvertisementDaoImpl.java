package com.feast.demo.ad.dao;

import com.feast.demo.ad.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by ggke on 2017/6/25.
 */
public class AdvertisementDaoImpl implements AdvertisementDaoCustom{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public List<Advertisement> findAll2(){
        StringBuilder sb = new StringBuilder();
        sb.append("select a from Advertisement a");
        TypedQuery<Advertisement> query = em.createQuery( sb.toString(), Advertisement.class);
        List<Advertisement> list = query.getResultList();
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
}
