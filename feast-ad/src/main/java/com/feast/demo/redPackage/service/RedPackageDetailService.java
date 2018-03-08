package com.feast.demo.redPackage.service;

import com.feast.demo.redPackage.entity.RedPackageDetail;

import java.util.ArrayList;

public interface RedPackageDetailService {
    public void saveRedPackageDetail(RedPackageDetail redPackageDetail);

    public ArrayList<RedPackageDetail> queryRedPackageDetail(String redPackageId);
}
