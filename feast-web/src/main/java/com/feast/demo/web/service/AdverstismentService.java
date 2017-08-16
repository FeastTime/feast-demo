package com.feast.demo.web.service;

import com.feast.demo.ad.entity.AdTargetType;
import com.feast.demo.ad.entity.Advertisement;
import com.feast.demo.ad.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by gk on 17-5-9.
 * 广告信息service
 */

@Service
public class AdverstismentService {

    @Autowired
    private AdService adRemoteService;

    private static final String path="http://47.94.16.58:9798/feast-web";

    public String getRemontAdUrl(AdTargetType type, Integer width, Integer height){
//        return adRemoteService.getRemoteUrl(type,width,height);
        List<Advertisement> list = findByTypeAndSize(type.name(),width,height);
        if(list == null){
            return path;
        }
        Advertisement randomAd = list.get((int) ((new Date()).getTime()%list.size()));
        return path+randomAd.getPath();
    }

    public List<String> getAdArray(Integer num,String width,String height){
        return adRemoteService.getAdArray(num,width,height);
    }

    public List<Advertisement> findAll(){
        return adRemoteService.findAll();
    }

    /**
     * 根据广告尺寸和类型，查找广告列表
     * @param type
     * @param width
     * @param height
     * @return
     */
    public List<Advertisement> findByTypeAndSize(String type,Integer width,Integer height){
        if(StringUtils.isEmpty(type) || width == null || width<1 || height == null || height<1){
            return null;
        }
        return adRemoteService.findByTypeAndSize(type,width,height);
    }
}
