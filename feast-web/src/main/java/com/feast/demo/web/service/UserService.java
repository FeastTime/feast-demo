package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class UserService {

    @Autowired
    private com.feast.demo.user.service.UserService userRemoteService;

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
                        userObj.setMobileNO("13301019999");
                        userObj.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
                    } else {
                        if (LoginMemory.get(mobileNo) !=null){
                            userObj.setResultCode("0");
                            userObj.setMobileNO(mobileNo);
                            userObj.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
                        }else{
                            //插入操作
                            userObj.setResultCode("0");
                            userObj.setMobileNO(mobileNo);
                            userObj.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
                        }

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

    /**
     * 根据手机号码查询用户
     * @param mobileNo
     * @return
     */
    public User fingByMobileNo(Long mobileNo){
        return userRemoteService.findByMobileNo(mobileNo);
    }

    /**
     * 手机号和密码登陆
     * @param mobileNo
     * @param pwd
     * @return
     */
    public User findByMobileAndPwd(Long mobileNo,String pwd){
        return userRemoteService.findByMobileAndPwd(mobileNo,pwd);
    }

    public String createUser(User user){
        String msg = null;
        if(user.getMobileNo()==null){
            msg = "手机号码不能为空";
        }else if(fingByMobileNo(user.getMobileNo()) != null){
            msg = "该手机号已经注册过，请登录";
        }
        if(StringUtils.isEmpty(msg)){
            userRemoteService.create(user);
        }
        return msg;
    }

    /**
     * 根据名称查询用户
     * @param name
     * @return
     */
    public List<User> findByName(String name){
        return userRemoteService.findByName(name);
    }
}

