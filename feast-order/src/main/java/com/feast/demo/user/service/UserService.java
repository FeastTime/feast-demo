package com.feast.demo.user.service;

import com.feast.demo.user.entity.User;

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
}
