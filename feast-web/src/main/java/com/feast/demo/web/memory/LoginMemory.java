package com.feast.demo.web.memory;


import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.UserObj;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginMemory {

    private static Map<String, User> map;
    private static boolean isStop = false;

    static  {
        map = new ConcurrentHashMap<String, User>();

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

    public static User get(String key){

        return map.get(key);
    }

    public static void set(String key, User user){
        map.put(key, user);
    }

    public static void remove(String key){
        map.remove(key);
    }


}
