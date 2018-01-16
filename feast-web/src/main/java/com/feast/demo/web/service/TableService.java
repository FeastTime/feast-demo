package com.feast.demo.web.service;

import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.table.entity.TableTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TableService {

    @Autowired
    private com.feast.demo.table.service.TableService tableRemoteService;

    public void updateTableInfo(TableInfo tableInfo) {
        tableRemoteService.updateTableInfo(tableInfo);
    }


    public void setBusinessInfo(TableTemplate tableTemplate) {
        tableRemoteService.setBusinessInfo(tableTemplate);
    }

    public TableTemplate getBusinessInfo(long storeId, long userId) {
        return tableRemoteService.getBusinessInfo(storeId,userId);
    }

    public TableInfo queryPayTableDetail(Long tableId) {
        return tableRemoteService.queryPayTableDetail(tableId);
    }

    public ArrayList<TableInfo> queryPayTableList(Long userId,Long storeId) {
        return tableRemoteService.queryPayTableList(userId,storeId);
    }

    public ArrayList<TableInfo> getHistoryTables(Long storeId) {
        return tableRemoteService.getHistoryTables(storeId);
    }

    public ArrayList<TableInfo> queryMyTableList(Long userId) {
        return tableRemoteService.queryMyTableList(userId);
    }

    public TableInfo findTableInfoByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId) {
        return tableRemoteService.findTableInfoByUserIdAndStoreIdAndTableId(userId,storeId,tableId);
    }
}
