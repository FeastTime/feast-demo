package com.feast.demo.user.service.impl;

import com.feast.demo.history.dao.HistoryDao;
import com.feast.demo.history.entity.History;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.dao.UserCouponDao;
import com.feast.demo.user.dao.UserDao;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.entity.UserCoupon;
import com.feast.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ggke on 2017/7/23.
 */
@Service()
public class UserServiceImpl implements UserService{


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserCouponDao userCouponDao;

    @Autowired
    private HistoryDao historyDao;

    public User findByMobileNo(Long mobileNo) {
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


    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public Page<User> findByPage(int page,int size){
        PageRequest pageRequest = new PageRequest(page,size);
        return userDao.findByPage2(pageRequest);
    }

    /**
     * 根据名称查询用户
     * @param name
     * @return
     */
    public List<User> findByName(String name){
        return userDao.findByName(name);
    }

    public User findByMobileAndPwd(Long mobileNo, String pwd) {
        return userDao.findByMobileAndPwd(mobileNo,pwd);
    }

    @Transactional(readOnly = false)
    public void saveWeChatUserInfo(User user) {
        userDao.save(user);
    }

    public User checkWeChatUserBindStatus(User user) {
        //String openId,String imei,String androidId,String ipv4,String mac
        return userDao.checkWeChatUserBindStatus(user.getOpenId(),user.getImei(),user.getAndroidId(),user.getIpv4(),user.getMac());
    }

    @Transactional(readOnly = false)
    public void saveUserPhone(User user) {
        User user_ = userDao.findUserByImeiAndAndroidIdAndIpv4AndMacAndOpenIdAndUserId(user.getImei(),user.getAndroidId(),user.getIpv4(),user.getMac(),user.getOpenId(),user.getId());
        if(user_!=null){
            user_.setMobileNo(user.getMobileNo());
            userDao.save(user_);
        }
    }

    public User findById(Long userId) {
        return userDao.findOne(userId);
    }

    public User findByNameAndPwd(String name, String pwd) {
        return userDao.findByNameAndPwd(name,pwd);
    }

    public History selectHistoryByUserIdAndStoreId(Long userId, Long storeId) {
        return historyDao.selectHistoryByUserIdAndStoreId(userId,storeId);
    }

    public List<Store> selectVisitStore(Long userId) {

        List<Store> stores = historyDao.selectVisitStore(userId);
        return stores;
    }

    public void saveHistory(History history) {
        historyDao.save(history);
    }

    public List<User> selectVisitUser(Long storeId) {
        return historyDao.selectVisitUser(storeId);
    }

    public UserCoupon selectCouponByUserIdAndStoreIdAndCouponCode(UserCoupon userCoupon) {
        return userDao.selectCouponByUserIdAndStoreIdAndCouponCode(userCoupon.getUserId(),userCoupon.getStoreId(),userCoupon.getCode());
    }

    public void updateUserCoupon(UserCoupon userCoupon) {
        userCouponDao.save(userCoupon);
    }


}
