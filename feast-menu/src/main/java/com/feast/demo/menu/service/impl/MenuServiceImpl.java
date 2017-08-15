package com.feast.demo.menu.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.dao.MenuDao;
import com.feast.demo.menu.service.MenuService;
import com.feast.demo.menu.vo.MenuVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by matao on 2017/8/6.
 */
@Service()
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    public String getMenuCountByCategoryIdAndStoreId(JSONObject jsonObj) {
        String categoryId = jsonObj.getString("categoryId");
        String storeId = jsonObj.getString("storeId");
        if (StringUtils.isNotEmpty(categoryId) && StringUtils.isNotEmpty(storeId)) {
            return (String) menuDao.getMenuCountByCategoryIdAndStoreId(categoryId, storeId);
        }
        return null;
    }

    public List<MenuVo> findMenuByCategoryIdAndStoreId(JSONObject jsonObj) {
        String categoryId = jsonObj.getString("categoryId");
        String storeId = jsonObj.getString("storeId");
        String pageNo = jsonObj.getString("pageNo");
        String pageNum = jsonObj.getString("pageNum");
        if (StringUtils.isNotEmpty(categoryId) && StringUtils.isNotEmpty(storeId)
                && StringUtils.isNotEmpty(pageNo) && StringUtils.isNotEmpty(pageNum)) {
            int pageNoInt = Integer.parseInt(pageNo);
            int pageNumInt = Integer.parseInt(pageNum);
            List<?> result = menuDao.findMenuByCategoryIdAndStoreId(categoryId, storeId, pageNoInt, pageNumInt);
            List<MenuVo> list = Lists.newArrayList();
            for(Object o:result){
                MenuVo vo = convertMenuVo((Object[]) o);//查询结果set到vo上
                list.add(vo);
            }
            return list;
        }else {
            return null;
        }
    }
    private MenuVo convertMenuVo(Object[] o){
        MenuVo vo = new MenuVo();
        vo.setDishId((String) o[0]);
        vo.setDishNo((String) o[1]);
        vo.setDishName((String) o[2]);
        vo.setDishImgUrl((String) o[3]);
        vo.setTvUrl((String) o[4]);
        vo.setMaterialFlag((String) o[5]);
        vo.setTitleAdImgUrl((String) o[6]);
        vo.setTitleAdUrl((String) o[7]);
        vo.setDetail((String) o[8]);
        vo.setCost((BigDecimal) o[9]);
        vo.setWaitTime((String) o[10]);
        vo.setPungencyDegree((String) o[11]);
        vo.setHotFlag((String) o[12]);
        vo.setEatTimes((String) o[13]);
        vo.setDiscountsTime((String) o[14]);
        vo.setPrice((BigDecimal) o[15]);
        vo.setSales((String) o[16]);
        vo.setStarLevel((String)o[17]);
        vo.setTmpId((String)o[18]);
        vo.setPageId((String) o[219]);
        return vo;
    }
}