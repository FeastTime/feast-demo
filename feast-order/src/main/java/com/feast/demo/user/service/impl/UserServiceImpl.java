package com.feast.demo.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.user.dao.UserDao;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ggke on 2017/7/23.
 */
@Service()
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    public User findByMobileNo(Long mobileNo) {
        if(mobileNo == null){
            return null;
        }
        return userDao.findByMobileNo(mobileNo);
    }
}
