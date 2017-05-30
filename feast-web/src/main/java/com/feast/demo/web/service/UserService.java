package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Service;
import com.feast.demo.web.entity.UserObj;
/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class UserService {

    public UserObj getStatus(JSONObject jsonObj, String flag) {

        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("mobileNO is:"+jsonObj.getString("mobileNO"));

        UserObj userObj = new UserObj();

        if ("register".equals(flag) || "login".equals(flag)) {
            String mobileNo = jsonObj.getString("mobileNO");
            if (!"".equals(mobileNo)) {
                if ("register".equals(flag)) {
                    if ("13301018888".equals(mobileNo)) {
                        userObj.setResultCode("0");
                        userObj.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
                    } else {
                        userObj.setResultCode("1");
                        userObj.setResultMsg(StringUtils.encode("该手机号已注册，请更换手机号！"));
                    }
                } else if ("login".equals(flag)) {
                    if ("13301019999".equals(mobileNo)) {
                        userObj.setResultCode("0");
                        userObj.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
                    } else {
                        userObj.setResultCode("1");
                        userObj.setResultMsg(StringUtils.encode("该手机号不存在，请更换手机号！"));
                    }
                } else {
                    userObj.setResultCode("1");
                    userObj.setResultMsg(StringUtils.encode("非法操作，请重试！"));
                }
            } else {
                userObj.setResultCode("1");
                userObj.setResultMsg(StringUtils.encode("手机号为空！"));
            }
        }
        if("logout".equals(flag)){
            String token = jsonObj.getString("token");
            if("ljiqsdgf54sdfweq6565f7wes51635sad4f65f".equals(token)){
                userObj.setResultCode("0");
                userObj.setResultMsg(StringUtils.encode("成功退出"));
            }else{
                userObj.setResultCode("1");
                userObj.setResultMsg(StringUtils.encode("退出异常，请重新尝试！"));
            }
        }
        return userObj;
    }
}

