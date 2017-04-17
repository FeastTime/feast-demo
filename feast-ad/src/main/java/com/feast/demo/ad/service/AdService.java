package com.feast.demo.ad.service;

import com.feast.demo.ad.entity.TAd;

import java.util.List;

/**
 * Created by pinyou on 17-4-11.
 */
public interface AdService {

    public String status();

    public String transferEntity(TAd tad);

    public String transferString(String msg);

    public List<TAd> getAdList(Integer num);
}
