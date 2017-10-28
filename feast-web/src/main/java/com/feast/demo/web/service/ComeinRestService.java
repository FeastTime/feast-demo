package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.ComeinRestBean;
import com.feast.demo.web.entity.UserBean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/10/22.
 */
@Service
public class ComeinRestService {
    // 所有店铺缓存
    private static HashMap<String, ArrayList> storeMap= new HashMap<String, ArrayList>();
    // 所有店铺用户缓存
    private static HashMap<String, ArrayList> store_userMap= new HashMap<String, ArrayList>();
    // 桌位-用户缓存
    private static HashMap<String, UserBean> desk_userMap = new HashMap<String, UserBean>();

    /**
     * 用户进店
     * @param jsonObj
     * @return
     */
    public ComeinRestBean userComeinProc(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        System.out.println("deskID is:"+jsonObj.getString("deskID"));
        System.out.println("userID is:"+jsonObj.getString("userID"));

        ComeinRestBean crBean = new ComeinRestBean();
        // 用户相关信息
        UserBean userBean = new UserBean();
        userBean.setUserID(jsonObj.getString("userID"));
        String storeID = jsonObj.getString("storeID");
        String userID = jsonObj.getString("userID");

        // 添加此人信息到本店缓存列表
        if(store_userMap.get(storeID)!=null && store_userMap.get(storeID).size()>0){
            store_userMap.get(storeID).add(userID);
        }else{
            ArrayList<String> userList = new ArrayList<String>();
            userList.add(userID);
            store_userMap.put(storeID, userList);
        }

        crBean.setResultCode("0");
        return crBean;
    }

    /**
     * 老板放桌
     * @param jsonObj
     * @return
     */
    public ComeinRestBean addDeskList(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));

        ComeinRestBean crBean = new ComeinRestBean();
        String deskID = "";
        String storeID = jsonObj.getString("storeID");
        if(storeMap.size() != 0 && storeMap.containsKey(storeID)){
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            deskID = formatter.format(currentTime) + storeID;
            ArrayList<String> deskList = storeMap.get(storeID);
            deskList.add(deskID);
            storeMap.put(storeID, deskList);
        }else{
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            deskID = formatter.format(currentTime) + storeID;
            ArrayList<String> deskList = new ArrayList<String>();
            deskList.add(deskID);
            storeMap.put(storeID, deskList);
        }
        crBean.setResultCode("0");
        crBean.setDeskID(deskID);

        return crBean;
    }

    /**
     * 新桌位通知
     * @param jsonObj
     * @return
     */
    public ComeinRestBean newDeskNotify(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("maxPerson is:"+jsonObj.getString("maxPerson"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("minPerson is:"+jsonObj.getString("minPerson"));
        System.out.println("desc is:"+jsonObj.getString("desc"));
        System.out.println("type is:"+jsonObj.getString("type"));

        ComeinRestBean crBean = new ComeinRestBean();

        crBean.setResultCode("0");

        return crBean;
    }

    /**
     * 用户出价
     * @param jsonObj
     * @return
     */
    public ComeinRestBean userOfferPrice(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        System.out.println("deskID is:"+jsonObj.getString("deskID"));
        System.out.println("userID is:"+jsonObj.getString("userID"));
        System.out.println("price is:"+jsonObj.getString("price"));
        System.out.println("type is:"+jsonObj.getString("type"));

        // 用户相关信息
        String userID = jsonObj.getString("userID");
        UserBean userBean = desk_userMap.get(userID);
        if(userBean == null){
            userBean = new UserBean();
        }
        BigDecimal price = new BigDecimal(jsonObj.getString("price"));
        userBean.setUserID(userID);
        userBean.setName(userID); // 暂时用手机号代替姓名
        userBean.setPrice(price);
        String deskID = jsonObj.getString("deskID");
        BigDecimal highPrice = new BigDecimal("0.00");
        if(desk_userMap.get(deskID)!=null){
            highPrice = desk_userMap.get(deskID).getHighPrice();
        }
        if(highPrice.compareTo(price)<0){
            highPrice = price;
        }
        userBean.setHighPrice(highPrice);

        desk_userMap.put(deskID, userBean);

        ComeinRestBean crBean = new ComeinRestBean();
        crBean.setResultCode("0");
        return crBean;
    }

    /**
     * 抢桌位
     * @param jsonObj
     * @return
     */
    public ComeinRestBean grabDesk(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        System.out.println("deskID is:"+jsonObj.getString("deskID"));
        System.out.println("userID is:"+jsonObj.getString("userID"));
        System.out.println("type is:"+jsonObj.getString("type"));

        // 抢桌位恒成功。逻辑暂时不定。结束后清除缓存桌位信息。
        String deskID = jsonObj.getString("deskID");
        desk_userMap.remove(deskID);
        // 保存历史到数据库历史桌位，待后续查询使用。

        ComeinRestBean crBean = new ComeinRestBean();
        crBean.setResultCode("0");
        return crBean;
    }

    /**
     * 抢桌位通知
     * @param jsonObj
     * @return
     */
    public ComeinRestBean grabDeskNotify(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("deskID is:"+jsonObj.getString("deskID"));
        System.out.println("userID is:"+jsonObj.getString("userID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        System.out.println("price is:"+jsonObj.getString("price"));

        ComeinRestBean crBean = new ComeinRestBean();
        crBean.setResultCode("0");
        return crBean;
    }

    /**
     * 出价排名变更通知
     * @param jsonObj
     * @return
     */
    public ComeinRestBean priceRankNotify(JSONObject jsonObj){
        //
        return new ComeinRestBean();
    }

    /**
     * 历史桌位列表接口
     * @param jsonObj
     * @return
     */
    public ComeinRestBean deskHistory(JSONObject jsonObj){
        //
        return new ComeinRestBean();
    }
}
