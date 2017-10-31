package com.feast.demo.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.service.ComeinRestService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
//@ServerEndpoint("/websocket")
//@ServerEndpoint(value = "/websocket/{tokenId}")
@ServerEndpoint("/websocket/{tokenId}/{storeId}")
public class WSService {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//    private static CopyOnWriteArraySet<WSService> webSocketSet = new CopyOnWriteArraySet<WSService>();


    CopyOnWriteArraySet<WSService> webSocketSet;
    private static HashMap<String,CopyOnWriteArraySet> hm =new HashMap<String,CopyOnWriteArraySet>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String tokenId;
    private String storeId;

    public WSService() {
        webSocketSet = new CopyOnWriteArraySet<WSService>();
        //hm =new HashMap<String,CopyOnWriteArraySet>();
    }

    /**
     * 接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("tokenId") String tokenId,@PathParam("storeId") String storeId) {
        this.session = session;
        this.tokenId = tokenId;
        this.storeId = storeId;

        if(storeId == null || "".equals(storeId)){
            //跳出去
            onClose();
        }

        if(tokenId == null || "".equals(tokenId)){
            //跳出去
            onClose();
        }
        // 回消息
        try {
            this.sendMessage("success666success");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(hm.containsKey(storeId)){
            webSocketSet = hm.get(storeId);
            webSocketSet.add(this);
        } else {
            webSocketSet = new CopyOnWriteArraySet<WSService>();
            webSocketSet.add(this);
            hm.put(storeId, webSocketSet);
        }

//        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     *连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
//        for (WSService item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }

        String storeId = null;

        try{
            JSONObject jsono = JSON.parseObject(message);
            storeId = jsono.getString("storeId");

        } catch (Exception e){
            e.printStackTrace();
        }

        if (null == storeId){
            return;
        }

        String resultMessage = ComeinRestService.WSInterfaceProc(message);

        webSocketSet = hm.get(storeId);

        for (WSService item : webSocketSet) {
            try {
                item.sendMessage(resultMessage);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     *发生错误时调用
     *@param session
     *@param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     *这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *@param message
     *@throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WSService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WSService.onlineCount--;
    }
}
