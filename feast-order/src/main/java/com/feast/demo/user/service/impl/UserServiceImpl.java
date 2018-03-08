package com.feast.demo.user.service.impl;

import com.feast.demo.history.dao.UserStoreDao;
import com.feast.demo.history.entity.UserStore;
import com.feast.demo.store.dao.StoreDao;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.dao.UserDao;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ggke on 2017/7/23.
 */
@Service()
public class UserServiceImpl implements UserService{


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserStoreDao userStoreDao;

    @Autowired
    private StoreDao storeDao;

    public User findByMobileNo(String mobileNo) {
        if(mobileNo == null){
            return null;
        }
        return userDao.findByMobileNo(mobileNo);
    }

    /**
     * 创建用户
     * @param user
     */
    @Transactional(readOnly = false)
    public void create(User user) {
        userDao.save(user);
    }

    /**
     * 修改用户
     * @param user
     */
    @Transactional(readOnly = false)
    public void update(User user) {
        userDao.save(user);
    }



    public User findByMobileAndPwd(String mobileNo, String pwd) {
        return userDao.findByMobileAndPwd(mobileNo,pwd);
    }

    @Transactional(readOnly = false)
    public User saveWeChatUserInfo(User user) {
        return userDao.save(user);
    }

    public String checkWeChatUserBindStatus(Long userId) {
        return userDao.findOpenIdByUserId(userId);
    }

    @Transactional(readOnly = false)
    public void saveUserPhone(Long userId,String mobileNo) {
        User user = userDao.findOne(userId);
        if(user!=null){
            user.setMobileNo(mobileNo);
            userDao.save(user);
        }
    }

    public User findById(Long userId) {
        return userDao.findOne(userId);
    }

    public User queryUserInfo(Long userId) {
        return userDao.findOne(userId);
    }

    public void setRelationshipWithStore(Long userId, Long storeId, Integer status) {
        UserStore us = userStoreDao.findByUserIdAndStoreId(userId,storeId);
        if(us!=null){
            us.setStatus(status);
            userStoreDao.save(us);
        }
    }

    public User storeLogin(String username, String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }

    public ArrayList<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public ArrayList<Store> queryHadEatenStore(Long userId, Integer order) {
        ArrayList<Store> userStoreList = null;
        if(order==1){
            userStoreList = userStoreDao.findByUserIdOrderByCount(userId);
        }else if(order==2){
            userStoreList = userStoreDao.findByUserIdOrderByCountDesc(userId);
        }else{
            userStoreList = userStoreDao.findByUserIdOrderByLastModifiedDesc(userId);
        }
        return userStoreList;
    }

    public UserStore findUserStoreByUserIdAndStoreId(Long userId, Long storeId) {
        return userStoreDao.findByUserIdAndStoreId(userId,storeId);
    }

    @Transactional(readOnly = false)
    public void saveUserStore(UserStore us) {
        userStoreDao.save(us);
    }

    public Long findUserId(String openId) {
        return userDao.findUserId(openId);
    }

    public Set<Long> findStoreIdByUserId(Long userId) {
        return userStoreDao.findStoreIdByUserId(userId);
    }

    public ArrayList<Long> findUserIdByStoreId(Long storeId) {
        return userDao.findUserIdByStoreId(storeId);
    }

    public ArrayList<Long> findWaitersIdByStoreIdAndUserType(String storeId, Integer userType) {
        return userDao.findWaitersIdByStoreIdAndUserType(Long.parseLong(storeId),userType);
    }

    @Transactional(readOnly = false)
    public void saveUserInfo(User user) {
        userDao.save(user);
    }

    public User findByOpenId(String openId) {
        return userDao.findByOpenId(openId);
    }

    @Transactional(readOnly = false)
    public void updateUserInfo(String deviceId, String mobileNo, String nickName, String userIcon, String openId) {
        userDao.updateUserInfo(deviceId,mobileNo,nickName,userIcon,openId);
    }

    public User checkWeChatUserBindStatus(String openId) {
        return userDao.findByOpenId(openId);
    }

}
