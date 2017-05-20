package com.feast.demo.web.service;

import org.springframework.stereotype.Service;
import com.feast.demo.web.entity.UserObj;
/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class UserService {

    public UserObj getStatus(UserObj user, String flag) {

        System.out.println("androidID is:" + user.getAndroidID());
        System.out.println("imei is:" + user.getImei());
        System.out.println("ipv4 is:" + user.getIpv4());
        System.out.println("mac is:" + user.getMac());
        System.out.println("mobileNO is:" + user.getMobileNO());
        if ("register".equals(flag) || "login".equals(flag)) {
            String mobileNo = user.getMobileNO();
            if (!"".equals(mobileNo)) {
                if ("register".equals(flag)) {
                    if ("13301018888".equals(mobileNo)) {
                        user.setResultCode("0");
                        user.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
                    } else {
                        user.setResultCode("1");
                        user.setResultMsg("该手机号已注册，请更换手机号！");
                    }
                } else if ("login".equals(flag)) {
                    if ("13301019999".equals(mobileNo)) {
                        user.setResultCode("0");
                        user.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
                    } else {
                        user.setResultCode("1");
                        user.setResultMsg("该手机号已注册，请更换手机号！");
                    }
                } else {
                    user.setResultCode("1");
                    user.setResultMsg("非法操作，请重试！");
                }
            } else {
                user.setResultCode("1");
                user.setResultMsg("手机号为空！");
            }
        }
        if("logout".equals(flag)){
            String token = user.getToken();
            if("ljiqsdgf54sdfweq6565f7wes51635sad4f65f".equals(token)){
                user.setResultCode("0");
                user.setResultMsg("成功退出");
            }else{
                user.setResultCode("1");
                user.setResultMsg("退出异常，请重新尝试！");
            }
        }

        return user;

    }
}

