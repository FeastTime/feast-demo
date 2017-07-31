package com.feast.demo.user.dao;

import com.feast.demo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ggke on 2017/7/23.
 */
public interface UserDao  extends PagingAndSortingRepository<User,Long>,UserDaoCustom {

    @Query("select u from User u where u.mobileNo=?1")
    User findByMobileNo(Long mobileNo);

    @Query(countQuery = "select count(u) from User u",value = "select c from User u order by u.id")
    Page<User> findByPage(Pageable pageable);
}
