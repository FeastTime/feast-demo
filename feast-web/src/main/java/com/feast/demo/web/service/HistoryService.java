package com.feast.demo.web.service;

import com.feast.demo.history.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    @Autowired
    private com.feast.demo.history.service.HistoryService historyRemoteService;

    public void save(History history) {
        historyRemoteService.save(history);
    }
}
