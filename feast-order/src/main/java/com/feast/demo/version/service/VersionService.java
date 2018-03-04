package com.feast.demo.version.service;

import java.util.Map;

public interface VersionService {

   public Map<String,Object> upgradeReminding(String clientType, Integer versionNumber);
}
