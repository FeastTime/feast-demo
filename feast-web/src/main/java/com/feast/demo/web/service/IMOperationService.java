package com.feast.demo.web.service;

import com.alibaba.fastjson.JSON;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.history.entity.UserStore;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.redPackage.entity.RedPackageDetail;
import com.feast.demo.redPackage.service.RedPackageDetailService;
import com.feast.demo.store.entity.Store;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.DinnerInfo;
import com.feast.demo.web.entity.UserBean;
import com.feast.demo.web.util.CouponIdCreator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.rong.RYConfig;
import io.rong.RongCloud;
import io.rong.messages.*;
import io.rong.models.CodeSuccessResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.rpc.holders.IntegerWrapperHolder;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;


/**
 *
 * Created by LiXQ on 2017/10/22.
 */
public class IMOperationService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // 用户与商户关系
    private static Map<String, HashMap<String, UserBean>> user2Store = Maps.newHashMap();

    // 红包
    private static Map<String, List<Object>> redPackageMap = Maps.newHashMap();

    // 红包与用户关系
    private static Map<String,Set<String>> redId2UserIdMap = Maps.newHashMap();


//    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    private static Random random = new Random();

    private Lock lock = new ReentrantLock();

    @Autowired
    private CouponService couponService;

    @Autowired
    private TableService tableService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private RedPackageService redPackageService;

    @Autowired
    private RedPackageDetailService redPackageDetailRemoteService;

    /**
     * 设置就餐人数
     *
     ** @param storeId 店铺ID
     */
    public void setNumberOfUser(Integer numberPerTable, String storeId,String userId) throws Exception{

        if (userId.length() == 0) {
            return;
        }

        user2Store.computeIfAbsent(storeId, k -> Maps.newHashMap());

        if (!user2Store.get(storeId).containsKey(userId)) {

            // 添加用户与商家关系 -> cache
            UserBean userBean = new UserBean();
            userBean.setUserID(userId);
            userBean.setNumberPerTable(0);
            userBean.setUserType(1);
            user2Store.get(storeId).put(userId, userBean);
        }

        user2Store.get(storeId).get(userId).setNumberPerTable(numberPerTable);

        List<Long> waiters = getWaiters(storeId);
        sendDinnerListChangeMessage(storeId, waiters);
    }


    /**
     * 通知商家人数变更
     *
     * @param storeId 商家ID
     */
    private void sendDinnerListChangeMessage(String storeId, List<Long> waiters) throws Exception {


        if (null == waiters || waiters.size() == 0) {
            return;
        }

        Map<String,Object> result = Maps.newHashMap();

        result.put("dinnerList",getDinnerList(storeId));
        result.put("storeId",storeId);


        String[] messagePublishPrivateToUserId = new String[waiters.size()];

        for (int i = 0; i < waiters.size(); i++) {

            messagePublishPrivateToUserId[i] = waiters.get(i).toString();
        }

        WaitingUserChangedMessage messagePublishPrivateVoiceMessage = new WaitingUserChangedMessage(new Date().getTime(),JSON.toJSONString(result));
        RongCloud rongCloud = RongCloud.getInstance(RYConfig.appKey, RYConfig.appSecret);


        CodeSuccessResult messagePublishPrivateResult = rongCloud.message.publishPrivate(messagePublishPrivateToUserId[0], messagePublishPrivateToUserId, messagePublishPrivateVoiceMessage, "thisisapush", "{\"pushData\":\"hello\"}", "4", 0, 0, 0, 0);
        System.out.println("publishPrivate:  " + messagePublishPrivateResult.toString());
    }

    /**
     * 获取店铺食客人数列表
     *
     * @param storeId 店铺Id
     * @return 食客人数列表
     */
    private List<DinnerInfo> getDinnerList(String storeId) {

        Map<Integer, Integer> personMap = new HashMap<>();

        Integer oneNumberPerTable;

        for (UserBean oneUser : user2Store.get(storeId).values()) {

            oneNumberPerTable = oneUser.getNumberPerTable();

            personMap.putIfAbsent(oneNumberPerTable, 0);
            personMap.put(oneNumberPerTable, personMap.get(oneNumberPerTable) + 1);
        }

        List<DinnerInfo> dinnerInfoList = new ArrayList<>();
        DinnerInfo dinnerInfo;

        for (Integer noPerTable : personMap.keySet()) {

            if(noPerTable==0){
                continue;
            }
            dinnerInfo = new DinnerInfo();
            dinnerInfo.setNumberPerTable(noPerTable);
            dinnerInfo.setWaitingCount(personMap.get(noPerTable));

            dinnerInfoList.add(dinnerInfo);
        }
        return dinnerInfoList;
    }

    /**
     * 拆红包
     *
     * @param redPackageId 红包ID
     * @param userId 用户ID
     * @param storeId 店铺ID
     */

    public Map<String, Object> takeRedPackage(String redPackageId,String userId,String storeId) {

        Map<String, Object> result = Maps.newHashMap();
        try {
            lock.lock();

            List<Object> redPackage = redPackageMap.get(redPackageId);
            redId2UserIdMap.computeIfAbsent(redPackageId+"", k -> Sets.newHashSet());


            if (null == redPackage || redPackage.size() == 0) {

                result.put("message", "对不起，您来晚了");
                result.put("takeRedPackageResultType",1);
                // 删除缓存数据
                redPackageMap.remove(redPackageId);
                redId2UserIdMap.remove(redPackageId+"");

                return result;

            } else if (redId2UserIdMap.get(redPackageId+"").contains(userId)) {

                result.put("message", "您已经抢过这个红包了");
                result.put("takeRedPackageResultType",2);
                return result;

            } else {

                Object lastObject = redPackage.get(redPackage.size() - 1);

                boolean hasGetTable = false;

                User user = userService.findById(Long.parseLong(userId));

                List<Long> waiters = getWaiters(storeId);

                String storeName = storeService.findStoreName(Long.parseLong(storeId));

                if (lastObject instanceof TableInfo) {

                    TableInfo tableInfo = (TableInfo) lastObject;
                    String[] supportSeatNumbers = tableInfo.getSuportSeatNumber().split(",");

                    for (String supportSeatNumber : supportSeatNumbers) {

                        System.out.println(supportSeatNumber+"===="+user2Store.get(storeId).get(userId).getNumberPerTable());
                        if(Integer.parseInt(supportSeatNumber)==user2Store.get(storeId).get(userId).getNumberPerTable()){

                            tableInfo.setUserPhone(user.getMobileNo());
                            tableInfo.setUserIcon(user.getUserIcon());
                            tableInfo.setUserNickname(user.getNickName());
                            tableInfo.setNumberOfMeals(Integer.parseInt(supportSeatNumber));
                            tableInfo.setUserId(Long.parseLong(userId));
                            tableInfo.setIsCome(1);
                            Date date = new Date();
                            tableInfo.setTaketableTime(date);
                            tableInfo.setStoreName(storeName);

                            tableInfo= tableService.saveTableInfo(tableInfo);

                            redId2UserIdMap.get(redPackageId+"").add(userId);

                            result.put("tableInfo", tableInfo);
                            result.put("takeRedPackageResultType",3);
                            redPackage.remove(redPackage.size() - 1);
                            result.put("message", "恭喜您获得一个桌位");

                            //保存红包详情信息
                            RedPackageDetail redPackageDetail = new RedPackageDetail();
                            redPackageDetail.setRedPackageId(redPackageId);
                            redPackageDetail.setUnpackTime(date);
                            redPackageDetail.setRedPackageTitle("座位");
                            redPackageDetail.setIsBestLuck(RedPackageDetail.ISBESTLUCK);
                            redPackageDetail.setUserId(Long.parseLong(userId));

                            redPackageDetailRemoteService.saveRedPackageDetail(redPackageDetail);

                            String[] messagePublishGroupToGroupId = {storeId};


                            Map<String ,Object> sendMessageMap = new HashMap<>();
                            sendMessageMap.put("date", new Date());
                            sendMessageMap.put("message", user.getNickName()+"手气太棒了！，恭喜您获得一个桌位！");
                            sendMessageMap.put("userIcon",user.getUserIcon());


                            ChatTextMessage messagePublishGroupTxtMessage = new ChatTextMessage(new Date().getTime(), JSON.toJSONString(sendMessageMap));


                            RongCloud rongCloud = RongCloud.getInstance(RYConfig.appKey, RYConfig.appSecret);


                            CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup(waiters.get(0)+"", messagePublishGroupToGroupId, messagePublishGroupTxtMessage, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
                            System.out.println("publishGroup:  " + messagePublishGroupResult.toString());
                            hasGetTable = true;
                            break;
                        }
                    }
                }

                if (!hasGetTable) {

                    int randomSize = redPackage.size();
                    if (lastObject instanceof TableInfo){
                        randomSize-- ;
                    }
                    if(randomSize<=0){

                        result.put("message", "对不起，您来晚了");
                        result.put("takeRedPackageResultType",1);
                    }else{
                        int i = random.nextInt(randomSize);

                        CouponTemplate couponTemplate = (CouponTemplate) redPackage.get(i);
                        couponTemplate = couponService.findCouponTemplateById(couponTemplate.getId());

                        UserCoupon userCoupon = new UserCoupon();
                        userCoupon.setCouponCode(CouponIdCreator.nextId());
                        userCoupon.setCouponTitle(couponTemplate.getCouponTitle());
                        userCoupon.setCouponPicture(couponTemplate.getCouponPicture());
                        userCoupon.setCouponType(couponTemplate.getCouponType());
                        Date date = new Date();
                        userCoupon.setStartTime(date);
                        userCoupon.setTakeTime(date);
                        userCoupon.setIsUse(UserCoupon.ISUSE_UNUSED);
                        userCoupon.setCouponValidity(new Date(new Date().getTime() + couponTemplate.getCouponValidity() * 24 * 60 * 60 * 1000));
                        userCoupon.setStoreId(couponTemplate.getStoreId());
                        userCoupon.setPermissionsDescribed(couponTemplate.getPermissionsDescribed());
                        userCoupon = couponService.saveUserCoupon(userCoupon);
                        userCoupon.setUserId(Long.parseLong(userId));
                        userCoupon.setStoreName(storeName);
                        couponService.saveUserCoupon(userCoupon);

                        redId2UserIdMap.get(redPackageId+"").add(userId);
                        result.put("couponInfo", userCoupon);
                        result.put("takeRedPackageResultType",3);
                        result.put("message", "恭喜您获得一张优惠券");

                        //保存红包详情信息
                        RedPackageDetail redPackageDetail = new RedPackageDetail();
                        redPackageDetail.setRedPackageId(redPackageId);
                        redPackageDetail.setUnpackTime(date);
                        redPackageDetail.setRedPackageTitle(couponTemplate.getCouponTitle());
                        redPackageDetail.setIsBestLuck(RedPackageDetail.NOTBESTLUCK);
                        redPackageDetail.setUserId(Long.parseLong(userId));

                        redPackageDetailRemoteService.saveRedPackageDetail(redPackageDetail);

                        redPackage.remove(i);

                        String[] messagePublishGroupToGroupId = {storeId};


                        Map<String ,Object> sendMessageMap = new HashMap<>();
                        sendMessageMap.put("date", new Date());
                        sendMessageMap.put("message", user.getNickName()+"手气不错哦！，恭喜获得一个优惠券！");
                        sendMessageMap.put("userIcon",user.getUserIcon());


                        ChatTextMessage messagePublishGroupTxtMessage = new ChatTextMessage(new Date().getTime(), JSON.toJSONString(sendMessageMap));


                        RongCloud rongCloud = RongCloud.getInstance(RYConfig.appKey, RYConfig.appSecret);

                        CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup(waiters.get(0)+"", messagePublishGroupToGroupId, messagePublishGroupTxtMessage, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
                        System.out.println("publishGroup:  " + messagePublishGroupResult.toString());
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return result;

    }

    /**
     * 发红包
     *
     * @param senderId 用户ID
     * @param storeId 店铺ID
     * @param tableInfo 桌位信息
     * @param couponList 优惠券信息
     */
    public void sendRedPackage(String senderId,String storeId,TableInfo tableInfo,List<CouponTemplate> couponList) {

        List<Object> redPackage = Lists.newArrayList();

        try {
            User sender = userService.findById(Long.parseLong(senderId));

            //红包中添加优惠券
            for (CouponTemplate couponTemplate : couponList) {

                Long count = couponTemplate.getCouponCount();

                CouponTemplate couponTemplate_ = couponService.findCouponTemplateById(couponTemplate.getId());

                if (null == count || null == couponTemplate_) {
                    continue;
                }

                logger.info("couponTemplate_.getCouponCount()" + couponTemplate_.getCouponCount() + "-----" + count);


                if (Long.compare(couponTemplate_.getCouponCount(), count) < 0 ) {

                    logger.info("couponTemplate_.getCouponCount()    result");
                    // 提示商家优惠券不足

                    try {
                        List<Long> waiters = getWaiters(storeId);

                        System.out.println("waiters.toString()" + waiters.toString());

                        sendCouponNotEnoughMessage(couponTemplate_.getCouponTitle(), waiters);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    continue;
                }

                for (int i = 0; i < count; i++) {
                    redPackage.add(couponTemplate_);
                }

                couponTemplate_.setCouponCount(couponTemplate_.getCouponCount()-count);
                couponService.createCouponTemplate(couponTemplate_);

            }


            //添加桌位

            if (null != tableInfo) {
                tableInfo.setMaketableTime(new Date());
                tableInfo = tableService.saveTableInfo(tableInfo);
                redPackage.add(tableInfo);
            }

            String redPackageId = UUID.randomUUID() + "";
            redPackageMap.put(redPackageId, redPackage);


            String senderNickName = sender.getNickName();
            String senderIcon = sender.getUserIcon();

            System.out.println(redPackageId);
            sendRedPackageMessage(senderId, storeId, redPackageId, senderNickName, senderIcon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送红包消息
     * @param senderId 发送者ID
     * @param storeId 店铺ID
     * @param redPackageId 红包ID
     * @param senderNickName 发送者昵称
     * @param senderIcon 发送者图标
     * @throws Exception 异常
     */
    private void sendRedPackageMessage(String senderId, String storeId, String redPackageId, String senderNickName, String senderIcon) throws Exception {
        Map<String, Object> result = Maps.newHashMap();

        result.put("userId", senderId);
        result.put("nickname", senderNickName);
        result.put("userIcon", senderIcon);
        result.put("redPackageId", redPackageId);

        String[] messagePublishGroupToGroupId = {storeId};
        RongCloud rongCloud = RongCloud.getInstance(RYConfig.appKey, RYConfig.appSecret);
        ReceivedRedPackageMessage messagePublishGroupTxtMessage = new ReceivedRedPackageMessage(new Date().getTime(), JSON.toJSONString(result));
        CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup(senderId, messagePublishGroupToGroupId, messagePublishGroupTxtMessage, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
        System.out.println("publishGroup:  " + messagePublishGroupResult.toString());
    }


    /**
     * 用户扫码进店
     *
     * @param userId 用户ID
     * @param storeId 商家ID
     */
    public void userComeInProc(String userId, String storeId) throws  Exception{

        if (null == storeId || null == userId) {

            logger.info("userId or storeId  is null");
            return;
        }

        User user = userService.findById(Long.parseLong(userId));

        // 添加用户与商家关系 -> cache
        UserBean userBean = new UserBean();
        userBean.setUserID(userId);
        userBean.setNumberPerTable(0);
        userBean.setUserType(1);

        user2Store.computeIfAbsent(storeId, k -> Maps.newHashMap());
        user2Store.get(storeId).put(userId, userBean);

        HashMap<String, Object> result = Maps.newHashMap();

        // 添加用户与商家关系 -> db
        try {
            UserStore userStore = userService.findUserStoreByUserIdAndStoreId(Long.parseLong(userId), Long.parseLong(storeId));

            Date date = new Date();

            if (userStore == null) {
                userStore = new UserStore();
                userStore.setCreateTime(date);
                userStore.setStoreId(Long.parseLong(storeId));
                userStore.setUserId(Long.parseLong(userId));
                userStore.setCount(1L);

            } else {
                userStore.setCount(userStore.getCount() + 1);
            }

            userStore.setStatus(3);
            userStore.setLastModified(date);

            userService.saveUserStore(userStore);

            Store store = storeService.getStoreInfo(Long.parseLong(storeId));

            result.put("date", new Date());
            result.put("userId", userId);
            result.put("message", "欢迎" + user.getNickName() + "进店！店小二祝您用餐愉快");
            result.put("nickName", store.getStoreName());
            result.put("userIcon", store.getStoreIcon());

            ChatTextMessage messagePublishGroupTxtMessage = new ChatTextMessage(new Date().getTime(),JSON.toJSONString(result));

            RongCloud rongCloud = RongCloud.getInstance(RYConfig.appKey, RYConfig.appSecret);

            String[] messagePublishGroupToGroupId = {storeId};


            List<Long> waiters = getWaiters(storeId);

            if(user.getUserType() == UserBean.CUSTOMER){

                CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup(waiters.get(0).toString(), messagePublishGroupToGroupId, messagePublishGroupTxtMessage, "", "", 1, 1, 0);
                System.out.println("publishGroup:  " + messagePublishGroupResult.toString());
            }

            // 通知商家人数变更
            sendDinnerListChangeMessage(storeId, waiters);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 设置用户与商家的关系
     *
     * @param userId 用户ID
     * @param storeId 店铺ID
     * @param status 关系状态
     * @throws Exception 异常
     */
    public void setRelationshipWithStore(String userId, String storeId, int status) throws Exception {

        if (null == userId || null == storeId){

            return ;
        }

        UserStore userStore = userService.findUserStoreByUserIdAndStoreId(Long.parseLong(userId), Long.parseLong(storeId));
        Date date = new Date();

        if (userStore == null) {

            userStore = new UserStore();

            userStore.setCreateTime(date);
            userStore.setStoreId(Long.parseLong(storeId));
            userStore.setUserId(Long.parseLong(userId));
        }

        userStore.setLastModified(date);


        // 修改用户与店铺之间的关系
        try {

            userStore.setStatus(status);

            userService.saveUserStore(userStore);

        } catch (Exception e){

            e.printStackTrace();
        }

        // 不再关注
        if (status == UserStore.STATUS_RELATION_BREAK){

            // 将连接  从  用户与商户的关系结构中 删除
            user2Store.get(storeId).remove(userId);

            List<Long> waiters = getWaiters(storeId);

            // 通知商家用户离店
            sendDinnerListChangeMessage(storeId, waiters);
        }
    }


//    /**
//     * 恢复用户与商家的关系
//     *
//     * @param userId 用户ID
//     */
//    public void repairRelationship(String userId){
//
//        // 恢复用户与商家的关系
//        Set<Long> storeIds = userService.findStoreIdByUserId(Long.parseLong(userId));
//
//        if(storeIds!=null){
//
//            String storeIdStr;
//            UserBean userBean;
//
//            for (Long storeId: storeIds) {
//
//                storeIdStr = storeId + "";
//                userBean = new UserBean();
//                userBean.setUserID(userId);
//                userBean.setNumberPerTable(0);
//
//                user2Store.computeIfAbsent(storeIdStr, k -> Maps.newHashMap());
//                user2Store.get(storeIdStr).put(userId, userBean);
//            }
//        }
//    }


//    /**
//     * 用户离店
//     *
//     * @param userId 用户ID
//     */
//    public List<WebSocketMessageBean> removeRelationship(String userId) throws Exception{
//
//        List<WebSocketMessageBean> allDinnerListMessage = new ArrayList<>();
//        List<WebSocketMessageBean> dinnerListMessage;
//
//
//        for (String storeId : user2Store.keySet()) {
//
//            // 将连接  从  用户与商户的关系结构中 删除
//            user2Store.get(storeId).remove(userId);
//
//            List<Long> waiters = userService.findUserIdByStoreId(Long.parseLong(storeId));
//
//            // 通知商家用户离店
//            sendDinnerListChangeMessage(storeId, waiters);
//
//        }
//
//        return allDinnerListMessage;
//    }

    // 存放最后一次发放时间
    public static Map<Long, Long> redPackageSendTime = new HashMap<>();


    /**
     * 自动发红包
     */
    public void autoSenderRedPackage(){

        logger.info("自动发红包");

        Date newDate = new Date();
        long nowTime = newDate.getTime();

//        // 查询在线的店
//        List<Long> storeIds = getLongListFromStringSet(user2Store.keySet());

        // 查询店存储的红包信息
        List<RedPackage> redPackageInfoList = redPackageService.findRedPackageByIsUse(2);

        if (null == redPackageInfoList || redPackageInfoList.size() == 0){
            logger.info("没有查询到需要发送的红包");

            return;
        }

        logger.info("循环发送红包");

        // 循环发送红包
        for (RedPackage redPackageInfo : redPackageInfoList) {

            // 如果之前没法送过红包，则设置发红包时间为0
            redPackageSendTime.putIfAbsent(redPackageInfo.getRedPackageId(), 0L);

            // 当前时间 - 最后一次发送时间  超过    设置的时间间隔   则发红包

            long distanceTime = nowTime - redPackageSendTime.get(redPackageInfo.getRedPackageId());

            if (distanceTime > redPackageInfo.getAutoSendTime()*60*1000 ){

                System.out.println("时间超过 设置时间 红包发送");

                // 设置最后发送红包的时间
                redPackageSendTime.put(redPackageInfo.getRedPackageId(), nowTime);

                // 发送红包
                sendRedPackage(redPackageInfo);



            }
        }
    }

    /**
     * 发送红包
     *
     * @param redPackageInfo 红包信息
     */
    private void sendRedPackage(RedPackage redPackageInfo) {

        // 创建红包
        List<Object> redPackage  = Lists.newArrayList();

        // 红包信息 ID
        Long redPackageInfoId = redPackageInfo.getRedPackageId();

        // 查询红包优惠券模板
        List<RedPackageCouponTemplate> redPackageCouponTemplates = redPackageService.findRedPackageCouponTemplateByRedPackageId(redPackageInfoId);

        ArrayList<Long> waiters = getWaiters(redPackageInfo.getStoreId().toString());

        if (null == waiters || waiters.size() == 0){
            logger.info("没有店员 不发送红包");
            try {
                countDown(redPackageInfo.getStoreId(),false,null,waiters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        logger.info("waiters.toString()" + waiters.toString());

        // 把优惠券添加到红包中
        for (RedPackageCouponTemplate redPackageCouponTemplate : redPackageCouponTemplates) {

            Long couponTemplateId = redPackageCouponTemplate.getCouponTemplateId();
            Integer couponCount = redPackageCouponTemplate.getCouponCount();

            CouponTemplate couponTemplate = couponService.findCouponTemplateById(couponTemplateId);

            if(null == couponTemplate){
                continue;
            }

            if (Long.compare(couponTemplate.getCouponCount() , couponCount) < 0) {

                try {
                    redPackageService.setRedPackageIsNotUse(redPackageInfoId);
                    sendCouponNotEnoughMessage(couponTemplate.getCouponTitle(), waiters);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                continue;
            }

            // 添加优惠券
            for (int i = 0; i < couponCount; i++) {

                redPackage.add(couponTemplate);
            }

            // 扣减  优惠券模板中的  优惠券张数
            couponTemplate.setCouponCount(couponTemplate.getCouponCount() - couponCount);
            couponService.updateCouponTemplate(couponTemplate);

        }

        // 创建优惠券Id
        String redPackageId = UUID.randomUUID() + "";

        if(redPackage.size()!=0){
            redPackageMap.put(redPackageId, redPackage);
        }else{
            return;
        }


        // 发送红包 到  群
        try {

            User firstWaiter = userService.findById(waiters.get(0));
            sendRedPackageMessage(firstWaiter.getUserId().toString(), redPackageInfo.getStoreId().toString(), redPackageId, firstWaiter.getNickName(), firstWaiter.getUserIcon());
            long countDownTime = new Date().getTime() - IMOperationService.redPackageSendTime.get(redPackageInfoId);
            countDown(redPackageInfo.getStoreId(),true,countDownTime,waiters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 通知商家优惠券不足
     *
     * @param couponTitle 优惠券标题
     * @param waiters 服务器
     * @throws Exception 异常
     */
    private void sendCouponNotEnoughMessage(String couponTitle, List<Long> waiters) throws Exception {


        if (null == waiters || waiters.size() == 0) {
            return;
        }

        Map<String, Object> result = Maps.newHashMap();

        result.put("title", "库存不足");
        result.put("message", "\""+couponTitle + "\"存量不足");
        result.put("promptInformation", "请及时到\"优惠券管理\"补充");

        String[] messagePublishPrivateToUserId = new String[waiters.size()];

        for (int i = 0; i < waiters.size(); i++) {

            messagePublishPrivateToUserId[i] = waiters.get(i).toString();
        }

        CouponNotEnoughMessage messagePublishPrivateVoiceMessage = new CouponNotEnoughMessage(new Date().getTime(),JSON.toJSONString(result));
        RongCloud rongCloud = RongCloud.getInstance(RYConfig.appKey, RYConfig.appSecret);

        CodeSuccessResult messagePublishPrivateResult = rongCloud.message.publishPrivate(messagePublishPrivateToUserId[0], messagePublishPrivateToUserId, messagePublishPrivateVoiceMessage, "thisisapush", "{\"pushData\":\"hello\"}", "4", 0, 0, 0, 0);
        System.out.println("publishPrivate:  " + messagePublishPrivateResult.toString());
    }


    private ArrayList<Long> getWaiters(String storeId){
        return userService.findWaitersIdByStoreIdAndUserType(Long.parseLong(storeId),UserBean.STORE);
    }


    /**
     * 倒计时
     *
     * @param storeId
     * @param isCountDown
     * @param countDownTime
     * @param waiterIds
     * @throws Exception
     */
    public void countDown(Long storeId,boolean isCountDown,Long countDownTime,ArrayList<Long> waiterIds) throws Exception{
        Map<String,Object> result = Maps.newHashMap();
        result.put("isCountDown",isCountDown);
        result.put("countDownTime",countDownTime);

        String[] messagePublishGroupToGroupId = {storeId+""};
        RongCloud rongCloud = RongCloud.getInstance(RYConfig.appKey, RYConfig.appSecret);
        CountDownMessage countDownMessage = new CountDownMessage(new Date().getTime(), JSON.toJSONString(result));
        CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup(waiterIds.get(0)+"", messagePublishGroupToGroupId, countDownMessage, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
        System.out.println("publishGroup:  " + messagePublishGroupResult.toString());
    }
//    /**
//     * 格式转换
//     *
//     * @param set 输入
//     * @return 返回列表
//     */
//    private List<Long> getLongListFromStringSet(Set<String> set){
//
//        List<Long> result = Lists.newArrayList();
//
//        for (String storeId : set) {
//
//            if (null != storeId && storeId.length()>0){
//                result.add(Long.parseLong(storeId));
//            }
//        }
//
//        return result;
//    }

}
