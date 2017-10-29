package com.feast.demo.web.service;

import com.feast.demo.web.util.CacheManager;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by ggke on 2017/10/25.
 */

@Service
public class CacheManagerService {

    public Serializable put(String key, String value){
        return CacheManager.putToCache(key,value);
    }

    public Serializable get(String key){
        return  CacheManager.getFromCache(key);
    }

}
