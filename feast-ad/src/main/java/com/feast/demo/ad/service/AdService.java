package com.feast.demo.ad.service;

import com.feast.demo.ad.entity.AdTargetType;
import com.feast.demo.ad.entity.Advertisement;
import com.feast.demo.ad.entity.TAd;

import java.util.List;

/**
 * Created by pinyou on 17-4-11.
 */
public interface AdService {

    public List<Advertisement> findAll();

    public void save(Advertisement advertisement);

    public String status();

    public String transferEntity(TAd tad);

    public String transferString(String msg);

    public List<TAd> getAdList(Integer num);

    public String getRemoteUrl(AdTargetType type, Integer width, Integer height);

    public List<String> getAdArray(Integer num,String width,String height);
}
