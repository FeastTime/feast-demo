package com.feast.demo.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.user.dao.UserDao;
import com.feast.demo.user.entity.User;
import com.feast.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

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
        return userDao.findByPage(pageRequest);
    }
}
