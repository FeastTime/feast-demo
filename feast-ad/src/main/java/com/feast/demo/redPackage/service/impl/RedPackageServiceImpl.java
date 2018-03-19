package com.feast.demo.redPackage.service.impl;
import com.feast.demo.coupon.dao.CouponTemplateDao;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.redPackage.dao.RedPackageAutoSendTimeDao;
import com.feast.demo.redPackage.dao.RedPackageCouponTemplateDao;
import com.feast.demo.redPackage.dao.RedPackageDao;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageAutoSendTime;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.redPackage.service.RedPackageService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RedPackageServiceImpl implements RedPackageService {

    @Autowired
    private RedPackageDao redPackageDao;

    @Autowired
    private RedPackageCouponTemplateDao redPackageCouponTemplateDao;

    @Autowired
    private CouponTemplateDao couponTemplateDao;

    @Autowired
    private RedPackageAutoSendTimeDao redPackageAutoSendTimeDao;

    public void createRedPackage(RedPackage redPackage, List<RedPackageCouponTemplate> redPackageCouponTemplateIds) {

        redPackage.setCreateTime(new Date());
        redPackage = redPackageDao.save(redPackage);
        Long redPackageId = redPackage.getRedPackageId();

        for (RedPackageCouponTemplate redPackageCouponTemplate : redPackageCouponTemplateIds) {
            redPackageCouponTemplate.setRedPackageId(redPackageId);
            redPackageCouponTemplateDao.save(redPackageCouponTemplate);
        }

    }

    @Transactional(readOnly = false)
    public void setAutoRedPackageIsUse(Long redPackageId, Long storeId) {

        System.out.println("setAutoRedPackageIsUse  :  " + redPackageId +"  --    "+ storeId);

        try {

            RedPackage redPackage = redPackageDao.findOne(redPackageId);

            redPackage.setIsUse(RedPackage.IS_USE);
            redPackageDao.save(redPackage);

            redPackageDao.updateRedPackageId(RedPackage.IS_NOT_USE,storeId,redPackageId);

        } catch (Exception e){
            e.printStackTrace();
        }


    }



    public List<RedPackage> queryRedPackageList(Long storeId,Integer isCouponEnough) {

        List<Object[]> objectArrayList = redPackageDao.queryRedPackageList(storeId);

        System.out.println(objectArrayList.size());
        ArrayList<RedPackage> redPackageList = Lists.newArrayList();
        if(isCouponEnough==1){
            for(int i=0;i<objectArrayList.size();i++) {
                Object[] obj = (Object[]) objectArrayList.get(i);
                RedPackage redPackage = new RedPackage();
                redPackage.setIsCouponEnough(((BigInteger)obj[0]).intValue());
                redPackage.setRedPackageId(((BigInteger)obj[1]).longValue());
                redPackage.setRedPackageName((String)obj[2]);
                redPackage.setStoreId(((BigInteger)obj[3]).longValue());
                redPackage.setIsUse((Integer)obj[4]);
                redPackage.setCreateTime((Date)obj[5]);
                redPackageList.add(redPackage);
            }
        }else {
            for(int i=0;i<objectArrayList.size();i++) {
                Object[] obj = (Object[]) objectArrayList.get(i);
                if(((BigInteger)obj[0]).intValue()>0){
                    RedPackage redPackage = new RedPackage();
                    redPackage.setIsCouponEnough(((BigInteger)obj[0]).intValue());
                    redPackage.setRedPackageId(((BigInteger)obj[1]).longValue());
                    redPackage.setRedPackageName((String)obj[2]);
                    redPackage.setStoreId(((BigInteger)obj[3]).longValue());
                    redPackage.setIsUse((Integer)obj[4]);
                    redPackage.setCreateTime((Date)obj[5]);
                    redPackageList.add(redPackage);
                }
            }
        }
        return redPackageList;
    }

    @Transactional(readOnly = false)
    public void setRedPackageAutoSendTime( Integer time,Long storeId) {
        redPackageAutoSendTimeDao.updateAutoSendTimeByStoreId(time,storeId);
    }

    public ArrayList<RedPackageCouponTemplate> findRedPackageCouponTemplateByRedPackageId(Long id) {
        return redPackageCouponTemplateDao.findByRedPackageId(id);
    }

    public List<RedPackage> findRedPackageByIsUse(Integer isUse){

        return redPackageDao.findRedPackageByIsUse(isUse);
    }

    public RedPackage findByIsUseAndStoreId(Integer isUse, Long storeId) {
        return redPackageDao.findByIsUseAndStoreId(isUse,storeId);
    }

    @Transactional(readOnly = false)
    public void setRedPackageIsNotUse(Long redPackageId) {
        redPackageDao.updateByRedPackageIdAndIsUse(1,redPackageId);
    }

    public ArrayList<CouponTemplate> queryCouponInRedPackage(Long redPackageId) {
        ArrayList<Long> couponTemplateIdList = redPackageCouponTemplateDao.findCouponTemplateIdByRedPackageId(redPackageId);

        ArrayList<CouponTemplate> couponTemplateList = couponTemplateDao.findByIdIn(couponTemplateIdList);

        System.out.println(couponTemplateList.size()+"0000000000");
        return couponTemplateList;
    }

    @Transactional(readOnly = false)
    public void deleteAutoRedPackage(Long redPackageId) {
        redPackageDao.delete(redPackageId);
        redPackageCouponTemplateDao.deleteByRedPackageId(redPackageId);
    }

    public int findAutoSendTimeByStoreId(Long storeId) {
        int autoSendTime = redPackageAutoSendTimeDao.findAutoSendTimeByStoreId(storeId);

        return autoSendTime;
    }

    public RedPackageAutoSendTime findByStoreId(Long storeId) {
        return redPackageAutoSendTimeDao.findByStoreId(storeId);
    }

    public void save(RedPackageAutoSendTime redPackageAutoSendTime_) {
        redPackageAutoSendTimeDao.save(redPackageAutoSendTime_);
    }


    public int queryRedPackageIsCouponEnough(Long redPackageId){
        int isCouponEnough = ((BigInteger)redPackageDao.queryRedPackageIsCouponEnough(redPackageId)).intValue();

        return isCouponEnough;
    }

}
