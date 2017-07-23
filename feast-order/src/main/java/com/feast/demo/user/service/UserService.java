package com.feast.demo.user.service;

import com.feast.demo.user.entity.User;

/**
 * Created by ggke on 2017/7/23.
 */
public interface UserService {

    public User findByMobileNo(Long mobileNo);
}
