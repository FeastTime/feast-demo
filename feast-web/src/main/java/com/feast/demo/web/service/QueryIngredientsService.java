package com.feast.demo.web.service;

import com.feast.demo.web.entity.UserObj;
import org.springframework.stereotype.Service;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class QueryIngredientsService {


    public UserObj getStatus(UserObj user,String flag){

        System.out.println("androidID is:"+user.getAndroidID());
        System.out.println("imei is:"+user.getImei());
        System.out.println("ipv4 is:"+user.getIpv4());
        System.out.println("mac is:"+user.getMac());
        System.out.println("mobileNO is:"+user.getMobileNO());
        if("register".equals(flag)){
            System.out.println("register");
        }else{
            System.out.println("login");
        }



        user.setResultCode("0");
        user.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
        return user;
    }



}
