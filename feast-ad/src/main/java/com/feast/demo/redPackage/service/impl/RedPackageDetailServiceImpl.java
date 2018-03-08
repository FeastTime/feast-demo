package com.feast.demo.redPackage.service.impl;

import com.feast.demo.redPackage.dao.RedPackageDetailDao;
import com.feast.demo.redPackage.entity.RedPackageDetail;
import com.feast.demo.redPackage.service.RedPackageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RedPackageDetailServiceImpl implements RedPackageDetailService{

    @Autowired
    private RedPackageDetailDao redPackageDetailDao;

    public void saveRedPackageDetail(RedPackageDetail redPackageDetail) {
        redPackageDetailDao.save(redPackageDetail);
    }

    public ArrayList<RedPackageDetail> queryRedPackageDetail(String redPackageId) {
        return redPackageDetailDao.findByRedPackageId(redPackageId);
    }
}
