package com.feast.demo.web.service;

import com.feast.demo.redPackage.entity.RedPackageDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RedPackageDetailService {

    @Autowired
    private com.feast.demo.redPackage.service.RedPackageDetailService redPackageDetailRemoteService;

    public ArrayList<RedPackageDetail> queryRedPackageDetail(String redPackageId) {

        return redPackageDetailRemoteService.queryRedPackageDetail(redPackageId);
    }
}
