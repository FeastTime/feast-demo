package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.WebSocketMessageBean;
import com.feast.demo.web.entity.WsBean;
import com.feast.demo.web.service.ComeinRestService;
import com.feast.demo.web.service.UserService;
import com.feast.demo.web.service.WebSocketEvent;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;


/**
 * ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{userId}")
public class WSService {

    private ComeinRestService comeinRestService;

    private UserService userService;

    private String userId;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Session session;


    // 用户与server关系
    private static Map<String, WsBean> user2Server = Maps.newConcurrentMap();

    // 用户与商户关系
    private static Map<String, CopyOnWriteArraySet<String>> user2Store = Maps.newConcurrentMap();

    public WSService() {}

    private void setComeInRestService() {

        String configLocation = "classpath*:/spring*/*.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        comeinRestService = context.getBean(ComeinRestService.class);
        userService = context.getBean(UserService.class);
    }

    /**
     * 接建立成功调用的方法
     *
     * @param session 连接
     * @param userId 用户ID
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {

        // 如果用户ID为空， 关闭连接
        if(userId == null || "".equals(userId)){

            try {
                session.close();
            } catch (Exception ignored) {
            }

            return;
        }

        this.session = session;
        this.userId = userId;

        // 启动 Spring Service
        if(comeinRestService == null) {
            setComeInRestService();
        }

        // 回消息 告诉客户端连接成功
        sendMessage("success666success", session);

        User user = userService.findById(Long.parseLong(userId));

        WsBean wsBean = new WsBean();
        wsBean.setWsService(this);
        wsBean.setUser(user);

        if (!user2Server.containsKey(userId)){
            user2Server.put(userId, wsBean);
        }


        // 恢复用户与商家的关系
        Set<Long> storeIds = userService.findStoreIdByUserId(Long.parseLong(userId));
        if(storeIds!=null){
            for (Long storeId: storeIds) {
                String storeIdStr = storeId + "";
                logger.info(storeIdStr);

                user2Store.computeIfAbsent(storeIdStr, k -> Sets.newCopyOnWriteArraySet());
                user2Store.get(storeIdStr).add(userId);
            }
        }

    }

    /**
     *连接关闭调用的方法
     */
    @OnClose
    public void onClose() {

        // 将连接  从  用户与服务端的关系结构中 删除
        user2Server.remove(this.userId);

        // 将连接  从  用户与商户的关系结构中 删除
        for (CopyOnWriteArraySet<String> set : user2Store.values()) {
            set.remove(this.userId);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        if(comeinRestService == null) {
            setComeInRestService();
        }

        logger.info("来自客户端的消息:" + message + (null == session));

        System.out.println("转之前 -- 来自客户端的消息"+message);
        message = StringUtils.decode(message);
        System.out.println("转之后 -- 来自客户端的消息"+message);

        JSONObject jsonObject  = JSON.parseObject(message);

        int type = jsonObject.getInteger("type");

        String storeId = jsonObject.getString("storeId");
        String userId = jsonObject.getString("userId");



        // 如果是扫码进店, 添加用户与商家关系
        if (type == WebSocketEvent.ENTER_STORE) {

            if (null != storeId && null != userId){

                user2Store.computeIfAbsent(storeId, k -> Sets.newCopyOnWriteArraySet());

                user2Store.get(storeId).add(userId);

                //查看这条记录是否已经存在，不存在保存到数据库，存在更新访问时间
                System.out.println(user2Server.get(userId).getUser());
                comeinRestService.WSInterfaceProc(type,jsonObject,user2Server.get(userId).getUser(),storeId);

            }
        }
        // 如果不是扫码进店，处理其他业务
        else {
            try {
                List<WebSocketMessageBean> list = comeinRestService.WSInterfaceProc(type, jsonObject, user2Server.get(userId).getUser(), storeId);

                if (null != list){

                    for (WebSocketMessageBean webSocketMessageBean: list) {

                        // 发送消息给单个用户
                        if (null != webSocketMessageBean.getUserId()
                                && null != webSocketMessageBean.getMessage()
                                && webSocketMessageBean.getMessage().length() > 0){

                            System.out.println("send to user : " + webSocketMessageBean.getUserId() + "  --    message : " + webSocketMessageBean.getMessage());
                            sendMessageToUser(webSocketMessageBean.getUserId(), webSocketMessageBean.getMessage());
                        }
                        // 发送消息给店家所有用户
                        else if (null != webSocketMessageBean.getStoreId()
                                && null != webSocketMessageBean.getMessage()
                                && webSocketMessageBean.getMessage().length() > 0){

                            System.out.println("send to Store : " + webSocketMessageBean.getStoreId()+ "  --    message : " + webSocketMessageBean.getMessage());
                            sendMessageToStore(webSocketMessageBean.getStoreId(), webSocketMessageBean.getMessage());
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 对用户发送消息
     *
     * @param userId 用户ID
     * @param message 消息
     */
    private static void sendMessageToUser(String userId, String message) {

        try {
            sendMessage(message, user2Server.get(userId).getWsService().session);
        } catch (Exception e) {
            System.out.println("发送消息异常");
        }
    }


    /**
     * 对商户下所有用户发送消息
     *
     * @param storeId 店铺ID
     * @param message 消息
     */
    private static void sendMessageToStore(String storeId, String message) {

        CopyOnWriteArraySet<String> set = user2Store.get(storeId);

        for (String userId : set) {
            try {
                sendMessage(message, user2Server.get(userId).getWsService().session);
            } catch (Exception e) {
                System.out.println("发送消息异常");
            }
        }
    }

    /**
     *发生错误时调用
     *
     *@param session Session
     *@param error Throwable
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误" + (null == session));
        error.printStackTrace();
    }

    /**
     *这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     *@param message 消息
     */
    private static void sendMessage(String message, Session session) {

        //  同步发送消息
        // session.getBasicRemote().sendText(message);

        //  异步发送消息
        session.getAsyncRemote().sendText(message);
    }

}
