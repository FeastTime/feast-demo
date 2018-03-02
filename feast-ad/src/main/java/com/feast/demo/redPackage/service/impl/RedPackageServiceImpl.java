package com.feast.demo.redPackage.service.impl;
import com.feast.demo.redPackage.dao.RedPackageCouponTemplateDao;
import com.feast.demo.redPackage.dao.RedPackageDao;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.redPackage.service.RedPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RedPackageServiceImpl implements RedPackageService {

    @Autowired
    private RedPackageDao redPackageDao;

    @Autowired
    private RedPackageCouponTemplateDao redPackageCouponTemplateDao;


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

    public List<RedPackage> findRedPackageByStoreIds(List<Long> storeIds) {
        return redPackageDao.findByStoreIdIn(storeIds);
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

}
