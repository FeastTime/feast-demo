package com.feast.demo.cache.service.impl;

import org.springframework.stereotype.Service;
import com.feast.demo.cache.CacheManager;
import com.feast.demo.cache.service.CacheService;

import java.io.Serializable;

/**
 * Created by ggke on 2017/10/24.
 */
@Service
public class CacheServiceImpl implements CacheService {

    public Serializable getFromCache(Serializable key) {
        Serializable value =  CacheManager.getFromMap(key);
        return value==null?"none":value;
    }

    public Serializable putToCache(Serializable key, Serializable value) {
        return CacheManager.putToMap(key,value);
    }
}
