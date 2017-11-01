package com.feast.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.ComeinRestBean;
import com.feast.demo.web.entity.DeskInfoBean;
import com.feast.demo.web.entity.UserBean;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    // 桌位-用户缓存
    private static HashMap<String, DeskInfoBean> desk_infoMap = new HashMap<String, DeskInfoBean>();

    @Autowired
    private  TableBidService tableBidService;

    /**
     * 对接老马接口入口
     * @param message
     * @return
     */
    public String WSInterfaceProc(String message){
        System.out.println("转之前"+message);
        message = StringUtils.decode(message);
        System.out.println("转之后"+message);

        JSONObject jsono  = new JSONObject();

        try{
            jsono = JSON.parseObject(message);
        }catch(Exception e){
            e.printStackTrace();
        }

        int type = Integer.parseInt(jsono.getString("type"));
        String retMessage = "";
        switch(type){
            case 1:
                retMessage = userComeinProc(jsono);
                break;
            case 2:
                retMessage = addDeskList(jsono);
                break;
            case 3:
//                retMessage = newDeskNotify(jsono);
                break;
            case 4:
                retMessage = userOfferPrice(jsono);
                break;

        }
        return retMessage;
    }

    /**
     * 用户进店
     * @param jsonObj
     * @return
     */
    public static String userComeinProc(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        System.out.println("deskID is:"+jsonObj.getString("deskID"));
        System.out.println("userID is:"+jsonObj.getString("userID"));

        ComeinRestBean crBean = new ComeinRestBean();
        Map<Object,Object> result = Maps.newHashMap();
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
        result.put("resultCode", crBean.getResultCode());
        return JSON.toJSONString(result);
    }

    /**
     * 老板放桌
     * @param jsonObj
     * @return
     */
    public String addDeskList(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("type is:"+jsonObj.getString("type"));
        String maxPerson = jsonObj.getString("maxPerson");
        String minPerson = jsonObj.getString("minPerson");
        String storeID = jsonObj.getString("storeID");
        String desc = jsonObj.getString("desc");

        DeskInfoBean deskInfoBean = new DeskInfoBean();
        Map<Object,Object> result = Maps.newHashMap();
        String deskID = "";
        if(storeMap.size() != 0 && storeMap.containsKey(storeID)){
            String currentTime = String.valueOf(System.currentTimeMillis());
            deskID = currentTime + storeID;
            ArrayList<String> deskList = storeMap.get(storeID);
            deskList.add(deskID);
            storeMap.put(storeID, deskList);
        }else{
            String currentTime = String.valueOf(System.currentTimeMillis());
            deskID = currentTime + storeID;
            ArrayList<String> deskList = new ArrayList<String>();
            deskList.add(deskID);
            storeMap.put(storeID, deskList);
        }
        deskInfoBean.setResultCode("0");
        deskInfoBean.setDeskID(deskID);
        deskInfoBean.setMaxPerson(maxPerson);
        deskInfoBean.setMinPerson(minPerson);
        deskInfoBean.setDesc(desc);
        deskInfoBean.setStoreID(storeID);


        // 开启竞价
        String bid = tableBidService.openBid(120000L);

        deskInfoBean.setBid(bid);
        desk_infoMap.put(deskID, deskInfoBean);

        result.put("resultCode", deskInfoBean.getResultCode());
        result.put("maxPerson", deskInfoBean.getMaxPerson());
        result.put("minPerson", deskInfoBean.getMinPerson());
        result.put("storeID", deskInfoBean.getStoreID());
        result.put("desc", deskInfoBean.getDesc());
        result.put("deskID", deskInfoBean.getDeskID());
        result.put("bid", deskInfoBean.getBid());
        result.put("type", "3");
        return JSON.toJSONString(result);
    }

    /**
     * 新桌位通知
     * @param jsonObj
     * @return
     */
    public static String newDeskNotify(JSONObject jsonObj){
        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("maxPerson is:"+jsonObj.getString("maxPerson"));
        System.out.println("storeID is:"+jsonObj.getString("storeID"));
        System.out.println("minPerson is:"+jsonObj.getString("minPerson"));
        System.out.println("desc is:"+jsonObj.getString("desc"));
        System.out.println("type is:"+jsonObj.getString("type"));
        Map<Object,Object> result = Maps.newHashMap();

        ComeinRestBean crBean = new ComeinRestBean();

        crBean.setResultCode("0");

        result.put("resultCode", crBean.getResultCode());
        return JSON.toJSONString(result);
    }

    /**
     * 用户出价
     * @param jsonObj
     * @return
     */
    public static String userOfferPrice(JSONObject jsonObj){
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
        Map<Object,Object> result = Maps.newHashMap();

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
        result.put("resultCode", crBean.getResultCode());
        return JSON.toJSONString(result);
    }

    /**
     * 抢桌位
     * @param jsonObj
     * @return
     */
    public static ComeinRestBean grabDesk(JSONObject jsonObj){
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
    public static ComeinRestBean grabDeskNotify(JSONObject jsonObj){
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

    public String open(){
        return tableBidService.openBid();
    }

}
