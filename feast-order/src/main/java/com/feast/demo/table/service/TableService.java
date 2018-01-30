package com.feast.demo.table.service;


import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.table.entity.TableTemplate;

import java.util.ArrayList;

public interface TableService {

    public void setBusinessInfo(TableTemplate tableTemplate);

    public TableTemplate getBusinessInfo(long storeId, long userId);

    public TableInfo queryPayTableDetail(Long tableId);

    public ArrayList<TableInfo> queryPayTableList(Long userId, Long storeId);

    public ArrayList<TableInfo> getHistoryTables(Long storeId);

    public ArrayList<TableInfo> queryMyTableList(Long userId);

    public TableInfo findTableInfoByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId);

    public void updateTableInfo(TableInfo tableInfo);

    public TableInfo saveTableInfo(TableInfo tableInfo);
}
