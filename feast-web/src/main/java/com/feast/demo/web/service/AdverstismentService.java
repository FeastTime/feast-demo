package com.feast.demo.web.service;

import com.feast.demo.ad.entity.AdTargetType;
import com.feast.demo.ad.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gk on 17-5-9.
 * 广告信息service
 */

@Service
public class AdverstismentService {

    @Autowired
    private AdService adRemoteService;

    public String getRemontAdUrl(AdTargetType type, Integer width, Integer height){
        return adRemoteService.getRemoteUrl(type,width,height);
    }
}
