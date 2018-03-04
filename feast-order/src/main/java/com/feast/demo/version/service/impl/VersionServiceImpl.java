package com.feast.demo.version.service.impl;

import com.feast.demo.version.dao.VersionDao;
import com.feast.demo.version.entity.Version;
import com.feast.demo.version.service.VersionService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VersionServiceImpl implements VersionService{

    @Autowired
    private VersionDao versionDao;

    public Map<String, Object> upgradeReminding(String clientType, Integer versionNumber) {
        Version version  = versionDao.findVersionNumberByClientTypeOrderByVersionNumberDesc(clientType,versionNumber);
        Map<String,Object> versionMessage = Maps.newHashMap();
        boolean isUpgrade;
        if(version!=null){
            String downLoadAddress = version.getDownloadAddress();
            versionMessage.put("downloadAddress",downLoadAddress);
            isUpgrade = true;
        }else{
            versionMessage.put("downloadAddress",null);
            isUpgrade = false;
        }
        versionMessage.put("isUpgrade",isUpgrade);
        return versionMessage;
    }
}
