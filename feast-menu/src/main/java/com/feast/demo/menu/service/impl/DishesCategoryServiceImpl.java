package com.feast.demo.menu.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.dao.DishesCategoryDao;
import com.feast.demo.menu.service.DishesCategoryService;
import com.feast.demo.menu.vo.DishesCategoryVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by matao on 2017/8/12.
 */
@Service()
public class DishesCategoryServiceImpl implements DishesCategoryService {
    @Autowired
    private DishesCategoryDao dishesCategoryDao;

    public ArrayList<DishesCategoryVo> findDishesCategoryByStoreId(JSONObject jsonObj) {
        String storeId = jsonObj.getString("storeId");
        if(StringUtils.isNotEmpty(storeId)){
            ArrayList<?> result = dishesCategoryDao.findDishesCategoryByStoreId(storeId);
            ArrayList<DishesCategoryVo> list = Lists.newArrayList();
            for(Object o:result){
                DishesCategoryVo vo = convertDishesCategoryVo((Object[]) o);//查询结果set到vo上
                list.add(vo);
            }
            return list;
        }else {
            return null;
        }
    }
    private DishesCategoryVo convertDishesCategoryVo(Object[] o){
        DishesCategoryVo vo = new DishesCategoryVo();
        vo.setCategoryId((String) o[0]);
        vo.setCategoryName((String) o[1]);
        return vo;
    }

}
