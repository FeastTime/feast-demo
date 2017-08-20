package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.DishesCategoryVo;
import com.feast.demo.web.entity.DishesCategoryBean;
import com.feast.demo.web.entity.DishesCategoryObj;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matao on 2017/8/6.
 */

@Service
public class DishesCategoryService {

    @Autowired
    private com.feast.demo.menu.service.DishesCategoryService dishesCategoryRemoteService;

    public DishesCategoryObj findDishesCategoryByStoreId(JSONObject jsonObj) {
        System.out.println("storeId is:" + jsonObj.getString("storeId"));

        DishesCategoryObj dishesCategoryObj = new DishesCategoryObj();
        try {
            List<DishesCategoryVo> list = dishesCategoryRemoteService.findDishesCategoryByStoreId(jsonObj);
            if (list != null && list.size() > 0) {
                dishesCategoryObj.setResultCode("0");
                DishesCategoryBean dishesCategoryBean = null;
                ArrayList<DishesCategoryBean> dishesCategoryBeanList = Lists.newArrayList();
                for (int i = 0; i < list.size(); i++) {
                    DishesCategoryVo dishesCategoryVo = (DishesCategoryVo)list.get(i);
                    dishesCategoryBean = new DishesCategoryBean();
                    dishesCategoryBean.setCategoryId(dishesCategoryVo.getCategoryId());
                    dishesCategoryBean.setCategoryName(dishesCategoryVo.getCategoryName());
                    dishesCategoryBeanList.add(dishesCategoryBean);
                }
                dishesCategoryObj.setDishesCategoryList(dishesCategoryBeanList);
            } else {
                dishesCategoryObj.setResultCode("0");
                dishesCategoryObj.setDishesCategoryList(null);
            }
        }catch (Exception e){
            dishesCategoryObj.setResultCode("1");
            e.printStackTrace();
        }
        return dishesCategoryObj;
    }
}
