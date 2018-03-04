package com.feast.demo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VersionService {

    @Autowired
    private com.feast.demo.version.service.VersionService versionRemoteService;

    public Map<String,Object> upgradeReminding(String clientType, Integer versionNumber) {
        return versionRemoteService.upgradeReminding(clientType,versionNumber);
    }
}
