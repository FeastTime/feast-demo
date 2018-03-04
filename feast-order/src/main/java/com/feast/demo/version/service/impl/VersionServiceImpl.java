package com.feast.demo.version.service.impl;

import com.feast.demo.version.dao.VersionDao;
import com.feast.demo.version.service.VersionService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class VersionServiceImpl implements VersionService{

    @Autowired
    private VersionDao versionDao;

    public Map<String, Object> upgradeReminding(String clientType, Integer versionNumber) {
        ArrayList<Integer> versionNumberMax = versionDao.findVersionNumberByClientTypeOrderByVersionNumberDesc(clientType,new PageRequest(1,1));
        System.out.println(versionNumberMax.size()+"---------");
        Map<String,Object> versionMessage = Maps.newHashMap();
        boolean isUpgrade;
        if(versionNumber.compareTo(versionNumberMax.get(0))<0){
            String downLoadAddress = versionDao.findDownloadAddressByVersionNumber(versionNumberMax.get(0));
            versionMessage.put("downloadAddress",downLoadAddress);
            isUpgrade = true;
        }else{
            String downLoadAddress = versionDao.findDownloadAddressByVersionNumber(versionNumber);
            versionMessage.put("downloadAddress",downLoadAddress);
            isUpgrade = false;
        }
        versionMessage.put("isUpgrade",isUpgrade);
        return versionMessage;
    }
}
