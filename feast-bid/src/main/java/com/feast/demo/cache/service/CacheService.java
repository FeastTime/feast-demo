package com.feast.demo.cache.service;

import java.io.Serializable;

/**
 * Created by ggke on 2017/10/24.
 */
public interface CacheService {

    public Serializable getFromCache(Serializable key);

    public Serializable putToCache(Serializable key,Serializable value);


}
