package com.feast.demo.web.service;

import com.feast.demo.table.entity.DeskInfo;
import com.feast.demo.table.entity.DeskTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    @Autowired
    private com.feast.demo.table.service.TableService tableRemoteService;

    public DeskInfo selectDeskByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId) {
        return tableRemoteService.selectDeskByUserIdAndStoreIdAndTableId(userId,storeId,tableId);
    }

    public void updateRecieveTime(DeskInfo desk) {
        tableRemoteService.updateRecieveTime(desk);
    }

    public void updateIsCome(DeskInfo desk) {
        tableRemoteService.updateIsCome(desk);
    }


    public void setBusinessInfo(DeskTemplate desk) {
        tableRemoteService.setBusinessInfo(desk);
    }

    public DeskTemplate getBusinessInfo(long storeId, long userId) {
        return tableRemoteService.getBusinessInfo(storeId,userId);
    }
}
