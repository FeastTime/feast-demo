package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.history.entity.UserStore;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.entity.UserDevice;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.LoginMemory;
import com.feast.demo.web.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public User fingByMobileNo(String mobileNo){
        return userRemoteService.findByMobileNo(mobileNo);
    }

    /**
     * 手机号和密码登陆
     * @param mobileNo
     * @param pwd
     * @return
     */
    public User findByMobileAndPwd(String mobileNo,String pwd){
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
    
    public User saveWeChatUserInfo(User user) {
        return userRemoteService.saveWeChatUserInfo(user);
    }

    public String checkWeChatUserBindStatus(Long userId) {
        return userRemoteService.checkWeChatUserBindStatus(userId);
    }

    public void saveUserPhone(Long userId,String mobileNo) {
        userRemoteService.saveUserPhone(userId,mobileNo);
    }

    public User findById(Long userId) { return userRemoteService.findById(userId);}

    public User queryUserInfo(Long userId) {
        return userRemoteService.queryUserInfo(userId);
    }

    public User storeLogin(String username, String password) {
        return userRemoteService.storeLogin(username,password);
    }

    public List<User> findByUsername(String username) {
        return userRemoteService.findByUsername(username);
    }

    public ArrayList<Store> queryHadEatenStore(Long userId, Integer order) {
        return userRemoteService.queryHadEatenStore(userId,order);
    }

    public UserStore findUserStoreByUserIdAndStoreId(Long userId, Long storeId) {
        return userRemoteService.findUserStoreByUserIdAndStoreId(userId,storeId);
    }

    public void saveUserStore(UserStore us) {
        userRemoteService.saveUserStore(us);
    }

    public ArrayList<Long> findWaitersIdByStoreIdAndUserType(Long storeId, Integer userType) {
        return userRemoteService.findWaitersIdByStoreIdAndUserType(storeId,userType);
    }

    public void saveUserInfo(User user) {
        userRemoteService.saveUserInfo(user);
    }

    public void updateUserInfo(String deviceId, String mobileNo, String nickName, String userIcon, String openId) {
        userRemoteService.updateUserInfo(deviceId,mobileNo,nickName,userIcon,openId);
    }

    public User checkWeChatUserBindStatus(String openId) {
        return userRemoteService.checkWeChatUserBindStatus(openId);
    }

    public UserDevice findUserDeviceByUserIdAndDeviceId(Long userId, String deviceId) {
        return userRemoteService.findUserDeviceByUserIdAndDeviceId(userId,deviceId);
    }

    public void updateUserDeviceByUserId(Long userId, String deviceId) {
        userRemoteService.updateUserDeviceByUserId(userId,deviceId);
    }

    public void saveUserDevice(UserDevice userDevice) {
        userRemoteService.saveUserDevice(userDevice);
    }

    public UserDevice findUserDeviceByUserId(Long userId) {
        return userRemoteService.findUserDeviceByUserId(userId);
    }
}

