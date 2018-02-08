package com.feast.demo.redPackage.service.impl;
import com.feast.demo.redPackage.dao.RedPackageCouponTemplateDao;
import com.feast.demo.redPackage.dao.RedPackageDao;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.redPackage.service.RedPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RedPackageServiceImpl implements RedPackageService {

    @Autowired
    private RedPackageDao redPackageDao;

    @Autowired
    private RedPackageCouponTemplateDao redPackageCouponTemplateDao;


    public void createRedPackage(RedPackage redPackage, List<Long> couponTemplateIds) {
        redPackage = redPackageDao.save(redPackage);
        Long redPackageId = redPackage.getRedPackageId();
        for (Long couponTemplateId : couponTemplateIds) {
            RedPackageCouponTemplate redPackageCouponTemplate = new RedPackageCouponTemplate();
            redPackageCouponTemplate.setRedPackageId(redPackageId);
            redPackageCouponTemplate.setCouponTemplateId(couponTemplateId);
            redPackageCouponTemplateDao.save(redPackageCouponTemplate);
        }
    }

    public void setRedPackageIsUse(Long redPackageId, Long storeId) {
        RedPackage redPackage = redPackageDao.findOne(redPackageId);
        redPackage.setIsUse(2);
        redPackageDao.save(redPackage);
        List<RedPackage> redPackages = redPackageDao.findByStoreIdFilterRedPackageId(storeId,redPackageId);
        for (RedPackage rp : redPackages) {
            rp.setIsUse(1);
            redPackageDao.save(rp);
        }
    }

    public List<RedPackage> queryRedPackageList(Long storeId) {
        List<RedPackage> redPackages = redPackageDao.findByStoreId(storeId);
        return redPackages;
    }

    @Transactional(readOnly = false)
    public void setRedPackageAutoSendTime( Integer time,Long storeId) {
        redPackageDao.updateByStoreId(time,storeId);
    }

    public List<RedPackage> findRedPackageByIsUse(Integer isUse) {
        return redPackageDao.findByIsUse(isUse);
    }
}
