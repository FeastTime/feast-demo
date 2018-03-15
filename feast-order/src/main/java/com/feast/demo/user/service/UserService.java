package com.feast.demo.user.service;

import com.feast.demo.history.entity.UserStore;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.entity.UserDevice;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ggke on 2017/7/23.
 */
public interface UserService {

    User findByMobileNo(String mobileNo);

    void create(User user);

    void update(User user);

    User findByMobileAndPwd(String mobileNo, String pwd);

    /**
     * @param user
     */
    User saveWeChatUserInfo(User user);

    String checkWeChatUserBindStatus(Long openId);

    void saveUserPhone(Long userId, String mobileNo);

    User findById(Long userId);

    User queryUserInfo(Long userId);

    User storeLogin(String username, String password);

    ArrayList<User> findByUsername(String username);

    ArrayList<Store> queryHadEatenStore(Long userId, Integer order);

    UserStore findUserStoreByUserIdAndStoreId(Long userId, Long storeId);

    void saveUserStore(UserStore us);

    ArrayList<Long> findWaitersIdByStoreIdAndUserType(Long storeId, Integer userType);

    void saveUserInfo(User user);

    void updateUserInfo(String deviceId, String mobileNo, String nickName, String userIcon, String openId);

    User checkWeChatUserBindStatus(String openId);

    UserDevice findUserDeviceByUserIdAndDeviceId(Long userId, String deviceId);

    void saveUserDevice(UserDevice userDevice);

    void updateUserDeviceByUserId(Long userId,String deviceId);

    UserDevice findUserDeviceByUserId(Long userId);
}

