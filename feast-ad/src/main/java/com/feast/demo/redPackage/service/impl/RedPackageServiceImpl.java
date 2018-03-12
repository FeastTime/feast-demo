package com.feast.demo.redPackage.service.impl;
import com.feast.demo.coupon.dao.CouponTemplateDao;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.redPackage.dao.RedPackageCouponTemplateDao;
import com.feast.demo.redPackage.dao.RedPackageDao;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.redPackage.service.RedPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public void createRedPackage(RedPackage redPackage, List<RedPackageCouponTemplate> redPackageCouponTemplateIds) {

        redPackage.setCreateTime(new Date());
        redPackage = redPackageDao.save(redPackage);
        Long redPackageId = redPackage.getRedPackageId();
        System.out.println(redPackageId);
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



    public List<RedPackage> queryRedPackageList(Long storeId) {
        List<RedPackage> redPackages = redPackageDao.findByStoreIdOrderByCreateTimeDesc(storeId);
        return redPackages;
    }

    @Transactional(readOnly = false)
    public void setRedPackageAutoSendTime( Integer time,Long storeId) {
        redPackageDao.updateByStoreId(time,storeId);
    }

    public List<RedPackageCouponTemplate> findRedPackageCouponTemplateByRedPackageId(Long id) {
        return redPackageCouponTemplateDao.findByRedPackageId(id);
    }

    public List<RedPackage> findRedPackageByStoreIdAndIsUse(List<Long> storeIds, Integer isUse) {
        return redPackageDao.findByIsUseAndStoreIdIn(isUse,storeIds);
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

    public long findAutoSendTimeByRedPackageId(Long redPackageId) {
        return redPackageDao.findAutoSendTimeByRedPackageId(redPackageId)*60*1000;
    }

}
