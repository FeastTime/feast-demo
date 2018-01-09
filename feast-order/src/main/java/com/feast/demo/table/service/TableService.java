package com.feast.demo.table.service;


import com.feast.demo.table.entity.DeskInfo;
import com.feast.demo.table.entity.DeskTemplate;

public interface TableService {
    public DeskInfo selectDeskByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId);

    public void updateRecieveTime(DeskInfo desk);

    public void updateIsCome(DeskInfo desk);

    public void setBusinessInfo(DeskTemplate desk);

    public DeskTemplate getBusinessInfo(long storeId, long userId);
}
