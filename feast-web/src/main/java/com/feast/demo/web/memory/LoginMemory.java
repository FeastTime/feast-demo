package com.feast.demo.web.memory;


import com.feast.demo.web.entity.UserObj;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginMemory {

    private static Map<String, UserObj> map;
    private static boolean isStop = false;

    static  {
        map = new ConcurrentHashMap<String, UserObj>();

        new Thread(new Runnable() {

            public void run() {

                while (isStop) {
                    System.out.println("map size is  : " + map.size());
                    try {
                        Thread.sleep(3000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static UserObj get(String key){

        return map.get(key);
    }

    public static void set(String key, UserObj userObj){
        map.put(key, userObj);
    }

    public void remove(String key){
        map.remove(key);
    }


}
