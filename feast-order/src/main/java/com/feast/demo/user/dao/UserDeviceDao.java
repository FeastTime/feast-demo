package com.feast.demo.user.dao;

import com.feast.demo.user.entity.UserDevice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDeviceDao extends PagingAndSortingRepository<UserDevice,Long>{

    UserDevice findByUserIdAndDeviceId(Long userId, String deviceId);


    @Modifying
    @Query("update UserDevice ud set ud.deviceId = ?1 where ud.userId = ?2")
    void updateUserDeviceByUserId(String deviceId,Long userId);

    UserDevice findByUserId(Long userId);
}
