package com.feast.demo.user.service;

import com.feast.demo.history.entity.UserStore;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ggke on 2017/7/23.
 */
public interface UserService {

    public User findByMobileNo(String mobileNo);

    public void create(User user);

    public void update(User user);

    public User findByMobileAndPwd(String mobileNo, String pwd);

    /**
     * @param user
     */
    public void saveWeChatUserInfo(User user);

    public User checkWeChatUserBindStatus(String openId);

    public void saveUserPhone(Long userId, String mobileNo);

    public User findById(Long userId);

    public User queryUserInfo(Long userId);

    public void setRelationshipWithStore(Long userId, Long storeId, Integer status);

    public User storeLogin(String username, String password);

    public ArrayList<User> findByUsername(String username);

    public ArrayList<Store> queryHadEatenStore(Long userId, Integer order);

    public UserStore findUserStoreByUserIdAndStoreId(Long userId, Long storeId);

    public void saveUserStore(UserStore us);

    public Long findUserId(String openId);

    public Set<Long> findStoreIdByUserId(Long userId);

    public ArrayList<Long> findUserIdByStoreId(Long storeId);

    public ArrayList<Long> findWaitersIdByStoreIdAndUserType(String storeId, Integer userType);

    public void saveUserInfo(User user);
}

