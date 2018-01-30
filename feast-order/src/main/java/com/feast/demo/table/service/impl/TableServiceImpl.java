package com.feast.demo.table.service.impl;

import com.feast.demo.table.dao.TableInfoDao;
import com.feast.demo.table.dao.TableTemplateDao;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.table.entity.TableTemplate;
import com.feast.demo.table.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service()
public class TableServiceImpl implements TableService{

    @Autowired
    private TableInfoDao tableInfoDao;

    @Autowired
    private TableTemplateDao tableTemplateDao;

    public void setBusinessInfo(TableTemplate tableTemplate) {
        tableTemplateDao.save(tableTemplate);
    }

    public TableTemplate getBusinessInfo(long storeId, long userId) {
        return tableTemplateDao.selectDeskByStoreIdAndUserd(storeId,userId);
    }

    public TableInfo queryPayTableDetail(Long tableId) {

        return tableInfoDao.findOne(tableId);
    }

    public ArrayList<TableInfo> queryPayTableList(Long userId, Long storeId) {
        return tableInfoDao.findByUserIdAndStoreIdAndPassType(userId,storeId,2);
    }


    public ArrayList<TableInfo> getHistoryTables(Long storeId) {
        return tableInfoDao.findByStoreId(storeId);
    }

    public ArrayList<TableInfo> queryMyTableList(Long userId) {
        return tableInfoDao.findByUserId(userId);
    }

    public TableInfo findTableInfoByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId) {
        return tableInfoDao.findByUserIdAndStoreIdAndTableId(userId,storeId,tableId);
    }

    @Transactional(readOnly = false)
    public void updateTableInfo(TableInfo tableInfo) {
        tableInfoDao.save(tableInfo);
    }

    @Transactional(readOnly = false)
    public TableInfo saveTableInfo(TableInfo tableInfo) {
        return tableInfoDao.save(tableInfo);
    }
}
