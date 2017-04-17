package com.feast.demo.web.service;

import com.feast.demo.menu.service.MenuService;
import com.feast.demo.order.service.OrderListService;
import com.feast.demo.ad.entity.TAd;
import com.feast.demo.ad.service.AdService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class RemoteApiStatusService {

    @Autowired
    private OrderListService orderRemoteService;

    @Autowired
    private MenuService menuRemoteService;

    @Autowired
    private AdService adRemoteService;

    public Map<String,Object> getStatus(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("order service status",orderRemoteService.status());
        map.put("menu service status",menuRemoteService.getStatus());
        map.put("ad service status",adRemoteService.status());
        return map;
    }

    public String transferEntityToTAd(TAd tad){
        return adRemoteService.transferEntity(tad);
    }

    public String transferStringToTAd(String msg){
        return adRemoteService.transferString(msg);
    }

    public List<TAd> getAdList(int num){
        return adRemoteService.getAdList(num);
    }
}
