package com.feast.demo.user.service;

import com.feast.demo.history.entity.History;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.entity.UserCoupon;

import java.util.List;

/**
 * Created by ggke on 2017/7/23.
 */
public interface UserService {

    public User findByMobileNo(Long mobileNo);

    public void create(User user);

    public void update(User user);

    public List<User> findByName(String name);

    public User findByMobileAndPwd(Long mobileNo, String pwd);

    public void saveWeChatUserInfo(User user);

    public User checkWeChatUserBindStatus(User user);

    public void saveUserPhone(User user);

    public User findById(Long userId);

    public User findByNameAndPwd(String name, String pwd);

    public History selectHistoryByUserIdAndStoreId(Long userId, Long storeId);

    public List<Store> selectVisitStore(Long userId);

    public void saveHistory(History history);

    public List<User> selectVisitUser(Long storeId);

    public UserCoupon selectCouponByUserIdAndStoreIdAndCouponCode(UserCoupon userCoupon);

    public void updateUserCoupon(UserCoupon userCoupon);
}
