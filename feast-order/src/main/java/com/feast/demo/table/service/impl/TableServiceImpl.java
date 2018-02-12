package com.feast.demo.table.service.impl;

import com.feast.demo.store.dao.StoreDao;
import com.feast.demo.table.dao.TableInfoDao;
import com.feast.demo.table.dao.TableTemplateDao;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.table.entity.TableInfoExpand;
import com.feast.demo.table.entity.TableTemplate;
import com.feast.demo.table.service.TableService;
import com.feast.demo.user.dao.UserDao;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service()
public class TableServiceImpl implements TableService{

    @Autowired
    private TableInfoDao tableInfoDao;

    @Autowired
    private TableTemplateDao tableTemplateDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private UserDao userDao;

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
        ArrayList<TableInfo> tableInfos = tableInfoDao.findByStoreId(storeId);
        for (TableInfo tableInfo : tableInfos) {
            if(tableInfo.getUserPhone()==null||tableInfo.getUserPhone().equals("")){
                Long userId = tableInfo.getUserId();
                if(userId!=null){
                    String userPhone = userDao.findMobileNoByUserId(userId);
                    tableInfo.setUserPhone(userPhone);
                }
            }
        }
        return tableInfos;
    }

    public ArrayList<TableInfoExpand> queryMyTableList(Long userId) {
        ArrayList<TableInfo> lists = tableInfoDao.findByUserId(userId);
        ArrayList<TableInfoExpand> tableInfoLists = Lists.newArrayList();
        for (TableInfo tableInfo : lists) {
            String storeName = storeDao.findStoreName(tableInfo.getStoreId());
            TableInfoExpand tableInfoExpand = new TableInfoExpand();
            tableInfoExpand.setStoreName(storeName);
            BeanUtils.copyProperties(tableInfo,tableInfoExpand);
            tableInfoLists.add(tableInfoExpand);
        }
        return tableInfoLists;
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
