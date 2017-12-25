package com.feast.demo.history.service.impl;

import com.feast.demo.history.dao.HistoryDao;
import com.feast.demo.history.entity.History;
import com.feast.demo.history.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService{
    @Autowired
    private HistoryDao historyDao;
    public void save(History history) {
        historyDao.save(history);
    }
}
