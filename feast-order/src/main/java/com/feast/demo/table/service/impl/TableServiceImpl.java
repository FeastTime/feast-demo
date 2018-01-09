package com.feast.demo.table.service.impl;

import com.feast.demo.table.dao.DeskInfoDao;
import com.feast.demo.table.dao.DeskTemplateDao;
import com.feast.demo.table.entity.DeskInfo;
import com.feast.demo.table.entity.DeskTemplate;
import com.feast.demo.table.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service()
public class TableServiceImpl implements TableService{

    @Autowired
    private DeskInfoDao deskInfoDao;

    @Autowired
    private DeskTemplateDao deskTemplateDao;

    public DeskInfo selectDeskByUserIdAndStoreIdAndTableId(Long userId, Long storeId, Long tableId) {
        return deskInfoDao.selectDeskByUserIdAndStoreIdAndTableId(userId,storeId,tableId);
    }

    @Transactional(readOnly = false)
    public void updateRecieveTime(DeskInfo desk) {
        deskInfoDao.save(desk);
    }

    @Transactional(readOnly = false)
    public void updateIsCome(DeskInfo desk) {
        deskInfoDao.save(desk);
    }

    public void setBusinessInfo(DeskTemplate desk) {
        deskTemplateDao.save(desk);
    }

    public DeskTemplate getBusinessInfo(long storeId, long userId) {
        return deskTemplateDao.selectDeskByStoreIdAndUserd(storeId,userId);
    }

    public DeskInfo queryPayTableDetail(Long deskId) {

        return deskInfoDao.findOne(deskId);
    }
}
